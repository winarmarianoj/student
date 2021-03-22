package com.marianowinar.service.factory;

import com.marianowinar.model.Account;
import com.marianowinar.model.Person;
import com.marianowinar.model.Professor;
import com.marianowinar.model.forms.Register;

public class FactoryEntities {
	
	private static FactoryEntities fact;
	
	private FactoryEntities() {}
	
	public static FactoryEntities getInstance() {
		if(fact == null) fact = new FactoryEntities();
		
		return fact;
	}
	
	public Account createAccount(Register entity) {
		Account acc = new Account();
		acc.setActive(false);
		acc.setDni(entity.getDni());
		acc.setLegajo(entity.getLegajo());
		acc.setPassword(entity.getPassword());
		return acc;
	}
	
	public Person createPerson(Register entity) {
		Person per = new Person();
		per.setName(entity.getName());
		per.setSurname(entity.getSurname());
		per.setPhone(entity.getPhone());
		per.setEmail(entity.getEmail());
		return per;
	}

	public Account changeAccount(Person per, Register entity) {
		per.getAccount().setActive(true);
		per.getAccount().setDni(entity.getDni());
		per.getAccount().setLegajo(entity.getLegajo());
		per.getAccount().setPassword(entity.getPassword());
		return per.getAccount();
	}

	public Person changePerson(Person per, Register entity) {
		per.setName(entity.getName());
		per.setSurname(entity.getSurname());
		per.setEmail(entity.getEmail());
		per.setPhone(entity.getPhone());
		return per;
	}


}
