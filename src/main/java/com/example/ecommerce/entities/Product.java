package com.example.ecommerce.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.ecommerce.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table (name = "products")
public class Product {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Lob
	private String description;
	
	private double price;
	
	private int amount;
	
	@Column(columnDefinition = "longblob")
	private byte[] image;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id",nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Category category;
	
	public ProductDTO getProductDTO() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setAmount(this.getAmount());
		productDTO.setDescription(this.getDescription());
		productDTO.setName(this.getName());
		productDTO.setPrice(this.getPrice());
		productDTO.setId(this.getId());
		productDTO.setReturnedImage(image);
		productDTO.setCategoryId(category.getId());
		productDTO.setCategoryName(category.getName());
		
		return productDTO;
	}
	
	
}
