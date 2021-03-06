package com.beebrick.service;

import java.util.List;
import java.util.Optional;

import com.beebrick.entity.Product;

public interface ProductService {
	
	List<Product> getAll();
	
	void save(Product product);
	
	void delete(Integer productID);

	Optional<Product> findById(Integer productID);
}
