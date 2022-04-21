package com.example.pracainzynierska;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;

public class Wykres_terenu extends AppCompatActivity {

    private ArrayList<String> time=new ArrayList<>();
    private ArrayList<Double> speed=new ArrayList<>();
    private String horizontalTitle="Time [s]";
    private String verticalTitle;
    private String chartname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wykres_terenu);

        speed=(ArrayList<Double>) getIntent().getSerializableExtra("speed");
        time=(ArrayList<String>) getIntent().getSerializableExtra("time");
        chartname=(String) getIntent().getSerializableExtra("chartname");
        verticalTitle=(String) getIntent().getSerializableExtra("Yaxis");
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series;
        series= new LineGraphSeries<>(data());
        series.setTitle(chartname);
        series.setColor(Color.GREEN);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);
        //graph.getViewport().setScalable(true);
        graph.getGridLabelRenderer().setHorizontalAxisTitle(horizontalTitle);
        graph.getGridLabelRenderer().setVerticalAxisTitle(verticalTitle);
// activate horizontal scrolling
        graph.getViewport().setScrollable(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(30);
// activate horizontal and vertical zooming and scrollin
graph.getViewport().setScalableY(true);
        graph.getViewport().setScalable(true);
// activate vertical scrolling
       graph.getViewport().setScrollableY(true);

       graph.addSeries(series);                   //adding the series to the GraphView


    }
    public void wstecz(View view){
        finish();
}


    public DataPoint[] data(){
        int n=speed.size();     //to find out the no. of data-points
        DataPoint[] values = new DataPoint[n];     //creating an object of type DataPoint[] of size 'n'
        for(int i=0;i<n;i++){
            DataPoint v = new DataPoint(Double.parseDouble(time.get(i)),speed.get(i));
            values[i] = v;
        }
        return values;
    }

}