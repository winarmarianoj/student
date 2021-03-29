const emailRegex = /^([a-zA-Z0-9-_.ñ]+)@([a-zA-Z0-9-_.ñ]+).([a-zA-Z]{2,5})$/;
const passRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/;
const namesRegex = /^([a-zA-ZñÑ])+$/;
const numberRegex = /^([0-9])+$/;
var errorsList = [];

class UIFunctions {

	// Funcion que supervisa que el campo no este nulo ni vacío
	isNotNullEmpty(text, typeText) {
	    let resul = true;
	    if(text===null || text===' '){
	    	setTimeout(msg.invalidNull(typeText + 'Esta vacío. Ingrese los datos por favor.'),2000);
	    	resul=false;
	    }	    
	    return resul;
	}

	// Funcion que supervisa el ingreso de Name y lastName
	isNameCorrect(namesAndSureName, typeNames) {	 
	    let resName = false;
	    if (namesRegex.test(namesAndSureName)){
	    	resName = true;
	    } else{
	    	setTimeout(msg.invalidName(typeNames + ' ' + 'Debe contener solo letras.'), 2000);
	    }
	    return resName;
	}

	// Funcion que supervisa si el email cumple con los reqmsgsitos
	isEmailCorrect(emailAddRegister, typeEmail){
	    let resEmail = false;
	    if (emailRegex.test(emailAddRegister)){
	    	resEmail = true;
	    } else{
	    	setTimeout(msg.invalidEmail(typeEmail + ' ' + 'Email ingresado incorrectamente. Intentelo nuevamente por favor.'),2000);
	    }
	    return resEmail;
 	}

 	//Funcion que valida si ambos emails ingresados son iguales o no
 	isEmailEquals(uno, dos){
 		let emailEquals = false;
 		if (uno == dos){
 			emailEquals = true;
 		} else{
 			setTimeout(msg.invalidEqualsEmail('Los campos de Emails no son iguales. Ingreselos nuevamente por favor.'),2000);
 		}
 		return emailEquals;
 	}

	// Funcion que supervisa si el password cumple con los reqmsgsitos
	isPassCorrect(passView, typePass){
		let resPass = false;
		if (passRegex.test(passView)){
			resPass = true;
		} else{
			setTimeout(msg.invalidPass(typePass + ' ' + 'Debe contener Números, Letras Mayúsculas y Minúsculas, algunos caracteres.'),2000);
		}
	    return resPass;
	}

	// Funcion que supervisa si ambos password ingresados son iguales o no
	isPasswordEquals(p1,p2){
		let equalsPass = false;
		if (p1 == p2){
			equalsPass = true;
		} else {
			setTimeout(msg. invalidEqualsPass('Los campos de Password no son iguales. Ingreselos nuevamente por favor.'),2000);
		}
		return equalsPass;
	}

	// Funcion que supervisa que los numeros asi lo sean
	isNumber(num){
	    let resNumber = false;
	    if (numberRegex.test(num)){
	    	resNumber = true;
	    } else{
	    	setTimeout(msg.invalidNumber('Los datos deben ser numéricos.'),2000);
	    }	
	    return resNumber;
	}

	// Funcion que revisa toda la fecha por cada campo y condición
	isFecha(checkin){
	    let resulFecha = true;
	    if(checkin.year<1915 || checkin.year>2005){setTimeout(msg.invalidYear('Debes ingresar un año válido. No se permiten menores de 15 años.'),2000);resulFecha=false;}
	    if(checkin.month<=0 || checkin.month>12){setTimeout(msg.invalidMonth('Debes ingresar un mes válido. Son del 1 al 12'),2000);resulFecha=false;}
	    if(checkin.day<=0 || checkin.day>31){setTimeout(msg.invalidDay('Debes ingresar un día válido. Son del 1 al 31.'),2000);resulFecha=false;}
	    if(checkin.month == 2 && checkin.day == 29){
	        if(!isBisiesto(checkin.year)){
	            setTimeout(msg.invalidBisiesto('El año ingresado no es bisiesto. Revise la fecha completa. Gracias.'),2000);
	            resulFecha=false;
	        }
	    }
	    return resulFecha;
	}

	isFechaComplete(checkin){
		let resulFecha = true;
		if(checkin.birthdate.year<1915 || checkin.birthdate.year>2005){setTimeout(msg.invalidYear('Debes ingresar un año válido. No se permiten menores de 15 años.'),2000);resulFecha=false;}
		if(checkin.birthdate.month<=0 || checkin.birthdate.month>12){setTimeout(msg.invalidMonth('Debes ingresar un mes válido. Son del 1 al 12'),2000);resulFecha=false;}
		if(checkin.birthdate.day<=0 || checkin.birthdate.day>31){setTimeout(msg.invalidDay('Debes ingresar un día válido. Son del 1 al 31.'),2000);resulFecha=false;}
		if(checkin.birthdate.month == 2 && checkin.birthdate.day == 29){
			if(!isBisiesto(checkin.birthdate.year)){
				setTimeout(msg.invalidBisiesto('El año ingresado no es bisiesto. Revise la fecha completa. Gracias.'),2000);
				resulFecha=false;
			}
		}
		return resulFecha;
	}


	// Funcion que analiza un año bisiesto o no
	isBisiesto(years){
	    return years % 100 === 0? years % 400 === 0 : years % 4 === 0;
	}

	// Funcion que agrega errores a la lista de errores
	saveErrorsList(text){
		errorsList.push(text)
		sessionStorage.setItem('errores', JSON.stringify(errorsList));
	}

	// Devuelve la lista de errores guardada temporalmente en sessionStorage
	getErrorList(){
		var auxError = sessionStorage.getItem('errores');
		var getErrorList = [];
		if (auxError == null){
			getErrorList = [];
		} else {
			getErrorList = JSON.parse(auxError);			
		}
		return getErrorList;
	}

}
const op = new UIFunctions();
