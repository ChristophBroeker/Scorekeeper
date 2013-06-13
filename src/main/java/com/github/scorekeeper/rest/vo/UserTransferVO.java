/*
Creation date: 13.06.2013
Created by andre.marbeck
Project: scorekeeper

Copyright diron 2013
*/

package com.github.scorekeeper.rest.vo;

import java.util.Map;

public class UserTransferVO {

	private final String name;

	private final Map<String, Boolean> roles;

	public UserTransferVO(String userName, Map<String, Boolean> roles) {

		this.name = userName;
		this.roles = roles;
	}

	public String getName() {

		return this.name;
	}

	public Map<String, Boolean> getRoles() {

		return this.roles;
	}

}