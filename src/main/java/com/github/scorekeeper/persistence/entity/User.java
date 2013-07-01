package com.github.scorekeeper.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private Boolean changedPassword;

	@ManyToMany
	@CollectionTable(name = "User_Security_Role")
	private List<SecurityRole> userRoles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<SecurityRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<SecurityRole> userRoles) {
		this.userRoles = userRoles;
	}

	public void addUserRole(SecurityRole role) {
		if (userRoles == null) {
			userRoles = new ArrayList<SecurityRole>();
		}
		userRoles.add(role);
	}

	public Boolean hasChangedPassword() {
		return changedPassword;
	}

	public void hasChangedPassword(Boolean changedPassword) {
		this.changedPassword = changedPassword;
	}

}
