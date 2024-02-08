package com.example.ecommerce.service.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entities.User;
import com.example.ecommerce.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user1 = userRepo.findFirstByEmail(username);
		if (user1 == null) throw new UsernameNotFoundException("Username not found");
		UserDetails userdetail = new org.springframework.security.core.userdetails.User(user1.getEmail(),user1.getPassword(),new ArrayList<>());
		return userdetail;
	}

}
