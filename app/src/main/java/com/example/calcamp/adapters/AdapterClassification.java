package com.example.calcamp.adapters;

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
import com.example.calcamp.model.entities.view.Classification;

import java.util.ArrayList;
import java.util.List;

public class AdapterClassification extends RecyclerView.Adapter<AdapterClassification.MyViewHolder> {

    private List<Classification> list = new ArrayList<>();
    private SelectListener listener;

    public AdapterClassification(List<Classification> list, SelectListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Layout de cada linha
        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_classification, parent, false);
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getTeam().getName());
        holder.image.setImageResource(R.drawable.avatar);
        holder.position.setText(list.get(position).getPositionFinal().toString());
        holder.punctuationFinal.setText(list.get(position).getPunctuationFinal().toString());

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
        TextView position;
        CardView cardView;

        ImageView image;

        TextView punctuationFinal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.tvName);
            this.image = itemView.findViewById(R.id.image);
            this.position = itemView.findViewById(R.id.tvPositionFinal);
            this.cardView = itemView.findViewById(R.id.cardView);
            this.punctuationFinal = itemView.findViewById(R.id.tvPunctuationFinal);
        }
    }
}
