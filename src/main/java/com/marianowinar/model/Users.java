package com.marianowinar.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	private String legajo;
	private String password;	
	private boolean enabled;	
	
	public Users() {}
	
	public Users(String legajo, String password, boolean enabled) {
		super();
		this.legajo = legajo;
		this.password = password;
		this.enabled = enabled;
	}

	public Long getId() {
		return id;
	}


	public String getUsername() {
		return legajo;
	}


	public void setUsername(String username) {
		this.legajo = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}