package com.egg.upgym.servicio;

import com.egg.upgym.entidades.Direccion;
import com.egg.upgym.entidades.Usuario;
import com.egg.upgym.repositorio.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {
    
    @Autowired
    UsuarioRepositorio usurep;
    
    @Transactional
    public void crear(Long dni, String nombre, String apellido,  String telefono, String email, String clave, Direccion direccion){
        Usuario usuario = new Usuario();
        
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setClave(clave);

        usurep.save(usuario);
    }
    
     @Transactional(readOnly = true)
    public Usuario buscarPorId(Long dni) {
        Optional<Usuario> usuarioOptional = usurep.findById(dni);
        return usuarioOptional.orElse(null);
    }
    
    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos(){
        List<Usuario> usuarios = usurep.findAll();
        return usuarios;
    }
    
    @Transactional
    public void editar(Long dni, String nombre, String apellido, String email, String clave) {
        usurep.editar(dni, nombre, apellido, email, clave);
    }
    
    @Transactional
    public void eliminar(Long documento) {
        usurep.deleteById(documento);
    }
    
}
