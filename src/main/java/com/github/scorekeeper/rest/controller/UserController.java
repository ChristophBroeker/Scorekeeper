package com.github.scorekeeper.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scorekeeper.persistence.entity.User;
import com.github.scorekeeper.rest.vo.UserTransferVO;
import com.github.scorekeeper.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@PreAuthorize("permitAll")
	@RequestMapping(value = "/currentUser", method = { RequestMethod.GET })
	@ResponseBody
	public UserTransferVO getUser() {

		Map<String, Boolean> roles = new HashMap<String, Boolean>();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
			return new UserTransferVO(0L, "anonymous", roles, true);
		}
		UserDetails userDetails = (UserDetails) principal;

		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			roles.put(authority.toString(), Boolean.TRUE);
		}
		User u = userService.getUserByName(userDetails.getUsername());
		return new UserTransferVO(u.getId(), u.getName(), roles, u.hasChangedPassword());
	}

	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/changeUsersPassword/{userId}/{newPassword}", method = RequestMethod.POST)
	@ResponseBody
	public void changeUsersPassword(@PathVariable("userId") Long userid, @PathVariable("newPassword") String newPassword) {
		userService.changeUserPassword(userid, newPassword);
	}

	@PreAuthorize("hasRole('APPADMIN')")
	@RequestMapping(value = "/{name}/{firstPassword}", method = RequestMethod.POST)
	@ResponseBody
	public User addUser(@PathVariable("name") String name, @PathVariable("firstPassword") String firstPassword) {
		return userService.addNewUser(name, firstPassword);
	}

	@PreAuthorize("hasRole('APPADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
	}

	@PreAuthorize("hasRole('APPADMIN')")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public List<UserTransferVO> getAllUser() {
		return userService.getAllUser();
	}

	@PreAuthorize("hasRole('APPADMIN')")
	@RequestMapping(value = "/roles/{userId}", method = RequestMethod.PUT)
	@ResponseBody
	public void updateUserRole(@PathVariable("userId") Long userId, @RequestBody Map<String, Boolean> roles) {

		userService.updateSecurityRoles(userId, roles);
	}

}
