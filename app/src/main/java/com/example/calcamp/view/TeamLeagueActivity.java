package com.example.calcamp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.calcamp.R;
import com.example.calcamp.SelectListener;
import com.example.calcamp.adapters.AdapterLeague;
import com.example.calcamp.adapters.AdapterTeam;
import com.example.calcamp.adapters.AdapterTeamLeague;
import com.example.calcamp.controller.PunctuationPositionController;
import com.example.calcamp.controller.PunctuationTypeController;
import com.example.calcamp.controller.TeamController;
import com.example.calcamp.controller.TeamLeagueController;
import com.example.calcamp.model.dao.DAOFactory;
import com.example.calcamp.model.dao.leagueDAO;
import com.example.calcamp.model.entities.League;
import com.example.calcamp.model.entities.PunctuationPosition;
import com.example.calcamp.model.entities.Team;
import com.example.calcamp.model.entities.TeamLeague;
import com.example.calcamp.tools.Alert;

import java.util.ArrayList;
import java.util.List;

public class TeamLeagueActivity extends AppCompatActivity implements SelectListener {
    RecyclerView recycleViewTeamsLeague;
    RecyclerView recycleViewTeams;
    private League league;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_league);

        // seach paramters received
        Intent intent = getIntent();
        league = intent.getParcelableExtra("league");

        initializeComponents();

        recycleViewTeams.setLayoutManager(new LinearLayoutManager(this));
        recycleViewTeamsLeague.setLayoutManager(new LinearLayoutManager(this));

        populateTeams();
        populateTeamsLeague();
    }

    private void initializeComponents() {
        recycleViewTeamsLeague = (RecyclerView) findViewById(R.id.recycleViewTeamLeague);
        recycleViewTeams = (RecyclerView) findViewById(R.id.recycleViewTeams);
    }
    protected void populateTeams(){
        TeamController teamController = new TeamController(this);
        List<Team> list = teamController.findNoLeagueController(league.getId());
        AdapterTeam adapter = new AdapterTeam(list, this);
        recycleViewTeams.setAdapter(adapter);
    }

    protected void populateTeamsLeague(){
        TeamLeagueController teamLeagueController = new TeamLeagueController(this);
        List<TeamLeague> list = teamLeagueController.findByIdLeague(league.getId());
        AdapterTeamLeague adapter = new AdapterTeamLeague(list, this);
        recycleViewTeamsLeague.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(Object obj) {
        Team team = (Team) obj;
        TeamLeagueController teamLeagueController = new TeamLeagueController(this);
        TeamLeague teamLeague = new TeamLeague(team, league, 0, 0, null);
        if(teamLeagueController.insertController(teamLeague) != -1){
            Alert.alert("New Team Inserted Successfully", this);
        }else{
            Alert.alert("Error inserting Team", this);
        }
        this.recreate();
    }
}