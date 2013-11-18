package com.github.scorekeeper.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import jskills.GameInfo;
import jskills.IPlayer;
import jskills.ITeam;
import jskills.Rating;
import jskills.TrueSkillCalculator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.scorekeeper.persistence.dao.GameRepository;
import com.github.scorekeeper.persistence.dao.PlayerRepository;
import com.github.scorekeeper.persistence.dao.SuggestedGameRepository;
import com.github.scorekeeper.persistence.entity.Game;
import com.github.scorekeeper.persistence.entity.Player;
import com.github.scorekeeper.persistence.entity.ResultType;
import com.github.scorekeeper.persistence.entity.Score;
import com.github.scorekeeper.persistence.entity.SuggestedGame;
import com.github.scorekeeper.rest.vo.GameVO;
import com.github.scorekeeper.rest.vo.ScoreBoardEntryVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Service
public class GameService {

	@Resource
	private GameRepository gameRepository;

	@Resource
	private SuggestedGameRepository suggestedGameRepository;

	@Resource
	private PlayerRepository playerRepository;

	@Transactional
	public Long addGame(GameVO gameVO) {
		Game gameEntity = new Game();

		gameEntity.setPlayedDate(gameVO.getPlayedDate());
		gameEntity.setTeamAScore(gameVO.getTeamAScore());
		gameEntity.setTeamBScore(gameVO.getTeamBScore());
		gameEntity.setTeamA(findPlayers(gameVO.getTeamA()));
		gameEntity.setTeamB(findPlayers(gameVO.getTeamB()));

		ResultType gameResult = getGameResult(gameVO);
		gameEntity.setResult(gameResult);

		GameInfo gameInfo = GameInfo.getDefaultGameInfo();
		Collection<ITeam> teams = makeTeamAdapters(gameEntity);

		double matchQuality = TrueSkillCalculator.calculateMatchQuality(gameInfo, teams);
		gameEntity.setQuality(new BigDecimal(matchQuality));

		updatePlayerScores(gameEntity, gameInfo);
		checkForSuggestedGame(gameEntity);
		return gameRepository.save(gameEntity).getId();
	}

	@Transactional
	private void checkForSuggestedGame(Game gameEntity) {
		Iterable<SuggestedGame> suggestedGames = suggestedGameRepository.findAll();
		if (gameEntity.getTeamA().size() == 2 && gameEntity.getTeamB().size() == 2) {
			for (SuggestedGame suggestedGame : suggestedGames) {
				if (hasTeamPlayed(gameEntity, suggestedGame.getTeamA())
						&& hasTeamPlayed(gameEntity, suggestedGame.getTeamB())) {

					suggestedGame.setPlayedDate(gameEntity.getPlayedDate());
					suggestedGame.setQuality(gameEntity.getQuality());
					suggestedGame.setResult(gameEntity.getResult());

					Long suggUserTA = suggestedGame.getTeamA().get(0).getId();
					List<Long> teamAIds = new ArrayList<Long>();
					teamAIds.add(gameEntity.getTeamA().get(0).getId());
					teamAIds.add(gameEntity.getTeamA().get(1).getId());
					if (teamAIds.contains(suggUserTA)) {
						suggestedGame.setTeamAScore(gameEntity.getTeamAScore());
						suggestedGame.setTeamBScore(gameEntity.getTeamBScore());
					} else {
						suggestedGame.setTeamAScore(gameEntity.getTeamBScore());
						suggestedGame.setTeamBScore(gameEntity.getTeamAScore());
					}

					suggestedGameRepository.save(suggestedGame);
				}

			}
		}

	}

	@Transactional
	private boolean hasTeamPlayed(Game game, List<Player> team) {
		boolean result;
		List<Long> teamIds = new ArrayList<Long>();
		teamIds.add(team.get(0).getId());
		teamIds.add(team.get(1).getId());

		List<Long> teamAIds = new ArrayList<Long>();
		teamAIds.add(game.getTeamA().get(0).getId());
		teamAIds.add(game.getTeamA().get(1).getId());

		List<Long> teamBIds = new ArrayList<Long>();
		teamBIds.add(game.getTeamB().get(0).getId());
		teamBIds.add(game.getTeamB().get(1).getId());

		result = teamAIds.containsAll(teamIds);
		result = result || teamBIds.containsAll(teamIds);
		return result;
	}

