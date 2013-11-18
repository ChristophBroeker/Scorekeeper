/*
Creation date: 09.07.2013
Created by andre.marbeck
Project: scorekeeper

Copyright diron 2013
*/

package com.github.scorekeeper.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.scorekeeper.persistence.dao.PlayerRepository;
import com.github.scorekeeper.persistence.dao.SuggestedGameRepository;
import com.github.scorekeeper.persistence.entity.Player;
import com.github.scorekeeper.persistence.entity.SuggestedGame;
import com.github.scorekeeper.rest.vo.SuggestedGameVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Service
public class SuggestedGameService {

	@Resource
	private SuggestedGameRepository suggestedGameRepository;

	@Resource
	private PlayerRepository playerRepository;

	@Transactional
	public List<SuggestedGameVO> getAllSuggestedGames() {
		return convert(Lists.newArrayList(suggestedGameRepository.findAll()));
	}

	@Transactional
	public List<SuggestedGameVO> createNewSuggestions() {
		List<SuggestedGame> newGames = new ArrayList<SuggestedGame>();
		List<SuggestedGame> oldGames = new ArrayList<SuggestedGame>();
		List<Player> players = Lists.newArrayList(playerRepository.findAll());
		suggestedGameRepository.deleteAll();
		// find current suggestions
		// Iterable<SuggestedGame> currentGames = suggestedGameRepository.findAll();
		// for (SuggestedGame suggestedGame : currentGames) {
		// if (suggestedGame.getPlayedDate() == null) {
		// // not played yet
		// oldGames.add(suggestedGame);
		// players.remove(suggestedGame.getTeamA().get(0));
		// players.remove(suggestedGame.getTeamA().get(1));
		// players.remove(suggestedGame.getTeamB().get(0));
		// players.remove(suggestedGame.getTeamB().get(1));
		// } else {
		// // played and ready to remove
		// suggestedGameRepository.delete(suggestedGame);
		// }
		// }

		createSuggestedGame(newGames, players);
		newGames.addAll(oldGames);
		return convert(newGames);
	}

	@Transactional
	private void createSuggestedGame(List<SuggestedGame> targetList, List<Player> playerList) {

		if (playerList.size() >= 4) {
			System.out.println("create new suggested Game...");
			Collections.shuffle(playerList);
			SuggestedGame newGame = new SuggestedGame();

			List<Player> teamA = new ArrayList<Player>();
			teamA.add(playerList.get(0));
			teamA.add(playerList.get(1));
			newGame.setTeamA(teamA);

			List<Player> teamB = new ArrayList<Player>();
			teamB.add(playerList.get(2));
			teamB.add(playerList.get(3));
			newGame.setTeamB(teamB);

			List<Player> newList = new ArrayList<Player>();
			newList.addAll(teamA);
			newList.addAll(teamB);
			playerList.removeAll(newList);

			targetList.add(newGame);
			suggestedGameRepository.save(newGame);
			createSuggestedGame(targetList, playerList);

		}
	}

	private List<SuggestedGameVO> convert(List<SuggestedGame> gameEntities) {
		return Lists.newArrayList(Lists.transform(gameEntities, new Function<SuggestedGame, SuggestedGameVO>() {
			@Override
			public SuggestedGameVO apply(SuggestedGame gameEntity) {
				return new SuggestedGameVO(gameEntity);
			}
		}));
	}
}
