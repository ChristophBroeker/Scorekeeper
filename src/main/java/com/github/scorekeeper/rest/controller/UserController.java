package com.github.scorekeeper.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scorekeeper.rest.vo.UserTransferVO;
import com.github.scorekeeper.service.PlayerService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private PlayerService playerService;

	@RequestMapping(value = "", method = { RequestMethod.GET })
	@ResponseBody
	public UserTransferVO getUser() {

		Map<String, Boolean> roles = new HashMap<String, Boolean>();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
			return new UserTransferVO("anonymous", roles);
		}
		UserDetails userDetails = (UserDetails) principal;

		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			roles.put(authority.toString(), Boolean.TRUE);
		}

		return new UserTransferVO(userDetails.getUsername(), roles);
	}
}
