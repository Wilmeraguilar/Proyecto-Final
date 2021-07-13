package com.egg.upgym.controlador;

import com.egg.upgym.servicio.GimnasioServicio;
import com.egg.upgym.servicio.UsuarioServicio;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/login")
public class LoginControlador {

    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    GimnasioServicio gimnasioServicio;

    @GetMapping
    public ModelAndView login(Principal principal) {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("action", "login");
        try {
            if (usuarioServicio.buscarPorEmail(principal.getName()) != null) {
                mav.addObject("usuario", usuarioServicio.buscarPorEmail(principal.getName()));
            }
            if (gimnasioServicio.buscarPorEmail(principal.getName()) != null) {
                mav.addObject("gimnasio", gimnasioServicio.buscarPorEmail(principal.getName()));
            }

        } catch (Exception e) {

        }

        return mav;
    }

    @PostMapping("/login")
    public RedirectView inicio(Principal principal) {

        try {
            if (usuarioServicio.buscarPorEmail(principal.getName()) != null) {
                usuarioServicio.buscarPorEmail(principal.getName());
            }
            if (gimnasioServicio.buscarPorEmail(principal.getName()) != null) {
                gimnasioServicio.buscarPorEmail(principal.getName());
            }

        } catch (Exception e) {

        }

        return new RedirectView("/");
    }

    @GetMapping("/elegir")
    public ModelAndView GimnasioUsuario() {
        ModelAndView mav = new ModelAndView("gimnasioOUsuario");
        mav.addObject("action", "login");
        mav.addObject("actividad", "elegir");

        return mav;

    }

}
