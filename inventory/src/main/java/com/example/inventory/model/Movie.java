package com.example.inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Movie {
  

    @Id
    @Column(name="id")
    private String key;
    private String value;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    @Override
    public String toString() {
        return "Movie [key=" + key + ", value=" + value + "]";
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }


}
