package com.example.pracainzynierska;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView dystans, czas, srednia_predkosc, kalorie, cisnienie, pdystans, pczas, pkalorie, psredniapredkosc;
    private Chronometer chronometer;
    private Button zakoncz_trening;
    private SupportMapFragment mapFragment;
    private GoogleApi googleApi;
    private Location lastKnownLocation;
    private LatLng lastKnownLatLng;
    private LatLng currentLatLang;
    private double altitude=0;
    private Polyline polyline = null;
    private PolylineOptions routeOpts = null;
    private double secondinarray=0;
    private boolean stop = false;
    private String activityname;
    private ArrayList<String> time=new ArrayList<>();
    private ArrayList<LatLng> position=new ArrayList<>();
    private ArrayList<Double> speed=new ArrayList<>();
    private ArrayList<String> altitudee=new ArrayList<>();
    Handler mHandler = new Handler();
    private boolean locationt=true;
    private boolean wasRun = true;
    private double Kcal=0;
    private double lastval=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        pkalorie = (TextView) findViewById(R.id.textView9);
        chronometer = (Chronometer) findViewById(R.id.chronw);
        zakoncz_trening = (Button) findViewById(R.id.button5);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        Intent i= getIntent();
        Kcal=i.getDoubleExtra("value", 0);
        activityname=(String) getIntent().getSerializableExtra("aktywnosc");

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(wasRun){
                    secondinarray++;
                    lastval=lastval+Kcal;
                    if (currentLatLang !=null ){position.add((currentLatLang));}
                    time.add(String.valueOf(secondinarray));
                    if (psredniapredkosc !=null){speed.add(Double.parseDouble(psredniapredkosc.getText().toString()));}
                    altitudee.add(String.valueOf(altitude));
                    pkalorie.setText(String.format("%.2f", lastval));

                    for (int i = 0; i < speed.size();i++)
                    {

                        Log.i("speed", String.valueOf(speed.get(i)));

                    }

                }
                if (!stop) {
                mHandler.postDelayed(this, 1000);}
            }
        }, 1000); // 1 seconds

    }



    public void zakoncz_trening(View view) {
        Intent intent = new Intent(this, Potreningu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("kcal", pkalorie.getText());
        intent.putExtra("time", time);
        intent.putExtra("position", position);
        intent.putExtra("speed", speed);
        intent.putExtra("altitude", altitudee);
        intent.putExtra("chrono", chronometer.getText());
        intent.putExtra("road", pdystans.getText());
        intent.putExtra("aktywnosc", activityname);
        startActivity(intent);
        stop=true;
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                    lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (lastKnownLocation!=null){
                    updateMap(lastKnownLocation);}
                    else {
                        lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }

                }

            }


        }

    }

    public void addUpdatePolyLine()
    {
        {
            PolylineOptions polylineOptions = new PolylineOptions().add(lastKnownLatLng).add(currentLatLang).width(10).color(Color.GREEN);
            polyline = mMap.addPolyline(polylineOptions);
            lastKnownLatLng=currentLatLang;
        }
    }

    public void updateMap(Location location) {
try {
    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
    //mMap.clear();
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
    //mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
}
catch (Exception e){
    Log.i("Exception", String.valueOf(e));
};

    }


    private void updateDistance(CLocation location ){
        float nCurrentDistance=0;
        if(lastKnownLocation != null)
        {
            location.setUseMetricunits(true);
            nCurrentDistance = lastKnownLocation.distanceTo(location);

        }

        Formatter fmt = new Formatter(new StringBuilder());
        fmt.format(Locale.US, "%5.1f", nCurrentDistance);
        String strCurrentDistance = fmt.toString();
        strCurrentDistance = strCurrentDistance.replace(' ', '0');



        pdystans = (TextView) this.findViewById(R.id.dw);
        pdystans.setText(strCurrentDistance  );

    }
    private void updateSpeed(CLocation location) {
        // TODO Auto-generated method stub
        float nCurrentSpeed = 0;

        if(location != null)
        {
            location.setUseMetricunits(true);
            nCurrentSpeed = location.getSpeed();

        }

        Formatter fmt = new Formatter(new StringBuilder());
        fmt.format(Locale.US, "%5.1f", nCurrentSpeed);
        String strCurrentSpeed = fmt.toString();
        strCurrentSpeed = strCurrentSpeed.replace(' ', '0');



        psredniapredkosc = (TextView) this.findViewById(R.id.spw);
        psredniapredkosc.setText(strCurrentSpeed );
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
       ;
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (locationt == true) {
                    lastKnownLocation = location;
                    lastKnownLatLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                    locationt = false;
                    currentLatLang=lastKnownLatLng;
                    location.hasAltitude();
                    altitude=(double) location.getAltitude();

                }
                    currentLatLang = new LatLng(location.getLatitude(), location.getLongitude());
                    location.hasAltitude();
                    altitude=(double) location.getAltitude();

                    CLocation myLocation = new CLocation(location, true);
                    updateSpeed(myLocation);
                    updateDistance(myLocation);
                    addUpdatePolyLine();
                    updateMap(location);


            }



            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (Build.VERSION.SDK_INT < 23) {

            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        } else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (lastKnownLocation != null) {
                    updateMap(lastKnownLocation);

                } else lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


            }


        }

    }



}