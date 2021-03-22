package com.marianowinar.model.forms;

public class Forgot {
	private String dni;	
	private String legajo;	
	private String password;
	private String password2;
	
	public Forgot() {}

	public Forgot(String dni, String legajo, String password, String password2) {
		this.dni = dni;
		this.legajo = legajo;
		this.password = password;
		this.password2 = password2;
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

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}	
	
}
