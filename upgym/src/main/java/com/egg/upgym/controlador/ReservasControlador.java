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

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping
    public ModelAndView mostrarTodos() {
        ModelAndView mav = new ModelAndView("reservas-lista");
        mav.addObject("reservas", reservasServicio.buscarTodos());
        return mav;
    }

//    @GetMapping("/buscar/dniFecha")
//    public ModelAndView mostrarPorDniFecha(@RequestParam Long dni,@RequestParam Date fecha) {
//        ModelAndView mav = new ModelAndView("reservas");
//        mav.addObject("reservas", reservasServicio.buscarPorDniFecha(dni,fecha));
//
//        return mav;
//    }
    @GetMapping("/mias")
    public ModelAndView Reserva(Principal principal) {
        ModelAndView mav = new ModelAndView("reservas-lista");
        mav.addObject("reservas", reservasServicio.buscarPorUsuario(usuarioServicio.buscarPorEmail(principal.getName()).getDni()));
        mav.addObject("title", "Crear Reserva");
        mav.addObject("action", "guardar");

        return mav;
    }

    @GetMapping("/crear/{id}")

    public ModelAndView crearReserva(@PathVariable String id, Principal principal, HttpServletRequest request) {

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

    @GetMapping("/editar/{id}")
    public ModelAndView editarReserva(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("reservas");
        mav.addObject("reserva", reservasServicio.buscarPorId(id));
        mav.addObject("title", "Editar Reserva");
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha, @RequestParam String horario, @RequestParam("gimnasio") String idGimnasio, @RequestParam("usuario") String emailUsuario, RedirectAttributes attributes) {
        try {
            reservasServicio.crear(fecha, horario, idGimnasio, emailUsuario);
            attributes.addFlashAttribute("creado", "Reserva creada correctamente");

        } catch (ErrorServicio e) {
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("fecha", fecha);
            attributes.addFlashAttribute("gimnasio", gimnasioServicio.buscarPorId(idGimnasio));
            attributes.addFlashAttribute("usuario", usuarioServicio.buscarPorEmail(emailUsuario));
            return new RedirectView("/reservas/crear/" + idGimnasio);

        }
        return new RedirectView("/");

    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam String id, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha, @RequestParam String horario, @RequestParam String idGimnasio, @RequestParam Long dniUsuario, @RequestParam String estado) {
        reservasServicio.modificar(id, fecha, horario, idGimnasio, dniUsuario, estado);
        return new RedirectView("/");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable String id) {
        reservasServicio.eliminar(id);
        return new RedirectView("/reservas/mias");
    }

}
