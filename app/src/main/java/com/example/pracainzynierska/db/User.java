package com.example.pracainzynierska.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name="Activity")
    public String Activity;

    @ColumnInfo(name="Data")
    public String Data;

    @ColumnInfo(name="Position")
    public String Position;

    @ColumnInfo(name="Time")
    public String Time;

    @ColumnInfo(name="ChronoTime")
    public String ChronoTime;

    @ColumnInfo(name="speed")
    public String Speed;

    @ColumnInfo(name="kcal")
    public String Kcal;

    @ColumnInfo(name="Distance")
    public String Distance;




}
