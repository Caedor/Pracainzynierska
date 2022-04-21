package com.example.pracainzynierska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

   private Button start;
   private Button historia;
   private Button wyjscie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start=(Button)findViewById(R.id.button);
        historia=(Button)findViewById(R.id.button2);
        wyjscie=(Button)findViewById(R.id.button3);
    }

    public void wybor_aktywnosci(View view) {
        Intent intent = new Intent(this, Chooseactivity.class);
        startActivity(intent);
    }
    public void historia(View view){
        Intent intent = new Intent(this, Historia.class);
        startActivity(intent);

    }

    public void wstecz(View view){
        finish();

    }
    public void bluetooth( View view){
        Intent intent = new Intent(this, BluetoothConnect.class);
        startActivity(intent);

    }

    public void exit(View view){
        System.exit(0);
    }

}