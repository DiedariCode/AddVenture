document.addEventListener("DOMContentLoaded", function () {
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
                    } else {
                        usernameStatus.innerHTML = '<i class="fas fa-exclamation-triangle text-danger"></i>';
                        advertenciaUsername.style.display = "block";
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
                document.getElementById('step-1').classList.remove('active');
                document.getElementById('step-2').classList.add('active');
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
                document.getElementById('step-2').classList.remove('active');
                document.getElementById('step-4').classList.add('active');
            } else {
                alert("❌ Código incorrecto o expirado. Intenta nuevamente.");
            }
        } catch (err) {
            console.error("Error al verificar código:", err);
            alert("Error de red al verificar el código.");
        }
    });

    // === Enviar formulario final ===
    document.getElementById('complete-registration').addEventListener('click', function() {
      document.getElementById('registro-form').submit();
    });
});
