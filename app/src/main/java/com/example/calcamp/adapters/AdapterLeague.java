package com.example.calcamp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calcamp.R;
import com.example.calcamp.SelectListener;
import com.example.calcamp.controller.PunctuationTypeController;
import com.example.calcamp.model.entities.League;
import com.example.calcamp.model.entities.Team;

import java.util.ArrayList;
import java.util.List;

public class AdapterLeague extends RecyclerView.Adapter<AdapterLeague.MyViewHolder> {
    private List<League> list = new ArrayList<League>();
    private SelectListener listener;
    private Context context;
    private Spinner spinner;

    public AdapterLeague(List<League> list, SelectListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterLeague.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        //Layout de cada linha
        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list, parent, false);
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLeague.MyViewHolder holder, int position) {
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
        ImageView image;
        TextView id;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.tvName);
            this.image = itemView.findViewById(R.id.image);
            this.id = itemView.findViewById(R.id.tvId);
            this.cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
