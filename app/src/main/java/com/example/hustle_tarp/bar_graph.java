package com.example.hustle_tarp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class bar_graph extends AppCompatActivity {

    BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);

        barChart= (BarChart) findViewById(R.id.bargraph);
        ArrayList<BarEntry> barEntries=new ArrayList<>();
        barEntries.add(new BarEntry(44f,0));
        barEntries.add(new BarEntry(22f,1));
        barEntries.add(new BarEntry(20f,2));
        barEntries.add(new BarEntry(34f,3));
        barEntries.add(new BarEntry(30f,4));
        BarDataSet barDataSet=new BarDataSet(barEntries,"Dates");

        ArrayList<String> theDates=new ArrayList<>();
        theDates.add("Jan");
        theDates.add("Feb");
        theDates.add("March");
        theDates.add("April");
        theDates.add("May");

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(theDates));
        BarData theData = new BarData(barDataSet);
        barChart.setData(theData);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
    }
}