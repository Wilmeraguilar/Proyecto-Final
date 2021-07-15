
package com.egg.upgym.servicio;

import com.egg.upgym.entidades.Gimnasio;
import com.egg.upgym.entidades.Usuario;
import com.egg.upgym.repositorio.GimnasioRepositorio;
import com.egg.upgym.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServicio {
    
    @Autowired
    UsuarioRepositorio usurep;
    
    @Autowired
    GimnasioRepositorio gimrep;
    
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    public void validar(String email, String clave) throws ErrorServicio{
        
        Usuario usuario = usurep.buscarPorUser(email);
        Gimnasio gimnasio = gimrep.buscarPorEmail(email);
        
        if (usuario == null) {
            throw new ErrorServicio("Email o Contraseña incorrecto");
        } else {
            
            String criptClave = encoder.encode(clave);
            if (!criptClave.equals(usuario.getClave())) {
                throw new ErrorServicio("Email o Contraseña incorrecto");
            }
        }
        
    }
    
}
