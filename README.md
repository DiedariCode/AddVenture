# 👥 Guía para Colaboradores – AddVenture

¡Bienvenido al proyecto **AddVenture**! 🌍✈️  
Este documento te ayudará a trabajar correctamente con Git y las ramas del repositorio.

---

## 📦 Clonar el proyecto

```bash
git clone https://github.com/DiedariCode/AddVenture.git
cd AddVenture
```

---

## 🌿 Ver ramas disponibles en GitHub

Después de clonar, solo tendrás la rama `main`. Para ver todas las ramas remotas:

```bash
git fetch --all
git branch -r
```

---

## 🔄 Cambiarte a una rama remota

Por ejemplo, para trabajar en la rama `rama-castro`:

```bash
git checkout rama-castro
```

Esto crea una rama local conectada a `origin/rama-castro`.

---

## 🌱 Crear una nueva rama (si necesitas una nueva funcionalidad)

```bash
git checkout main
git pull origin main
git checkout -b feature/nombre-de-tu-rama
git push -u origin feature/nombre-de-tu-rama
```

---

## 💾 Comandos Git básicos

| Acción                         | Comando                                      |
|--------------------------------|----------------------------------------------|
| Ver ramas locales              | `git branch`                                 |
| Ver ramas remotas              | `git branch -r`                              |
| Ver todas las ramas            | `git branch -a`                              |
| Cambiar de rama                | `git checkout nombre-rama`                   |
| Crear nueva rama               | `git checkout -b nueva-rama`                 |
| Subir rama al repositorio      | `git push -u origin nombre-rama`             |
| Descargar cambios sin mezclar  | `git fetch`                                  |
| Descargar y mezclar cambios    | `git pull`                                   |
| Subir tus cambios              | `git push`                                   |
| Ver estado de archivos         | `git status`                                 |
| Agregar archivos al commit     | `git add .`                                  |
| Hacer commit con mensaje       | `git commit -m "Mensaje claro del cambio"`   |
| Ver historial de commits       | `git log --oneline --graph --all`            |

---

## 🧠 Consejos

- ✅ Siempre haz `git pull` antes de empezar a trabajar.
- 🧪 Usa nombres de rama descriptivos como `feature/registro-usuarios` o `fix/validacion-email`.
- 🚫 Nunca trabajes directamente en `main`, siempre usa ramas.
- ❓ Si dudas, crea una rama y pregunta antes de hacer cambios grandes.

---

## 🆘 ¿Dudas?

Habla con el equipo o revisa esta guía.  
¡Feliz colaboración! 😄
