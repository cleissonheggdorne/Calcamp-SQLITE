package com.example.calcamp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calcamp.R;
import com.example.calcamp.SelectListener;
import com.example.calcamp.controller.PunctuationPositionController;
import com.example.calcamp.model.entities.PunctuationPosition;
import com.example.calcamp.model.entities.Team;
import com.example.calcamp.model.entities.TeamLeague;

import java.util.ArrayList;
import java.util.List;

public class AdapterTeamLeague extends RecyclerView.Adapter<AdapterTeamLeague.MyViewHolder> {
    private Context context;
    private Integer idLeague;
    private List<TeamLeague> list = new ArrayList<TeamLeague>();
    private SelectListener listener;

    public AdapterTeamLeague(List<TeamLeague> list, SelectListener listener) {
        this.list = list;
        this.listener = listener;
    }
    //Constructor if List of Teams linked to the League
    public AdapterTeamLeague(List<TeamLeague> list, SelectListener listener, Integer idLeague) {
        this.list = list;
        this.listener = listener;
        this.idLeague = idLeague;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        //Layout de cada linha
        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list_team_league, parent, false);
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getTeam().getName());
        holder.positionFinal.setText(Integer.toString(list.get(position).getPosition()));
        holder.image.setImageResource(R.drawable.avatar);

        List<String> listPosition = new ArrayList<String>();
        listPosition = getListPosition(list.get(position).getLeague().getPunctuationType().getId());
        ArrayAdapter arrayAdapter = getAdapter(listPosition);
        holder.position.setAdapter(arrayAdapter);
//        holder.position.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String select = listPpString.get(position);
//                // punctuation = ptc.findByName(select);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        //Retorna tamanho de lista
        return list.size();
    }

    protected List<String> getListPosition(Integer idPunctuationType){
        PunctuationPositionController ppc = new PunctuationPositionController(context);
        List <PunctuationPosition> listPunctuationPosition = ppc.findScore(idPunctuationType, null);
        List <String> listPpString = new ArrayList<String>();
        for(PunctuationPosition pp : listPunctuationPosition){
            listPpString.add(Integer.toString(pp.getPosition()));
        }
        return listPpString;
    }

    protected ArrayAdapter getAdapter(List<String> listPosition){
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, listPosition);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView positionFinal;
        ImageView image;
        TextView name;
        Spinner position;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.adapterTvNameTeam);
            this.image = itemView.findViewById(R.id.adapterImageTeam);
            this.positionFinal = itemView.findViewById(R.id.adapterTvPositionFinal);
            this.position = itemView.findViewById(R.id.adapterTeamLeagueSpinner);
            this.cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
