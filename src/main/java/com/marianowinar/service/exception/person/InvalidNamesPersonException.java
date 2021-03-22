package com.marianowinar.service.exception.person;

import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class InvalidNamesPersonException extends PersonException{
	
	public InvalidNamesPersonException(String name){
        idError = 6;
        String currentTime = LocalDateTime.now().toString().replace("T", " ");
        setError("["+ currentTime +"] Error " + idError + " :" + name + " no es un nombre valido.");
    }
}
