package com.beebrick.service;

import java.util.List;
import java.util.Optional;

import com.beebrick.entity.Customer;

public interface CustomerService {
	
	List<Customer> findAll();

	void save(Customer customer);

	Optional<Customer> findByUsername(String username);
	
}
