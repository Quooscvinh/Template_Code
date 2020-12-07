package com.beebrick.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.beebrick.controller.UserUserDetail;
import com.beebrick.entity.Employee;
import com.beebrick.repository.EmployeeRepository;

public class UserUserDetailSevice implements UserDetailsService {
	
	@Autowired
	private EmployeeRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee user = repo.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		return new UserUserDetail(user);
	}
}
