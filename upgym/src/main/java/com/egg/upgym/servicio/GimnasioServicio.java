package com.egg.upgym.servicio;

import com.egg.upgym.entidades.Direccion;
import com.egg.upgym.entidades.Gimnasio;
import com.egg.upgym.entidades.Reservas;
import com.egg.upgym.repositorio.GimnasioRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GimnasioServicio {
    
    @Autowired
    GimnasioRepositorio gimrep;
    
    @Transactional
    public void crear(String nombre, String telefono, Integer capacidad, String email, String clave, Direccion direccion){
        Gimnasio gimnasio = new Gimnasio();
        
        gimnasio.setNombre(nombre);
        gimnasio.setTelefono(telefono);
        gimnasio.setCapacidad(capacidad);
        gimnasio.setEmail(email);
        gimnasio.setClave(clave);
        gimnasio.setDireccion(direccion);

        gimrep.save(gimnasio);
    }
    
     @Transactional(readOnly = true)
    public Gimnasio buscarPorId(String id) {
        Optional<Gimnasio> gimnasioOptional = gimrep.findById(id);
        return gimnasioOptional.orElse(null);
    }
    
    @Transactional(readOnly = true)
    public List<Gimnasio> buscarTodos(){
        List<Gimnasio> gimnasios = gimrep.findAll();
        return gimnasios;
    }
    
    @Transactional
    public void modificar(String id, String nombre, String telefono, Integer capacidad, String email, String clave, List<Reservas> reservas, String idDireccion, String provincia, String ciudad, String calleNro) {

        Optional<Gimnasio> gimnasio = gimrep.findById(id);

        if (gimnasio.isPresent()) {
            Gimnasio g = gimnasio.get();
            Direccion d = new Direccion();

            if (g.getEstado().equalsIgnoreCase("ACTIVO")) {
                g.setNombre(nombre);
                g.setTelefono(telefono);
                g.setCapacidad(capacidad);
                g.setEmail(email);
                g.setClave(clave);
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
    
//    @Transactional
//    public void editar(String nombre, String telefono, Integer capacidad, String email, String clave, Direccion direccion) {
//        gimrep.editar(nombre, telefono, capacidad, email, clave, direccion);
//    }
    
    @Transactional
    public void eliminar(String id) {
        gimrep.deleteById(id);
    }
}
