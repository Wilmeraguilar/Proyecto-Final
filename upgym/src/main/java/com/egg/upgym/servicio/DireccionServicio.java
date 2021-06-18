package com.egg.upgym.servicio;

import com.egg.upgym.entidades.Direccion;
import com.egg.upgym.repositorio.DireccionRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DireccionServicio {
//    
//    @Autowired
//    DireccionRepositorio dirrep;
//    
//    @Transactional
//    public void crear(String provincia, String ciudad, String calleNro){
//        Direccion direccion = new Direccion();
//        
//        direccion.setProvincia(provincia);
//        direccion.setCiudad(ciudad);
//        direccion.setCallenro(calleNro);
//
//        dirrep.save(direccion);
//    }
//    
//     @Transactional(readOnly = true)
//    public Direccion buscarPorId(String id) {
//        Optional<Direccion> direccionOptional = dirrep.findById(id);
//        return direccionOptional.orElse(null);
//    }
//    
//    @Transactional(readOnly = true)
//    public List<Direccion> buscarTodos(){
//        List<Direccion> direccion = dirrep.findAll();
//        return direccion;
//    }
//    
//    @Transactional
//    public void editar(String provincia, String ciudad, String callenro) {
//        dirrep.editar(provincia, ciudad, callenro);
//    }
//    
//    @Transactional
//    public void eliminar(String id) {
//        dirrep.deleteById(id);
//    }
    
}
