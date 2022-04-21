package com.example.pracainzynierska;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class Conversionlogic {




    public double averageInArrayList(ArrayList<Double> arrayList) {
        return arrayList
                .stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0D);
    }


    public String getData(){
        String currentTime = Calendar.getInstance().getTime().toString();
        return currentTime;
    }


    public String doubletoString(ArrayList<Double> inputArray){
        Gson gson = new Gson();
        String inputString= gson.toJson(inputArray);

        return inputString;
    }
    public String LatlangtoString(ArrayList<LatLng> inputArray){
        Gson gson = new Gson();
        String inputString= gson.toJson(inputArray);

        return inputString;
    }

    public String StringtoString(ArrayList<String> inputArray){
        Gson gson = new Gson();
        String inputString= gson.toJson(inputArray);

        return inputString;
    }

    public ArrayList<String> conversetoStringArray(String data){
        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        ArrayList<String> finalOutputString = gson.fromJson(data, type);
        return finalOutputString;


    }
    public ArrayList<Double> conversetoDoubleArray(String data){
        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<Double>>() {}.getType();

        ArrayList<Double> finalOutputString = gson.fromJson(data, type);
        return finalOutputString;


    }
    public ArrayList<LatLng> conversetoLAtLangArray(String data){
        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<LatLng>>() {}.getType();

        ArrayList<LatLng> finalOutputString = gson.fromJson(data, type);
        return finalOutputString;


    }

}
