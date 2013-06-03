package com.github.scorekeeper.persistence.dao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.github.scorekeeper.persistence.AbstractScorekeeperPersistenceTest;
import com.github.scorekeeper.persistence.entity.Player;
import com.github.scorekeeper.persistence.entity.Score;
import com.google.common.collect.Lists;

public class PlayerRepositoryTest extends AbstractScorekeeperPersistenceTest {

	@Resource
	private PlayerRepository playerRepository;

	@Test
	public void createPlayer() {
		Player player = new Player();
		player.setName("X. Ample");

		Score s = new Score();
		s.setCaptured(Calendar.getInstance());
		s.setMean(new BigDecimal("8.2"));
		s.setStandardDeviation(new BigDecimal("2.33"));
		player.setScoreHistory(Lists.newArrayList(s));

		player = playerRepository.save(player);

		Assert.assertNotNull(player.getId());

		List<Player> players = playerRepository.findByName("X. Ample");
		Assert.assertNotNull(players);
		Assert.assertFalse(players.isEmpty());
		Assert.assertEquals(players.get(0).getName(), "X. Ample");
		Assert.assertEquals(player.getScoreHistory().size(), 1);
	}

}
