package com.beebrick.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@NotBlank(message = "Vui lòng nhập TÀI KHOẢN")
	@Column(name = "Username")
	private String username;
	
	@Size(min = 8, message = "Vui lòng nhập MẬT KHẨU hoặc đảm bảo MẬT KHẨU trên 8 ký tự")
	@Column(name = "Password")
	private String password;
	
	@NotBlank(message = "Vui lòng nhập HỌ VÀ TÊN")
	@Column(name = "Fullname")
	private String fullname;
	
	@NotBlank(message = "Vui lòng nhập ĐỊA CHỈ")
	@Column(name = "Address")
	private String address;
	
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,63}$", message = "Vui lòng nhập đúng định dạng EMAIL")
	@Column(name = "Email")
	private String email;
	
	@Pattern(regexp="([0][0-9]{9})", message = "Vui lòng nhập đúng định dạng SỐ ĐIỆN THOẠI")
	@Column(name = "PhoneNumber")
	private String phoneNumber;
	
	@Column(name = "Image")
	private String image;
	
	@NotNull(message = "Vui lòng nhập NGÀY SINH")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "Birthday")
	private LocalDate birthday;
	
	@Column(name = "Gender")
	private Integer gender;
	
	@Column(name = "CreatedDate", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(name = "ModifiedDate")
	@UpdateTimestamp
	private LocalDateTime modifiedDate;
	
	@OneToMany(mappedBy = "customers")
	private List<Order> order;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}
}