package com.egg.upgym.repositorio;

import com.egg.upgym.entidades.Reservas;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservasRepositorio extends JpaRepository<Reservas, String> {
    @Query("SELECT r FROM Reservas r WHERE gimnasio.id=:gimnasio AND horario=:horario AND fecha=:fecha")
    List<Reservas> buscarPorGymHorarioFecha(@Param("gimnasio") String gimnasio,@Param("horario") String horario,@Param("fecha") Date fecha);
}
