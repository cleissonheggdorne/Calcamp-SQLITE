package com.example.calcamp.view;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.calcamp.adapters.AdapterTeam;
import com.example.calcamp.R;
import com.example.calcamp.SelectListener;
import com.example.calcamp.controller.TeamController;
import com.example.calcamp.model.dao.TeamDAO;
import com.example.calcamp.model.dao.DAOFactory;
import com.example.calcamp.model.entities.Team;
import com.example.calcamp.tools.Alert;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends AppCompatActivity implements SelectListener {

    private Toolbar toolbar;
    private EditText name;
    
    protected EditText popupEditTextName;
    protected ImageView popupImage;
    protected Button popupBtnDelete, popupBtnUpdate;

    private List<Team> list = new ArrayList<Team>();
    //private ArrayAdapter<Team> aaTeam;
    private RecyclerView recycleView;
    private ActivityResultLauncher<String> pickImageLauncher;

    ArrayList<Team> teamList = new ArrayList<Team>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipes);
        initializeComponents();
        setSupportActionBar(toolbar);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recycleView.setLayoutManager(llm);
        populateList();

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        popupImage.setImageURI(uri);
                    }
                });
    }

    private void initializeComponents() {
        name = findViewById(R.id.etNameTeam);
        recycleView = findViewById(R.id.recycleView);
        toolbar = findViewById(R.id.toolbar);
    }

    private void populateList() {
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
        initializeComponentsPopup(view);

        popupEditTextName.setText(team.getName());

        byte[] image = team.getImage();
        Bitmap finalBitmapDefault = BitmapFactory.decodeResource(getResources(),R.drawable.avatar);
        Bitmap bitmapSelected = null;
        if(image != null){
            //conversion of blob in object bitmap
            bitmapSelected = BitmapFactory.decodeByteArray(image,0, image.length);
            popupImage.setImageBitmap(bitmapSelected);
        }else{
            popupImage.setImageBitmap(finalBitmapDefault);
        }
        //Open gallery
        popupImage.setOnClickListener(viewImage -> pickImageLauncher.launch("Image/*"));
        popupBtnDelete.setOnClickListener(new View.OnClickListener() {
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

        popupBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalBitmapDefault != null){
                    byte[] imageByte = transformInArrayOfBytes(popupImage);
                    team.setImage(imageByte);
                }else{
                    team.setImage(null);
                }
                team.setName(popupEditTextName.getText().toString());
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
                initializeComponentsPopup(popupView);
                popupImage.setImageResource(R.drawable.avatar);
                popupBtnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        byte[] imageByte = transformInArrayOfBytes(popupImage);
                        Team team = new Team(null, popupEditTextName.getText().toString(), imageByte);
                        TeamController teamController = new TeamController(getApplicationContext());
                        if(teamController.saveController(team) != -1){
                            Alert.alert("New Team Inserted Successfully", getApplicationContext());
                        }else{
                            Alert.alert("Error inserting Team", getApplicationContext());
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
    }
    protected void openPopup(View popupView) {
        PopupWindow popupWindow;
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }
}