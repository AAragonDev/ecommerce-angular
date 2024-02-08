package com.example.ecommerce.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class ProductDTO {

	private Long id;
	
	private String name;
	
	@Lob
	private String description;
	
	private double price;
	
	private int amount;
	
	private MultipartFile image;
	
	private byte[] returnedImage;
	
	private Long categoryId;
	
	private String categoryName;
}
