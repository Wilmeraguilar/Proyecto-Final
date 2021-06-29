package com.egg.upgym.repositorio;

import com.egg.upgym.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE apellido=:apellido")
    List<Usuario> buscarPorApellido(@Param("apellido") String apellido);

        @Query("SELECT u from Usuario u WHERE email=:email")
    Usuario buscarPorUser(@Param("email") String email);
}
