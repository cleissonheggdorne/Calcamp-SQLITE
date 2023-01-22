package com.example.calcamp.model.entities;

public class Team {
    private Integer id;
    private String name;

    public Team () {

    }

    public Team(Integer id, String name) {
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
        return "Team [id=" + id + ", name=" + name + "]";
    }

}
