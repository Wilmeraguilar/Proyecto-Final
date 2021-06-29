package com.egg.upgym.servicio;

import com.egg.upgym.entidades.Direccion;
import com.egg.upgym.entidades.Rol;
import com.egg.upgym.entidades.Usuario;
import com.egg.upgym.repositorio.DireccionRepositorio;
import com.egg.upgym.repositorio.RolRepositorio;
import com.egg.upgym.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    UsuarioRepositorio usurep;

    @Autowired
    DireccionRepositorio dirrep;

    @Autowired
    RolRepositorio rolrep;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void crear(Long dni, String nombre, String apellido, String telefono, String email, String clave, String provincia, String ciudad, String calleNro) {
        Usuario usuario = new Usuario();
        Direccion direccion = new Direccion();

        Rol rol = new Rol();

        for (Rol roles : rolrep.findAll()) {

            if (roles.getEstado().equalsIgnoreCase("ACTIVO") && roles.getNombre().equalsIgnoreCase("USUARIO")) {
                rol = roles;

            }

        }
        usuario.setRol(rol);

        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setClave(encoder.encode(clave));
        direccion.setProvincia(provincia);
        direccion.setCiudad(ciudad);
        direccion.setCalleNro(calleNro);
        usuario.setDireccion(direccion);
        usuario.setEstado("ACTIVO");

        rolrep.save(rol);
        dirrep.save(direccion);
        usurep.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long dni) {
        Optional<Usuario> usuarioOptional = usurep.findById(dni);
        if (usuarioOptional.isPresent() && usuarioOptional.get().getEstado().equalsIgnoreCase("ACTIVO")) {
            return usuarioOptional.get();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        List<Usuario> usuarios = new ArrayList();

        for (Usuario usuario : usurep.findAll()) {
            if (usuario.getEstado().equalsIgnoreCase("ACTIVO")) {
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    @Transactional
    public List<Usuario> buscarPorApellido(String apellido) {
        List<Usuario> usuarios = usurep.findAll();
        List<Usuario> u = new ArrayList<Usuario>();

        for (Usuario usuario1 : usuarios) {
            if (usuario1.getApellido().equalsIgnoreCase(apellido) && usuario1.getEstado().equalsIgnoreCase("ACTIVO")) {
                u.add(usuario1);
            }
        }
        return u;
    }
     @Transactional
    public Usuario buscarPorEmail(String email) {
        List<Usuario> usuarios = usurep.findAll();
        

        for (Usuario usuario1 : usuarios) {
            if (usuario1.getEmail().equalsIgnoreCase(email) && usuario1.getEstado().equalsIgnoreCase("ACTIVO")) {
                return usuario1;
            }
        }
        return null;
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
                u.setClave(encoder.encode(clave));
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
            } else {

                System.out.println("El usuario se encuentra INACTIVO. No se puede eliminar");

            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usurep.buscarPorUser(email);

        if (usuario == null) {
            throw new UsernameNotFoundException("No se encontro un usuario registrado con el email " + email);
        }
        GrantedAuthority rol = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre());

        return new User(usuario.getEmail(), usuario.getClave(), Collections.singletonList(rol));
    }

}
