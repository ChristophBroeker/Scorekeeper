package com.github.scorekeeper.rest;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.scorekeeper.persistence.dao.PlayerRepository;
import com.github.scorekeeper.persistence.entity.Player;

/*
Creation date: 04.06.2013
Created by andre.marbeck
Project: scorekeeper

Copyright diron 2013
*/

@Service
public class PlayerServiceImpl implements PlayerService {

	@Resource
	public PlayerRepository repo;

	/* (non-Javadoc)
	 * @see com.github.scorekeeper.rest.PlayerService#findById(java.lang.Long)
	 */
	@Override
	@Transactional
	public Player findById(Long id) {
		return repo.findOne(id);

	}

	@Override
	@Transactional
	public Iterable<Player> findAllPlayers() {

		Iterable<Player> players = repo.findAll();
		return players;
	}

}
