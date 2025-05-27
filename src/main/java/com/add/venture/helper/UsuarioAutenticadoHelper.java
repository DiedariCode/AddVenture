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

            if (usuario != null) {
                model.addAttribute("iniciales", usuario.getIniciales());
                model.addAttribute("username", usuario);

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
            PerfilUsuarioDTO usuariodDto = usuarioService.buscarPerfilPorEmail(correo);
            if (usuariodDto != null) {
                model.addAttribute("iniciales", usuariodDto.getIniciales());
                System.out.println("imagenPortada: " + usuariodDto.getImagenPortada());
                model.addAttribute("usuario", usuariodDto); // para formulario de edición
            }
        }
    }

    

}
