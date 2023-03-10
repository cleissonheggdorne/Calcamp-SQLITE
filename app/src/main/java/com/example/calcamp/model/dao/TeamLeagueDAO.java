package com.example.calcamp.model.dao;

import com.example.calcamp.model.entities.Team;
import com.example.calcamp.model.entities.TeamLeague;

import java.util.List;

public interface TeamLeagueDAO {
        long insert(TeamLeague obj);
        long update(TeamLeague obj);
        long deleteByObj(TeamLeague teamLeague);
        List<TeamLeague> findByIdLeague(Integer id, Integer... match);
        List<TeamLeague> findAll();
        Integer amountMatch(Integer id);
        Integer maxMatchCurrent(Integer id);
        void closeDb();
}


