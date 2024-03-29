package com.example.ecommerce.dto;

import lombok.Data;

@Data
public class CartItemsDTO {
	
	private Long id;
	
	private double price;
	
	private Long quantity;
	
	private Long productId;
	
	private Long orderId;
	
	private String productName;
	
	private byte[] returnedImage;
	
	private Long userId;

}
