package com.marianowinar.model;

import java.util.ArrayList;
import java.util.List;

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
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "professors_materials", joinColumns=@JoinColumn(name = "personId") , inverseJoinColumns = @JoinColumn(name = "materialId"))
	private List<Material> materials;	
	
	public Professor() {}	
	
	public Professor(Long personId, String name, String surname, String phone, String email, PersonType type,
			Account account, List<Material> materials, boolean active) {
		super(personId, name, surname, phone, email, type, account, materials);
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
