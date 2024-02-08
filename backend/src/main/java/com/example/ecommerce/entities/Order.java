package com.example.ecommerce.entities;

import java.util.Date;
import java.util.List;

import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	
	private String address;
	
	private String paymentType;
	
	private Date date;
	
	private double price;
	
	private OrderStatus orderStatus;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "order")
	private List<CartItems> cartItems;


	public OrderDTO getOrderDTO() {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(id);
		orderDTO.setOrderStatus(orderStatus);
		orderDTO.setAmount(price);
		orderDTO.setAddress(address);
		orderDTO.setPaymentTyte(paymentType);
		orderDTO.setUsername(user.getName());
		orderDTO.setDate(date);
		orderDTO.setOrderDescription(description);
		return orderDTO;
	}

}
