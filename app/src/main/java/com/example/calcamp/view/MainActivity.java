package com.example.calcamp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.calcamp.R;

public class MainActivity extends AppCompatActivity {

    private Object View;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CardView cardTeam = (CardView) findViewById(R.id.cardTeam);
        CardView cardLeague = (CardView) findViewById(R.id.cardLeague);

        cardTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent EquipesActivity = new Intent(getApplicationContext(), com.example.calcamp.view.EquipesActivity.class);
                startActivity(EquipesActivity);
            }
        });

        cardLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent LeagueActivity = new Intent(getApplicationContext(), com.example.calcamp.view.LeagueActivity.class);
                startActivity(LeagueActivity);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_equipes, menu);
        return true;
    }

}