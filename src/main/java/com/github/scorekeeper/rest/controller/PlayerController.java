package com.github.scorekeeper.rest.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scorekeeper.rest.vo.PlayerVO;
import com.github.scorekeeper.service.PlayerService;

@Controller
@RequestMapping("/players")
public class PlayerController {

	@Resource
	private PlayerService playerService;

	@RequestMapping(value = "", method = { RequestMethod.GET })
	@ResponseBody
	public List<PlayerVO> findAllPlayers() {
		return playerService.findAllPlayers();
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	@ResponseBody
	public PlayerVO findPlayerById(@PathVariable("id") Long id) {
		return playerService.findPlayerById(id);
	}

	@RequestMapping(value = "/{name}", method = RequestMethod.POST)
	@ResponseBody
	public Long addPlayer(@PathVariable("name") String name) {
		return playerService.addPlayer(name);
	}
}
