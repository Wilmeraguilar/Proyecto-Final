package com.egg.upgym.servicio;

import com.egg.upgym.entidades.Direccion;
import com.egg.upgym.entidades.Gimnasio;
import com.egg.upgym.entidades.Reservas;
import com.egg.upgym.entidades.Usuario;
import com.egg.upgym.repositorio.GimnasioRepositorio;
import com.egg.upgym.repositorio.ReservasRepositorio;
import com.egg.upgym.repositorio.UsuarioRepositorio;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
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

    @Autowired
    EmailServicio emailServicio;

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
        reservas.setEstado("ACTIVA");

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

        if (listaCap.size() > g.getCapacidad()) {

            throw new ErrorServicio("Horario/fecha supera capacidad disponible");

        }

        Reservas reserva = resrep.buscarPorUsuarioHorarioFecha(idGimnasio, usuario.getDni(), horario, fecha);

        if (reserva != null && reserva.getEstado().equalsIgnoreCase("ACTIVA")) {

            throw new ErrorServicio("Ya tiene una reserva creada");

        }

        LocalDate fechaActual = LocalDate.now();

        LocalDate fechaElegida = convertirDateALocal(fecha);

        if (fechaElegida.isBefore(fechaActual)) {

            throw new ErrorServicio("Fecha fuera de rango");
        }

        LocalTime horaActual = LocalTime.now();

        LocalTime horaElegida = LocalTime.of(Integer.valueOf(horario.substring(0, 2)), 00, 00);

        if (fechaElegida.isEqual(fechaActual) && horaElegida.isBefore(horaActual)) {
            throw new ErrorServicio("Horario fuera de rango");

        }

    }

    @Transactional(readOnly = true)
    public LocalDate convertirDateALocal(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd-");

        String fechaE = formato.format(fecha);
        LocalDate fechaElegida = LocalDate.of(Integer.valueOf(fechaE.substring(0, 4)), Integer.valueOf(fechaE.substring(5, 7)), Integer.valueOf(fechaE.substring(8, 10)));

        return fechaElegida;

    }

    @Transactional
    public List<Reservas> buscarPorUsuario(Long dni) {

        List<Reservas> reservas = new ArrayList();
        LocalDate actual = LocalDate.now();

        for (Reservas reserva : resrep.buscarPorUsuario(dni)) {

            LocalDate fecha = convertirDateALocal(reserva.getFecha());

            if (fecha.isBefore(actual)) {

                reserva.setEstado("TERMINADA");
                resrep.save(reserva);
            }
            if (reserva.getEstado().equalsIgnoreCase("ACTIVA")) {
                reservas.add(reserva);
            }

        }
        return reservas;
    }

    @Transactional(readOnly = true)
    public List<Reservas> buscarPorUsuarioTodas(Long dni) {

        List<Reservas> reservas = resrep.buscarPorUsuario(dni);

        return reservas;
    }

    @Transactional
    public List<Reservas> buscarPorGimnasio(String id) {

        List<Reservas> reservas = new ArrayList();
        LocalDate actual = LocalDate.now();

        for (Reservas reserva : resrep.buscarPorGimnasio(id)) {

            LocalDate fecha = convertirDateALocal(reserva.getFecha());

            if (fecha.isBefore(actual)) {

                reserva.setEstado("TERMINADA");
                resrep.save(reserva);
            }
            if (reserva.getEstado().equalsIgnoreCase("ACTIVA")) {
                reservas.add(reserva);
            }

        }
        return reservas;
    }

    @Transactional(readOnly = true)
    public List<Reservas> buscarPorGimnasioTodas(String id) {

        List<Reservas> reservas = resrep.buscarPorGimnasio(id);

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

                if (r.getEstado().equalsIgnoreCase("ACTIVA")) {
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
    public void eliminar(String id, String email) throws ErrorServicio,MessagingException {
        Optional<Reservas> reserva = resrep.findById(id);

        if (reserva.isPresent()) {
            Reservas r = reserva.get();

            if (r.getEstado().equalsIgnoreCase("ACTIVA")) {
                r.setEstado("CANCELADA");

                if (gimrep.buscarPorGim(email) != null) {
                    emailServicio.enviarCorreoAsincrono(email, "Cancelacion Reserva","Cancelaste la reserva de "+r.getUsuario().getNombre()+" "+r.getUsuario().getApellido());
                    emailServicio.enviarCorreoAsincrono(r.getUsuario().getEmail(), "Cancelacion Reserva",r.getGimnasio().getNombre()+" ha cancelado su reserva");

                }

                resrep.save(r);
            } else {

                throw new ErrorServicio("La reserva se encuentra CANCELADA o TERMINADA. No se puede eliminar");

            }
        }
    }
}
