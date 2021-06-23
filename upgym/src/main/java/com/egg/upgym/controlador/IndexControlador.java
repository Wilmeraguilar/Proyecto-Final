
package com.egg.upgym.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexControlador {

     @GetMapping("/")
    public ModelAndView inicio(){
        ModelAndView mav =new ModelAndView("inicio");
        return mav;
    }
    @GetMapping("/login1")
    public ModelAndView login(){
        ModelAndView mav =new ModelAndView("login1");
        return mav;
    }
    
    @GetMapping("/error-403")
    public ModelAndView error403(){
        return new ModelAndView("error403");
    }
    
}
