package com.marianowinar.model.forms;

public class Register {
		
	private String dni;
	private String name;
	private String surname;	
	private String phone;
	private String email;
	private String legajo;
	private String password;
	
	public Register() {	}
	
	public Register(String name, String surname, String phone, String email, String dni, String legajo,
			String password) {
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.email = email;
		this.dni = dni;
		this.legajo = legajo;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
		
}
