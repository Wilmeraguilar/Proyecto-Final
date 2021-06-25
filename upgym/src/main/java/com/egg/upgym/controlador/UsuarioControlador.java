package com.egg.upgym.controlador;

import com.egg.upgym.entidades.Usuario;
import com.egg.upgym.servicio.UsuarioServicio;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/usuarios")

public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping
    public ModelAndView mostrarTodos() {
        ModelAndView mav = new ModelAndView("usuario");
        mav.addObject("usuarios", usuarioServicio.buscarTodos());
        return mav;
    }

    @GetMapping("/buscar/dni")
    public ModelAndView mostrarPorDni(@RequestParam Long dni) {
        ModelAndView mav = new ModelAndView("usuario");
        mav.addObject("usuario", usuarioServicio.buscarPorId(dni));

        return mav;
    }

    @GetMapping("/buscar/apellido")
    public ModelAndView mostrarPorApellido(@RequestParam String apellido) {
        ModelAndView mav = new ModelAndView("usuario");
        mav.addObject("usuarios", usuarioServicio.buscarPorApellido(apellido));

        return mav;
    }

   

    @GetMapping("/crear")
    public ModelAndView crearUsuario() {
        ModelAndView mav = new ModelAndView("usuario-registro");
        mav.addObject("usuario", new Usuario());
        mav.addObject("title", "Crear Usuario");
        mav.addObject("action", "guardar");

        return mav;
    }

    @GetMapping("/editar/{dni}")
    public ModelAndView editarUsuario(@PathVariable Long dni) {
        ModelAndView mav = new ModelAndView("usuario-Formulario");
        mav.addObject("usuario", usuarioServicio.buscarPorId(dni));
        mav.addObject("title", "Editar Usuario");
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Long dni,@RequestParam String nombre,@RequestParam String apellido, @RequestParam String telefono,@RequestParam String email, @RequestParam String clave, @RequestParam("direccion.provincia") String provincia, @RequestParam("direccion.ciudad") String ciudad, @RequestParam("direccion.calleNro") String calleNro, HttpServletRequest request) {
        usuarioServicio.crear(dni,nombre, apellido, telefono, email, clave, provincia, ciudad, calleNro);
        try {
        request.login(email, clave);
    } catch (ServletException e) {
        e.printStackTrace();
    }
        return new RedirectView("/");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam Long dni,@RequestParam String nombre,@RequestParam String apellido, @RequestParam String telefono,@RequestParam String email, @RequestParam String clave, @RequestParam String idDireccion, @RequestParam("direccion.provincia") String provincia, @RequestParam("direccion.ciudad") String ciudad, @RequestParam("direccion.calleNro") String calleNro, @RequestParam String estado) {
        usuarioServicio.modificar(dni,nombre, apellido, telefono, email, clave, idDireccion, provincia, ciudad, calleNro, estado);
        return new RedirectView("/");
    }

    @PostMapping("/eliminar/{dni}")
    public RedirectView eliminar(@PathVariable Long dni) {
        usuarioServicio.eliminar(dni);
        return new RedirectView("/");
    }

}
