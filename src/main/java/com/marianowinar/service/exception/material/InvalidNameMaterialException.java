package com.marianowinar.service.exception.material;

import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class InvalidNameMaterialException extends MaterialException{
	public InvalidNameMaterialException(String name){
        idError = 8;
        String currentTime = LocalDateTime.now().toString().replace("T", " ");
        setError("["+ currentTime +"] Error " + idError + " :" + name + " no es un nombre valido.");
    }
}
