package com.example.calcamp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class TeamLeagueScoreActivity extends AppCompatActivity implements SelectListener {
    RecyclerView recycleViewTeamsLeague;
    RecyclerView recycleViewTeams;

    Button btnTeamLeagueScoreSave;
    private League league;
    protected ChipGroup chipGroup;
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


        btnTeamLeagueScoreSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamLeagueController teamLeagueController = new TeamLeagueController(getApplicationContext());
                teamLeagueController.updatePositionController(teamLeagueList);
            }
        });
        fillChips();
        chipGroup.setOnCheckedChangeListener (new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip = findViewById(checkedId);
                Log.i(chip.getText().toString(), "teste");
                Alert.alert(chip.getText().toString(), getApplicationContext());
            }
        });

    }

    private void initializeComponents() {
        recycleViewTeamsLeague = (RecyclerView) findViewById(R.id.recycleViewTeamLeague);
        recycleViewTeams = (RecyclerView) findViewById(R.id.recycleViewTeams);
        btnTeamLeagueScoreSave = (Button) findViewById(R.id.btnTeamLeagueScoreSave);
        chipGroup = (ChipGroup) findViewById(R.id.chipGroupTeamLeagueScore);
    }

    protected void populateTeamsLeague(){
        TeamLeagueController teamLeagueController = new TeamLeagueController(this);
        List<TeamLeague> list = teamLeagueController.findByIdLeague(league.getId());
        AdapterTeamLeague adapter = new AdapterTeamLeague(list, this);
        recycleViewTeamsLeague.setAdapter(adapter);
    }

    protected void fillChips(){
        Integer amountMath;
        TeamLeagueController teamLeagueController = new TeamLeagueController(getApplicationContext());
        amountMath = teamLeagueController.amountMatchController(league.getId());
        for(int i = 1; i <= amountMath; i++){
            Chip chip = new Chip(this);
            chip.setText("Partida " + i);
            chip.setId(i);
            chip.setCheckable(true);
            chipGroup.addView(chip);
        }
    }

    @Override
    public void onItemClicked(Object obj) {
        populateArrayListTeamLeague((TeamLeague) obj);
    }

    private void populateArrayListTeamLeague(TeamLeague obj) {
        teamLeagueList.add(obj);
    }
}