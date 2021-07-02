package com.egg.upgym.servicio;

import com.egg.upgym.entidades.Direccion;
import com.egg.upgym.entidades.Gimnasio;
import com.egg.upgym.entidades.Reservas;
import com.egg.upgym.entidades.Usuario;
import com.egg.upgym.repositorio.GimnasioRepositorio;
import com.egg.upgym.repositorio.ReservasRepositorio;
import com.egg.upgym.repositorio.UsuarioRepositorio;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservasServicio {

    @Autowired
    ReservasRepositorio resrep;

    @Autowired
    UsuarioRepositorio usurep;

    @Autowired
    GimnasioRepositorio gimrep;

    @Transactional(rollbackFor = Exception.class)
    public void crear(Date fecha, String horario, String idGimnasio, String emailUsuario) throws ErrorServicio {

        validar(fecha, horario, idGimnasio, emailUsuario);

        Reservas reservas = new Reservas();
        Usuario usuario = usurep.buscarPorUser(emailUsuario);
        Optional<Gimnasio> gimnasio = gimrep.findById(idGimnasio);
        Gimnasio g = gimnasio.get();

        reservas.setFecha(fecha);
        reservas.setHorario(horario);
        reservas.setGimnasio(g);
        reservas.setUsuario(usuario);
        reservas.setEstado("ACTIVO");

        resrep.save(reservas);

    }

    @Transactional(readOnly = true)
    public Reservas buscarPorId(String id) {
        Optional<Reservas> reservaOptional = resrep.findById(id);
        return reservaOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public void validar(Date fecha, String horario, String idGimnasio, String emailUsuario) throws ErrorServicio {

        Usuario usuario = usurep.buscarPorUser(emailUsuario);
        Optional<Gimnasio> gimnasio = gimrep.findById(idGimnasio);
        Gimnasio g = gimnasio.get();
        List<Reservas> listaCap = resrep.buscarPorGymHorarioFecha(idGimnasio, horario, fecha);
        Date actual = new Date();

        if (listaCap.size() > g.getCapacidad()) {

            throw new ErrorServicio("Horario/fecha elegido no disponible");

        }

        LocalTime horaActual = LocalTime.now();
        
        String horaAc=horaActual.format(DateTimeFormatter.ISO_LOCAL_TIME);

        LocalTime horaElegida = LocalTime.of(Integer.valueOf(horario.substring(0, 2)), 00, 00);
        
        if(fecha.equals(actual)){
            
        }

        if (horaElegida.isBefore(horaActual)) {
            throw new ErrorServicio("Horario ingresado fuera de rango");
            
        }

        if (fecha.before(actual)) {

            throw new ErrorServicio("Fecha ingresada fuera de rango");
        }
        

    }

    @Transactional(readOnly = true)
    public List<Reservas> buscarPorUsuario(Long dni
    ) {

        List<Reservas> reservas = new ArrayList();

        for (Reservas reserva : resrep.buscarPorUsuario(dni)) {
            if (reserva.getEstado().equalsIgnoreCase("ACTIVO")) {
                reservas.add(reserva);
            }
        }
        return reservas;
    }

    @Transactional(readOnly = true)
    public List<Reservas> buscarTodos() {
        List<Reservas> reservas = resrep.findAll();
        return reservas;
    }

    @Transactional
    public void modificar(String id, Date fecha,
             String horario, String idGimnasio,
             Long idUsuario, String estado
    ) {

        Optional<Reservas> reserva = resrep.findById(id);
        Optional<Usuario> usuario = usurep.findById(idUsuario);
        Optional<Gimnasio> gimnasio = gimrep.findById(idGimnasio);

        List<Reservas> listaCap = resrep.buscarPorGymHorarioFecha(idGimnasio, horario, fecha);

        if (listaCap.size() < gimnasio.get().getCapacidad()) {
            if (reserva.isPresent()) {
                Reservas r = reserva.get();
                Gimnasio g = gimnasio.get();
                Usuario u = usuario.get();

                if (r.getEstado().equalsIgnoreCase("ACTIVO")) {
                    r.setFecha(fecha);
                    r.setHorario(horario);
                    r.setGimnasio(g);
                    r.setUsuario(u);
                    r.setEstado(estado);

                    resrep.save(r);
                } else {

                    System.out.println("La reserva se encuentra INACTIVA o TERMINADA. No se puede modificar");

                }

            }
        } else {
            System.out.println("La capacidad ha sido excedida");
        }

    }

    @Transactional
    public void eliminar(String id
    ) {
        Optional<Reservas> reserva = resrep.findById(id);

        if (reserva.isPresent()) {
            Reservas r = reserva.get();

            if (r.getEstado().equalsIgnoreCase("ACTIVO")) {
                r.setEstado("INACTIVO");

                resrep.save(r);
            } else {

                System.out.println("La reserva se encuentra INACTIVA o TERMINADA. No se puede eliminar");

            }
        }
    }
}
