package com.example.pracainzynierska;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import com.example.pracainzynierska.db.User;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter extends RecyclerView.Adapter<DBAdapter.MyViewHolder> {

    private Context context;
    private List<User> userList;
    //MVVM
    private HandleDataClick clickListener;

    public DBAdapter(Context context, HandleDataClick clickListener) {
        this.context = context;
        this.clickListener=clickListener;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DBAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerow, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DBAdapter.MyViewHolder holder,  int position) {

        holder.activityname.setText(this.userList.get(position).Activity);
        holder.date.setText(this.userList.get(position).Data);
        holder.vieww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), Potreningu.class);
                String data= userList.get(holder.getAdapterPosition()).Data;
                String activityname= userList.get(holder.getAdapterPosition()).Activity;
                String positionn= userList.get(holder.getAdapterPosition()).Position;
                String Speed=userList.get(holder.getAdapterPosition()).Speed;
                String Time=userList.get(holder.getAdapterPosition()).Time;
                String Kcal=userList.get(holder.getAdapterPosition()).Kcal;
                String Chronometer= userList.get(holder.getAdapterPosition()).ChronoTime;
                String Dystans=userList.get(holder.getAdapterPosition()).Distance;
                intent.putExtra("kcal", Kcal);
                intent.putExtra("time", Time);
                intent.putExtra("speed", Speed);
                intent.putExtra("position", positionn);
                intent.putExtra("kcal", Kcal);
                intent.putExtra("chrono", Chronometer);
                intent.putExtra("road",Dystans);
                intent.putExtra("aktywnosc", "db");
                view.getContext().startActivity(intent);




            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            clickListener.removeData(userList.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return  this.userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView activityname;
        TextView date;
        Button vieww;
        Button delete;

        public MyViewHolder(View view) {
            super(view);
            activityname = view.findViewById(R.id.activityname);
            date = view.findViewById(R.id.date);
            vieww= view.findViewById(R.id.view);
            delete=view.findViewById(R.id.delete);
        }
    }


    public interface HandleDataClick {
        void removeData( User user);

    }
}