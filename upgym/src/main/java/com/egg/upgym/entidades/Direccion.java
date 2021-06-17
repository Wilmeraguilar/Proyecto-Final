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
    private String calleNro;

    public Direccion() {
    }

    public Direccion(String id, String provincia, String ciudad, String calleNro) {
        this.id = id;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.calleNro = calleNro;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalleNro() {
        return calleNro;
    }

    public void setCalleNro(String calleNro) {
        this.calleNro = calleNro;
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
