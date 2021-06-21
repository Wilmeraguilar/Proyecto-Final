package com.egg.upgym.servicio;

import com.egg.upgym.entidades.Direccion;
import com.egg.upgym.entidades.Gimnasio;
import com.egg.upgym.entidades.Reservas;
import com.egg.upgym.entidades.Usuario;
import com.egg.upgym.repositorio.GimnasioRepositorio;
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
    
    @Autowired
    UsuarioRepositorio usurep;
    
    @Autowired
    GimnasioRepositorio gimrep;
    
    @Transactional
    public void crear(Date fecha, String horario, String idGimnasio,Long dniUsuario){
        Reservas reservas = new Reservas();
        Optional<Usuario> usuario = usurep.findById(dniUsuario);
        Optional<Gimnasio> gimnasio = gimrep.findById(idGimnasio);
        
        Usuario u = usuario.get();
        Gimnasio g = gimnasio.get();
        
        reservas.setFecha(fecha);
        reservas.setHorario(horario);
        reservas.setGimnasio(g);
        reservas.setUsuario(u);

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
    public void modificar(String id, Date fecha, String horario, String idGimnasio, Long idDni, String clave, String idDireccion, String provincia, String ciudad, String calleNro, String estado) {

        Optional<Gimnasio> gimnasio = gimrep.findById(id);
        Optional<Direccion> direccion = dirrep.findById(id);
        

        if (gimnasio.isPresent()) {
            Gimnasio g = gimnasio.get();
            Direccion d = direccion.get();

            if (g.getEstado().equalsIgnoreCase("ACTIVO")) {
                g.setNombre(nombre);
                g.setTelefono(telefono);
                g.setCapacidad(capacidad);
                g.setEmail(email);
                g.setClave(clave);
                g.setEstado(estado);
                d.setProvincia(provincia);
                d.setCiudad(ciudad);
                d.setCalleNro(calleNro);
                g.setDireccion(d);
                
                gimrep.save(g);
            }else{
                
                System.out.println("El gimnasio se encuentra INACTIVO. No se puede modificar");
                
            } 

        }

    }
    
    @Transactional
    public void eliminar(String id) {
        Optional<Gimnasio> gimnasio = gimrep.findById(id);
        
        if (gimnasio.isPresent()) {
            Gimnasio g = gimnasio.get();

            if (g.getEstado().equalsIgnoreCase("ACTIVO")) {
                g.setEstado("INACTIVO");
                
                gimrep.save(g);
            }else{
                
                System.out.println("El gimnasio se encuentra INACTIVO. No se puede eliminar");
                
            }
        }
    }
}
