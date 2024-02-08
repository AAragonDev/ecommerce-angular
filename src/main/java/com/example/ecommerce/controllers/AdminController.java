package com.example.ecommerce.controllers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entities.Category;
import com.example.ecommerce.entities.Product;
import com.example.ecommerce.service.admin.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	private final Logger log = LoggerFactory.getLogger(AdminController.class);
	
	@PostMapping("/category")
	public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDTO){
		log.info("inicio de creacion de categoria");
		Category category = adminService.createCategory(categoryDTO);
		ResponseEntity<Category> response=  ResponseEntity.status(HttpStatus.CREATED).body(category);
		log.info("categoria creada");
		return response;
	}
	
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDTO>> getAllCategories(){
		List<CategoryDTO> allCategories = adminService.getAllCategories();
		return ResponseEntity.ok(allCategories);
	}
	
	@PostMapping("/product/{categoryId}")
	public ResponseEntity<Product> postProduct(@PathVariable Long categoryId,@ModelAttribute ProductDTO productDTO) throws IOException{
	
		Product postProduct = adminService.postProduct(categoryId,productDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(postProduct);
	}

	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getAllProducts(){
		List<ProductDTO> products = adminService.getAllProducts();
		return ResponseEntity.ok(products);
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
		adminService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("product/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
		ProductDTO productDTO = adminService.getProductById(id);
		if (productDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productDTO);
		
	}
	
	@PutMapping("/{categoryId}/product/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable Long categoryId,@PathVariable Long productId, @ModelAttribute ProductDTO productDTO) throws IOException{
		ProductDTO updatedProduct = adminService.updateProduct(categoryId, productId, productDTO);
		log.info("id de producto: {}",productId);
		if(updatedProduct == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Algo salió mal");
		return ResponseEntity.ok(updatedProduct);
	}
	
	@GetMapping("/orders")
	public ResponseEntity<List<OrderDTO>> getMethodName() {
		List<OrderDTO> ordersDTO = adminService.getAllOrders();
		return ResponseEntity.ok(ordersDTO);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> usersDTO = adminService.getAllUsers();
		return ResponseEntity.ok(usersDTO);
	}
	
}
