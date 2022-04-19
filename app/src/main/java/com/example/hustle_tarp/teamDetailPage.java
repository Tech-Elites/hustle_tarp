package com.example.hustle_tarp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link teamDetailPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class teamDetailPage extends Fragment {

    String uid;

    ArrayList<String> tags;
    ArrayList<String> dates;
    ArrayList<Integer> tagsPoints;
    ArrayList<Integer> datesPoints;
    DatabaseReference databaseReference;
    TextView most_occ_tag,most_prod_day,least_prod_day,average_ov;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public teamDetailPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment teamDetailPage.
     */
    // TODO: Rename and change types and number of parameters
    public static teamDetailPage newInstance(String param1, String param2) {
        teamDetailPage fragment = new teamDetailPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        tags=new ArrayList<>();
        tagsPoints=new ArrayList<>();
        dates=new ArrayList<>();
        datesPoints=new ArrayList<>();
        try{
            find_dates();
            find();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_detail_page, container, false);
    }

    ProgressBar progressBar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar=getView().findViewById((R.id.progressBarTeamDetails));
        progressBar.setVisibility(View.INVISIBLE);
    }

    public class MyXAxisFormatter extends ValueFormatter {
        private ArrayList<String > mvalues;

        public MyXAxisFormatter(ArrayList<String> values) {
            this.mvalues=values;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            System.out.println(value);
            return mvalues.get((int)value);
        }
    }

    void find(){
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("info-employee").child(uid).child("tags");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //int i=0;
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    tagsPoints.add(Integer.parseInt(dataSnapshot.getValue().toString()));
                    tags.add(dataSnapshot.getKey());
                }
                printhorizontal_chart(tags,tagsPoints);
                getDetails(tags,tagsPoints);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void find_dates(){
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("info-employee").child(uid).child("dates");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    datesPoints.add(Integer.parseInt(dataSnapshot.getValue().toString()));
                    dates.add(dataSnapshot.getKey());
                }
                printchart(dates,datesPoints);
                getDetails1(dates,datesPoints);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void printchart(ArrayList<String> x,ArrayList<Integer> y){
        try {
            LineChart lineChart=(LineChart) getView().findViewById(R.id.linegraph_emp);
            ArrayList<Entry> entries=new ArrayList<>();
            for(int i=0;i<x.size();i++){
                if(y.get(i)==0){
                    continue;
                }
                else{
                    entries.add(new Entry(i,y.get(i)));
                }
                System.out.println(entries);
            }
            LineDataSet lineDataSet=new LineDataSet(entries,"Dates");
            lineDataSet.setColor(Color.RED);
            lineDataSet.setValueTextSize(10f);

            ArrayList<ILineDataSet> dataSets=new ArrayList<>();
            dataSets.add(lineDataSet);

            LineData theData = new LineData(dataSets);
            lineChart.setData(theData);
            lineChart.getXAxis().setValueFormatter(new MyXAxisFormatter(x));
            lineChart.getXAxis().setGranularity(1);
            lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            lineChart.getAxisRight().setEnabled(false);
            lineChart.getDescription().setText("No of Issues solved per day");
            lineChart.setAutoScaleMinMaxEnabled(true);
            lineChart.setDrawGridBackground(true);
            lineChart.animateY(2000);
            lineChart.invalidate();
            lineChart.refreshDrawableState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void printhorizontal_chart(ArrayList<String> x,ArrayList<Integer> y){
        try {
            int count=0;
            HorizontalBarChart horizontalBarChart=(HorizontalBarChart) getView().findViewById(R.id.hori_bargraph_emp);
            ArrayList<BarEntry> entries=new ArrayList<>();
            for(int i=0;i<x.size();i++){
                if(y.get(i)==0){
                    continue;
                }
                else{
                    count++;
                    entries.add(new BarEntry(i,y.get(i)));
                }
            }
            BarDataSet barDataSet=new BarDataSet(entries,"Topics");
            barDataSet.setValueTextSize(10f);
            barDataSet.setColors(new int[] {Color.RED, Color.GREEN, Color.GRAY, Color.BLACK, Color.BLUE});

            ArrayList<IBarDataSet> dataSets=new ArrayList<>();
            dataSets.add(barDataSet);

            BarData theData = new BarData(dataSets);
            theData.setBarWidth(0.3f);
            horizontalBarChart.setData(theData);

            XAxis xAxis= horizontalBarChart.getXAxis();
            xAxis.setLabelCount(count);
            xAxis.setValueFormatter(new MyXAxisFormatter(x));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setEnabled(true);

            horizontalBarChart.getDescription().setText("No of Issues solved");
            horizontalBarChart.setAutoScaleMinMaxEnabled(true);
            horizontalBarChart.setDrawGridBackground(true);
            horizontalBarChart.animateY(2000);
            horizontalBarChart.invalidate();
            horizontalBarChart.refreshDrawableState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getDetails(ArrayList<String> x,ArrayList<Integer> y){
        try {
            most_occ_tag=(TextView) getView().findViewById(R.id.most_sought_tag_emp);
            int max=0;
            String res_tag="";
            for(int i=0;i<x.size();i++){
                if(y.get(i)>max){
                    max=y.get(i);
                    res_tag=x.get(i);
                }
            }
            most_occ_tag.setText(res_tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getDetails1(ArrayList<String> x,ArrayList<Integer> y){
        try {
            most_prod_day=(TextView) getView().findViewById(R.id.most_productive_day_emp);
            least_prod_day=(TextView) getView().findViewById(R.id.least_productive_day_emp);
            average_ov=(TextView) getView().findViewById(R.id.average_emp);
            int max1=0,min1=10000,sum=0,count=0;
            String most_date="";
            String least_date="";
            for(int i=0;i<x.size();i++){
                if(y.get(i)>max1){
                    max1=y.get(i);
                    most_date=x.get(i);
                }
                if(y.get(i)<min1 && y.get(i)!=0){
                    min1=y.get(i);
                    least_date=x.get(i);
                }
                if(y.get(i)!=0){
                    sum=sum+y.get(i);
                    count++;
                }
            }
            most_prod_day.setText(most_date);
            least_prod_day.setText(least_date);
            average_ov.setText(""+ sum/count);
            progressBar.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}