package com.github.scorekeeper.persistence.dao;

import java.util.Calendar;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.github.scorekeeper.persistence.AbstractScorekeeperPersistenceTest;
import com.github.scorekeeper.persistence.entity.Game;
import com.github.scorekeeper.persistence.entity.Player;
import com.github.scorekeeper.persistence.entity.ResultType;
import com.google.common.collect.Lists;

public class GameRepositoryTest extends AbstractScorekeeperPersistenceTest {

	@Resource
	private PlayerRepository playerRepository;

	@Resource
	private GameRepository gameRepository;

	@Test
	public void createGame() {
		Game g = new Game();

		g.setPlayedDate(Calendar.getInstance());
		g.setResult(ResultType.WIN_A);

		g.setTeamA(Lists.newArrayList(createPlayer("A1"), createPlayer("A2")));
		g.setTeamB(Lists.newArrayList(createPlayer("B1"), createPlayer("B2")));

		g.setTeamAScore(10);
		g.setTeamBScore(2);

		g = gameRepository.save(g);

		Assert.assertNotNull(g.getId());
	}

	private Player createPlayer(String name) {
		Player p = new Player();
		p.setName(name);
		p = playerRepository.save(p);

		return p;
	}

}
