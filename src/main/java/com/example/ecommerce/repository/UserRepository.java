package com.example.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.ecommerce.entities.User;
import com.example.ecommerce.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	User findFirstByEmail(String email);

	User findByUserRole(UserRole admin);

	Optional<User> findByEmail(String email);

	List<User> findAllByUserRole(UserRole userRole);


}
