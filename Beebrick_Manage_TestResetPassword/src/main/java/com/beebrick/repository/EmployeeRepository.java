package com.beebrick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.beebrick.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
	@Query(value = "Select * from employees where Username = ?1 and IsActive = 0", nativeQuery = true)
	Employee findByUsername(String username);
	
	@Query(value = "Select * from employees where Email = ?1 and IsActive = 0", nativeQuery = true)
	Employee findByEmail(String email);
	
	@Query(value = "SELECT * FROM employees WHERE IsActive = 0", nativeQuery = true)
	public List<Employee> getAll();
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE employees SET IsActive = 1 WHERE Username=?1", nativeQuery = true)
	void delete(String username);
	
	public Employee findByRestPasswordToken(String token);
}
