package com.github.scorekeeper.persistence.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.github.scorekeeper.persistence.entity.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {

	List<Player> findByName(String name);

}
