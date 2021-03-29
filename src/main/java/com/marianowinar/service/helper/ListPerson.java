package com.marianowinar.service.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.marianowinar.model.Material;
import com.marianowinar.model.Person;
import com.marianowinar.repository.PersonRepository;

public class ListPerson {
		
	private List<Person> listPerson;
	private Person per;
	
	public ListPerson() {this.per = new Person();}
	
	// METHODS AND FUNCTION GAME LIST	

		public List<Person> getListPerson() {
	        if (listPerson == null){listPerson = new ArrayList<>();}
	        return listPerson;
	    }

	    public void addPerson(Person per) {
	    	if (listPerson == null){listPerson = new ArrayList<>();}
	        listPerson.add(per);
	    }  
	    public int listaPersonSize(){
	    	if (listPerson == null){listPerson = new ArrayList<>();}
	        return listPerson.size();
	    }
	    public Person searchPerson(Long index){
	        if (index < 0 || index >= listaPersonSize()){return null;}
	        Person aux = null;
	        for(Person ele : listPerson) {
	        	if(ele.getPersonId() == index) {
	        		aux = ele;
	        	}
	        }
	        return aux;
	    }
	    public boolean removePerson(Long index){
	    	if (index < 0 || index >= listaPersonSize()){return false;}
	        listPerson.remove(index);
	        return true;
	    }	
	    
	    public Person searchPerDNI(String dni) {			
			for(Person ele : listPerson) {
				if(ele.getDni().equals(dni)){
					this.per = ele;
				}
			}
			return this.per;
		}
	    
	    public boolean removePerName(String dni) {
	    	boolean res = false;
			for(Person ele : listPerson) {
				if(ele.getDni().equals(dni)) {
					removePerson(ele.getPersonId());
					res = true;
				}
			}
			return res;
		}
	

}
