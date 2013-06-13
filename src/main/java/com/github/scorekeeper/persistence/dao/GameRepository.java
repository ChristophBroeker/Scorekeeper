package com.github.scorekeeper.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.scorekeeper.persistence.entity.Game;

public interface GameRepository extends PagingAndSortingRepository<Game, Long> {

	@Query(value = "select pl.name as playerName, sc.MEAN as currentMean, (select count(*) from PUBLIC.GAME_TEAMA where GAME_TEAMA.TEAMA_ID = pl.ID) + (select count(*) from PUBLIC.GAME_TEAMB where GAME_TEAMB.TEAMB_ID = pl.ID) as playedGames, (select count(*) from PUBLIC.GAME_TEAMA gta join PUBLIC.GAME ga on ga.ID=gta.GAME_ID where gta.TEAMA_ID = pl.ID and ga.RESULT='WIN_A') + (select count(*) from PUBLIC.GAME_TEAMB gtb join PUBLIC.GAME gb on gb.ID=gtb.GAME_ID where gtb.TEAMB_ID = pl.ID and gb.RESULT='WIN_B') as wonGames from PUBLIC.PLAYER pl join PUBLIC.PLAYER_SCORE plsc on plsc.PLAYER_ID = pl.ID join PUBLIC.SCORE sc on sc.ID = plsc.SCOREHISTORY_ID where (select max(SCOREHISTORY_ORDER) from PUBLIC.PLAYER_SCORE where PLAYER_ID = pl.ID) = plsc.SCOREHISTORY_ORDER group by pl.NAME order by sc.MEAN desc", nativeQuery = true)
	List<Object[]> getScoreBoard();

}
