package com.github.scorekeeper.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.scorekeeper.persistence.dao.PlayerRepository;
import com.github.scorekeeper.persistence.entity.Player;
import com.github.scorekeeper.rest.vo.PlayerVO;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Service
public class PlayerService {

	public static final BigDecimal DEFAULT_INITIAL_MEAN = new BigDecimal("25.0");
	public static final BigDecimal DEFAULT_INITIAL_STANDARD_DEVIATION = DEFAULT_INITIAL_MEAN.divide(new BigDecimal(
			"3.0"), 10, RoundingMode.HALF_EVEN);

	@Resource
	private PlayerRepository playerRepository;

	@Transactional
	public List<PlayerVO> findAllPlayers() {
		return convert(playerRepository.findAll());
	}

	@Transactional
	public PlayerVO findPlayerById(@PathVariable("id") Long id) {
		Player player = playerRepository.findOne(id);
		if (player == null) {
			return null;
		}

		return new PlayerVO(player);
	}

	@Transactional
	public Player addPlayer(@PathVariable("name") String name) {
		Player player = new Player();
		player.setName(name);

		player = playerRepository.save(player);
		return player;
	}

	@Transactional
	public void deletePlayer(@PathVariable("id") Long id) {

		Player p = playerRepository.findOne(id);
		if (p.getScoreHistory() == null || p.getScoreHistory().size() == 0) {
			playerRepository.delete(id);
		}

	}

	private static List<PlayerVO> convert(Iterable<Player> players) {
		return Lists.newArrayList(Iterables.transform(players, new Function<Player, PlayerVO>() {
			@Override
			public PlayerVO apply(Player player) {
				return new PlayerVO(player);
			}
		}));
	}

}
