package com.example.calcamp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.calcamp.R;
import com.example.calcamp.SelectListener;
import com.example.calcamp.adapters.AdapterClassification;
import com.example.calcamp.adapters.AdapterTeam;
import com.example.calcamp.controller.ClassificationController;
import com.example.calcamp.model.dao.DAOFactory;
import com.example.calcamp.model.dao.TeamDAO;
import com.example.calcamp.model.entities.League;
import com.example.calcamp.model.entities.Team;
import com.example.calcamp.model.entities.view.Classification;
import com.example.calcamp.tools.Alert;

import java.util.ArrayList;
import java.util.List;

public class ClassificationActivity extends AppCompatActivity implements SelectListener {
    RecyclerView recyclerView;

    League league;
    List<Classification> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        inicializarComponentes();
        Intent intent = getIntent();
        league = intent.getParcelableExtra("league");
        Alert.alert(league.getName(), getApplicationContext());

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        popularList();
    }
    private void inicializarComponentes() {
        recyclerView = findViewById(R.id.recycleViewClassification);
    }

    private void popularList() {
        list.clear();
        ClassificationController classificationController = new ClassificationController(getApplicationContext());
        list = classificationController.findByIdLeague(league.getId());
        AdapterClassification adapter = new AdapterClassification(list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(Object obj) {

    }
}