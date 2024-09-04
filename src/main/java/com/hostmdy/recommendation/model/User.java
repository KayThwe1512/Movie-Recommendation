package com.hostmdy.recommendation.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
	
	private Long id;
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private String password;
	private String role; 
	private boolean enable = true;
	private LocalDateTime createdAt;
	private String biography;
	private String gender;
	private LocalDate dateOfBirth;
	private String profile_pic;
	
	public User() {}
	
	
	
	public User(Long id, String firstname, String lastname, String username, String email, String biography,
			String gender, LocalDate dateOfBirth) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.biography = biography;
		this.gender = gender;
		this.dateOfBirth = LocalDate.now();
	}



	public User(String firstname, String lastname, String username, String email, String password, String role,
			boolean enable, LocalDateTime createdAt, String biography, String gender, LocalDate dateOfBirth) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enable = enable;
		this.createdAt = createdAt;
		this.biography = biography;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
	}

	//login
	public User(Long id, String firstname, String lastname, String username, String email, String password, String role, boolean enable,
			LocalDateTime createdAt, String biography, String gender, LocalDate dateOfBirth, String profile_pic ) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enable = enable;
		this.createdAt = createdAt;
		this.biography = biography;
		this.gender = gender;
		this.dateOfBirth = (dateOfBirth != null ? dateOfBirth : LocalDate.now());
		this.profile_pic = profile_pic;
	}
	
	//getUserProfileById
	public User(Long id, String firstname, String lastname, String username, String email, String role, boolean enable,
			LocalDateTime createdAt, String biography, String gender, LocalDate dateOfBirth, String profile_pic ) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.role = role;
		this.enable = enable;
		this.createdAt = createdAt;
		this.biography = biography;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.profile_pic = profile_pic;
	}

	public User( String firstname, String lastname, String username, String email, String password, String role,
			boolean enable, LocalDateTime createdAt, String biography, String gender, LocalDate dateOfBirth, String profile_pic) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enable = enable;
		this.createdAt = createdAt;
		this.biography = biography;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.profile_pic = profile_pic;
	}

	//sign up
	public User(String firstname, String lastname, String username, String email, String password, String role, String biography, String gender) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.createdAt = LocalDateTime.now();
		this.biography = biography;
		this.gender = gender;
		this.dateOfBirth = LocalDate.now();
		
	}
	
	//update profile
	public User(Long id, String firstname, String lastname, String username, String email, String biography,
			String gender, LocalDate dateOfBirth, String profile_pic) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.biography = biography;
		this.gender = gender;
		this.dateOfBirth = LocalDate.now();
		this.profile_pic = profile_pic;
	}

	public User(Long id, String profile_pic) {
		super();
		this.id = id;
		this.profile_pic = profile_pic;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getProfile_pic() {
		return profile_pic;
	}

	public void setProfile_pic(String profile_pic) {
		this.profile_pic = profile_pic;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", username=" + username
				+ ", email=" + email + ", password=" + password + ", role=" + role + ", enable=" + enable
				+ ", createdAt=" + createdAt + ", biography=" + biography + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + "]";
	}
	
	   
	
}
