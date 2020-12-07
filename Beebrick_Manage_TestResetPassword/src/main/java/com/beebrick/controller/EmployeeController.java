package com.beebrick.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.beebrick.entity.Employee;
import com.beebrick.repository.EmployeeRepository;
import com.beebrick.service.EmployeeService;

import javassist.NotFoundException;

@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@RequestMapping(value = "admin/employee")
	public String index(Model model) {
		List<Employee> employees = employeeService.getAll();
		model.addAttribute("employees", employees);
		return "admin/employee/index";
	}

	@RequestMapping(value = "admin/employee/add")
	public String add(Model model) {
		model.addAttribute("employee", new Employee());
		return "admin/employee/add";
	}

	@RequestMapping(value = "admin/employee/save", method = RequestMethod.POST)
	public String save(@Valid Employee employee, BindingResult bindingResult, @RequestParam("photo_file") MultipartFile photo,
			Model model) {
		if (employeeRepository.existsById(employee.getUsername())) {
			employee.setUsername(null);
			model.addAttribute("message", "Username is already exists!");
			return "admin/employee/add";
		}
		if (bindingResult.hasErrors()) {
			return "admin/employee/add";
		} else {
				try {
					Path path = Paths.get("uploads/");
					InputStream inputStream = photo.getInputStream();
					Files.copy(inputStream, path.resolve(photo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
					employee.setImage(photo.getOriginalFilename().toLowerCase());
				} catch (Exception e) {
				}
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String encoderPassword = encoder.encode(employee.getPassword());
				employee.setPassword(encoderPassword);
				employeeService.save(employee);
			return "redirect:/admin/employee";
		}
	}

	@RequestMapping(value = "admin/employee/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("username") String username, Model model) {
		Optional<Employee> edit = employeeService.findByUsername(username);
		edit.ifPresent(employee -> model.addAttribute("employee", employee));
		return "admin/employee/edit";
	}
	
	@RequestMapping(value = "admin/employee/update", method = RequestMethod.POST)
	public String update(@Valid Employee employee, BindingResult bindingResult, @RequestParam("photo_file") MultipartFile photo, Model model) {
		
		if (bindingResult.hasErrors()) {
			return "admin/employee/edit";
		} else {
			try {
				Path path = Paths.get("uploads/");
				InputStream inputStream = photo.getInputStream();
				Files.copy(inputStream, path.resolve(photo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				employee.setImage(photo.getOriginalFilename().toLowerCase());
			} catch (Exception e) {
			}
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encoderPassword = encoder.encode(employee.getPassword());
			employee.setPassword(encoderPassword);
			employeeService.save(employee);
			employeeService.save(employee);		
			return "redirect:/admin/employee";
		}
	}

	@RequestMapping(value = "admin/employee/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("username") String username, Model model) {
		employeeService.delete(username);
		return "redirect:/admin/employee";
	}
	
	public void updateResetPasswordToken(String token, String email) throws EmployeeNotFoundException {
		Employee emp = employeeRepository.findByEmail(email);
		if(emp != null) {
			emp.setRestPasswordToken(token);
			employeeRepository.save(emp);
		}else {
			throw new EmployeeNotFoundException("Cound not find Email");
		}
	}
	public Employee get(String restPasswordToken) {
		return employeeRepository.findByRestPasswordToken(restPasswordToken);
	}
	
	public void updatePassword(Employee emp, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		emp.setPassword(encodedPassword);
		emp.setRestPasswordToken(null);
		
		employeeService.save(emp);
	}
	
	
	
	
	
	
	
	
	
}
