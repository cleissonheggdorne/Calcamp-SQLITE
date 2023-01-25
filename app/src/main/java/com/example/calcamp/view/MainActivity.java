package com.example.calcamp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.calcamp.R;
import com.example.calcamp.view.LeagueActivity;

public class MainActivity extends AppCompatActivity {

    private Object View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_teams = (Button) findViewById(R.id.btn_teams);
        Button btn_leagues = (Button) findViewById(R.id.btn_leagues);


        btn_teams.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EquipesActivity = new Intent(getApplicationContext(), com.example.calcamp.view.EquipesActivity.class);
                startActivity(EquipesActivity);
            }
        });

        btn_leagues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent LeagueActivity = new Intent(getApplicationContext(), com.example.calcamp.view.LeagueActivity.class);
                startActivity(LeagueActivity);
            }
        });
    }
}