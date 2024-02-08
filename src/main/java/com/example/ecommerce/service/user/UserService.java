package com.example.ecommerce.service.user;


import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.SignupDTO;
import com.example.ecommerce.dto.UserDTO;

@Service
public interface UserService {

	UserDTO createUser(SignupDTO signupDTO);

	boolean hasUserWithEmail(String email);

}
