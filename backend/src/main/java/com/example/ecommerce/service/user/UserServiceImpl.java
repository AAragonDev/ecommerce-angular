package com.example.ecommerce.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.SignupDTO;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entities.Order;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.enums.UserRole;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@PostConstruct
	public void createAdmin() {
		User adminAcc = userRepository.findByUserRole(UserRole.ADMIN);
		if(adminAcc == null) {
			User user = new User();
			user.setUserRole(UserRole.ADMIN);
			user.setEmail("admin@test.com");
			user.setName("admin");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
		
	}

	@Override
	public UserDTO createUser(SignupDTO signupDTO) {
		User user = new User();
		user.setName(signupDTO.getName());
		user.setEmail(signupDTO.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
		user.setUserRole(UserRole.USER);
		User createdUser = userRepository.save(user);
		Order order = new Order();
		order.setPrice(0L);
		order.setOrderStatus(OrderStatus.PENDING);
		order.setUser(createdUser);
		orderRepository.save(order);
		UserDTO userDTO= new UserDTO();
		userDTO.setEmail(createdUser.getEmail());
		userDTO.setId(createdUser.getId());
		userDTO.setName(createdUser.getName());
		userDTO.setUserRole(createdUser.getUserRole());
		return userDTO;
	}

	@Override
	public boolean hasUserWithEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findFirstByEmail(email) != null;
	}

}
