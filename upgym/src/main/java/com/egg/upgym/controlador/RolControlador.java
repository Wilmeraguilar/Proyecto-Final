package com.egg.upgym.controlador;

import com.egg.upgym.entidades.Rol;
import com.egg.upgym.servicio.RolServicio;
import com.egg.upgym.servicio.UsuarioServicio;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAnyRole('ADMIN')")
public class RolControlador {

    @Autowired
    private RolServicio rolServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ModelAndView mostrarTodos(Principal principal) {
        ModelAndView mav = new ModelAndView("rol");
        mav.addObject("roles", rolServicio.buscarTodos());
        mav.addObject("usuario", usuarioServicio.buscarPorEmail(principal.getName()));
        return mav;
    }

    @GetMapping("/crear")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ModelAndView crearRol(Principal principal) {
        ModelAndView mav = new ModelAndView("rol");
        mav.addObject("rol", new Rol());
        mav.addObject("title", "Crear Rol");
        mav.addObject("action", "guardar");
        mav.addObject("usuario", usuarioServicio.buscarPorEmail(principal.getName()));
        return mav;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ModelAndView editarRol(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("rol");
        mav.addObject("rol", rolServicio.buscarPorId(id));
        mav.addObject("title", "Editar Rol");
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public RedirectView guardar(@RequestParam String nombre) {
        rolServicio.crear(nombre);
        return new RedirectView("/");
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public RedirectView modificar(@RequestParam String id, @RequestParam String nombre, @RequestParam String estado) {
        rolServicio.modificar(id, nombre, estado);
        return new RedirectView("/");
    }

    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public RedirectView eliminar(@PathVariable String id) {
        rolServicio.eliminar(id);
        return new RedirectView("/");
    }

}
