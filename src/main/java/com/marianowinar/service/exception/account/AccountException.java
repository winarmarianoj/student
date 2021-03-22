package com.marianowinar.service.exception.account;

@SuppressWarnings("serial")
public class AccountException extends Exception{

	protected int idError;
	protected String error;
	
	public AccountException() {}
	
	public AccountException(String message) {
        super(message);
    }

    public String getError(){
	    return this.error;
    }

    protected void setError(String message){
	    this.error = message;
    }
}
