package com.egg.upgym.controlador;

import com.egg.upgym.entidades.Gimnasio;
import com.egg.upgym.servicio.DireccionServicio;
import com.egg.upgym.servicio.GimnasioServicio;
import com.egg.upgym.servicio.UsuarioServicio;
import java.security.Principal;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/gimnasios")

public class GimnasioControlador {

    @Autowired
    private GimnasioServicio gimnasioServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    

    @GetMapping

    public ModelAndView mostrarTodos(Principal principal) {
        ModelAndView mav = new ModelAndView("gimnasio");
        try{
            if(usuarioServicio.buscarPorEmail(principal.getName())!=null){
                 mav.addObject("usuario",usuarioServicio.buscarPorEmail(principal.getName()));
            }
            if(gimnasioServicio.buscarPorEmail(principal.getName())!=null){
                mav.addObject("gimnasio", gimnasioServicio.buscarPorEmail(principal.getName()));
            }
            
           
        }catch(Exception e){
            
        }
        mav.addObject("gimnasios", gimnasioServicio.buscarTodos());
        return mav;
    }
    @GetMapping("/buscar/provinciaCiudad")
    public ModelAndView mostrarTodosPorProvinciaCiudad(@RequestParam String provincia, @RequestParam String ciudad,Principal principal) {
        
        ModelAndView mav = new ModelAndView("gimnasio");
        try{
            if(usuarioServicio.buscarPorEmail(principal.getName())!=null){
                 mav.addObject("usuario",usuarioServicio.buscarPorEmail(principal.getName()));
            }
            if(gimnasioServicio.buscarPorEmail(principal.getName())!=null){
                mav.addObject("gimnasio", gimnasioServicio.buscarPorEmail(principal.getName()));
            }
        }catch(Exception e){
            
        }
        mav.addObject("gimnasios", gimnasioServicio.buscarPorprovinciaCiudad(provincia, ciudad));
        return mav;
    }
    @GetMapping("/buscar/nombre")
    public ModelAndView mostrarPorNombre(@RequestParam String nombre) {
        ModelAndView mav = new ModelAndView("gimnasio");
        mav.addObject("gimnasios", gimnasioServicio.buscarPorNombre(nombre));

        return mav;
    }

    @GetMapping("/buscar/ciudad")
    public ModelAndView mostrarPorCiudad(@RequestParam("direccion.ciudad") String ciudad) {
        ModelAndView mav = new ModelAndView("gimnasio");
        mav.addObject("gimnasios", gimnasioServicio.buscarPorCiudad(ciudad));

        return mav;
    }

    @GetMapping("/crear")
    @PreAuthorize("!hasAnyRole('USUARIO,GIMNASIO')")
    public ModelAndView crearGimnasio() {
        ModelAndView mav = new ModelAndView("gimnasio-registro");
        mav.addObject("gimnasio", new Gimnasio());
        mav.addObject("title", "Crear Gimnasio");
        mav.addObject("action", "guardar");

        return mav;
    }

    @GetMapping("/perfil")
    @PreAuthorize("hasAnyRole('ADMIN,GIMNASIO')")
    public ModelAndView perfil(Principal principal) {
        ModelAndView mav = new ModelAndView("Perfil-gym");
        mav.addObject("gimnasio", gimnasioServicio.buscarPorEmail(principal.getName()));
        return mav;
    }
    
   

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN,GIMNASIO')")
    public ModelAndView editarGimnasio(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("gimnasio-registro");
        mav.addObject("gimnasio", gimnasioServicio.buscarPorId(id));
        mav.addObject("title", "Editar Gimnasio");
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAnyRole('ADMIN,GIMNASIO')")
    public RedirectView guardar(@RequestParam String nombre, @RequestParam String telefono, @RequestParam Integer capacidad, @RequestParam String email, @RequestParam String clave, @RequestParam("direccion.provincia") String provincia, @RequestParam("direccion.ciudad") String ciudad, @RequestParam("direccion.calleNro") String calleNro, HttpServletRequest request, @RequestParam("file") MultipartFile imagen) throws MessagingException {

            gimnasioServicio.crear(nombre, telefono, capacidad, email, clave, provincia, ciudad, calleNro, imagen);

        try {
            request.login(email, clave);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return new RedirectView("/");
    }


    @PostMapping("/modificar")
    @PreAuthorize("hasAnyRole('ADMIN,GIMNASIO')")
    public RedirectView modificar(@RequestParam String id, @RequestParam String nombre, @RequestParam String telefono, @RequestParam Integer capacidad, @RequestParam String email, @RequestParam String clave, @RequestParam("direccion.id") String idDireccion, @RequestParam("direccion.provincia") String provincia, @RequestParam("direccion.ciudad") String ciudad, @RequestParam("direccion.calleNro") String calleNro, @RequestParam String estado, @RequestParam("file") MultipartFile imagen) {
        gimnasioServicio.modificar(id, nombre, telefono, capacidad, email, clave, idDireccion, provincia, ciudad, calleNro, estado, imagen);
        return new RedirectView("/");
    }

    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN,GIMNASIO')")
    public RedirectView eliminar(@PathVariable String id) {
        gimnasioServicio.eliminar(id);
        return new RedirectView("/gimnasios");
    }

    @GetMapping("/staff")
    public ModelAndView GimnasioStaff(Principal principal) {
        ModelAndView mav = new ModelAndView("gimnasioStaff");
        try{
            if(usuarioServicio.buscarPorEmail(principal.getName())!=null){
                 mav.addObject("usuario",usuarioServicio.buscarPorEmail(principal.getName()));
            }
            if(gimnasioServicio.buscarPorEmail(principal.getName())!=null){
                mav.addObject("gimnasio", gimnasioServicio.buscarPorEmail(principal.getName()));
            }
           
        }catch(Exception e){
            
        }
        mav.addObject("action", "staff");

        return mav;

    }

}
