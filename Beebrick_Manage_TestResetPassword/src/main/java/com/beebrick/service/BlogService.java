package com.beebrick.service;

import java.util.List;
import java.util.Optional;

import com.beebrick.entity.Blog;

public interface BlogService {
	
	List<Blog> getAll();

	void save(Blog blog);

	void delete(Integer blogID);

	Optional<Blog> findById(Integer blogID);
}
