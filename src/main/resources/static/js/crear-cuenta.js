document.addEventListener("DOMContentLoaded", function () {
    // === REFERENCIA BOTON PASO 1 ===
    const botonSiguiente = document.getElementById('step-1-next');

    // === Mostrar/ocultar contraseña ===
    const togglePassword = document.getElementById("toggle-password");
    const passwordInput = document.getElementById("password");
    const confirmPasswordInput = document.getElementById("confirmPassword");

    togglePassword.addEventListener("click", function () {
        const type = passwordInput.getAttribute("type") === "password" ? "text" : "password";
        passwordInput.setAttribute("type", type);
        confirmPasswordInput.setAttribute("type", type);

        this.querySelector("i").classList.toggle("fa-eye");
        this.querySelector("i").classList.toggle("fa-eye-slash");
    });

    // === FUNCION PARA CAMBIAR PASO ===
    function cambiarPaso(pasoActual, pasoSiguiente) {
        // Oculta la sección actual y muestra la siguiente
        document.getElementById(`step-${pasoActual}`).classList.remove('active');
        document.getElementById(`step-${pasoSiguiente}`).classList.add('active');

        // Actualiza los indicadores visuales
        document.getElementById(`step-indicator-${pasoActual}`).classList.remove('active');
        document.getElementById(`step-indicator-${pasoSiguiente}`).classList.add('active');
    }

    // === Verificación dinámica de nombre de usuario ===
    const usernameInput = document.getElementById("nombreUsuario");
    const usernameStatus = document.getElementById("username-status");
    const advertenciaUsername = document.getElementById("advertencia-username");
    usernameInput.addEventListener("input", function () {
        let username = this.value.trim();

        if (!username.startsWith("@")) {
            username = "@" + username.replace(/@/g, "");
            this.value = username;
        }

        if (username.length >= 4) {
            fetch(`/api/usuarios/existe-username?username=${encodeURIComponent(username)}`)
                .then(res => res.json())
                .then(existe => {
                    if (!existe) {
                        usernameStatus.innerHTML = '<i class="fas fa-check-circle text-success"></i>';
                        advertenciaUsername.style.display = "none";
                        botonSiguiente.disabled = false; // ✅ Habilita si no existe
                    } else {
                        usernameStatus.innerHTML = '<i class="fas fa-exclamation-triangle text-danger"></i>';
                        advertenciaUsername.style.display = "block";
                        botonSiguiente.disabled = true; // ❌ Deshabilita si ya existe
                    }
                })
                .catch(error => {
                    console.error("Error al verificar username:", error);
                    usernameStatus.innerHTML = '<i class="fas fa-times-circle text-warning"></i>';
                    advertenciaUsername.style.display = "none";
                });
        } else {
            usernameStatus.innerHTML = "";
            advertenciaUsername.style.display = "none";
        }
    });

    // === Verificación de correo electrónico ===
    document.getElementById('email').addEventListener('blur', async function () {
        const email = this.value.trim();
        if (!email) return;

        try {
            const res = await fetch(`/api/usuarios/existe-email?email=${encodeURIComponent(email)}`);
            const existe = await res.json();

            const emailStatus = document.getElementById("email-status");
            emailStatus.classList.remove("d-none");

            const advertenciaEmail = document.getElementById("advertencia-email");

            if (!existe) {
                emailStatus.innerHTML = '<i class="fas fa-check-circle text-success"></i>';
                advertenciaEmail.style.display = "none";
                botonSiguiente.disabled = false;
            } else {
                emailStatus.innerHTML = '<i class="fas fa-exclamation-triangle text-danger"></i>';
                advertenciaEmail.style.display = "block";
                botonSiguiente.disabled = true;
            }
        } catch (error) {
            console.error("Error al verificar correo:", error);
        }
    });

    // === Configurar rango válido para fecha de nacimiento (edad 18-35 años) ===
    const fechaInput = document.getElementById('fechaNacimiento');
    if (fechaInput) {
        const today = new Date();

        // Fecha máxima: hace 18 años (edad mínima)
        const maxDate = new Date(today.getFullYear() - 18, today.getMonth(), today.getDate());

        // Fecha mínima: hace 35 años (edad máxima)
        const minDate = new Date(today.getFullYear() - 35, today.getMonth(), today.getDate());

        // Función para formatear a YYYY-MM-DD
        const formatDate = (date) => date.toISOString().split('T')[0];

        fechaInput.setAttribute('max', formatDate(maxDate));
        fechaInput.setAttribute('min', formatDate(minDate));
    }

    // === Validación del teléfono ===
    const telefonoInput = document.getElementById('telefono');
    const advertenciaTelefono = document.getElementById('advertencia-telefono');
    const telefonoStatus = document.getElementById('telefono-status');
    telefonoInput.addEventListener('input', function () {
        const numero = this.value.trim();
        if (numero.length >= 9) {
            console.log("Número de teléfono:", numero);
            console.log(validarTelefono(numero));
            if (validarTelefono(numero)) {
                telefonoStatus.innerHTML = '<i class="fas fa-check-circle text-success"></i>';
                advertenciaTelefono.style.display = "none";
                botonSiguiente.disabled = false; // ✅ Habilita si es válido
            } else {
                telefonoStatus.innerHTML = '<i class="fas fa-exclamation-triangle text-danger"></i>';
                advertenciaTelefono.style.display = "block";
                botonSiguiente.disabled = true; // ❌ Deshabilita si no es válido
            }
        } else {
            telefonoStatus.innerHTML = '<i class="fas fa-exclamation-triangle text-danger"></i>';
            advertenciaTelefono.style.display = "block";
            botonSiguiente.disabled = true; // ❌ Deshabilita si no es válido
        }
    });

    function validarTelefono(numero) {
        // Elimina espacios y guiones
        const tel = numero.replace(/\s+/g, '').replace(/-/g, '');

        // Regex para teléfono peruano: 9 dígitos que empiezan con 6,7,9
        // (sin contar el código +51)
        const regexPeru = /^[679]\d{8}$/;

        return regexPeru.test(tel);
    }

    // === Envío de código de verificación por correo ===
    document.getElementById('step-1-next').addEventListener('click', async function () {
        const email = document.getElementById('email').value;
        const nombre = document.getElementById('nombre').value;
        const apellido = document.getElementById('apellido').value;

        if (!email || !nombre || !apellido) {
            alert('Por favor, completa todos los campos obligatorios.');
            return;
        }

        try {
            const response = await fetch(`/api/verification/sendCode?email=${encodeURIComponent(email)}`, {
                method: 'POST'
            });

            const data = await response.json();

            if (response.ok) {
                document.getElementById('email-display').innerText = email;

                // Usa la función para cambiar de paso visual y sección
                cambiarPaso(1, 2);
            } else {
                alert(data.message || 'Error al enviar el código de verificación.');
            }
        } catch (error) {
            console.error('Error al enviar código:', error);
            alert('Ocurrió un error de red. Intenta nuevamente.');
        }
    });


    // === Validar código de verificación ===
    document.getElementById('verify-email').addEventListener('click', async function () {
        const code = document.getElementById('emailCode').value.trim();
        const email = document.getElementById('email-display').innerText;

        if (!code) {
            alert("Por favor, ingresa el código de verificación.");
            return;
        }

        try {
            const response = await fetch('/api/verification/verifyCode', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email, code })
            });

            const data = await response.json();

            if (data.verified) {
                alert("✅ Código verificado correctamente. Puedes continuar con el registro.");
                cambiarPaso(2, 3);
            } else {
                alert("❌ Código incorrecto o expirado. Intenta nuevamente.");
            }
        } catch (err) {
            console.error("Error al verificar código:", err);
            alert("Error de red al verificar el código.");
        }
    });

    // === Enviar formulario final ===
    document.getElementById('complete-registration').addEventListener('click', function () {
        document.getElementById('registro-form').submit();
    });
});
