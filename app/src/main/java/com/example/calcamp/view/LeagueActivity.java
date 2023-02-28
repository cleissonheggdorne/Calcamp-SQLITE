package com.example.calcamp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.calcamp.R;
import com.example.calcamp.SelectListener;
import com.example.calcamp.adapters.AdapterLeague;
import com.example.calcamp.controller.LeagueController;
import com.example.calcamp.controller.PunctuationTypeController;
import com.example.calcamp.db.DataBaseHelper;
import com.example.calcamp.model.dao.DAOFactory;
import com.example.calcamp.model.entities.League;
import com.example.calcamp.model.entities.PunctuationType;
import com.example.calcamp.model.dao.leagueDAO;
import com.example.calcamp.tools.Alert;

import java.util.ArrayList;
import java.util.List;

public class LeagueActivity extends AppCompatActivity implements SelectListener {
    private DataBaseHelper database;
    private leagueDAO leagueDao;
    private EditText name, idTeam;
    private Button button, buttonDelete;
    private Toolbar toolbar;
    private Spinner spinner;

    private List<League> list = new ArrayList<League>();
    private ArrayAdapter<League> aaLeague;
    private RecyclerView recycleView;
    private PunctuationType punctuationType;

    ArrayList<League> leagueList = new ArrayList<League>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league);
        inicializarComponentes();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recycleView.setLayoutManager(llm);
        popularList();
        fillSpinner(spinner, null);
    }

    protected void fillSpinner(Spinner spinner, String namePunctuationType) {
        PunctuationTypeController ptc = new PunctuationTypeController(this);
        List<String> listPtString = ptc.findAllController();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listPtString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //checks 2nd input parameter to condition selected position
        if(namePunctuationType != null) {
            spinner.setSelection(listPtString.indexOf(namePunctuationType));
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select = listPtString.get(position);
                punctuationType = ptc.findByName(select);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void save(View view){
        League league = new League(null, name.getText().toString(), punctuationType);
        league.setName(name.getText().toString());
        LeagueController leagueController = new LeagueController(this);
        if(leagueController.saveController(league) != -1){
            Alert.alert("New League Inserted Successfully", this);
        }else{
            Alert.alert("Error inserting League", this);
        }
        this.recreate();

    }

    private void inicializarComponentes() {
        name = findViewById(R.id.etNameTeam);
        button = findViewById(R.id.btnNew);
        recycleView = findViewById(R.id.recycleView);
        spinner = (Spinner) findViewById(R.id.spinnerTypePunctuation);
    }

    private void popularList() {
        list.clear();
        leagueDAO leagueDao = DAOFactory.createLeagueDao(this);
        List<League> list = leagueDao.findAll();
        AdapterLeague adapter = new AdapterLeague(list, this);
        recycleView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(Object obj) {
        League league = (League) obj;
        LeagueController leagueController = new LeagueController(this);
        leagueList.clear();
        leagueList.add(league);

        AlertDialog.Builder data = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup_edit_league,null);

        EditText ename;
        ImageView image;
        Spinner spinner;
        Button btnDelete, btnUpdate, btnTeams, btnActivityTeamLeagueScore, btnClassification;

        ename = (EditText) view.findViewById(R.id.etNamePopupLeague);
        image = (ImageView) view.findViewById(R.id.imagePopupLeague);
        spinner = (Spinner) view.findViewById(R.id.spinnerPopupLeague);

        fillSpinner(spinner, league.getPunctuationType().getName());

        ename.setText(league.getName());
        image.setImageResource(R.drawable.avatar);

        btnDelete = view.findViewById(R.id.btnDeleteLeague);
        btnUpdate = view.findViewById(R.id.btnUpdateLeague);
        btnTeams = view.findViewById(R.id.btnPopupAddTeams);
        btnActivityTeamLeagueScore = view.findViewById(R.id.btnPopupActivityTeamLeagueScore);
        btnClassification = view.findViewById(R.id.btnPopupActivityTeamLeagueScoreClassification);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leagueController.deleteController(league.getId()) == 1 ){
                    Alert.alert("Deleted Sucessfully", getApplicationContext());
                }else{
                    Alert.alert("Error in Deleting", getApplicationContext());
                }
                data.create().dismiss();
                reloadActivity();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                league.setName(ename.getText().toString());
                league.setPunctuationType(punctuationType);
                if(leagueController.updateController(league) == 1){
                    Alert.alert("League Updated", getApplicationContext());
                }else{
                    Alert.alert("Error in Updating", getApplicationContext());
                }
                data.create().dismiss();
                reloadActivity();
            }
        });

        btnTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teamLeagueActivity = new Intent(getApplicationContext(), com.example.calcamp.view.TeamLeagueActivity.class);
                // Pass parameters between intents
                teamLeagueActivity.putExtra("league", (League) obj);
                startActivity(teamLeagueActivity);
            }
        });

        btnActivityTeamLeagueScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teamLeagueScoreActivity = new Intent(getApplicationContext(), TeamLeagueScoreActivity.class);
                teamLeagueScoreActivity.putExtra("league", (League) obj);
                startActivity(teamLeagueScoreActivity);
            }
        });
        btnClassification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent classification = new Intent(getApplicationContext(), ClassificationActivity.class);
                classification.putExtra("league", (League) obj);
                startActivity(classification);
            }
        });

        data.setView(view);
        data.create().show();
    }

    protected void reloadActivity(){
        this.recreate();
    }
}