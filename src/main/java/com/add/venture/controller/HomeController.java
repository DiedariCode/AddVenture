package com.add.venture.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String indexForm(Model model) {
        return "index";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        return "/grupos/crear";
    }

    @GetMapping("/explore")
    public String exploreForm(Model model) {
        return "/user/exploraViajes";
    }

    @GetMapping("/about")
    public String aboutForm(Model model) {
        return "/user/aboutUs";
    }

    @GetMapping("/profile")
    public String prefilForm(Model model) {
        return "/user/perfilUsuario";
    }

    @GetMapping("/details")
    public String detallesForm(Model model) {
        return "/user/trip-detalles";
    }
}
