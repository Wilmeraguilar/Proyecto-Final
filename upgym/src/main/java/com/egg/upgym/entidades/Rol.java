package com.egg.upgym.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Rol{
    
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
}
