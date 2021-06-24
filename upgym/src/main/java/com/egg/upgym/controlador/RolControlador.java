package com.egg.upgym.controlador;

import com.egg.upgym.entidades.Rol;
import com.egg.upgym.servicio.RolServicio;
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
@RequestMapping("/roles")
public class RolControlador {
    
  /*  @Autowired
    private RolServicio rolServicio;

    @GetMapping
    public ModelAndView mostrarTodos() {
        ModelAndView mav = new ModelAndView("rol");
        mav.addObject("roles", rolServicio.buscarTodos());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearRol() {
        ModelAndView mav = new ModelAndView("rolFormulario");
        mav.addObject("rol", new Rol());
        mav.addObject("title", "Crear Rol");
        mav.addObject("action", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarRol(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("rolFormulario");
        mav.addObject("rol", rolServicio.buscarPorId(id));
        mav.addObject("title", "Editar Rol");
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar( @RequestParam String nombre) {
        rolServicio.crear( nombre);
        return new RedirectView("/roles");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam String id,@RequestParam String nombre,@RequestParam String estado) {
        rolServicio.modificar(id,nombre,estado);
        return new RedirectView("/roles");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id) {
        rolServicio.eliminar(id);
        return new RedirectView("/roles");
    }*/
    
}
