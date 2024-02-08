package com.example.ecommerce.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entities.Order;
import com.example.ecommerce.enums.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	Order findByUserIdAndOrderStatus(Long userId, OrderStatus pending);

	List<Order> findAllByUserIdAndOrderStatus(Long userId, OrderStatus orderstatus);

	List<Order> findAllByOrderStatus(OrderStatus submitted);

}
