package com.example.calcamp.view;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.example.calcamp.R;
import com.example.calcamp.SelectListener;
import com.example.calcamp.adapters.AdapterLeague;
import com.example.calcamp.controller.LeagueController;
import com.example.calcamp.controller.PunctuationTypeController;
import com.example.calcamp.controller.TeamController;
import com.example.calcamp.db.DataBaseHelper;
import com.example.calcamp.model.dao.DAOFactory;
import com.example.calcamp.model.entities.League;
import com.example.calcamp.model.entities.PunctuationType;
import com.example.calcamp.model.dao.leagueDAO;
import com.example.calcamp.model.entities.Team;
import com.example.calcamp.tools.Alert;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LeagueActivity extends AppCompatActivity implements SelectListener {
    private EditText name, idTeam;
    private Button button, buttonDelete;
    private Toolbar toolbar;
    protected EditText popupEditTextName;
    protected ImageView popupImage;
    protected Button popupBtnDelete, popupBtnUpdate;
    private ActivityResultLauncher<String> pickImageLauncher;
    protected Uri uriSelected = null;
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
        setSupportActionBar(toolbar);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recycleView.setLayoutManager(llm);
        popularList();
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        uriSelected = uri;
                        popupImage.setImageURI(uriSelected);
                    }
                });
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

    private void inicializarComponentes() {
        name = findViewById(R.id.etNameTeam);
        button = findViewById(R.id.btnNew);
        recycleView = findViewById(R.id.recycleView);
        toolbar = findViewById(R.id.toolbar);
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
        uriSelected = null;

        League league = (League) obj;
        LeagueController leagueController = new LeagueController(this);
        leagueList.clear();
        leagueList.add(league);

        AlertDialog.Builder data = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup_edit_league,null);

        EditText ename;
        ImageView imageView;
        Spinner spinner;
        Button btnDelete, btnUpdate, btnTeams, btnActivityTeamLeagueScore, btnClassification;

        ename =  view.findViewById(R.id.etNamePopupLeague);
        imageView =  view.findViewById(R.id.imagePopupLeague);
        spinner =  view.findViewById(R.id.spinnerPopupLeague);

        fillSpinner(spinner, league.getPunctuationType().getName());

        ename.setText(league.getName());

        byte[] image = league.getImage();
        Bitmap finalBitmapDefault = BitmapFactory.decodeResource(getResources(),R.drawable.avatar);
        Bitmap bitmapSelected = null;
        if(image != null){
            //conversion of blob in object bitmap
            bitmapSelected = BitmapFactory.decodeByteArray(image,0, image.length);
            imageView.setImageBitmap(bitmapSelected);
        }else{
            imageView.setImageBitmap(finalBitmapDefault);
        }

        //Open gallery
        popupImage.setOnClickListener(viewImage -> pickImageLauncher.launch("Image/*"));

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
                byte[] imageByte = null;
                if(uriSelected != null){
                    imageByte = transformInArrayOfBytes(popupImage);
                }
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
                //Inflar?? o Layout personalizado
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                //View do Layout inflado
                View popupView = inflater.inflate(R.layout.popup_new_league, null);
                initializeComponentsPopup(popupView);
                fillSpinner(spinner, null);

                if(uriSelected != null){
                    popupImage.setImageURI(uriSelected);
                }else{
                    popupImage.setImageResource(R.drawable.avatar);
                }
                popupBtnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        byte[] imageByte = null;
                        if(uriSelected != null){
                            imageByte = transformInArrayOfBytes(popupImage);
                        }
                        League league = new League(null, popupEditTextName.getText().toString(), punctuationType, imageByte);
                        LeagueController leagueController = new LeagueController(getApplicationContext());
                        if(leagueController.saveController(league) != -1){
                            Alert.alert("New League Inserted Successfully", getApplicationContext());
                        }else{
                            Alert.alert("Error inserting League", getApplicationContext());
                        }
                    }
                });

                popupImage.setOnClickListener(view -> pickImageLauncher.launch("popupImage/*"));
                openPopup(popupView);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private byte[] transformInArrayOfBytes(ImageView popupImage) {
        //Converted in bitmap
        Bitmap bitmap = ((BitmapDrawable) popupImage.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    protected void initializeComponentsPopup(View view){
        popupImage = view.findViewById(R.id.imagePopupTeam);
        popupEditTextName = view.findViewById(R.id.etNamePopupTeam);
        popupBtnUpdate = view.findViewById(R.id.btnUpdateTeam);
        popupBtnDelete = view.findViewById(R.id.btnDeleteTeam);
        spinner = view.findViewById(R.id.spinnerPopupLeague);
    }
    protected void openPopup(View popupView) {
        PopupWindow popupWindow;
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }
}