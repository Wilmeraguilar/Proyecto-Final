package com.egg.upgym.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Gimnasio implements Serializable{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String telefono;
    private Integer capacidad;
    @Column(unique = true)
    private String email;
    private String clave;
    private String foto;
    
    
    @ManyToOne
    private Rol rol;
    
    @OneToOne
    private Direccion direccion;
    
    @OneToMany
    private List<Reservas>reservas;
    
    private String estado;

    public Gimnasio() {
    }

    

    public Gimnasio(String id, String nombre, String telefono, Integer capacidad, String email, String clave, Direccion direccion, List<Reservas> reservas, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.telefono = telefono;
        this.email = email;
        this.clave = clave;
        this.direccion = direccion;
        this.reservas = reservas;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public List<Reservas> getReservas() {
        return reservas;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    

    public void setReservas(List<Reservas> reservas) {
        this.reservas = reservas;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public Gimnasio(String foto) {
        this.foto = foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    

    @Override
    public String toString() {
        return "Gimnasio{" + "nombre=" + nombre + ", capacidad=" + capacidad + ", email=" + email + ", estado=" + estado +  ", foto=" + foto + '}';
    }
    
    
}
