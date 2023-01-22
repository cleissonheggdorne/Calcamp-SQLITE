package com.example.calcamp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.calcamp.adapters.AdapterTeam;
import com.example.calcamp.R;
import com.example.calcamp.SelectListener;
import com.example.calcamp.controller.TeamController;
import com.example.calcamp.model.dao.TeamDAO;
import com.example.calcamp.db.DataBaseHelper;
import com.example.calcamp.model.dao.DAOFactory;
import com.example.calcamp.model.entities.Team;
import com.example.calcamp.tools.Alert;

import java.util.ArrayList;
import java.util.List;

public class EquipesActivity extends AppCompatActivity implements SelectListener {
    private DataBaseHelper database;
    private TeamDAO teamDao;

    private EditText name, idTeam;
    private Button button, buttonDelete;

    private List<Team> list = new ArrayList<Team>();
    private ArrayAdapter<Team> aaTeam;
    private RecyclerView recycleView;

    ArrayList<Team> teamList = new ArrayList<Team>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipes);
        inicializarComponentes();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recycleView.setLayoutManager(llm);
        popularList();
    }

    public void save(View view){
        Team team = new Team(null, name.getText().toString());
        team.setName(name.getText().toString());
        TeamController teamController = new TeamController(this);
        if(teamController.saveController(team) != -1){
            Alert.alert("New Team Inserted Successfully", this);
        }else{
            Alert.alert("Error inserting Team", this);
        }
        this.recreate();

    }

    private void inicializarComponentes() {
        name = findViewById(R.id.etNameTeam);
        button = findViewById(R.id.btnNew);
        recycleView = findViewById(R.id.recycleView);
    }

    private void popularList() {
        list.clear();
        TeamDAO teamDao = DAOFactory.createTeamDao(this);
        List<Team> list = teamDao.findAll();
        AdapterTeam adapter = new AdapterTeam(list, this);
        recycleView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(Object obj) {
        Team team = (Team) obj;
        TeamController teamController = new TeamController(this);
        teamList.clear();
        teamList.add(team);

        AlertDialog.Builder data = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup_edit,null);

        EditText ename;
        ImageView image;
        Button btnDelete, btnUpdate;

        ename = (EditText) view.findViewById(R.id.etNamePopupTeam);
        image = (ImageView) view.findViewById(R.id.imagePopupTeam);

        ename.setText(team.getName());
        image.setImageResource(R.drawable.avatar);

        btnDelete = view.findViewById(R.id.btnDeleteTeam);
        btnUpdate = view.findViewById(R.id.btnUpdateTeam);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teamController.deleteController(team.getId()) == 1 ){
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
                team.setName(ename.getText().toString());
                if(teamController.updateController(team) == 1){
                    Alert.alert("Team Updated", getApplicationContext());
                }else{
                    Alert.alert("Error in Updating", getApplicationContext());
                }
                data.create().dismiss();
                reloadActivity();
            }
        });
        data.setView(view);
        data.create().show();
    }

    protected void reloadActivity(){
        this.recreate();
    }

}