package com.example.calcamp.model.dao;

import com.example.calcamp.model.entities.League;
import com.example.calcamp.model.entities.Team;

import java.util.List;

public interface leagueDAO {
        long insert(League obj);
        long update(League obj);
        long deleteById(Integer id);
        League findById(Integer id);
        List<League> findAll();

}


