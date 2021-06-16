package com.egg.upgym.repositorio;

import com.egg.upgym.entidades.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservasRepositorio extends JpaRepository<Reservas, String> {
    
}
