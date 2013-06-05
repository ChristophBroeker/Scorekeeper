/*
Creation date: 04.06.2013
Created by andre.marbeck
Project: scorekeeper

Copyright diron 2013
*/

package com.github.scorekeeper.rest;

import org.springframework.transaction.annotation.Transactional;

import com.github.scorekeeper.persistence.entity.Player;

public interface PlayerService {

	@Transactional
	public abstract Player findById(Long id);

}