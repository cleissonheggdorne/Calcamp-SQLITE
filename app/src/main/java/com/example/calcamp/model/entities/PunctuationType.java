package com.example.calcamp.model.entities;

import java.io.Serializable;
import java.util.Objects;

public class PunctuationType implements Serializable{

    private int id;
    private String name;

    public PunctuationType() {

    }

    public PunctuationType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PunctuationType [id=" + id + ", name=" + name + "]";
    }


}

