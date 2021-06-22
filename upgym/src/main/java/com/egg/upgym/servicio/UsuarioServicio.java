package com.egg.upgym.servicio;

import com.egg.upgym.entidades.Direccion;
import com.egg.upgym.entidades.Usuario;
import com.egg.upgym.repositorio.DireccionRepositorio;
import com.egg.upgym.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {

    @Autowired
    UsuarioRepositorio usurep;

    @Autowired
    DireccionRepositorio dirrep;

    @Transactional
    public void crear(Long dni, String nombre, String apellido, String telefono, String email, String clave, String provincia, String ciudad, String calleNro) {
        Usuario usuario = new Usuario();
        Direccion direccion = new Direccion();

        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setClave(clave);
        direccion.setProvincia(provincia);
        direccion.setCiudad(ciudad);
        direccion.setCalleNro(calleNro);
        usuario.setDireccion(direccion);
        
        dirrep.save(direccion);
        usurep.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long dni) {
        Optional<Usuario> usuarioOptional = usurep.findById(dni);
        return usuarioOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        List<Usuario> usuarios = usurep.findAll();
        return usuarios;
    }
    
    @Transactional
    public List<Usuario> buscarPorApellido(String apellido){
        List<Usuario> usuarios = usurep.findAll();
        List <Usuario> u = new ArrayList<Usuario>();
        
        for (Usuario usuario1 : usuarios) {
            if (usuario1.getNombre().equalsIgnoreCase(apellido)) {
                u.add(usuario1);
            }
        }
        return u;
    }

    @Transactional
    public void modificar(Long dni, String nombre, String apellido, String telefono, String email, String clave, String idDireccion, String provincia, String ciudad, String calleNro, String estado) {

        Optional<Usuario> usuario = usurep.findById(dni);
        Optional<Direccion> direccion = dirrep.findById(idDireccion);

        if (usuario.isPresent()) {
            Usuario u = usuario.get();
            Direccion d = direccion.get();

            if (u.getEstado().equalsIgnoreCase("ACTIVO")) {
                u.setNombre(nombre);
                u.setApellido(apellido);
                u.setTelefono(telefono);
                u.setEmail(email);
                u.setClave(clave);
                u.setEstado(estado);
                d.setProvincia(provincia);
                d.setCiudad(ciudad);
                d.setCalleNro(calleNro);
                u.setDireccion(d);
                
                dirrep.save(d);
                usurep.save(u);
            } else {

                System.out.println("El usuario se encuentra INACTIVO. No se puede modificar");

            }

        }

    }

//    @Transactional
//    public void editar(Long dni, String nombre, String apellido, String email, String clave) {
//        usurep.editar(dni, nombre, apellido, email, clave);
//    }
    @Transactional
    public void eliminar(Long dni) {
        Optional<Usuario> usuario = usurep.findById(dni);
        
        if (usuario.isPresent()) {
            Usuario u = usuario.get();

            if (u.getEstado().equalsIgnoreCase("ACTIVO")) {
                u.setEstado("INACTIVO");
                
                usurep.save(u);
            }else{
                
                System.out.println("El usuario se encuentra INACTIVO. No se puede eliminar");
                
            }
        }
    }

}
