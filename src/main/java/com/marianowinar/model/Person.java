package com.marianowinar.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "person")
@Inheritance(strategy=InheritanceType.JOINED)
public class Person implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long personId;		
	private String name;
	private String surname;
	private String dni;
	private String phone;
	private String email;
	private String type;
	
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "userId")
	private Users account;	
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "persons_materials", 	joinColumns = { @JoinColumn(name = "personId", nullable = false, updatable = false) },	inverseJoinColumns = { @JoinColumn(name = "materialId", nullable = false, updatable = false) })
	private List<Material> materials;

	public Person() {}
	
	public Person(Long personId, String name, String surname, String phone, String email, 
			Users account, List<Material> materials, String dni, String type) {
		this.personId = personId;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.email = email;
		this.account = account;
		this.materials = materials;
		this.dni = dni;
		this.type = type;
	}	
	
	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
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

	public Users getAccount() {
		return account;
	}
	public void setAccount(Users account) {
		this.account = account;
	}
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

	// METHODS AND FUNCTION GAME LIST
	

	

	public List<Material> getListMaterial() {
        if (materials == null){materials = new ArrayList<>();}
        return materials;
    }

    public void addMaterial(Material mat) {
    	if (materials == null){materials = new ArrayList<>();}
        materials.add(mat);
    }  
    public int listaMaterialSize(){
    	if (materials == null){materials = new ArrayList<>();}
        return materials.size();
    }
    public Material searchMaterial(Long index){
        if (index < 0 || index >= listaMaterialSize()){return null;}
        Material aux = null;
        for(Material ele : materials) {
        	if(ele.getId() == index) {
        		aux = ele;
        	}
        }
        return aux;
    }
    
    public boolean searchExistMaterial(Material mat){
    	boolean res = false;        
        
        for(Material ele : materials) {
        	if(ele.getId() == mat.getId()) {
        		res = true;
        	}
        }
        return res;
    }
    
    public boolean removeMaterial(Long index){
    	if (index < 0 || index >= listaMaterialSize()){return false;}
        materials.remove(Math.toIntExact(index));
        return true;
    }
    
    public boolean removeMaterialName(String name){ 
    	boolean res = false;
    	for(Material ele : getListMaterial()) {
    		if(ele.getName().equals(name)) {
    			if(removeMaterial(ele.getId())) {
    				res = true;
        			break;
    			}
    		}
    	}        
        return res;
    }
	
}
