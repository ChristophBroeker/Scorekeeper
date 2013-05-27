package com.github.scorekeeper.persistence.dao;

import org.springframework.data.repository.CrudRepository;

import com.github.scorekeeper.persistence.entity.Game;

public interface GameRepository extends CrudRepository<Game, Long> {

}
