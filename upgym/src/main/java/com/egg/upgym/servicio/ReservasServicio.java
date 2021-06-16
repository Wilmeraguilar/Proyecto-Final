package com.egg.upgym.servicio;

import com.egg.upgym.entidades.Gimnasio;
import com.egg.upgym.entidades.Reservas;
import com.egg.upgym.entidades.Usuario;
import com.egg.upgym.repositorio.ReservasRepositorio;
import com.egg.upgym.repositorio.UsuarioRepositorio;
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
    
    @Transactional
    public void crear(Date fecha, String turno, Gimnasio gimnasio, Usuario usuario){
        Reservas reservas = new Reservas();
        
        reservas.setFecha(fecha);
        reservas.setTurno(turno);
        reservas.setGimnasio(gimnasio);
        reservas.setUsuario(usuario);

        resrep.save(reservas);
    }
    
     @Transactional(readOnly = true)
    public Reservas buscarPorId(String id) {
        Optional<Reservas> reservaOptional = resrep.findById(id);
        return reservaOptional.orElse(null);
    }
    
    @Transactional(readOnly = true)
    public List<Reservas> buscarTodos(){
        List<Reservas> reservas = resrep.findAll();
        return reservas;
    }
    
    @Transactional
    public void editar(Date fecha, String turno, Gimnasio gimnasio, Usuario usuario) {
        resrep.editar(fecha, turno, gimnasio, usuario);
    }
    
    @Transactional
    public void eliminar(String id) {
        resrep.deleteById(id);
    }
}
