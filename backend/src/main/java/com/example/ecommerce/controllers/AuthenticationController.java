package com.example.ecommerce.controllers;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.AuthenticationRequest;

import com.example.ecommerce.entities.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.utils.JwtUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	
	@PostMapping("/authenticate")
	public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws BadCredentialsException,DisabledException,UsernameNotFoundException,IOException,JSONException,ServletException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		}catch(BadCredentialsException e) {
			throw new BadCredentialsException("Constraseña incorrecta");
		}catch (DisabledException disabledExcemption) {
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Usuario no activado");
			return;
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());
		final String jwt = jwtUtil.generateToken(authenticationRequest.getUsername());
		response.getWriter().write(new JSONObject()
				.put("userId", user.getId())
				.put("role", user.getUserRole())
				.toString()
				);
		String token = TOKEN_PREFIX + jwt;
		response.addHeader("Access-Control-Expose-Headers", HEADER_STRING);
		response.addHeader("Access-Control-Allow-Headers", "Authorization,X-PINGGOTHER,Origin,X-Requested-With,Content-Type,Accept,X-CustomHeader");
		response.setHeader(HEADER_STRING, token);
		
		
	}
}
