package com.egg.upgym.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Reservas implements Serializable{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    private String turno;
    
    @ManyToOne
    Gimnasio gimnasio;
    
    @ManyToOne
    Usuario usuario;
    
    private String estado;

    public Reservas() {
    }

    public Reservas(String id, Date fecha, String turno, Gimnasio gimnasio, Usuario usuario, String estado) {
        this.id = id;
        this.fecha = fecha;
        this.turno = turno;
        this.gimnasio = gimnasio;
        this.usuario = usuario;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Gimnasio getGimnasio() {
        return gimnasio;
    }

    public void setGimnasio(Gimnasio gimnasio) {
        this.gimnasio = gimnasio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    
    @Override
    public String toString() {
        return "Reservas{" + "id=" + id + ", fecha=" + fecha + ", turno=" + turno + '}';
    }
    
    
}
