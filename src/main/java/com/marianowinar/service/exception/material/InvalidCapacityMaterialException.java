package com.marianowinar.service.exception.material;

import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class InvalidCapacityMaterialException extends MaterialException{
	
	public InvalidCapacityMaterialException(String name){
        idError = 7;
        String currentTime = LocalDateTime.now().toString().replace("T", " ");
        setError("["+ currentTime +"] Error " + idError + " :" + name + " no es un nombre valido.");
    }

}
