package com.example.calcamp.controller;

import android.content.Context;

import com.example.calcamp.model.dao.DAOFactory;
import com.example.calcamp.model.entities.League;
import com.example.calcamp.model.dao.leagueDAO;

public class LeagueController {
    leagueDAO leagueDao;
    Context context;
    public LeagueController(Context context){
        this.context = context;
    }

    public long saveController(League league){
        leagueDao = DAOFactory.createLeagueDao(context);
        long id = leagueDao.insert(league);
        return id;
    }
    public long deleteController(Integer id){
        leagueDao = DAOFactory.createLeagueDao(context);
        long ret = leagueDao.deleteById(id);
        return ret;
    }

    public long updateController(League league){
        leagueDao = DAOFactory.createLeagueDao(context);
        long ret = leagueDao.update(league);
        return ret;
    }
}
