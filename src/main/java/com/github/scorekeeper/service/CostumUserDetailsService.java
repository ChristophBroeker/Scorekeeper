/*
Creation date: 25.06.2013
Created by andre.marbeck
Project: scorekeeper

Copyright diron 2013
*/

package com.github.scorekeeper.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.memory.UserAttribute;
import org.springframework.security.core.userdetails.memory.UserAttributeEditor;

import com.github.scorekeeper.persistence.entity.SecurityRole;
import com.github.scorekeeper.persistence.entity.User;

public class CostumUserDetailsService implements UserDetailsService {

	@Resource
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userService.getUserByName(userName);

		if (user == null) {
			return null;
		}

		UserAttributeEditor configAttribEd = new UserAttributeEditor();
		configAttribEd.setAsText(user.getPassword() + getRoles(user.getUserRoles()));

		UserAttribute userAttributes = (UserAttribute) configAttribEd.getValue();

		org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(
				userName, userAttributes.getPassword(), userAttributes.isEnabled(), true, true, true,
				userAttributes.getAuthorities());
		return springUser;
	}

	private String getRoles(List<SecurityRole> roles) {
		StringBuffer buffy = new StringBuffer();
		for (SecurityRole securityRole : roles) {
			buffy.append("," + securityRole.getName());
		}
		return buffy.toString();
	}
}
