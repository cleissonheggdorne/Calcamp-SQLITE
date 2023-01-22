package com.example.calcamp.model.dao;

import com.example.calcamp.model.entities.Team;

import java.util.List;

public interface TeamDAO {
        long insert(Team obj);
        long update(Team obj);
        long deleteById(Integer id);
        Team findById(Integer id);
        List<Team> findAll();
}


