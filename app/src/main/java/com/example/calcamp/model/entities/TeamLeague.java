package com.example.calcamp.model.entities;

public class TeamLeague {

    private Team team;
    private League league;
    private Integer position;
    private Integer punctuation;

    public TeamLeague() {

    }

    public TeamLeague(Team team, League league, Integer position, Integer punctuation) {
        super();
        this.team = team;
        this.league = league;
        this.position = position;
        this.punctuation = punctuation;
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

    @Override
    public String toString() {
        return "TeamLeague [team=" + team + ", league=" + league + ", position=" + position + ", punctuation="
                + punctuation + "]";
    }


}
