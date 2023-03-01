package com.example.calcamp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.Menu;
import android.widget.PopupWindow;

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
    private Toolbar toolbar;
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
        setSupportActionBar(toolbar);
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
        toolbar = findViewById(R.id.toolbar);
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

    @Override
    public void onItemChanged(Object obj) {

    }

    protected void reloadActivity(){
        this.recreate();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_equipes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.bar_btnAdd:
                //Inflará o Layout personalizado
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                //View do Layout inflado
                View popupView = inflater.inflate(R.layout.popup_edit, null);
                ImageView image = popupView.findViewById(R.id.imagePopupTeam);
                EditText editTextName = popupView.findViewById(R.id.etNamePopupTeam);
                Button btnSave = popupView.findViewById(R.id.btnUpdateTeam);
                Button btnDelete = popupView.findViewById(R.id.btnDeleteTeam);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Team team = new Team(null, editTextName.getText().toString());
                        TeamController teamController = new TeamController(getApplicationContext());
                        if(teamController.saveController(team) != -1){
                            Alert.alert("New Team Inserted Successfully", getApplicationContext());
                        }else{
                            Alert.alert("Error inserting Team", getApplicationContext());
                        }
                    }
                });
                openPopup(popupView);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    protected void openPopup(View popupView) {
        PopupWindow popupWindow;
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }
}