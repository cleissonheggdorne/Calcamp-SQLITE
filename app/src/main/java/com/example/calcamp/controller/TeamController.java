package com.example.calcamp.controller;

import android.content.Context;

import com.example.calcamp.model.dao.DAOFactory;
import com.example.calcamp.model.dao.TeamDAO;
import com.example.calcamp.model.dao.leagueDAO;
import com.example.calcamp.model.entities.League;
import com.example.calcamp.model.entities.Team;

import java.util.List;

public class TeamController {
    TeamDAO teamDao;
    Context context;
    public TeamController(Context context){
        this.context = context;
    }

    public long saveController(Team team){
        teamDao = DAOFactory.createTeamDao(context);
        long id = teamDao.insert(team);
        return id;
    }
    public long deleteController(Integer id){
        teamDao = DAOFactory.createTeamDao(context);
        long ret = teamDao.deleteById(id);
        return ret;
    }

    public long updateController(Team team){
        teamDao = DAOFactory.createTeamDao(context);
        long ret = teamDao.update(team);
        return ret;
    }
    public List<Team> findAllController(){
        TeamDAO teamDao = DAOFactory.createTeamDao(context);
        return(teamDao.findAll());
    }

    public List<Team> findNoLeagueController(Integer id) {
        TeamDAO teamDao = DAOFactory.createTeamDao(context);
        return(teamDao.findNoLeague(id));
    }
}
