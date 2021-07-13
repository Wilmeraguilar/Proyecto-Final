package com.egg.upgym.controlador;

import com.egg.upgym.entidades.Reservas;

import com.egg.upgym.servicio.ErrorServicio;
import com.egg.upgym.servicio.GimnasioServicio;
import com.egg.upgym.servicio.ReservasServicio;
import com.egg.upgym.servicio.UsuarioServicio;
import java.security.Principal;

import com.egg.upgym.servicio.GimnasioServicio;
import com.egg.upgym.servicio.ReservasServicio;
import com.egg.upgym.servicio.UsuarioServicio;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/reservas")
public class ReservasControlador {

    @Autowired
    private ReservasServicio reservasServicio;
    @Autowired
    private GimnasioServicio gimnasioServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/usuario")
    public ModelAndView ReservaUsuario(Principal principal, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("reservas-lista");
        try{
            if(usuarioServicio.buscarPorEmail(principal.getName())!=null){
                 mav.addObject("usuario",usuarioServicio.buscarPorEmail(principal.getName()));
            }
            
           
        }catch(Exception e){
            
        }
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("mensaje", flashMap.get("creado"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("reservas", reservasServicio.buscarPorUsuario(usuarioServicio.buscarPorEmail(principal.getName()).getDni()));
        mav.addObject("title", "Crear Reserva");
        mav.addObject("action", "guardar");

        return mav;
    }

    @GetMapping("/usuario/todas")
    public ModelAndView ReservaUsuarioTodas(Principal principal, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("reservas-lista");
        try{
            if(usuarioServicio.buscarPorEmail(principal.getName())!=null){
                 mav.addObject("usuario",usuarioServicio.buscarPorEmail(principal.getName()));
            }
           
        }catch(Exception e){
            
        }
         Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("mensaje", flashMap.get("creado"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("reservas", reservasServicio.buscarPorUsuarioTodas(usuarioServicio.buscarPorEmail(principal.getName()).getDni()));
        mav.addObject("title", "Crear Reserva");
        mav.addObject("action", "guardar");

        return mav;
    }

    @GetMapping("/gimnasio")
    public ModelAndView ReservaGimnasio(Principal principal) {
        ModelAndView mav = new ModelAndView("reservas-lista2");
        try{
            
            if(gimnasioServicio.buscarPorEmail(principal.getName())!=null){
                mav.addObject("gimnasio", gimnasioServicio.buscarPorEmail(principal.getName()));
            }
           
        }catch(Exception e){
            
        }
        mav.addObject("reservas", reservasServicio.buscarPorGimnasio(gimnasioServicio.buscarPorEmail(principal.getName()).getId()));
        mav.addObject("title", "Crear Reserva");
        mav.addObject("action", "guardar");

        return mav;
    }

    @GetMapping("/gimnasio/todas")
    public ModelAndView ReservaGimnasioTodas(Principal principal) {
        ModelAndView mav = new ModelAndView("reservas-lista2");
        try{
            
            if(gimnasioServicio.buscarPorEmail(principal.getName())!=null){
                mav.addObject("gimnasio", gimnasioServicio.buscarPorEmail(principal.getName()));
            }
           
        }catch(Exception e){
            
        }
        mav.addObject("reservas", reservasServicio.buscarPorGimnasioTodas(gimnasioServicio.buscarPorEmail(principal.getName()).getId()));
        mav.addObject("title", "Crear Reserva");
        mav.addObject("action", "guardar");

        return mav;
    }

    @GetMapping("/crear/{id}")
    @PreAuthorize("hasAnyRole('USUARIO,ADMIN,GIMNASIO')")
    public ModelAndView crearReserva(@PathVariable String id, Principal principal,HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("reservas");
        
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("mensaje", flashMap.get("creado"));
            mav.addObject("error", flashMap.get("error"));
        }

        mav.addObject("reserva", new Reservas());
        mav.addObject("gimnasio", gimnasioServicio.buscarPorId(id));
        mav.addObject("usuario", usuarioServicio.buscarPorEmail(principal.getName()));
        mav.addObject("title", "Crear Reserva");
        mav.addObject("action", "guardar");

        return mav;
    }

    

    @PostMapping("/guardar")
    @PreAuthorize("hasAnyRole('USUARIO,GIMNASIO,ADMIN')")
    public RedirectView guardar(@RequestParam("fecha") String fecha, @RequestParam String horario, @RequestParam("gimnasio") String idGimnasio, @RequestParam("usuario") String emailUsuario, RedirectAttributes attributes) throws MessagingException, ParseException {
//       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date dataFormateada = formato.parse(fecha);
        try {
            reservasServicio.crear(dataFormateada, horario, idGimnasio, emailUsuario);
            attributes.addFlashAttribute("creado", "Reserva creada");

        } catch (ErrorServicio e) {
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("gimnasio", gimnasioServicio.buscarPorId(idGimnasio));
            attributes.addFlashAttribute("usuario", usuarioServicio.buscarPorEmail(emailUsuario));
            attributes.addFlashAttribute("fecha", dataFormateada);
            attributes.addFlashAttribute("horario", horario);
            return new RedirectView("/reservas/crear/" + idGimnasio);

        }

        return new RedirectView("/reservas/usuario");

    }

    
    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyRole('USUARIO,GIMNASIO,ADMIN')")
    public RedirectView eliminar(@PathVariable String id, Principal principal, RedirectAttributes attributes) throws ErrorServicio, MessagingException{
        try {
            reservasServicio.eliminar(id, principal.getName());
            attributes.addFlashAttribute("creado", "Reserva cancelada");
        } catch (ErrorServicio e) {

            if (gimnasioServicio.buscarPorEmail(principal.getName()) != null) {
                attributes.addFlashAttribute("error", e.getMessage());
                return new RedirectView("/reservas/gimnasio/todas");

            }
            if (usuarioServicio.buscarPorEmail(principal.getName()) != null) {
                attributes.addFlashAttribute("error", e.getMessage());
                return new RedirectView("/reservas/usuario/todas");

            }

        }

        if (gimnasioServicio.buscarPorEmail(principal.getName()) != null) {
            return new RedirectView("/reservas/gimnasio/todas");
        }
        if (usuarioServicio.buscarPorEmail(principal.getName()) != null) {

            return new RedirectView("/reservas/usuario/todas");

        }

        return new RedirectView("/");

    }
    
    @PostMapping("/usuarios/{dni}")
    public ModelAndView ReservaPorUsuario(@PathVariable Long dni, Principal principal, HttpServletRequest request) throws ErrorServicio, MessagingException{
        
        ModelAndView mav = new ModelAndView("reservas-lista");
        try{
            if(usuarioServicio.buscarPorEmail(principal.getName())!=null){
                 mav.addObject("usuario",usuarioServicio.buscarPorEmail(principal.getName()));
            }
           
        }catch(Exception e){
            
        }
         Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("mensaje", flashMap.get("creado"));
            mav.addObject("error", flashMap.get("error"));
        }
        mav.addObject("reservas", reservasServicio.buscarPorUsuarioTodas(dni));
        mav.addObject("title", "Crear Reserva");
        mav.addObject("action", "guardar");

        return mav;

    }

    @GetMapping("/horarios")
    public ModelAndView Horarios(Principal principal) {
        ModelAndView mav = new ModelAndView("horarios");
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
