package com.egg.upgym.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginControlador {
    @GetMapping()
    public ModelAndView login(){
        ModelAndView mav =new ModelAndView("login");
        return mav;
    }
    
}
