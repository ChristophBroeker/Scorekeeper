package com.github.scorekeeper.persistence.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.scorekeeper.persistence.entity.Game;

public interface GameRepository extends PagingAndSortingRepository<Game, Long> {

}
