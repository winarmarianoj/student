package com.marianowinar.service.validator;

import com.marianowinar.model.Person;
import com.marianowinar.model.Professor;
import com.marianowinar.service.exception.person.InvalidMailException;
import com.marianowinar.service.exception.person.InvalidNamesPersonException;

public class ValidPersonTypes {
	
	private static ValidPersonTypes types;
	
	private ValidPersonTypes() {}
	
	public static ValidPersonTypes getInstance() {
		if(types == null) types = new ValidPersonTypes();
		
		return types;
	}
	
	public boolean validPerson(Person per) throws InvalidNamesPersonException, InvalidMailException {
		boolean res = validName(per.getName());
		res &= validName(per.getSurname());
		res &= validName(per.getPhone());		
		res &= validName(per.getEmail());	
		
		if(!res)throw new InvalidNamesPersonException("Invalid Name or Incorrect Text");		
		return res;
	}
	
	public boolean validProfessor(Professor pro) throws InvalidNamesPersonException, InvalidMailException {
		boolean res = validName(pro.getName());
		res &= validName(pro.getSurname());
		res &= validName(pro.getPhone());		
		res &= validName(pro.getEmail());
		
		if(!res)throw new InvalidNamesPersonException("Invalid Name or Incorrect Text");		
		return res;
	}
	
	
	private boolean validName(String name) {return name != null || name != "";}	
	
}
