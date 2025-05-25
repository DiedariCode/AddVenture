package com.add.venture.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.add.venture.dto.RegistroUsuarioDTO;
import com.add.venture.model.Usuario;
import com.add.venture.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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
}
