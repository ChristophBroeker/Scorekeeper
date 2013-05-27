package com.github.scorekeeper.persistence.dao;

import org.springframework.data.repository.CrudRepository;

import com.github.scorekeeper.persistence.entity.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {

	Player findByName(String name);

}
