document.addEventListener("DOMContentLoaded", function () {
    // * Inputs del formulario
    const usernameInput = document.getElementById("nombreUsuario");
    const usernameStatus = document.getElementById("username-status");
    const advertenciaUsername = document.getElementById("advertencia-username");
    const emailActualInput = document.getElementById("email");

    // * Boton de guardar perfil 
    const botonGuardar = document.getElementById("save-profile-btn");
    botonGuardar.disabled = true; // inicialmente deshabilitado

    // Lista de IDs de inputs a monitorear
    const campos = ["nombre", "apellido", "nombreUsuario", "country", "location", "birthDate", "bio"];

    // Guardamos los valores iniciales
    const valoresIniciales = {};
    campos.forEach(id => {
        const el = document.getElementById(id);
        if (el) valoresIniciales[id] = el.value;
    });

    function hayCambios() {
        return campos.some(id => {
            const el = document.getElementById(id);
            if (!el) return false;
            return el.value !== valoresIniciales[id];
        });
    }

    // Cuando cambie cualquier input, chequeamos si habilitar el botón
    campos.forEach(id => {
        const el = document.getElementById(id);
        if (el) {
            el.addEventListener("input", () => {
                botonGuardar.disabled = !hayCambios();
            });
        }
    });

    // * Validación del username

    usernameInput.addEventListener("input", function () {
        let username = this.value.trim();
        const emailActual = emailActualInput.value.trim();

        if (!username.startsWith("@")) {
            username = "@" + username.replace(/@/g, "");
            this.value = username;
        }

        if (username.length >= 6 && emailActual !== "") {
            fetch(`/api/usuarios/validar-username-edicion?username=${encodeURIComponent(username)}&emailActual=${encodeURIComponent(emailActual)}`)
                .then(res => res.json())
                .then(existe => {
                    if (!existe) {
                        usernameStatus.innerHTML = '<i class="fas fa-check-circle text-success"></i>';
                        advertenciaUsername.style.display = "none";
                        usernameInput.classList.remove("is-invalid");
                        usernameInput.classList.add("is-valid");
                        botonGuardar.disabled = false;
                    } else {
                        usernameStatus.innerHTML = '<i class="fas fa-exclamation-triangle text-danger"></i>';
                        advertenciaUsername.style.display = "block";
                        usernameInput.classList.add("is-invalid");
                        usernameInput.classList.remove("is-valid");
                        botonGuardar.disabled = true;
                    }
                })

                .catch(error => {
                    console.error("Error al verificar username:", error);
                    usernameStatus.innerHTML = '<i class="fas fa-times-circle text-warning"></i>';
                    advertenciaUsername.style.display = "none";
                    usernameInput.classList.remove("is-valid", "is-invalid");
                    botonGuardar.disabled = true;
                });
        } else {
            usernameStatus.innerHTML = '<i class="fas fa-exclamation-triangle text-danger"></i>';
            advertenciaUsername.style.display = "block";
            usernameInput.classList.add("is-invalid");
            usernameInput.classList.remove("is-valid");
            advertenciaUsername.textContent = "El nombre de usuario debe tener al menos 5 caracteres y no puede estar vacío.";
            botonGuardar.disabled = true;
        }
    });
});
