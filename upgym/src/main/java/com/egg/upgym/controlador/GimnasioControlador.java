package com.egg.upgym.controlador;

import com.egg.upgym.entidades.Gimnasio;
import com.egg.upgym.servicio.DireccionServicio;
import com.egg.upgym.servicio.GimnasioServicio;
import com.egg.upgym.servicio.UsuarioServicio;
import java.security.Principal;
import java.util.Locale;
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

    public ModelAndView mostrarTodos() {
        ModelAndView mav = new ModelAndView("gimnasio");
        mav.addObject("gimnasios", gimnasioServicio.buscarTodos());
        return mav;
    }
    @GetMapping("/buscar/provinciaCiudad")
    public ModelAndView mostrarTodosPorProvinciaCiudad(@RequestParam String provincia,@RequestParam String ciudad) {
        ModelAndView mav = new ModelAndView("gimnasio");
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
    public ModelAndView crearGimnasio() {
        ModelAndView mav = new ModelAndView("gimnasio-registro");
        mav.addObject("gimnasio", new Gimnasio()); 
        mav.addObject("title", "Crear Gimnasio");
        mav.addObject("action", "guardar");
       
        return mav;
    }
    

    @GetMapping("/editar/{id}")
    public ModelAndView editarGimnasio(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("gimnasio-registro");
        mav.addObject("gimnasio", gimnasioServicio.buscarPorId(id));      
        mav.addObject("title", "Editar Gimnasio");
        mav.addObject("action", "modificar");
        return mav;
    }
    
   @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre,@RequestParam String telefono,@RequestParam Integer capacidad,@RequestParam String email, @RequestParam String clave, @RequestParam("direccion.provincia") String provincia, @RequestParam("direccion.ciudad") String ciudad, @RequestParam("direccion.calleNro") String calleNro, HttpServletRequest request) {
        gimnasioServicio.crear(nombre,telefono,capacidad,email,clave,provincia, ciudad, calleNro);
        try {
        request.login(email, clave);
    } catch (ServletException e) {
        e.printStackTrace();
    }
        return new RedirectView("/");
    }
    
    
    
    /*con FOTO - en Carpeta*/
  
    /*@Secured("ROLE_ADMIN")*/
    /*@PostMapping("/form")
    public String guardar(@Valid Gimnasio gimnasio, BindingResult result, Model model,@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status,
    Locale locale) {
    if (result.hasErrors()){
    model.addAtributte("titulo", messageSource.getMessage("text.gimnasio.form.titulo", null, locale));
    return "form";
    }
    
        if (!foto.isEmpty()) {
            if (gimnasio.getId() !=null && gimnasio.getId().length() > 0 && gimnasio.getFoto() != null && gimnasio.getFoto().length() > 0) {
                
           gimnasioServicio.delete(gimnasio.getFoto());
            }
 
        }
    
        String uniqueFilename = null;
        try {
            uniqueFilename = gimnasioServicio.copy(foto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        flash.addFlashAtribute(messageSource.getMessage("text.cliente.flash.foto.subir.success", null, locale)
                + " ´ " + uniqueFilename + " ´ ");
        gimnasio.setFoto(uniqueFilename);
    }*/
        
    
    
    /*con FOTO MSQL https://www.youtube.com/watch?v=hMWNM6pT65s&list=PLgwlfcqa5h3w10Dz95B3QY2iinOskVf9t&index=12 */
   /*  @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre,@RequestParam String telefono,@RequestParam Integer capacidad,@RequestParam String email, @RequestParam String clave, @RequestParam("direccion.provincia") String provincia, @RequestParam("direccion.ciudad") String ciudad, @RequestParam("direccion.calleNro") String calleNro, @RequestParam MultipartFile foto, HttpServletRequest request) {
        gimnasioServicio.crear(nombre,telefono,capacidad,email,clave,provincia, ciudad, calleNro, foto);
        try {
        request.login(email, clave);
    } catch (ServletException e) {
        e.printStackTrace();
    }
        return new RedirectView("/");
    }*/
    
    
   
    @PostMapping("/modificar")
     public RedirectView modificar(@RequestParam String id,@RequestParam String nombre,@RequestParam String telefono,@RequestParam Integer capacidad,@RequestParam String email, @RequestParam String clave, @RequestParam("direccion.id") String idDireccion,@RequestParam("direccion.provincia") String provincia, @RequestParam("direccion.ciudad") String ciudad, @RequestParam("direccion.calleNro") String calleNro,@RequestParam String estado) {
        gimnasioServicio.modificar(id,nombre,telefono,capacidad,email,clave,idDireccion,provincia, ciudad, calleNro,estado);
        return new RedirectView("/gimnasios");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id) {
       gimnasioServicio.eliminar(id);
        return new RedirectView("/gimnasios");
    }
    
    @GetMapping("/staff")
    public ModelAndView GimnasioStaff() {
        ModelAndView mav = new ModelAndView("gimnasioStaff");

        return mav;

    }
    
    
}
