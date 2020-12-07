package com.beebrick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.beebrick.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query(value = "SELECT * FROM products WHERE IsActive = 0", nativeQuery = true)
	public List<Product> getAll();
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE products SET IsActive = 1 WHERE ProductID=?1", nativeQuery = true)
	void delete(Integer productID);
}
