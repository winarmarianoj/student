package com.marianowinar.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "personId")
public class Professor extends Person{	
	
	private boolean active;
	
	public Professor() {}		

	public Professor(Long personId, String name, String surname, String phone, String email, Users account,
			List<Material> materials, String dni, String type, boolean active) {
		super(personId, name, surname, phone, email, account, materials, dni, type);
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
