package com.marianowinar.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "material")
public class Material implements Serializable{	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long materialId;		
	private String name;
	private String hour;
	private String capacity;
	private String subscribed;
	private String detail;
	
		
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "materials")
	private List<Person> persons;	
	
	public Material() {}
	
	public Material(Long materialId, String name, String hour, String capacity, List<Person> persons, 
			String subscribed, String detail) {
		this.materialId = materialId;
		this.name = name;
		this.hour = hour;
		this.capacity = capacity;
		this.persons = persons;
		this.subscribed = subscribed;
		this.detail = detail;
	}

	public Long getId() {
		return materialId;
	}	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}	
	
	public String getSubscribed() {
		return subscribed;
	}

	public void setSubscribed(String subscribed) {
		this.subscribed = subscribed;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	// METHODS AND FUNCTION GAME LIST	

	public List<Person> getListPerson() {
        if (persons == null){persons = new ArrayList<>();}
        return persons;
    }

    public void addPerson(Person per) {
    	if (persons == null){persons = new ArrayList<>();}
        persons.add(per);
    }  
    public int listaPersonSize(){
    	if (persons == null){persons = new ArrayList<>();}
        return persons.size();
    }
    public Person searchPerson(Long index){
        if (index < 0 || index >= listaPersonSize()){return null;}
        Person aux = null;
        for(Person ele : persons) {
        	if(ele.getPersonId() == index) {
        		aux = ele;
        	}
        }
        return aux;
    }
    public boolean removePerson(Long index){
    	if (index < 0 || index >= listaPersonSize()){return false;}
        persons.remove(index);
        return true;
    }	
	
}
