/*
Creation date: 04.06.2013
Created by andre.marbeck
Project: scorekeeper

Copyright diron 2013
*/

package com.github.scorekeeper.rest;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.scorekeeper.persistence.dao.GameRepository;
import com.github.scorekeeper.persistence.dao.PlayerRepository;
import com.github.scorekeeper.persistence.entity.Player;

@Controller
public class ScorekeeperService {

	@Resource
	public PlayerRepository playerRepository;

	@Resource
	public GameRepository gameRepository;

	@Resource
	private PlayerService service;

	@RequestMapping(value = "players", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Iterable<Player> getAllPlayers() {

		return service.findAllPlayers();

	}
}
