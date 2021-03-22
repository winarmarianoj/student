package com.marianowinar.model.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Register {
	
	@Size(min = 3, max = 20, message = "el nombre debe tener mas de 3 letras y menos de 20.")
	private String name;	

	@NotBlank(message = "debe indicar el apellido del usuario.")
	private String surname;

	@Size(min = 10, max = 20, message = "El Número de teléfono o celular debe tener mas de 10 números y menos de 20.")	
	private String phone;

	@Email
    @NotBlank
	private String email;

	@Size(min = 7, max = 9, message = "El Número de teléfono o celular debe tener mas de 7 números y menos de 9.")	
	private String dni;
	
	@NotBlank
	private String legajo;

	@Pattern(regexp = "^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Debe contener al menos 1 Mayúscula, 1 Minúscula, 1 Número y 1 Carácter con Mínimo de 8 caracteres")
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
