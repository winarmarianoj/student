package com.marianowinar.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import com.marianowinar.model.enums.PersonType;

@Entity
@PrimaryKeyJoinColumn(name = "personId")
public class Professor extends Person{	
	
	private boolean active;		
	
	public Professor() {}	
	
    
	

	public Professor(Long personId, String name, String surname, String phone, String email, PersonType type,
			Account account, List<Material> materials) {
		super(personId, name, surname, phone, email, type, account, materials);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
