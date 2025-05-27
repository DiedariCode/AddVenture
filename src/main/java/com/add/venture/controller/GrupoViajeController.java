package com.add.venture.controller;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.add.venture.helper.UsuarioAutenticadoHelper;
import com.add.venture.model.GrupoViaje;
import com.add.venture.model.MensajeGrupo;
import com.add.venture.model.ParticipanteGrupo;
import com.add.venture.model.Usuario;
import com.add.venture.model.ParticipanteGrupo.EstadoSolicitud;
import com.add.venture.repository.GrupoViajeRepository;
import com.add.venture.repository.MensajeGrupoRepository;
import com.add.venture.repository.ParticipanteGrupoRepository;
import com.add.venture.repository.UsuarioRepository;
import com.add.venture.service.IGrupoViajeService;

@Controller
@RequestMapping("/grupos")
public class GrupoViajeController {

    @Autowired
    private UsuarioAutenticadoHelper usuarioAutenticadoHelper;
    
    @Autowired
    private GrupoViajeRepository grupoViajeRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ParticipanteGrupoRepository participanteGrupoRepository;
    
    @Autowired
    private MensajeGrupoRepository mensajeGrupoRepository;
    
    @Autowired
    private IGrupoViajeService grupoViajeService;
    
    @GetMapping
    public String listarGrupos(
            @RequestParam(required = false) String destino,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            @RequestParam(required = false) Long tipoViaje,
            @RequestParam(required = false) String tipoGrupo,
            @RequestParam(required = false) String rangoEdad,
            @RequestParam(required = false) Boolean verificado,
            @RequestParam(required = false) String etiquetas,
            @RequestParam(required = false) String ordenar,
            Model model) {
        
        // Cargar datos del usuario para la navbar y perfil
        usuarioAutenticadoHelper.cargarDatosUsuarioParaNavbar(model);
        usuarioAutenticadoHelper.cargarUsuarioParaPerfil(model);
        
        // Cargar todos los grupos (en una implementación real, aplicaríamos los filtros)
        model.addAttribute("grupos", grupoViajeRepository.findAll());
        
        // Cargar tipos de viaje para los filtros
        model.addAttribute("tiposViaje", grupoViajeService.obtenerTiposViaje());
        
        return "grupos/buscar";
    }
    
    @GetMapping("/{id}")
    public String verDetallesGrupo(@PathVariable("id") Integer idGrupo, Model model) {
        // Cargar datos del usuario para la navbar y perfil
        usuarioAutenticadoHelper.cargarDatosUsuarioParaNavbar(model);
        usuarioAutenticadoHelper.cargarUsuarioParaPerfil(model);
        
        // Cargar el grupo
        GrupoViaje grupo = grupoViajeRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
        
        model.addAttribute("grupo", grupo);
        
        // Verificar si el usuario autenticado es participante del grupo
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            String email = auth.getName();
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            boolean isParticipante = participanteGrupoRepository.existsByUsuarioAndGrupo(usuario, grupo);
            model.addAttribute("isParticipante", isParticipante);
        } else {
            model.addAttribute("isParticipante", false);
        }
        
