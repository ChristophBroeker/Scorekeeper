package com.github.scorekeeper.rest.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scorekeeper.rest.vo.ScoreBoardEntryVO;
import com.github.scorekeeper.service.GameService;

@Controller
@RequestMapping("/scoreboard")
public class ScoreBoardController {

	@Resource
	private GameService gameService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public List<ScoreBoardEntryVO> getScoreBoard() {
		return gameService.getScoreBoard();
	}

}
