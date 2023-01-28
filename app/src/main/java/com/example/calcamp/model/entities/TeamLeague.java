package com.example.calcamp.model.entities;

import java.util.Objects;

public class TeamLeague {

    private Team team;
    private League league;
    private Integer position;
    private Integer punctuation;
    private Integer match;

    public TeamLeague() {

    }

    public TeamLeague(Team team, League league, Integer position, Integer punctuation, Integer match) {
        super();
        this.team = team;
        this.league = league;
        this.position = position;
        this.punctuation = punctuation;
        this.match = match;
    }

    public TeamLeague(Team team, League league) {
        super();
        this.team = team;
        this.league = league;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Integer getPunctuation() {
        return punctuation;
    }

    public void setPunctuation(int punctuation) {
        this.punctuation = punctuation;
    }

    public Integer getMatch() {
        return match;
    }

    public void setMatch(Integer match) {
        this.match = match;
    }

    @Override
    public String toString() {
        return "TeamLeague [team=" + team + ", league=" + league + ", position=" + position + ", punctuation="
                + punctuation + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamLeague that = (TeamLeague) o;
        return Objects.equals(team, that.team) && Objects.equals(league, that.league);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, league);
    }

}
