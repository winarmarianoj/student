package com.marianowinar.service.validator;

import java.util.regex.Pattern;

import com.marianowinar.service.exception.account.InvalidPasswordAccountException;

public class ValidPass {
	
	private static ValidPass validPass;
	
	private ValidPass() {}
	
	public static ValidPass getInstance() {
		if(validPass == null) validPass = new ValidPass();
		
		return validPass;
	}
	
	 public boolean validatePass(String password) throws InvalidPasswordAccountException {
		 boolean res = true;
        if (password == null) { 
        	res = false; 
        	throw new InvalidPasswordAccountException("null");
        }
        
        if (!Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", password)) {
        	res = false; 
        	throw new InvalidPasswordAccountException(password);
        }
        return res;
    }
}
