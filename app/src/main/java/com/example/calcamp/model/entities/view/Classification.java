package com.example.calcamp.model.entities.view;

import com.example.calcamp.model.entities.League;
import com.example.calcamp.model.entities.Team;

public class Classification {
    private Team team;
    private League league;
    private Integer punctuationFinal;
    private Integer positionFinal;

    public Classification(Team team, League league, Integer punctuationFinal, Integer positionFinal) {
        this.team = team;
        this.league = league;
        this.punctuationFinal = punctuationFinal;
        this.positionFinal = positionFinal;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Integer getPunctuationFinal() {
        return punctuationFinal;
    }

    public void setPunctuationFinal(Integer punctuationFinal) {
        this.punctuationFinal = punctuationFinal;
    }

    public Integer getPositionFinal() {
        return positionFinal;
    }

    public void setPositionFinal(Integer positionFinal) {
        this.positionFinal = positionFinal;
    }
}
