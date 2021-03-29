// Funcion que me trae del Login HTML
function formLogin(btnLoginAdmin) {
    var dni = document.getElementById('dni').value
	let resLogin = op.isNotNullEmpty(dni,"El DNI");

    if (resLogin){sendingLoged(JSON.stringify(dni));}
}

// Funcion que envia el Post con los datos del logueo
function sendingLoged(dataLogin) {
    fetch('http://localhost:8082/admins/logueandoAdmin', {
        method: 'POST',
        body: dataLogin
    })
    .then(response => response.json()
    .then(data => {
        if (data) {
	       sessionStorage.setItem('user', JSON.stringify(data))  
            msg.correct()
            window.location.href = "../login.html"; 
        } else {
            throw 'Error en la llamada a Ajax en Login';
        }
    })    
    .catch(function(err) {
        op.saveErrorsList(err);
        msg.danger()
    }));

    
}

// funcion que me trae del Forgot HTML
function formForgot(btnForgot) {

    if(!verifyCaptcha()){
        msg.invalidCaptcha()
        return
    }

    var pass2 = document.getElementById('password2').value;

    var forgot = {
        email : document.getElementById('email').value,
        password : document.getElementById('password1').value
    };    	
    let resForgot = op.isNotNullEmpty(forgot.email,"El Email");
    resForgot &= op.isEmailCorrect(forgot.email,"El Email");
    resForgot &= op.isNotNullEmpty(forgot.password,"La Contraseña");
    resForgot &= op.isPassCorrect(forgot.password,"La Contraseña");
    resForgot &= op.isPasswordEquals(forgot.password,pass2);

    if (resForgot){sendingForgot(JSON.stringify(forgot));}        
}
function sendingForgot(dataForgot) {    
    fetch('http://localhost:8080/Devs/rest/account/forgot', {
        method: 'POST',
        body: dataForgot
    })
    .then(response => response.json()
    .then(data => {
        if (data) {
            msg.correct()
            window.location.href = "../login.html"; 
        } else {
            throw 'Error en la llamada a Ajax en Login';
        }
    })    
    .catch(function(err) {
        op.saveErrorsList(err);
        msg.danger()
    }));
}
