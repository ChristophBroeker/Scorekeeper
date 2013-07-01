/*
Creation date: 13.06.2013
Created by andre.marbeck
Project: scorekeeper

Copyright diron 2013
*/

package com.github.scorekeeper.rest.vo;

import java.util.HashMap;
import java.util.Map;

import com.github.scorekeeper.persistence.entity.SecurityRole;
import com.github.scorekeeper.persistence.entity.User;

public class UserTransferVO {

	private Long id;

	private String name;

	private Boolean changedPassword;

	private Map<String, Boolean> roles;

	public UserTransferVO(User user) {
		this.id = user.getId();
		this.name = user.getName();

		this.roles = new HashMap<String, Boolean>();
		for (String role : SecurityRole.ROLES) {
			roles.put(role, false);
		}

		for (SecurityRole userRole : user.getUserRoles()) {
			roles.put(userRole.getName(), true);
		}

	}

	public UserTransferVO(Long id, String userName, Map<String, Boolean> roles, Boolean hasChangedPassword) {
		this.id = id;
		this.name = userName;
		this.roles = roles;
		this.setChangedPassword(hasChangedPassword);
	}

	public String getName() {

		return this.name;
	}

	public Map<String, Boolean> getRoles() {

		return this.roles;
	}

	public Long getId() {
		return id;
	}

	public Boolean getChangedPassword() {
		return changedPassword;
	}

	public void setChangedPassword(Boolean changedPassword) {
		this.changedPassword = changedPassword;
	}

}