        return "grupos/detalles";
    }
    
    @PostMapping("/{id}/unirse")
    @ResponseBody
    public String unirseAlGrupo(@PathVariable("id") Integer idGrupo) {
        // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "{\"error\": \"Usuario no autenticado\"}";
        }
        
        String email = auth.getName();
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
        // Obtener el grupo
            GrupoViaje grupo = grupoViajeRepository.findById(idGrupo)
                    .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
            
        // Verificar si ya es participante
        if (participanteGrupoRepository.existsByUsuarioAndGrupo(usuario, grupo)) {
            return "{\"error\": \"Ya eres participante de este grupo\"}";
            }
            
        // Crear participante
        ParticipanteGrupo participante = new ParticipanteGrupo();
        participante.setUsuario(usuario);
        participante.setGrupo(grupo);
        participante.setRolParticipante("MIEMBRO");
        participante.setEstadoSolicitud(EstadoSolicitud.ACEPTADO);
        participante.setFechaUnion(LocalDateTime.now());
        
        participanteGrupoRepository.save(participante);
        
        return "{\"success\": true}";
    }
    
    @PostMapping("/{id}/abandonar")
    public String abandonarGrupo(@PathVariable("id") Integer idGrupo, RedirectAttributes redirectAttributes) {
        // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            redirectAttributes.addFlashAttribute("error", "Usuario no autenticado");
            return "redirect:/grupos/" + idGrupo;
        }
        
        String email = auth.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
        // Obtener el grupo
            GrupoViaje grupo = grupoViajeRepository.findById(idGrupo)
                    .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
            
        // Verificar si es participante
        Optional<ParticipanteGrupo> participanteOpt = participanteGrupoRepository.findByUsuarioAndGrupo(usuario, grupo);
        if (participanteOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No eres participante de este grupo");
                return "redirect:/grupos/" + idGrupo;
            }
            
        // Verificar que no sea el creador
        if (grupo.getCreador().equals(usuario)) {
            redirectAttributes.addFlashAttribute("error", "El creador no puede abandonar el grupo");
            return "redirect:/grupos/" + idGrupo;
        }
        
        // Eliminar participante
        participanteGrupoRepository.delete(participanteOpt.get());
        
        redirectAttributes.addFlashAttribute("mensaje", "Has abandonado el grupo exitosamente");
        return "redirect:/grupos";
    }
    
    @PostMapping("/{id}/expulsar")
    public String expulsarMiembro(
            @PathVariable("id") Integer idGrupo,
            @RequestParam("userId") Long userId,
            @RequestParam("expulsionReason") String expulsionReason,
            @RequestParam(value = "expulsionComment", required = false) String expulsionComment,
            RedirectAttributes redirectAttributes) {
        
        // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            redirectAttributes.addFlashAttribute("error", "Usuario no autenticado");
            return "redirect:/grupos/" + idGrupo;
        }
        
        String email = auth.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Obtener el grupo
        GrupoViaje grupo = grupoViajeRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
        
        // Verificar que el usuario autenticado sea el creador del grupo
        if (!grupo.getCreador().equals(usuario)) {
            redirectAttributes.addFlashAttribute("error", "Solo el creador puede expulsar miembros");
            return "redirect:/grupos/" + idGrupo;
        }
        
        // Obtener el usuario a expulsar
        Usuario usuarioExpulsado = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario a expulsar no encontrado"));
        
        // Verificar que no sea el creador
        if (grupo.getCreador().equals(usuarioExpulsado)) {
            redirectAttributes.addFlashAttribute("error", "No puedes expulsar al creador del grupo");
            return "redirect:/grupos/" + idGrupo;
        }
        
        // Verificar si es participante
        Optional<ParticipanteGrupo> participanteOpt = participanteGrupoRepository.findByUsuarioAndGrupo(usuarioExpulsado, grupo);
        if (participanteOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El usuario no es participante de este grupo");
            return "redirect:/grupos/" + idGrupo;
        }
        
        // Eliminar participante
        participanteGrupoRepository.delete(participanteOpt.get());
        
        // Aquí se podría enviar una notificación al usuario expulsado
        
        redirectAttributes.addFlashAttribute("mensaje", "Usuario expulsado exitosamente");
        return "redirect:/grupos/" + idGrupo;
    }
    
    @PostMapping("/{id}/mensaje")
    @ResponseBody
    public String enviarMensaje(
            @PathVariable("id") Integer idGrupo,
            @RequestBody MensajeRequest mensajeRequest) {
        
        // Obtener el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "{\"error\": \"Usuario no autenticado\"}";
        }
        
        String email = auth.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Obtener el grupo
        GrupoViaje grupo = grupoViajeRepository.findById(idGrupo)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
        
        // Verificar si es participante
        if (!participanteGrupoRepository.existsByUsuarioAndGrupo(usuario, grupo)) {
            return "{\"error\": \"No eres participante de este grupo\"}";
        }
        
        // Crear mensaje
        MensajeGrupo mensaje = new MensajeGrupo();
        mensaje.setMensaje(mensajeRequest.getMensaje());
        mensaje.setFechaEnvio(LocalDateTime.now());
        mensaje.setEstado("activo");
        mensaje.setGrupo(grupo);
        mensaje.setRemitente(usuario);
        
        mensajeGrupoRepository.save(mensaje);
        
        return "{\"success\": true}";
    }
    
    // Clase para recibir el mensaje
    public static class MensajeRequest {
        private String mensaje;
        
        public String getMensaje() {
            return mensaje;
        }
        
        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }
}
