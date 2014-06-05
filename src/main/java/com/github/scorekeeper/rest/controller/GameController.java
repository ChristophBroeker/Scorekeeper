package com.github.scorekeeper.rest.controller;

import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scorekeeper.rest.vo.GameVO;
import com.github.scorekeeper.rest.vo.SuggestedGameVO;
import com.github.scorekeeper.service.GameService;
import com.github.scorekeeper.service.SuggestedGameService;

@Controller
@RequestMapping("/games")
public class GameController {

	@Resource
	private GameService gameService;

	@Resource
	private SuggestedGameService suggestedGameService;

	@PreAuthorize("hasRole('SCOREADMIN')")
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public Long addGame(@RequestBody GameVO game) {
		game.setPlayedDate(new GregorianCalendar());
		return gameService.addGame(game);
	}

	@PreAuthorize("permitAll")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public List<GameVO> findAllGames(@RequestParam("pageSize") int pageSize, @RequestParam("page") int page) {
		return gameService.listGames(pageSize, page);
	}

	@PreAuthorize("permitAll")
	@RequestMapping(value = "/suggested", method = RequestMethod.GET)
	@ResponseBody
	public List<SuggestedGameVO> findAllSuggestedGames() {
		return suggestedGameService.getAllSuggestedGames();
	}

	@PreAuthorize("hasRole('SCOREADMIN')")
	@RequestMapping(value = "/suggested", method = RequestMethod.POST)
	@ResponseBody
	public List<SuggestedGameVO> createSuggestedGames() {

		return suggestedGameService.createNewSuggestions();
	}

	@PreAuthorize("hasRole('SCOREADMIN')")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public void deleteLastGame(@RequestBody GameVO game) {

		gameService.deleteGame(game);
	}

}
