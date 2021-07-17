package com.egg.upgym.controlador;

import com.egg.upgym.servicio.ErrorServicio;
import com.egg.upgym.servicio.GimnasioServicio;
import com.egg.upgym.servicio.LoginServicio;
import com.egg.upgym.servicio.UsuarioServicio;
import java.security.Principal;
import java.util.Map;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/login")
public class LoginControlador {

    @Autowired
    UsuarioServicio usuarioServicio;
    
    @Autowired
    GimnasioServicio gimnasioServicio;
    
    @Autowired
    LoginServicio logser;

    @GetMapping
    @PreAuthorize("!hasAnyRole('GIMNASIO,ADMIN,USUARIO')")
    public ModelAndView login(@RequestParam(required = false) String error) {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("error", error);
        mav.addObject("action", "login");
        
        return mav;
    }

//    @PostMapping("/logincheck")
//    public RedirectView inicio(@RequestParam String email, @RequestParam String clave, RedirectAttributes attributes) throws ErrorServicio, MessagingException {
//        try {
//            logser.validar(email, clave);
//        } catch (ErrorServicio e) {
//            
//            attributes.addFlashAttribute("error", e.getMessage());
//            return new RedirectView("/login");
//            
//        }
//        
//        return new RedirectView("/");
//    }

    @GetMapping("/elegir")
    @PreAuthorize("!hasAnyRole('ADMIN,GIMNASIO,USUARIO')")
    public ModelAndView GimnasioUsuario() {
        ModelAndView mav = new ModelAndView("gimnasioOUsuario");
        mav.addObject("action", "login");
        mav.addObject("actividad", "elegir");

        return mav;

    }

}
