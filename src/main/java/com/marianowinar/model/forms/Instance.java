package com.marianowinar.model.forms;

public class Instance {
	private String name;
	private String surname;
	private String type;
	
	public Instance() {	}

	public Instance(String name, String surname, String type) {
		this.name = name;
		this.surname = surname;
		this.type = type;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
