
package com.egg.upgym.controlador;

import com.egg.upgym.servicio.GimnasioServicio;
import com.egg.upgym.servicio.UsuarioServicio;
import java.security.Principal;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
@RequestMapping("/")
public class IndexControlador {
    
    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    GimnasioServicio gimnasioServicio;

     @GetMapping("/")
    public ModelAndView inicio(Principal principal)throws Exception{
        ModelAndView mav =new ModelAndView("inicio");
        try{
            if(usuarioServicio.buscarPorEmail(principal.getName())!=null){
                 mav.addObject("usuario",usuarioServicio.buscarPorEmail(principal.getName()));
            }
            if(gimnasioServicio.buscarPorEmail(principal.getName())!=null){
                mav.addObject("gimnasio", gimnasioServicio.buscarPorEmail(principal.getName()));
            }
           
        }catch(Exception e){
            
        }
       
        
        return mav;
    }
    
    
    @GetMapping("/error-403")
    public ModelAndView error403(){
        return new ModelAndView("error403");
    }
    
    
    
}
