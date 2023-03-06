package com.example.calcamp.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.calcamp.R;
import com.example.calcamp.SelectListener;
import com.example.calcamp.model.entities.Team;

import java.util.ArrayList;
import java.util.List;

public class AdapterTeam extends RecyclerView.Adapter<AdapterTeam.MyViewHolder> {

    private Integer idLeague;
    private List<Team> list = new ArrayList<Team>();
    private SelectListener listener;

    public AdapterTeam(List<Team> list, SelectListener listener) {
        this.list = list;
        this.listener = listener;
    }
    //Constructor if List of Teams linked to the League
    public AdapterTeam(List<Team> list, SelectListener listener, Integer idLeague) {
        this.list = list;
        this.listener = listener;
        this.idLeague = idLeague;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Layout de cada linha
        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carditem, parent, false);
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.image.setImageResource(R.drawable.avatar);
        holder.id.setText(Integer.toString(list.get(position).getId()));

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
        TextView name;
       // ImageView image;
        TextView id;
        CardView cardView;

        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.tvName);
            this.image = itemView.findViewById(R.id.image);
            this.id = itemView.findViewById(R.id.tvId);
            this.cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