	private void updatePlayerScores(Game gameEntity, GameInfo gameInfo) {
		Collection<ITeam> teams = makeTeamAdapters(gameEntity);

		ResultType gameResult = gameEntity.getResult();

		Map<IPlayer, Rating> newRatings = TrueSkillCalculator.calculateNewRatings(gameInfo, teams,
				gameResult == ResultType.WIN_B ? 2 : 1, gameResult == ResultType.WIN_A ? 2 : 1);

		for (Entry<IPlayer, Rating> playerRating : newRatings.entrySet()) {
			Player player = ((JSkillPlayerAdapter) playerRating.getKey()).player;
			Rating rating = playerRating.getValue();

			Score score = new Score();
			score.setCaptured((Calendar) gameEntity.getPlayedDate().clone());
			score.setMean(new BigDecimal(rating.getMean()));
			score.setStandardDeviation(new BigDecimal(rating.getStandardDeviation()));

			player.addScore(score);
		}
	}

	private Collection<ITeam> makeTeamAdapters(Game gameEntity) {
		ITeam teamA = new JSkillTeamAdapter(gameEntity.getTeamA());
		ITeam teamB = new JSkillTeamAdapter(gameEntity.getTeamB());
		Collection<ITeam> teams = Lists.newArrayList(teamA, teamB);
		return teams;
	}

	private ResultType getGameResult(GameVO gameVO) {
		if (gameVO.getTeamAScore() > gameVO.getTeamBScore()) {
			return ResultType.WIN_A;
		} else if (gameVO.getTeamAScore() < gameVO.getTeamBScore()) {
			return ResultType.WIN_B;
		} else {
			return ResultType.DRAW;
		}
	}

	@Transactional
	public List<GameVO> listGames(int pageSize, int page) {
		Page<Game> pageOfGames = gameRepository.findAll(new PageRequest(page, pageSize, new Sort(new Sort.Order(
				Direction.DESC, "playedDate"))));

		return convert(pageOfGames.getContent());
	}

	private List<Player> findPlayers(List<Long> playerIds) {
		return Lists.newArrayList(Lists.transform(playerIds, new Function<Long, Player>() {
			@Override
			public Player apply(Long playerId) {
				Player playerEntity = playerRepository.findOne(playerId);
				if (playerEntity == null) {
					throw new RuntimeException("No player exists with id " + playerId);
				}

				return playerEntity;
			}
		}));
	}

	private List<GameVO> convert(List<Game> gameEntities) {
		return Lists.newArrayList(Lists.transform(gameEntities, new Function<Game, GameVO>() {
			@Override
			public GameVO apply(Game gameEntity) {
				return new GameVO(gameEntity);
			}
		}));
	}

	private static final class JSkillPlayerAdapter implements IPlayer {
		public final Player player;

		public JSkillPlayerAdapter(Player player) {
			this.player = player;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((player == null) ? 0 : player.getId().intValue());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			JSkillPlayerAdapter other = (JSkillPlayerAdapter) obj;
			if (player == null) {
				if (other.player != null)
					return false;
			} else if (!player.getId().equals(other.player.getId()))
				return false;
			return true;
		}

	}

	private static class JSkillTeamAdapter extends HashMap<IPlayer, Rating> implements ITeam {
		private static final long serialVersionUID = 6725611835550185748L;

		public JSkillTeamAdapter(List<Player> players) {
			for (Player player : players) {
				Score latestScore = player.getLatestScore();

				Rating rating;
				if (latestScore == null) {
					rating = new Rating(PlayerService.DEFAULT_INITIAL_MEAN.doubleValue(),
							PlayerService.DEFAULT_INITIAL_STANDARD_DEVIATION.doubleValue());
				} else {
					rating = new Rating(latestScore.getMean().doubleValue(),
							player.getLatestScore().getStandardDeviation().doubleValue());
				}
				put(new JSkillPlayerAdapter(player), rating);
			}
		}
	}

	@Transactional
	public List<ScoreBoardEntryVO> getScoreBoard() {
		List<Object[]> sc = gameRepository.getScoreBoard();
		List<ScoreBoardEntryVO> result = new ArrayList<ScoreBoardEntryVO>();
		for (Object[] sce : sc) {
			ScoreBoardEntryVO sbe = new ScoreBoardEntryVO(sce);
			// if(sbe.getLastGameDate() )
			result.add(sbe);
		}
		return result;
	}

	@Transactional
	public List<ScoreBoardEntryVO> getNotRankedBoard() {
		List<Object[]> sc = gameRepository.getNotRankedBoard();
		List<ScoreBoardEntryVO> result = new ArrayList<ScoreBoardEntryVO>();
		for (Object[] sce : sc) {
			result.add(new ScoreBoardEntryVO(sce));
		}
		return result;
	}

	@Transactional
	public List<ScoreBoardEntryVO> getInactiveBoard() {
		List<Object[]> sc = gameRepository.getInactiveBoard();
		List<ScoreBoardEntryVO> result = new ArrayList<ScoreBoardEntryVO>();
		for (Object[] sce : sc) {
			result.add(new ScoreBoardEntryVO(sce));
		}
		return result;
	}
}
