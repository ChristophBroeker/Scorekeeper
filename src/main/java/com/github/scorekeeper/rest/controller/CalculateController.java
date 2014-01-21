package com.github.scorekeeper.rest.controller;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scorekeeper.rest.vo.CalculatedGameVO;
import com.github.scorekeeper.service.GameService;

@Controller
@RequestMapping("/calculate")
public class CalculateController {

	@Resource
	private GameService gameService;

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public CalculatedGameVO calculateGame(@RequestParam("teamAPlayer1") int teamAPlayer1,
			@RequestParam("teamAPlayer2") int teamAPlayer2, @RequestParam("teamBPlayer1") int teamBPlayer1,
			@RequestParam("teamBPlayer2") int teamBPlayer2) {
		CalculatedGameVO vo = gameService.calculateGame(teamAPlayer1, teamAPlayer2, teamBPlayer1, teamBPlayer2);
		return vo;
	}

}
