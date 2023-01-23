package com.example.calcamp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calcamp.R;
import com.example.calcamp.SelectListener;
import com.example.calcamp.model.entities.Team;
import com.example.calcamp.model.entities.TeamLeague;

import java.util.ArrayList;
import java.util.List;

public class AdapterTeamLeague extends RecyclerView.Adapter<AdapterTeamLeague.MyViewHolder> {

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

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView positionFinal;
        ImageView image;
        TextView name;
        EditText position;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.adapterTvNameTeam);
            this.image = itemView.findViewById(R.id.adapterImageTeam);
            this.positionFinal = itemView.findViewById(R.id.adapterTvPositionFinal);
            this.position = itemView.findViewById(R.id.adapterEtPosicao);
            this.cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
