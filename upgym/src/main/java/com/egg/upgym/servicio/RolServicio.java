package com.egg.upgym.servicio;

import com.egg.upgym.entidades.Rol;
import com.egg.upgym.repositorio.RolRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolServicio {
    
    @Autowired
    private RolRepositorio rolRepositorio;

    @Transactional
    public void crear(String nombre) {
        Rol rol = new Rol();

        rol.setNombre(nombre);
        rol.setEstado("ACTIVO");
        rolRepositorio.save(rol);
    }

    @Transactional
    public void modificar(String id, String nombre, String estado) {
        Optional<Rol> rolOp = rolRepositorio.findById(id);

        if (rolOp.isPresent()) {
            Rol rol = rolOp.get();
            rol.setNombre(nombre);
            rol.setEstado(estado);
            rolRepositorio.save(rol);
        }

    }

    @Transactional(readOnly = true)
    public List<Rol> buscarTodos() {
        List<Rol>listaRol=new ArrayList();
        for (Rol rol : rolRepositorio.findAll()) {
            if(rol.getEstado().equalsIgnoreCase("ACTIVO")){
                listaRol.add(rol);
            }
        }
        return listaRol;
    }

    @Transactional(readOnly = true)
    public Rol buscarPorId(String id) {
        Optional<Rol> rolOptional = rolRepositorio.findById(id);
        return rolOptional.orElse(null);
    }

    @Transactional
    public void eliminar(String id) {
       Optional<Rol> rolOp = rolRepositorio.findById(id);

        if (rolOp.isPresent()&&rolOp.get().getEstado().equalsIgnoreCase("ACTIVO")) {
            Rol rol = rolOp.get();
            rol.setEstado("INACTIVO");
            rolRepositorio.save(rol);
        }
    }
    
}
