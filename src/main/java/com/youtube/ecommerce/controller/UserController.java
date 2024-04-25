package com.youtube.ecommerce.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.ecommerce.entity.User;
import com.youtube.ecommerce.service.UserService;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void initRolesAndUsers() {
		userService.initRolesAndUser();
	}
	
	@PostMapping({"/registerNewUser"})
	public User registerNewUser(@RequestBody User user) {
		return userService.registerNewUser(user);
		
	}
	
//	@PostMapping({"/registerNewUser"})
//    public User registerNewUser(@RequestBody User user) {
//        return userService.registerNewUser(user);
//    }
	
	@GetMapping({"/forAdmin"})
	@PreAuthorize("hasRole('Admin')")
	public String forAdmin() {
		return "this URL is only accessible to admin";
	}
	
	@GetMapping({"/forUser"})
	@PreAuthorize("hasRole('User')")
	public String forUser() {
		System.out.println("user");
		return "this URL is only accessible to the user";
	}
	
}
