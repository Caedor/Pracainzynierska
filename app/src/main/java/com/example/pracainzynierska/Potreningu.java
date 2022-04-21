package com.example.pracainzynierska;



import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pracainzynierska.db.AppDatabase;
import com.example.pracainzynierska.db.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Potreningu extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

     GoogleMap mMap;
     Button wykres_terenu, wykres_predkosci, zakoncz_trening;
     Intent intent;
     Toast toast;
     TextView dystans, czas, srednia_predkosc, kalorie;
Conversionlogic conversionlogic= new Conversionlogic();
    private ArrayList<String> time=new ArrayList<>();
    private ArrayList<LatLng> position=new ArrayList<>();
    private ArrayList<Double> speed=new ArrayList<>();
    private ArrayList<String> altitudee=new ArrayList<>();
    private String chronometer;
    private String distance;
    private String kcal;
    private String Date;
    private String Position;
    private String Time;
    private String Speed;
    private String Activityname;
    private Polyline polyline = null;
    private LatLng lastKnownLatLng;
    private LatLng currentLatLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_activity);
        Activityname=(String) getIntent().getSerializableExtra("aktywnosc");

        if (Activityname.equals("db")){

            kcal=(String) getIntent().getSerializableExtra("kcal");
            chronometer=(String) getIntent().getSerializableExtra("chrono");
            distance=(String) getIntent().getSerializableExtra("road");
            speed=conversionlogic.conversetoDoubleArray((String) getIntent().getSerializableExtra("speed"));
            position=conversionlogic.conversetoLAtLangArray((String) getIntent().getSerializableExtra("position"));
            time=conversionlogic.conversetoStringArray((String) getIntent().getSerializableExtra("time"));


        }
else {
            kcal = (String) getIntent().getSerializableExtra("kcal");
            chronometer = (String) getIntent().getSerializableExtra("chrono");
            distance = (String) getIntent().getSerializableExtra("road");
            speed = (ArrayList<Double>) getIntent().getSerializableExtra("speed");
            position = (ArrayList<LatLng>) getIntent().getSerializableExtra("position");
            time = (ArrayList<String>) getIntent().getSerializableExtra("time");
        }
        Date=conversionlogic.getData();
        Position=conversionlogic.LatlangtoString(position);
        Time=conversionlogic.StringtoString(time);
        Speed=conversionlogic.doubletoString(speed);
        double sum = 0;
        for(double d : speed) {
            sum += d;
        }
        double avg =(sum / speed.size());

        dystans=(TextView) findViewById(R.id.dw);
        czas=(TextView) findViewById(R.id.chronw);
        srednia_predkosc=(TextView) findViewById(R.id.spw);
        kalorie=(TextView) findViewById(R.id.kcalw);

        czas.setText(chronometer);
        dystans.setText(distance);
        kalorie.setText(kcal);
        srednia_predkosc.setText(String.format("%.2f",avg ));
        wykres_terenu=(Button) findViewById(R.id.button6);
        wykres_predkosci=(Button) findViewById(R.id.button7);
        zakoncz_trening=(Button) findViewById(R.id.button5);

        wykres_terenu.setOnClickListener(this);
        wykres_predkosci.setOnClickListener(this);
        zakoncz_trening.setOnClickListener(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        lastKnownLatLng=position.get(0);
        for (int i = 0; i < position.size(); i++) {
            currentLatLang=position.get(i);
            PolylineOptions polylineOptions = new PolylineOptions().add(lastKnownLatLng).add(currentLatLang).width(10).color(Color.GREEN);
            polyline = mMap.addPolyline(polylineOptions);
            Log.i("position", String.valueOf(lastKnownLatLng));
            lastKnownLatLng=currentLatLang;

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position.get(0), 14));

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button6: // wykres terenu
                intent=new Intent(this, Wykres_terenu.class);
                intent.putExtra("time", time);
                intent.putExtra("speed", speed);
                intent.putExtra("chartname", "Wykres terenu");
                intent.putExtra("Yaxis", "Speed [km/h]");
                startActivity(intent);
                break;
            case R.id.button7: // wykres prędkości
                intent=new Intent(this, Wykres_terenu.class);
                startActivity(intent);
                break;
            case R.id.button5: // zakończenie treningu
                if (Activityname.equals("db")){
                    intent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                alert();}

                break;
        }
    }

    public void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_info)
        builder.setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Zapisz historię treningu.")
                .setMessage("Wybierz tak lub nie by zapisać historię treningu")
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveNewUser(Activityname, Date, Position, Time, Speed,kcal,chronometer, distance );}

                })
                .setNegativeButton("Nie zapisuj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show()
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        intent=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });



    }
    private void saveNewUser(String Activity, String Data, String Position, String Time, String Speed, String Kcal, String ChronoTime, String Distance) {
        AppDatabase db  = AppDatabase.getDbInstance(this.getApplicationContext());

        User user = new User();
        user.Activity = Activity;
        user.Data = Data;
        user.Position = Position;
        user.Time = Time;
        user.Speed = Speed;
        user.Kcal=Kcal;
        user.Distance=Distance;
        user.ChronoTime=ChronoTime;
        db.userDao().insertData(user);

        finish();

    }


    }
