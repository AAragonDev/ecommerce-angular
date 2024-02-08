package com.example.ecommerce.service.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entities.Category;
import com.example.ecommerce.entities.Order;
import com.example.ecommerce.entities.Product;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.enums.UserRole;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	@Override
	public Category createCategory (CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setName(categoryDTO.getName());
		category.setDescription(categoryDTO.getDescription());
		return categoryRepository.save(category);
	}
	
	@Override
	public List<CategoryDTO> getAllCategories(){
		return categoryRepository.findAll().stream().map(Category::getCategoryDTO).collect(Collectors.toList());
	}
	
	@Override
	public Product postProduct(Long categoryId,ProductDTO productDTO) throws IOException {
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		if (optionalCategory.isPresent()) {
			Product product  = new Product();
			product.setAmount(productDTO.getAmount());
			product.setCategory(optionalCategory.get());
			product.setDescription(productDTO.getDescription());
			product.setImage(productDTO.getImage().getBytes());
			product.setName(productDTO.getName());
			product.setPrice(productDTO.getPrice());
			return productRepository.save(product);
		}
		return null;
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll().stream().map(Product::getProductDTO).collect(Collectors.toList());
	}

	@Override
	public void deleteProduct(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if (optionalProduct.isEmpty()) {
			throw new IllegalArgumentException("Producto con id " + id + "invalida");
		}
		productRepository.deleteById(id);
		
	}

	@Override
	public ProductDTO getProductById(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if(optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			return product.getProductDTO();
		}
		return null;
	}

	@Override
	public ProductDTO updateProduct(Long categoryId, Long productId, ProductDTO productDTO) throws IOException {
		Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if(optionalCategory.isPresent() && optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			product.setName(productDTO.getName());
			product.setDescription(productDTO.getDescription());
			product.setAmount(productDTO.getAmount());
			product.setPrice(productDTO.getPrice());
			product.setCategory(optionalCategory.get());
			if(productDTO.getImage() != null) {
				product.setImage(productDTO.getImage().getBytes());
			}
			Product updatedProduct = productRepository.save(product);
			ProductDTO updatedProductDTO = new ProductDTO();
			updatedProductDTO.setId(updatedProduct.getId());
			return updatedProductDTO;
			
		}
		return null;
	}

	@Override
	public List<OrderDTO> getAllOrders() {
		// TODO Auto-generated method stub
		return orderRepository.findAllByOrderStatus(OrderStatus.SUBMITTED).stream().map(Order::getOrderDTO).collect(Collectors.toList());
	}

	@Override
	public List<UserDTO> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAllByUserRole(UserRole.USER).stream().map(User::getUserDTO).collect(Collectors.toList());
	}
}
