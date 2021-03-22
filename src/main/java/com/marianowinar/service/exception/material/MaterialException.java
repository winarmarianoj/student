package com.marianowinar.service.exception.material;

@SuppressWarnings("serial")
public class MaterialException extends Exception{
	protected int idError;
    protected String error;

    public MaterialException() {
    }

    public MaterialException(String message) {
        super(message);
    }

    public String getError(){
        return this.error;
    }

    protected void setError(String message){
        this.error = message;
    }

}
