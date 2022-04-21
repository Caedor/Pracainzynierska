package com.example.pracainzynierska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Chooseactivity extends AppCompatActivity {

    private ImageButton bike, walk, run, roll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseactivity);
        walk=(ImageButton) findViewById(R.id.imageButton3);
        roll=(ImageButton) findViewById(R.id.imageButton4);
        bike=(ImageButton) findViewById(R.id.imageButton5);
        run=(ImageButton) findViewById(R.id.imageButton6);
    }
    public void start_trening(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
       switch (view.getId())
       {
           case R.id.imageButton3: // walk
               intent.putExtra("value", 0.083);
                intent.putExtra("aktywnosc", "Spacer");
               break;
           case R.id.imageButton4: // roll
               intent.putExtra("value", 0.166);
               intent.putExtra("aktywnosc", "Rolki");
               break;
           case R.id.imageButton5: // bike
               intent.putExtra("value", 0.134);
               intent.putExtra("aktywnosc", "Rower");
               break;
           case R.id.imageButton6: // run
               intent.putExtra("value", 0.194);
               intent.putExtra("aktywnosc", "Bieganie");
               break;
       }



        startActivity(intent);
    }
    public void back_to_menu(View view){
        finish();

    }




}