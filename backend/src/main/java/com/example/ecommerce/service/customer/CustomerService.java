package com.example.ecommerce.service.customer;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.ecommerce.dto.CartItemsDTO;
import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.dto.PlaceOrderDTO;
import com.example.ecommerce.dto.ProductDTO;

public interface CustomerService {
	
	List<ProductDTO> getAllProducts();

	List<ProductDTO> searchProductByTitle(String title);
	
	ResponseEntity<?> addProductCart(CartItemsDTO cartItemsDTO);
	
	OrderDTO getCartByUserid(Long userid);
	
	OrderDTO addMinusOnProduct(Long userId, Long productId);
	
	OrderDTO addPlusOnProduct(Long userId, Long productId);
	
	OrderDTO placeOrder(PlaceOrderDTO placeOrderDTO);
	
	List<OrderDTO> getOrdersByUserId(Long userId);
}
