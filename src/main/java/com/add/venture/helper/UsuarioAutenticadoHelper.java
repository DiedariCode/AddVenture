package com.add.venture.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.add.venture.dto.PerfilUsuarioDTO;
import com.add.venture.dto.RegistroUsuarioDTO;
import com.add.venture.service.IUsuarioService;

@Component
public class UsuarioAutenticadoHelper {

    @Autowired
    private IUsuarioService usuarioService;

    public void cargarDatosUsuarioParaNavbar(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {

            String correo = authentication.getName();
            RegistroUsuarioDTO usuario = usuarioService.buscarPorEmail(correo);

            System.out.println("Usuario autenticado: " + usuario); // Aquí imprimes el objeto
            System.out.println("Correo autenticado: " + correo);

            if (usuario != null) {
                model.addAttribute("iniciales", usuario.getIniciales());
                model.addAttribute("username", usuario);

                System.out.println("Iniciales: " + usuario.getIniciales());
                System.out.println("Nombre Usuario: " + usuario.getNombreUsuario()); // Asegúrate que exista este getter
                model.addAttribute("iniciales", usuario.getIniciales());
                model.addAttribute("username", usuario);
            }
        }
    }

    public void cargarUsuarioParaPerfil(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {

            String correo = authentication.getName();
            PerfilUsuarioDTO usuario = usuarioService.buscarPerfilPorEmail(correo);
            if (usuario != null) {
                model.addAttribute("iniciales", usuario.getIniciales());
                model.addAttribute("usuario", usuario); // para formulario de edición
            }
        }
    }

}
