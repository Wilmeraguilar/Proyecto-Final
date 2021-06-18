package com.egg.upgym.servicio;

import com.egg.upgym.entidades.Direccion;
import com.egg.upgym.entidades.Gimnasio;
import com.egg.upgym.entidades.Reservas;
import com.egg.upgym.repositorio.DireccionRepositorio;
import com.egg.upgym.repositorio.GimnasioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GimnasioServicio {
    
    @Autowired
    GimnasioRepositorio gimrep;
    
    @Autowired
    DireccionRepositorio dirrep;
    
    @Transactional
    public void crear(String nombre, String telefono, Integer capacidad, String email, String clave, String ciudad, String provincia, String calleNro){
        Gimnasio gimnasio = new Gimnasio();
        Direccion direccion = new Direccion();
        
        gimnasio.setNombre(nombre);
        gimnasio.setTelefono(telefono);
        gimnasio.setCapacidad(capacidad);
        gimnasio.setEmail(email);
        gimnasio.setClave(clave);
        direccion.setCiudad(ciudad);
        direccion.setProvincia(provincia);
        direccion.setCalleNro(calleNro);
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
    public void modificar(String id, String nombre, String telefono, Integer capacidad, String email, String clave, String idDireccion, String provincia, String ciudad, String calleNro, String estado) {

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
    
//    @Transactional
//    public void editar(String nombre, String telefono, Integer capacidad, String email, String clave, Direccion direccion) {
//        gimrep.editar(nombre, telefono, capacidad, email, clave, direccion);
//    }
    
    @Transactional
    public List<Gimnasio> buscarPorCiudad(String ciudad){
        List<Gimnasio> gimnasio = gimrep.buscarPorCiudad(ciudad);
        return gimnasio;
    }
    
    @Transactional
    public List<Gimnasio> buscarPorNombre(String nombre){
        List<Gimnasio> gimnasios = gimrep.findAll();
        List <Gimnasio> g = new ArrayList<Gimnasio>();
        
        for (Gimnasio gimnasio1 : gimnasios) {
            if (gimnasio1.getNombre().equalsIgnoreCase(nombre)) {
                g.add(gimnasio1);
            }
        }
        return g;
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
