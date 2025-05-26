package com.add.venture.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.add.venture.dto.PerfilUsuarioDTO;
import com.add.venture.dto.RegistroUsuarioDTO;
import com.add.venture.model.Usuario;
import com.add.venture.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioDetallesService usuarioDetallesService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void crearUsuario(RegistroUsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellido());
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setPais(dto.getPais());
        usuario.setCiudad(dto.getCiudad());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setContrasenaHash(passwordEncoder.encode(dto.getContrasena()));
        usuario.setFechaRegistro(Timestamp.valueOf(LocalDateTime.now()));
        usuario.setEsVerificado(false);
        usuario.setPuntosReputacion(BigDecimal.valueOf(0));
        usuario.setResenasPositivas(0);
        usuario.setEstadoCuenta("ACTIVA");
        usuarioRepository.save(usuario);
    }

    @Override
    public boolean existeNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    @Override
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    /**
     * Busca un usuario en la base de datos por su correo electrónico y lo mapea a
     * un DTO de registro.
     *
     * @param email el correo electrónico del usuario a buscar.
     * @return un objeto {@link RegistroUsuarioDTO} con los datos del usuario
     *         encontrado;
     *         si no se encuentra ningún usuario con el correo dado, retorna
     *         {@code null}.
     *
     *         <p>
     *         Este método utiliza el repositorio {@code usuarioRepository} para
     *         buscar
     *         un usuario por su email. Si el usuario existe, se crea un nuevo
     *         {@code RegistroUsuarioDTO} con sus datos principales.
     *         </p>
     *
     *         <p>
     *         Los dos últimos parámetros del DTO se establecen como {@code null}.
     *         </p>
     */
    @Override
    public RegistroUsuarioDTO buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuario -> new RegistroUsuarioDTO(
                        usuario.getNombre(),
                        usuario.getApellidos(),
                        usuario.getNombreUsuario(),
                        usuario.getEmail(),
                        usuario.getTelefono(),
                        usuario.getPais(),
                        usuario.getCiudad(),
                        usuario.getFechaNacimiento(),
                        null,
                        null))
                .orElse(null);
    }

    @Override
    public PerfilUsuarioDTO buscarPerfilPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuario -> new PerfilUsuarioDTO(
                        usuario.getNombre(),
                        usuario.getApellidos(),
                        usuario.getNombreUsuario(),
                        usuario.getEmail(),
                        usuario.getTelefono(),
                        usuario.getPais(),
                        usuario.getCiudad(),
                        usuario.getFechaNacimiento(),
                        usuario.getDescripcion())) // Biografía se puede agregar más adelante
                .orElse(null);
    }

    @Override
    public void actualizarPerfil(PerfilUsuarioDTO dto) {
        // Obtener el usuario autenticado desde el contexto de seguridad
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Esto es el email

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellido());

        // Validar y actualizar username sólo si cambió y no existe otro igual
        if (!usuario.getNombreUsuario().equals(dto.getUsername())) {
            if (usuarioRepository.existsByNombreUsuario(dto.getUsername())) {
                throw new RuntimeException("El nombre de usuario ya está en uso");
            }
            usuario.setNombreUsuario(dto.getUsername());
        }

        usuario.setTelefono(dto.getTelefono());
        usuario.setPais(dto.getPais());
        usuario.setCiudad(dto.getCiudad());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setDescripcion(dto.getBiografia());

        usuarioRepository.save(usuario);

        // Reautenticar con el email como username
        UserDetails userDetails = usuarioDetallesService.loadUserByUsername(usuario.getEmail());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @Override
    public boolean existeNombreUsuarioExceptoActual(String nombreUsuario, String emailActual) {
        return usuarioRepository.existsByNombreUsuarioAndEmailNot(nombreUsuario, emailActual);
    }
}
