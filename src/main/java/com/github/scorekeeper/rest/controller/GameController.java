package com.github.scorekeeper.rest.controller;

import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scorekeeper.rest.vo.GameVO;
import com.github.scorekeeper.service.GameService;

@Controller
@RequestMapping("/games")
public class GameController {

	@Resource
	private GameService gameService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public Long addGame(@RequestBody GameVO game) {
		game.setPlayedDate(new GregorianCalendar());
		return gameService.addGame(game);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public List<GameVO> findAllGames(@RequestParam("pageSize") int pageSize, @RequestParam("page") int page) {
		return gameService.listGames(pageSize, page);
	}

}
