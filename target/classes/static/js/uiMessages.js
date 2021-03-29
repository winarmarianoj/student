class UIMessages {
    showMessage(info, cssClass) {
        const linksList = document.getElementById('links-list');
        const element = document.createElement("div");
        element.className = `alert alert-${cssClass} mt-2`;
        element.innerHTML = info;
        linksList.appendChild(element);

        // Remove the Message after al segundo y medio
        setTimeout(function () {
            document.querySelector(".alert").remove();
        }, 2500);
    }

    danger() {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: '¡Los datos son incorrectos! Debes hacerlo nuevamente. Gracias.',
        });
    }

    invalidAdd(text) {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: text,
        });
    }

    correct() {
        Swal.fire({
            type: 'success',
            title: 'Éxito',
            text: '¡Perfecto!',
        });
    }

    noStorage() {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: '¡No puedes utilizar Local Storage para almacenar imágenes :(',
        });
    }

    withImg() {
        Swal.fire({
            imageUrl: '../img/logo.png',
            imageHeight: 200,
            imageAlt: 'A tall image',
            title: 'Welcome',
        });
    }

    invalidNull(text) {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: text,
        });
    }

    invalidName(text1) {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: text1,
        });
    }

    invalidEmail(text2) {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: text2,
        });
    }
    invalidEqualsEmail(text3) {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: text3,
        });
    }
    invalidPass(text4) {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: text4,
        });
    }
    invalidEqualsPass(text5) {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: text5,
        });
    }
    invalidNumber(text6) {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: text6,
        });
    }
    invalidYear(text7) {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: text7,
        });
    }
    invalidMonth(text8) {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: text8,
        });
    }
    invalidDay(text9) {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: text9,
        });
    }
    invalidBisiesto(text10) {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: text10,
        });
    }
    linksEmpty(){
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: 'No contienes ningún elemento en tu lista de Links.',
        });
    }

    invalidPassword(text11) {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: text11,
        });
    }

    invalidCaptcha() {
        Swal.fire({
            type: 'error',
            title: 'Error',
            text: 'Debe completar el captcha.',
        });
    }
}
const msg = new UIMessages();