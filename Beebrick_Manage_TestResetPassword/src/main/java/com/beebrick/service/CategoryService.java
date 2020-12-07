package com.beebrick.service;

import java.util.List;
import java.util.Optional;

import com.beebrick.entity.Category;

public interface CategoryService {
	
	List<Category> getAll();

	void save(Category category);

	void delete(Integer categoryID);

	Optional<Category> findById(Integer categoryID);
	
	List<Category> findByName(String categoryName);
}
