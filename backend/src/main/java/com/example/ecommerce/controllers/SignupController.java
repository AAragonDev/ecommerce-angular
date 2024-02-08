package com.example.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.SignupDTO;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.service.user.UserService;

@RestController
public class SignupController {

	@Autowired
	private UserService userService;
	
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> signupUser(@RequestBody SignupDTO signupDTO){
		
		if (userService.hasUserWithEmail(signupDTO.getEmail())) {
			return new ResponseEntity<>("Este usuario ya existe",HttpStatus.BAD_REQUEST);
		}
		UserDTO user = userService.createUser(signupDTO);
		if(user == null) {
			return new ResponseEntity<>("Usuario no creado, intenta de nuevo", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(user,HttpStatus.CREATED);
		
	}
}
