package com.marianowinar.service.exception.account;

import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class InvalidPasswordAccountException extends AccountException{
	public InvalidPasswordAccountException(String password) {
        idError = 5;
        String currentTime = LocalDateTime.now().toString().replace("T", " ");
        setError("["+ currentTime +"] Error " + idError + ": Cuenta invalida: " + password + " no es una contraseña valida.");
    }

}
