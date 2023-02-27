package com.example.calcamp.controller;

import android.content.Context;

import com.example.calcamp.model.dao.DAOFactory;
import com.example.calcamp.model.dao.TeamLeagueDAO;
import com.example.calcamp.model.entities.League;
import com.example.calcamp.model.entities.PunctuationType;
import com.example.calcamp.model.entities.TeamLeague;

import java.util.List;

public class TeamLeagueController {
    TeamLeagueDAO teamLeagueDao;
    Context context;

    public TeamLeagueController(Context context){
        this.context = context;
    }

    public long insertController(TeamLeague teamLeague){
        teamLeagueDao = DAOFactory.createTeamLeagueDao(context);
        Integer maxMatchCurrent = teamLeagueDao.maxMatchCurrent(teamLeague.getLeague().getId());
        long ret = 0;
        if(maxMatchCurrent != 0) {
            for (int i = 1; i <= maxMatchCurrent; i++) {
                teamLeague.setMatch(i);
                ret = teamLeagueDao.insert(teamLeague);
            }
        }else{
            ret = teamLeagueDao.insert(teamLeague);
        }
        teamLeagueDao.closeDb();
        return ret;
    }

    public long saveController(TeamLeague teamLeague){
        teamLeagueDao = DAOFactory.createTeamLeagueDao(context);
        long id = teamLeagueDao.insert(teamLeague);
        return id;
    }
    public long deleteController(Integer id){
        teamLeagueDao = DAOFactory.createTeamLeagueDao(context);
        long ret = teamLeagueDao.deleteById(id);
        return ret;
    }

    public long updateController(TeamLeague teamLeague){
        teamLeagueDao = DAOFactory.createTeamLeagueDao(context);
        long ret = teamLeagueDao.update(teamLeague);
        return ret;
    }
    public List<TeamLeague> findByIdLeague(Integer id, Integer... match){
        teamLeagueDao = DAOFactory.createTeamLeagueDao(context);
        if (match.length > 0){
            return teamLeagueDao.findByIdLeague(id, match);
        }else{
            return teamLeagueDao.findByIdLeague(id);

        }
    }

    public void insertListController(List<TeamLeague> teamLeagueList) {
        teamLeagueDao = DAOFactory.createTeamLeagueDao(context);
        for(TeamLeague teamLeague : teamLeagueList){
            if(teamLeagueDao.insert(teamLeague) != -1){
                continue;
            }
        }
    }
    public Integer amountMatchController(Integer id){
        teamLeagueDao = DAOFactory.createTeamLeagueDao(context);
        return teamLeagueDao.amountMatch(id);
    }

    public void updatePositionController(List<TeamLeague> teamLeagueList) {
        teamLeagueDao = DAOFactory.createTeamLeagueDao(context);
        for(TeamLeague teamLeague : teamLeagueList){
            if(teamLeagueDao.update(teamLeague) != -1){
                continue;
            }
        }
    }

    public void addMatchInLeagueController(List<TeamLeague> teamLeagueList) {
        teamLeagueDao = DAOFactory.createTeamLeagueDao(context);
        Integer maxMatchCurrent = teamLeagueDao.maxMatchCurrent(teamLeagueList.get(0).getLeague().getId());
        for(TeamLeague tl : teamLeagueList){
            tl.setPosition(0);
            tl.setPunctuation(0);
            tl.setMatch(maxMatchCurrent+1);
            teamLeagueDao.insert(tl);
        }
        teamLeagueDao.closeDb();
    }
}
