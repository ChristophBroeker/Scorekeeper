/*
Creation date: 24.06.2013
Created by andre.marbeck
Project: scorekeeper

Copyright diron 2013
*/

package com.github.scorekeeper.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.scorekeeper.persistence.dao.SecurityRoleRepository;
import com.github.scorekeeper.persistence.dao.UserRepository;
import com.github.scorekeeper.persistence.entity.SecurityRole;
import com.github.scorekeeper.persistence.entity.User;
import com.github.scorekeeper.rest.vo.UserTransferVO;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Service
public class UserService {

	@Resource
	private UserRepository userRepository;

	@Resource
	private SecurityRoleRepository securityRoleRepository;

	@Transactional
	public List<UserTransferVO> getAllUser() {
		return convert(userRepository.findAll());
	}

	@Transactional
	public User getUserByName(String userName) {
		List<User> users = userRepository.findByName(userName);
		if (users != null && users.size() > 0) {
			User u = users.get(0);
			Hibernate.initialize(u.getUserRoles());
			return u;
		}
		return null;
	}

	@Transactional
	public User addNewUser(@PathVariable("name") String name, @PathVariable("firstPassword") String firstPassword) {
		User user = new User();
		user.setName(name);
		StandardPasswordEncoder encoder = new StandardPasswordEncoder();
		String result = encoder.encode(firstPassword);
		user.setPassword(result);
		user.hasChangedPassword(false);
		user.addUserRole(securityRoleRepository.findByName("USER"));
		return userRepository.save(user);
	}

	@Transactional
	public void deleteUser(@PathVariable("id") Long id) {
		if (id == 8) {
			try {
				throw new Exception("Cannot delete User with id " + 8 + " !!!! its the admin !!!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		userRepository.delete(id);

	}

	private List<UserTransferVO> convert(Iterable<User> userList) {

		return Lists.newArrayList(Iterables.transform(userList, new Function<User, UserTransferVO>() {
			@Override
			public UserTransferVO apply(User user) {
				return new UserTransferVO(user);
			}
		}));

	}

	@Transactional
	public void updateSecurityRoles(Long userId, Map<String, Boolean> roles) {
		User u = userRepository.findOne(userId);
		List<SecurityRole> currentRoles = u.getUserRoles();
		Map<String, Boolean> newRoles = roles;
		for (String key : newRoles.keySet()) {
			Boolean getRole = newRoles.get(key);
			Boolean hasRole = false;
			for (SecurityRole securityRole : currentRoles) {
				if (securityRole.getName().equals(key)) {
					hasRole = true;
					break;
				}
			}

			if (!hasRole && getRole) {
				addRole(u, key);
			} else if (hasRole && !getRole) {
				try {
					removeRole(u, key);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}

		}

	}

	@Transactional
	private void removeRole(User user, String roleName) throws Exception {
		if (roleName != null && roleName.equals("APPADMIN") && user.getName().equals("admin")) {
			throw new Exception("Unable to remove role 'APPADMIN' from User 'admin'");
		}
		int index = -1;
		for (SecurityRole role : user.getUserRoles()) {
			if (role.getName().equals(roleName)) {
				index = user.getUserRoles().indexOf(role);
				break;
			}
		}
		if (index > -1) {
			user.getUserRoles().remove(index);
			userRepository.save(user);
		}

	}

	@Transactional
	private void addRole(User user, String roleName) {
		SecurityRole role = securityRoleRepository.findByName(roleName);
		if (role != null) {
			user.getUserRoles().add(role);
			userRepository.save(user);
		}

	}

	@Transactional
	public void changeUserPassword(Long userId, String newPassword) {

		StandardPasswordEncoder encoder = new StandardPasswordEncoder();
		String result = encoder.encode(newPassword);
		User user = userRepository.findOne(userId);
		user.setPassword(result);
		user.hasChangedPassword(true);
		userRepository.save(user);
	}
}
