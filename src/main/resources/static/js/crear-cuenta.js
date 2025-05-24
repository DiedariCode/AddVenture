document.addEventListener("DOMContentLoaded", function () {
    // Referencias a elementos
    const steps = document.querySelectorAll(".form-step");
    const stepIndicators = document.querySelectorAll(".step");
    const stepDescription = document.getElementById("step-description");
    const step1Footer = document.getElementById("step-1-footer");

    // Botones de navegación
    const continueToStep2 = document.getElementById("continue-to-step-2");
    const verifyEmail = document.getElementById("verify-email");
    const verifyPhone = document.getElementById("verify-phone");
    const completeRegistration = document.getElementById("complete-registration");

    // Botones de reenvío de código
    const resendEmailCode = document.getElementById("resend-email-code");
    const resendPhoneCode = document.getElementById("resend-phone-code");

    // Contadores de reenvío
    const emailCountdown = document.getElementById("email-countdown");
    const phoneCountdown = document.getElementById("phone-countdown");

    // Mostrar/ocultar contraseña
    const togglePassword = document.getElementById("toggle-password");
    const passwordInput = document.getElementById("password");

    // Campos de visualización
    const emailDisplay = document.getElementById("email-display");
    const phoneDisplay = document.getElementById("phone-display");

    // Verificación de nombre de usuario
    const usernameInput = document.getElementById("nombreUsuario");
    const usernameStatus = document.getElementById("username-status");

    // Cambio de código de país
    const countrySelect = document.getElementById("pais");
    const countryCode = document.getElementById("country-code");

    // Función para cambiar de paso
    function goToStep(stepNumber) {
        // Ocultar todos los pasos
        steps.forEach((step) => step.classList.remove("active"));

        // Mostrar el paso actual
        document.getElementById(`step-${stepNumber}`).classList.add("active");

        // Actualizar indicadores de paso
        stepIndicators.forEach((indicator, index) => {
            if (index + 1 < stepNumber) {
                indicator.classList.remove("active");
                indicator.classList.add("completed");
            } else if (index + 1 === stepNumber) {
                indicator.classList.add("active");
                indicator.classList.remove("completed");
            } else {
                indicator.classList.remove("active", "completed");
            }
        });

        // Actualizar descripción del paso
        switch (stepNumber) {
            case 1:
                stepDescription.textContent =
                    "Completa tus datos personales para comenzar";
                step1Footer.style.display = "block";
                break;
            case 2:
                stepDescription.textContent = "Verifica tu dirección de email";
                step1Footer.style.display = "none";
                break;
            case 3:
                stepDescription.textContent = "Verifica tu número de teléfono";
                step1Footer.style.display = "none";
                break;
            case 4:
                stepDescription.textContent = "¡Tu cuenta está lista!";
                step1Footer.style.display = "none";
                break;
        }
    }

    // Validar formulario de información personal
    function validatePersonalInfo() {
        const form = document.getElementById("personal-info-form");
        const inputs = form.querySelectorAll("input, select");
        let isValid = true;

        inputs.forEach((input) => {
            if (input.hasAttribute("required") && !input.value) {
                input.classList.add("is-invalid");
                isValid = false;
            } else {
                input.classList.remove("is-invalid");
            }
        });

        // Validar contraseñas
        const password = document.getElementById("password");
        const confirmPassword = document.getElementById("confirmPassword");

        if (password.value.length < 8) {
            password.classList.add("is-invalid");
            isValid = false;
        }

        if (password.value !== confirmPassword.value) {
            confirmPassword.classList.add("is-invalid");
            isValid = false;
        }

        // Validar fecha de nacimiento (mayor de 18 años)
        const birthDate = new Date(
            document.getElementById("fechaNacimiento").value
        );
        const today = new Date();
        let age = today.getFullYear() - birthDate.getFullYear();
        const monthDiff = today.getMonth() - birthDate.getMonth();

        if (
            monthDiff < 0 ||
            (monthDiff === 0 && today.getDate() < birthDate.getDate())
        ) {
            age--;
        }

        if (age < 18) {
            document.getElementById("fechaNacimiento").classList.add("is-invalid");
            isValid = false;
        }

        return isValid;
    }

    // Función para iniciar cuenta regresiva
    function startCountdown(element, callback) {
        let seconds = 60;
        element.style.display = "block";
        element.textContent = `Reenviar código (${seconds}s)`;

        const interval = setInterval(() => {
            seconds--;
            element.textContent = `Reenviar código (${seconds}s)`;

            if (seconds <= 0) {
                clearInterval(interval);
                element.style.display = "none";
                callback();
            }
        }, 1000);
    }

    // Eventos de navegación
    continueToStep2.addEventListener("click", function () {
        if (validatePersonalInfo()) {
            // Actualizar email en la pantalla de verificación
            emailDisplay.textContent = document.getElementById("email").value;

            // Ir al paso 2
            goToStep(2);

            // Iniciar cuenta regresiva para reenvío
            startCountdown(emailCountdown, function () {
                resendEmailCode.disabled = false;
            });
        }
    });

    verifyEmail.addEventListener("click", function () {
        const emailCode = document.getElementById("emailCode").value;

        // Simulación: código correcto es "123456"
        if (emailCode === "123456") {
            // Actualizar teléfono en la pantalla de verificación
            const countryCodeText =
                document.getElementById("country-code").textContent;
            const phoneNumber = document.getElementById("telefono").value;
            phoneDisplay.textContent = `${countryCodeText} ${phoneNumber}`;

            // Ir al paso 3
            goToStep(3);

            // Iniciar cuenta regresiva para reenvío
            startCountdown(phoneCountdown, function () {
                resendPhoneCode.disabled = false;
            });
        } else {
            // Mostrar error
            document.getElementById("emailCode").classList.add("is-invalid");
        }
    });

    verifyPhone.addEventListener("click", function () {
        const phoneCode = document.getElementById("phoneCode").value;

        // Simulación: código correcto es "654321"
        if (phoneCode === "654321") {
            // Ir al paso 4
            goToStep(4);
        } else {
            // Mostrar error
            document.getElementById("phoneCode").classList.add("is-invalid");
        }
    });

    completeRegistration.addEventListener("click", function () {
        // Simulación de registro completado
        window.location.href = "BuscarGrupos.html";
    });

    // Eventos de reenvío de código
    resendEmailCode.addEventListener("click", function () {
        this.disabled = true;
        // Simulación de reenvío
        startCountdown(emailCountdown, function () {
            resendEmailCode.disabled = false;
        });
    });

    resendPhoneCode.addEventListener("click", function () {
        this.disabled = true;
        // Simulación de reenvío
        startCountdown(phoneCountdown, function () {
            resendPhoneCode.disabled = false;
        });
    });

    // Mostrar/ocultar contraseña
    togglePassword.addEventListener("click", function () {
        const type =
            passwordInput.getAttribute("type") === "password" ? "text" : "password";
        passwordInput.setAttribute("type", type);
        this.querySelector("i").classList.toggle("fa-eye");
        this.querySelector("i").classList.toggle("fa-eye-slash");
    });

    // Verificación de nombre de usuario
    usernameInput.addEventListener("input", function () {
        const username = this.value;

        if (username.length >= 3) {
            // Simulación de verificación
            setTimeout(() => {
                // Lista de nombres de usuario ya tomados
                const takenUsernames = [
                    "usuario_popular",
                    "viajero",
                    "aventurero",
                    "maria_viajera",
                    "juan_viajes",
                ];

                // Verificar si el nombre de usuario está disponible
                const isAvailable = !takenUsernames.includes(username.replace("@", ""));

                if (isAvailable) {
                    usernameStatus.innerHTML =
                        '<i class="fas fa-check-circle text-success"></i>';
                } else {
                    usernameStatus.innerHTML =
                        '<i class="fas fa-exclamation-triangle text-danger"></i>';
                }
            }, 500);
        } else {
            usernameStatus.innerHTML = "";
        }

        // Añadir @ si no lo tiene
        if (username && !username.startsWith("@")) {
            this.value = "@" + username;
        }
    });

    // Cambio de código de país
    countrySelect.addEventListener("change", function () {
        const country = this.value;

        // Actualizar código de país según la selección
        switch (country) {
            case "Perú":
                countryCode.textContent = "+51";
                break;
            default:
                countryCode.textContent = "+51";
        }
    });
});


// const data = {
//     nombre: document.getElementById("nombre").value,
//     apellidos: document.getElementById("apellido").value,
//     nombreUsuario: document.getElementById("nombreUsuario").value,
//     email: document.getElementById("email").value,
//     telefono: document.getElementById("telefono").value,
//     pais: document.getElementById("pais").value,
//     ciudad: document.getElementById("ciudad").value,
//     fechaNacimiento: document.getElementById("fechaNacimiento").value,
//     contrasena: document.getElementById("password").value
// };

// fetch("/api/usuarios/registrar", {
//     method: "POST",
//     headers: {
//         "Content-Type": "application/json"
//     },
//     body: JSON.stringify(data)
// })
//     .then(response => {
//         if (response.ok) {
//             // Avanzar a paso 2, etc.
//         } else {
//             alert("Error al registrar");
//         }
//     });
