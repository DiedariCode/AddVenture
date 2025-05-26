document.addEventListener("DOMContentLoaded", function () {
    // * Inputs del formulario
    const usernameInput = document.getElementById("nombreUsuario");
    const usernameStatus = document.getElementById("username-status");
    const advertenciaUsername = document.getElementById("advertencia-username");
    const emailActualInput = document.getElementById("email");

    // * Boton de guardar perfil 
    const botonGuardar = document.getElementById("save-profile-btn");
    botonGuardar.disabled = true; // inicialmente deshabilitado

    // * Elemento para mostrar errores de archivo
    const errorMensajeArchivo = document.getElementById("errorMensajeArchivo");

    // Lista de IDs de inputs a monitorear
    const campos = ["nombre", "apellido", "nombreUsuario", "country", "location", "birthDate", "bio"];
    
    // IDs de inputs de archivos
    const camposArchivos = ["cover-upload", "profile-upload"];

    // Guardamos los valores iniciales
    const valoresIniciales = {};
    campos.forEach(id => {
        const el = document.getElementById(id);
        if (el) valoresIniciales[id] = el.value;
    });

    // Estados iniciales de archivos (vacíos al inicio)
    const archivosIniciales = {};
    camposArchivos.forEach(id => {
        archivosIniciales[id] = false; // false = sin archivo seleccionado
    });

    function hayCambios() {
        // Verificar cambios en campos de texto/select
        const cambiosTexto = campos.some(id => {
            const el = document.getElementById(id);
            if (!el) return false;
            return el.value !== valoresIniciales[id];
        });

        // Verificar cambios en archivos
        const cambiosArchivos = camposArchivos.some(id => {
            const el = document.getElementById(id);
            if (!el) return false;
            const tieneArchivo = el.files && el.files.length > 0;
            return tieneArchivo !== archivosIniciales[id];
        });

        return cambiosTexto || cambiosArchivos;
    }

    // * Función para validar tamaño de archivo
    function validarTamanoArchivo(file) {
        const maxSize = 5 * 1024 * 1024; // 5 MB en bytes
        if (file.size > maxSize) {
            errorMensajeArchivo.textContent = `Error: El archivo "${file.name}" excede el tamaño máximo permitido de 5 MB. Tamaño actual: ${(file.size / (1024 * 1024)).toFixed(2)} MB`;
            errorMensajeArchivo.style.display = "block";
            botonGuardar.disabled = true;
            return false;
        } else {
            errorMensajeArchivo.textContent = "";
            errorMensajeArchivo.style.display = "none";
            return true;
        }
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

    // Cuando cambien los archivos, también chequeamos
    camposArchivos.forEach(id => {
        const el = document.getElementById(id);
        if (el) {
            el.addEventListener("change", () => {
                // Solo habilitamos si no hay errores de archivo y hay cambios
                if (errorMensajeArchivo.textContent === "") {
                    botonGuardar.disabled = !hayCambios();
                }
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


    // Preview imagen de portada
    const coverUpload = document.getElementById("cover-upload");
    const coverPreview = document.getElementById("cover-preview");
    const coverCurrentImg = document.getElementById("cover-current-img");
    const coverUploadIcon = document.getElementById("cover-upload-icon");

    coverUpload.addEventListener("change", function () {
        const file = this.files[0];
        if (file) {
            // Validar tamaño del archivo
            if (!validarTamanoArchivo(file)) {
                this.value = ""; // Limpiar el input
                return;
            }

            const reader = new FileReader();
            reader.onload = function (e) {
                // Ocultar la imagen actual y el ícono
                if (coverCurrentImg) coverCurrentImg.style.display = "none";
                if (coverUploadIcon) coverUploadIcon.style.display = "none";

                // Mostrar la imagen nueva como background
                coverPreview.style.backgroundImage = `url(${e.target.result})`;
                coverPreview.style.backgroundSize = "cover";
                coverPreview.style.backgroundPosition = "center";
                coverPreview.innerHTML = ""; // quitar iconos si hay
                
                // Verificar si habilitar el botón después de cargar la imagen
                botonGuardar.disabled = !hayCambios();
            };
            reader.readAsDataURL(file);
        } else {
            // Si no hay archivo seleccionado, volver a mostrar imagen actual o icono
            coverPreview.style.backgroundImage = "";
            if (coverCurrentImg) coverCurrentImg.style.display = "block";
            if (coverUploadIcon) coverUploadIcon.style.display = "block";

            coverPreview.innerHTML = coverCurrentImg ? "" : '<i class="bi bi-upload fs-3 text-secondary"></i>';
        }
    });

    // Preview imagen de perfil
    const profileUpload = document.getElementById("profile-upload");
    const profilePreview = document.getElementById("profile-preview");
    const profileCurrentImg = document.getElementById("profile-current-img");
    const avatarInitials = document.getElementById("avatar-initials");

    profileUpload.addEventListener("change", function () {
        const file = this.files[0];
        if (file) {
            // Validar tamaño del archivo
            if (!validarTamanoArchivo(file)) {
                this.value = ""; // Limpiar el input
                return;
            }

            const reader = new FileReader();
            reader.onload = function (e) {
                // Ocultar imagen actual e iniciales
                if (profileCurrentImg) profileCurrentImg.style.display = "none";
                if (avatarInitials) avatarInitials.style.display = "none";

                // Mostrar preview con background la imagen nueva
                profilePreview.style.backgroundImage = `url(${e.target.result})`;
                profilePreview.style.display = "block";
                profilePreview.style.backgroundSize = "cover";
                profilePreview.style.backgroundPosition = "center";
                
                // Verificar si habilitar el botón después de cargar la imagen
                botonGuardar.disabled = !hayCambios();
            };
            reader.readAsDataURL(file);
        } else {
            // Si no hay archivo seleccionado, mostrar imagen actual o iniciales
            profilePreview.style.backgroundImage = "";
            profilePreview.style.display = "none";

            if (profileCurrentImg) profileCurrentImg.style.display = "block";
            if (avatarInitials) avatarInitials.style.display = "block";
        }
    });
});