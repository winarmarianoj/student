package com.marianowinar.service.validator;

import com.marianowinar.model.Users;
import com.marianowinar.service.exception.account.NullAccountException;

public class ValidAccount {
	
	private static ValidAccount validAccount;
	
	private ValidAccount() {}
	
	public static ValidAccount getInstance() {
		if(validAccount == null) validAccount = new ValidAccount();
		
		return validAccount;
	}
	
	public boolean validCreateAccount(Users aco) throws NullAccountException{
		boolean res = validName(aco.getUsername());
		
		if(!res) {
			throw new NullAccountException();
		}
		
		return res;
	}
	
	private boolean validName(String name) {return name != null;}

}
