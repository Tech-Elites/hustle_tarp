package com.example.hustle_tarp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class bar_graph extends AppCompatActivity {

    BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);

        barChart= (BarChart) findViewById(R.id.bargraph);
        ArrayList<BarEntry> barEntries=new ArrayList<>();
        barEntries.add(new BarEntry(0,60f));
        barEntries.add(new BarEntry(1,50f));
        barEntries.add(new BarEntry(2,70f));
        barEntries.add(new BarEntry(3,30f));
        barEntries.add(new BarEntry(4,50f));
        System.out.println(barEntries);
        BarDataSet barDataSet=new BarDataSet(barEntries,"Dates");

        barDataSet.setColor(Color.RED);
        barDataSet.setValueTextSize(10f);

        ArrayList<IBarDataSet> dataSets=new ArrayList<>();
        dataSets.add(barDataSet);

        BarData theData = new BarData(dataSets);
        barChart.setData(theData);

        String[] values=new String[] {"Jan","Feb","March","April","May","June"};
        barChart.getXAxis().setValueFormatter(new MyXAxisFormatter(values));
        barChart.getXAxis().setGranularity(1);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.setDrawGridBackground(true);
        barChart.setAutoScaleMinMaxEnabled(true);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(false);
    }

    public class MyXAxisFormatter extends ValueFormatter{
        private String[] mvalues;

        public MyXAxisFormatter(String[] values) {
            this.mvalues=values;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return mvalues[(int)value];
        }
    }
}