package com.example.pracainzynierska;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class BluetoothConnect extends AppCompatActivity {

    private static final int R_E_BT = 0;
    private static final int R_D_BT = 1;
    private TextView status, parowanie;
    private Button on, off, szukaj, paruj;
    private BluetoothAdapter bluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_connect);


        status= findViewById(R.id.status);
        parowanie= findViewById(R.id.parujt);
        on=findViewById(R.id.onBtn);
        off=findViewById(R.id.offBtn);
        szukaj=findViewById(R.id.discoverableBtn);
        paruj=findViewById(R.id.pairedBtn);


        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            status.setText("Bluetooth nie jest dostępne");
        }
        else {
            status.setText("Bluetooth jest dostępne");
        }

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()){
                    showToast("Uruchamiam Bluetooth...");
                    //intent to on bluetooth
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, R_E_BT);
                }
                else {
                    showToast("Bluetooth jest włączone");
                }
            }
        });
        //discover bluetooth btn click
        szukaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isDiscovering()){
                    showToast("Twoje urządzenie można wykryć ");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, R_D_BT);
                }
            }
        });
        //off btn click
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled()){
                    bluetoothAdapter.disable();
                    showToast("Wyłączam bluetooth");
                }
                else {
                    showToast("Bluetooth jest wyłączone");
                }
            }
        });
        //get paired devices btn click
        paruj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled()){
                    parowanie.setText("Paired Devices");
                    Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
                    for (BluetoothDevice device: devices){
                        parowanie.append("\nDevice: " + device.getName()+ ", " + device);
                    }
                }
                else {

                    showToast("Włącz bluetooth by sparować urządzenie ");
                }
            }
        });


    }
    private void showToast (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public void wstecz(View view){
        finish();

    }
}