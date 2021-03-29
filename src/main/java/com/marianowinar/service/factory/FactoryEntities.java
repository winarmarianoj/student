package com.marianowinar.service.factory;

import com.marianowinar.model.Material;
import com.marianowinar.model.Person;
import com.marianowinar.model.Users;
import com.marianowinar.model.forms.Register;
import com.marianowinar.model.forms.ShareMaterial;

public class FactoryEntities {
	
	private static FactoryEntities fact;
	
	private FactoryEntities() {}
	
	public static FactoryEntities getInstance() {
		if(fact == null) fact = new FactoryEntities();
		
		return fact;
	}
	
	public Users createAccount(Register entity) {
		Users acc = new Users();
		acc.setEnabled(false);
		acc.setUsername(entity.getLegajo());
		acc.setPassword(entity.getPassword());
		return acc;
	}
	
	public Person createPerson(Register entity) {
		Person per = new Person();
		per.setDni(entity.getDni());
		per.setName(entity.getName());
		per.setSurname(entity.getSurname());
		per.setPhone(entity.getPhone());
		per.setEmail(entity.getEmail());
		return per;
	}

	public ShareMaterial createShare(Material ele) {
		ShareMaterial share = new ShareMaterial();
		share.setCapacity(ele.getCapacity());
		share.setDetail(ele.getDetail());
		share.setHour(ele.getHour());
		share.setName(ele.getName());
		share.setShare(0);
		share.setSubscribed(ele.getSubscribed());
		return share;
	}

}
