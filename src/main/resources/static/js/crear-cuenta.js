document.addEventListener("DOMContentLoaded", function () {
    // * Verificación de nombre de usuario
    const usernameInput = document.getElementById("nombreUsuario");
    const usernameStatus = document.getElementById("username-status");

    usernameInput.addEventListener("input", function () {
        let username = this.value.trim(); // eliminar espacios

        // Agregar @ automáticamente si no lo tiene
        if (!username.startsWith("@")) {
            username = "@" + username.replace(/@/g, "");
            this.value = username;
        }

        // Validar mínimo de longitud: @ + 3 caracteres
        if (username.length >= 4) {
            fetch(`/api/usuarios/existe-username?username=${encodeURIComponent(username)}`)

                .then(res => res.json())
                .then(existe => {
                    // Si existe, mostrar advertencia
                    if (!existe) {
                        usernameStatus.innerHTML = '<i class="fas fa-check-circle text-success"></i>';
                        document.getElementById("advertencia-username").style.display = "none";
                    } else {
                        usernameStatus.innerHTML = '<i class="fas fa-exclamation-triangle text-danger"></i>';
                        document.getElementById("advertencia-username").style.display = "block";
                    }
                })
                .catch(error => {
                    console.error("Error al verificar username:", error);
                    usernameStatus.innerHTML = '<i class="fas fa-times-circle text-warning"></i>';
                    document.getElementById("advertencia-username").style.display = "none";
                });
        } else {
            usernameStatus.innerHTML = "";
            document.getElementById("advertencia-username").style.display = "none";
        }
    });

    // * Verificación de correo electrónico
    document.getElementById('step-1-next').addEventListener('click', async function () {
        const emailInput = document.getElementById('email');
        const email = emailInput.value;

        // Validar campos básicos antes de continuar
        if (!email || !document.getElementById('nombre').value || !document.getElementById('apellido').value) {
            alert('Por favor, completa todos los campos obligatorios.');
            return;
        }

        // Aquí puedes hacer una petición AJAX al backend para enviar el código
        try {
            const response = await fetch('/usuarios/enviar-codigo-email', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email: email })
            });

            if (response.ok) {
                // Guardar el email mostrado
                document.getElementById('email-display').innerText = email;

                // Ocultar Step 1 y mostrar Step 2
                document.getElementById('step-1').classList.remove('active');
                document.getElementById('step-2').classList.add('active');
            } else {
                const errorData = await response.json();
                alert(errorData.message || 'Error al enviar el código de verificación.');
            }
        } catch (err) {
            alert('Ocurrió un error de red. Intenta nuevamente.');
        }
    });
});




