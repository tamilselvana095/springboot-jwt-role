package com.youtube.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.youtube.ecommerce.entity.JwtRequest;
import com.youtube.ecommerce.entity.JwtResponse;
import com.youtube.ecommerce.service.JwtService;

@RestController
@CrossOrigin
public class JwtController {

	@Autowired
	private JwtService jwtService;
	
	@PostMapping({"/authenticate"})
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
	
		return jwtService.createJwtToken(jwtRequest);
	}



}
