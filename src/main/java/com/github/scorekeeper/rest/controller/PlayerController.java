package com.github.scorekeeper.rest.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scorekeeper.persistence.entity.Player;
import com.github.scorekeeper.rest.vo.PlayerVO;
import com.github.scorekeeper.service.PlayerService;

@Controller
@RequestMapping("/players")
public class PlayerController {

	@Resource
	private PlayerService playerService;

	@PreAuthorize("permitAll")
	@RequestMapping(value = "", method = { RequestMethod.GET })
	@ResponseBody
	public List<PlayerVO> findAllPlayers() {
		return playerService.findAllPlayers();
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	@ResponseBody
	public PlayerVO findPlayerById(@PathVariable("id") Long id) {
		return playerService.findPlayerById(id);
	}

	@PreAuthorize("hasRole('SCOREADMIN')")
	@RequestMapping(value = "/{name}", method = RequestMethod.POST)
	@ResponseBody
	public Player addPlayer(@PathVariable("name") String name) {
		return playerService.addPlayer(name);
	}

	@PreAuthorize("hasRole('SCOREADMIN')")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public void deletePlayer(@PathVariable("id") Long id) {
		playerService.deletePlayer(id);
	}

	// @PreAuthorize("permitAll")
	// @RequestMapping(value = "scoreboard", method = { RequestMethod.GET })
	// @ResponseBody
	// public List<PlayerVO> getScoreBoard() {
	// List<PlayerVO> players = playerService.findAllPlayers();
	// Collections.sort(players);
	// return players;
	// }

}
