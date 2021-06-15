package com.egg.upgym.controlador;

import com.egg.upgym.entidades.Gimnasio;
import com.egg.upgym.servicio.DireccionServicio;
import com.egg.upgym.servicio.GimnasioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/gimnasios")

public class GimnasioControlador {
    
    @Autowired
    private GimnasioServicio gimnasioServicio;
    
    
      @GetMapping
    public ModelAndView mostrarTodos() {
        ModelAndView mav = new ModelAndView("gimnasio");
        mav.addObject("gimnasios", gimnasioServicio.buscarTodos());
        return mav;
    }
   
    @GetMapping("/crear")
    public ModelAndView crearGimnasio() {
        ModelAndView mav = new ModelAndView("gimnasioFormulario");
        mav.addObject("gimnasio", new Gimnasio()); 
        mav.addObject("title", "Crear Gimnasio");
        mav.addObject("action", "guardar");
       
        return mav;
    }
    @GetMapping("/editar/{email}")
    public ModelAndView editarGimnasio(@PathVariable String email) {
        ModelAndView mav = new ModelAndView("gimnasioFormulario");
        mav.addObject("gimnasio", gimnasioServicio.buscarPorEmail(email));      
        mav.addObject("title", "Editar Gimnasio");
        mav.addObject("action", "modificar");
        return mav;
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre,@RequestParam Integer capacidad,@RequestParam String email, @RequestParam String clave, @RequestParam("direccion.ciudad") String ciudad, @RequestParam("direccion.calleNro") String calleNro,@RequestParam("direccion.telefono") Long telefono,@RequestParam String estado) {
        gimnasioServicio.crear(nombre,capacidad,email, clave, ciudad, calleNro,telefono,estado);
        return new RedirectView("/gimnasios");
    }
    

    @PostMapping("/modificar")
     public RedirectView modificar(@RequestParam String nombre,@RequestParam Integer capacidad,@RequestParam String email, @RequestParam String clave, @RequestParam("direccion.ciudad") String ciudad, @RequestParam("direccion.calleNro") String calleNro,@RequestParam("direccion.telefono") Long telefono,@RequestParam String estado) {
        gimnasioServicio.modificar(nombre,capacidad,email, clave, ciudad, calleNro,telefono,estado);
        return new RedirectView("/gimnasios");
    }

    @PostMapping("/eliminar/{email}")
    public RedirectView eliminar(@PathVariable String email) {
       gimnasioServicio.eliminar(email);
        return new RedirectView("/gimansios");
    }
    
}
