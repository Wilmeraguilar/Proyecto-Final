package com.egg.upgym.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/login")
public class LoginControlador {
    
    
    @GetMapping
    public ModelAndView login(){
        ModelAndView mav =new ModelAndView("login");
        return mav;
    }
    
    @PostMapping("/login")
    public RedirectView inicio() {
        return new RedirectView("/");
   }

}
