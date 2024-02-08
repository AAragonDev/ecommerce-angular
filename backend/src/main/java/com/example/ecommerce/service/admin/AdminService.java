package com.example.ecommerce.service.admin;

import java.io.IOException;
import java.util.List;

import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entities.Category;
import com.example.ecommerce.entities.Product;

public interface AdminService {

	Category createCategory(CategoryDTO categoryDTO);
	List<CategoryDTO> getAllCategories();
	Product postProduct(Long categoryId, ProductDTO productDTO)throws IOException;
	List<ProductDTO> getAllProducts();
	void deleteProduct(Long id);
	ProductDTO getProductById(Long id);
	ProductDTO updateProduct(Long categoryId, Long productId,ProductDTO productDTO)throws IOException;
	
	List<OrderDTO> getAllOrders();
	
	List<UserDTO> getAllUsers();
	
}
