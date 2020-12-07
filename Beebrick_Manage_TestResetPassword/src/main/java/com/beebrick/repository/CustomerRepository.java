package com.beebrick.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beebrick.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

}
