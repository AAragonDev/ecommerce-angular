package com.example.ecommerce.dto;

import java.util.Date;
import java.util.List;

import com.example.ecommerce.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderDTO {

	private String orderDescription;
	
	private List<CartItemsDTO> cartItemsDTO;
	
	private Long id;
	
	private Date date;
	
	private double amount;
	
	private String address;
	
	private OrderStatus orderStatus;
	
	private String paymentTyte;
	
	private String username;
	
	
}
