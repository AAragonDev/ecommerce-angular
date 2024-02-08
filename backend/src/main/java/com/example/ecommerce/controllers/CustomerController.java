package com.example.ecommerce.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.CartItemsDTO;
import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.dto.PlaceOrderDTO;
import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.service.customer.CustomerService;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {
	
	private final CustomerService customerService;
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getAllProducts(){
		List<ProductDTO> products = customerService.getAllProducts();
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/product/search/{title}")
	public ResponseEntity<List<ProductDTO>> searchProductByTitle(@PathVariable String title){
		List<ProductDTO> productDTOList = customerService.searchProductByTitle(title);
		return ResponseEntity.ok(productDTOList);
		
	}
	
	@PostMapping("/cart")
	public ResponseEntity<?> postProductToCart(@RequestBody CartItemsDTO cartItemDTO){
		return customerService.addProductCart(cartItemDTO);
	}
	
	@GetMapping("/cart/{userId}")
	public ResponseEntity<OrderDTO> getCartByUserId(@PathVariable Long userId){
		OrderDTO orderDTO = customerService.getCartByUserid(userId);
		if (orderDTO == null) {return ResponseEntity.notFound().build();}
		return ResponseEntity.ok(orderDTO);
	}
	
	@GetMapping("/{userId}/discount/{productId}")
	public ResponseEntity<OrderDTO> addMinusOnProduct(@PathVariable Long userId,@PathVariable Long productId){
		OrderDTO orderDTO = customerService.addMinusOnProduct(userId, productId);
		return ResponseEntity.ok(orderDTO);
	}
	
	@GetMapping("/{userId}/add/{productId}")
	public ResponseEntity<OrderDTO> addPlusOnProduct(@PathVariable Long userId,@PathVariable Long productId){
		OrderDTO orderDTO = customerService.addPlusOnProduct(userId, productId);
		return ResponseEntity.ok(orderDTO);
	}
	
	@PostMapping("/placeOrder")
	public ResponseEntity<OrderDTO> placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO){
		OrderDTO orderDTO = customerService.placeOrder(placeOrderDTO);
		if(orderDTO == null) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();}
		return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
		
	}
	
	@GetMapping("/orders/{userId}")
	public ResponseEntity <List<OrderDTO>> getOrdersByUserId(@PathVariable Long userId){
		List<OrderDTO> ordersDTO = customerService.getOrdersByUserId(userId);
		
		return ResponseEntity.ok(ordersDTO);
		
	}

}
