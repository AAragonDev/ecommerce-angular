package com.example.ecommerce.entities;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.ecommerce.dto.CartItemsDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cartitems")
public class CartItems {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private double price;
	
	private Long quantity;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "product_id",nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "user_id",nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;
	
	public CartItemsDTO getCartItemDTO() {
		CartItemsDTO cartItemsDTO = new CartItemsDTO();
		cartItemsDTO.setId(id);
		cartItemsDTO.setQuantity(quantity);
		cartItemsDTO.setPrice(price);
		cartItemsDTO.setProductId(product.getId());
		cartItemsDTO.setProductName(product.getName());
		cartItemsDTO.setUserId(user.getId());
		cartItemsDTO.setOrderId(order.getId());
		cartItemsDTO.setReturnedImage(product.getImage());
		return cartItemsDTO;
	}
}
