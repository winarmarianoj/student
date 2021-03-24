package com.marianowinar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usr_id", unique=true, nullable=false)
	private Long userId;
	
	private String dni;
	private String legajo;
	private String password;
	private boolean active;
	
	public Account() {}

	public Account(Long userId, String dni, String legajo, String password, boolean active) {
		this.userId = userId;
		this.dni = dni;
		this.legajo = legajo;
		this.password = password;
		this.active = active;
	}

	public Long getUserId() {
		return userId;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getLegajo() {
		return legajo;
	}

	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
