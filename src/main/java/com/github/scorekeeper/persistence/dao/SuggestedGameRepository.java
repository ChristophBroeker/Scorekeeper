package com.github.scorekeeper.persistence.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.scorekeeper.persistence.entity.SuggestedGame;

public interface SuggestedGameRepository extends PagingAndSortingRepository<SuggestedGame, Long> {

}
