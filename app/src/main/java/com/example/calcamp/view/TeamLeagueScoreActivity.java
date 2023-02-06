package com.example.calcamp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.calcamp.R;
import com.example.calcamp.SelectListener;
import com.example.calcamp.adapters.AdapterTeam;
import com.example.calcamp.adapters.AdapterTeamLeague;
import com.example.calcamp.controller.TeamController;
import com.example.calcamp.controller.TeamLeagueController;
import com.example.calcamp.model.entities.League;
import com.example.calcamp.model.entities.Team;
import com.example.calcamp.model.entities.TeamLeague;
import com.example.calcamp.tools.Alert;

import java.util.ArrayList;
import java.util.List;

public class TeamLeagueScoreActivity extends AppCompatActivity implements SelectListener {
    RecyclerView recycleViewTeamsLeague;
    RecyclerView recycleViewTeams;

    Button btnTeamLeagueScore;
    private League league;
    protected List<TeamLeague> teamLeagueList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_league_score);
        // seach paramters received
        Intent intent = getIntent();
        league = intent.getParcelableExtra("league");

        initializeComponents();

        recycleViewTeamsLeague.setLayoutManager(new LinearLayoutManager(this));

        populateTeamsLeague();


        btnTeamLeagueScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamLeagueController teamLeagueController = new TeamLeagueController(getApplicationContext());
                teamLeagueController.insertListController(teamLeagueList);
            }
        });
    }

    private void initializeComponents() {
        recycleViewTeamsLeague = (RecyclerView) findViewById(R.id.recycleViewTeamLeague);
        recycleViewTeams = (RecyclerView) findViewById(R.id.recycleViewTeams);
        btnTeamLeagueScore = (Button) findViewById(R.id.btnTeamLeagueScoreSave);
    }

    protected void populateTeamsLeague(){
        TeamLeagueController teamLeagueController = new TeamLeagueController(this);
        List<TeamLeague> list = teamLeagueController.findByIdLeague(league.getId());
        AdapterTeamLeague adapter = new AdapterTeamLeague(list, this);
        recycleViewTeamsLeague.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(Object obj) {
        populateArrayListTeamLeague((TeamLeague) obj);
    }

    private void populateArrayListTeamLeague(TeamLeague obj) {
        teamLeagueList.add(obj);
    }
}