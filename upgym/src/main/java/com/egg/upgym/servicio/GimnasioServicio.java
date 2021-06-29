package com.egg.upgym.servicio;

import com.egg.upgym.entidades.Direccion;
import com.egg.upgym.entidades.Gimnasio;
import com.egg.upgym.entidades.Reservas;
import com.egg.upgym.entidades.Rol;
import com.egg.upgym.repositorio.DireccionRepositorio;
import com.egg.upgym.repositorio.GimnasioRepositorio;
import com.egg.upgym.repositorio.RolRepositorio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GimnasioServicio implements UserDetailsService {

    @Autowired
    GimnasioRepositorio gimrep;

    @Autowired
    DireccionRepositorio dirrep;

    @Autowired
    RolRepositorio rolrep;

    @Autowired
    private BCryptPasswordEncoder encoder;

   @Transactional
    public void crear(String nombre, String telefono, Integer capacidad, String email, String clave, String provincia, String ciudad, String calleNro) {
        Gimnasio gimnasio = new Gimnasio();
        Direccion direccion = new Direccion();
        Rol rol = new Rol();

        for (Rol roles : rolrep.findAll()) {

            if (roles.getEstado().equalsIgnoreCase("ACTIVO") && roles.getNombre().equalsIgnoreCase("GIMNASIO")) {
                rol = roles;

            }

        }
        gimnasio.setRol(rol);
        gimnasio.setNombre(nombre);
        gimnasio.setTelefono(telefono);
        gimnasio.setCapacidad(capacidad);
        gimnasio.setEmail(email);
        gimnasio.setClave(encoder.encode(clave));
        direccion.setCiudad(ciudad);
        direccion.setProvincia(provincia);
        direccion.setCalleNro(calleNro);
        gimnasio.setDireccion(direccion);
        gimnasio.setEstado("ACTIVO");

        rolrep.save(rol);
        dirrep.save(direccion);
        gimrep.save(gimnasio);
    }

    
       /*con FOTO*/
     /*@Transactional
    public void crear(String nombre, String telefono, Integer capacidad, String email, String clave, String provincia, String ciudad, String calleNro, String foto) {
        Gimnasio gimnasio = new Gimnasio();
        Direccion direccion = new Direccion();
        Rol rol = new Rol();

        for (Rol roles : rolrep.findAll()) {

            if (roles.getEstado().equalsIgnoreCase("ACTIVO") && roles.getNombre().equalsIgnoreCase("GIMNASIO")) {
                rol = roles;

            }

        }
        gimnasio.setRol(rol);
        gimnasio.setNombre(nombre);
        gimnasio.setTelefono(telefono);
        gimnasio.setCapacidad(capacidad);
        gimnasio.setEmail(email);
        gimnasio.setClave(encoder.encode(clave));
        direccion.setCiudad(ciudad);
        direccion.setProvincia(provincia);
        direccion.setCalleNro(calleNro);
        gimnasio.setFoto(foto);
        gimnasio.setDireccion(direccion);
        gimnasio.setEstado("ACTIVO");

        rolrep.save(rol);
        dirrep.save(direccion);
        gimrep.save(gimnasio);
    }*/
    
       
    
    
    
    
    
    
    @Transactional(readOnly = true)
    public Gimnasio buscarPorId(String id) {
        Optional<Gimnasio> gimnasioOptional = gimrep.findById(id);
        return gimnasioOptional.orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Gimnasio> buscarTodos() {
        List<Gimnasio> gimnasios = new ArrayList();

        for (Gimnasio gimnasio : gimrep.findAll()) {
            if (gimnasio.getEstado().equalsIgnoreCase("ACTIVO")) {
                gimnasios.add(gimnasio);
            }
        }
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
                g.setClave(encoder.encode(clave));
                g.setEstado(estado);
                d.setProvincia(provincia);
                d.setCiudad(ciudad);
                d.setCalleNro(calleNro);
                g.setDireccion(d);

                dirrep.save(d);
                gimrep.save(g);
            } else {

                System.out.println("El gimnasio se encuentra INACTIVO. No se puede modificar");

            }

        }

    }

//    @Transactional
//    public void editar(String nombre, String telefono, Integer capacidad, String email, String clave, Direccion direccion) {
//        gimrep.editar(nombre, telefono, capacidad, email, clave, direccion);
//    }
    @Transactional
    public List<Gimnasio> buscarPorCiudad(String ciudad) {
        List<Gimnasio> gimnasios = new ArrayList();

        for (Gimnasio gimnasio : gimrep.buscarPorCiudad(ciudad)) {

            if (gimnasio.getEstado().equalsIgnoreCase("ACTIVO")) {
                gimnasios.add(gimnasio);
            }
        }
        return gimnasios;
    }
    @Transactional
    public List<Gimnasio> buscarPorprovinciaCiudad(String provincia,String ciudad) {
        List<Gimnasio> gimnasios = new ArrayList();

        for (Gimnasio gimnasio : gimrep.buscarPorProvinciaYCiudad(provincia, ciudad)) {

            if (gimnasio.getEstado().equalsIgnoreCase("ACTIVO")) {
                gimnasios.add(gimnasio);
            }
        }
        return gimnasios;
    }

    @Transactional
    public List<Gimnasio> buscarPorNombre(String nombre) {
        List<Gimnasio> gimnasios = gimrep.findAll();
        List<Gimnasio> g = new ArrayList<Gimnasio>();

        for (Gimnasio gimnasio1 : gimnasios) {
            if (gimnasio1.getNombre().equalsIgnoreCase(nombre) && gimnasio1.getEstado().equalsIgnoreCase("ACTIVO")) {
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
            } else {

                System.out.println("El gimnasio se encuentra INACTIVO. No se puede eliminar");

            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Gimnasio gimnasio = gimrep.buscarPorGim(email);

        if (gimnasio == null) {
            throw new UsernameNotFoundException("No se encontro un gimnasio registrado con el email " + email);
        }
        GrantedAuthority rol = new SimpleGrantedAuthority("ROLE_" + gimnasio.getRol().getNombre());

        return new User(gimnasio.getEmail(), gimnasio.getClave(), Collections.singletonList(rol));
    }
}
