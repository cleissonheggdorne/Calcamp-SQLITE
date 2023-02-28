package com.example.calcamp.model.entities;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id.equals(team.id) && name.equals(team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
