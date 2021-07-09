package com.egg.upgym.repositorio;

import com.egg.upgym.entidades.Rol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, String> {
    
    @Query("SELECT r FROM Rol r WHERE r.nombre=:nombre")
    Rol buscarPorNombre(@Param("nombre") String nombre);
}
