package com.egg.upgym.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Direccion implements Serializable{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String provincia;
    private String ciudad;
    private String callenro;

    public Direccion() {
    }

    public Direccion(String id, String provincia, String ciudad, String callenro) {
        this.id = id;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.callenro = callenro;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCallenro() {
        return callenro;
    }

    public void setCallenro(String callenro) {
        this.callenro = callenro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    
    
}
