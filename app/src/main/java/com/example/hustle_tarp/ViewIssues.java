package com.example.hustle_tarp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewIssues extends AppCompatActivity {

    CustomAdaptorViewIssues customAdaptorViewIssues;
    ListView listView_viewIssues;
    ArrayList<Issues> issuesArrayList=new ArrayList<>();
    ArrayList<String> issuesId=new ArrayList<>();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_issues);
        listView_viewIssues=findViewById(R.id.view_issue_listView);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("Issues");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    issuesArrayList.add(dataSnapshot.getValue(Issues.class));
                    issuesId.add(dataSnapshot.getKey());
                }
                populateTheList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    void populateTheList(){
        customAdaptorViewIssues=new CustomAdaptorViewIssues(this,issuesArrayList);
        listView_viewIssues.setAdapter(customAdaptorViewIssues);
        listView_viewIssues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OnClickListView(i);
            }
        });
    }
    void OnClickListView(int pos){
        Intent i=new Intent(this,ViewExpandedIssueEmployee.class);
        Issues issues=issuesArrayList.get(pos);
        i.putExtra("title",issues.getTitle());
        i.putExtra("desc",issues.getDescription());
        i.putExtra("credits",issues.getCredits());
        i.putExtra("link",issues.getLink());
        i.putExtra("duedate",issues.getDue_date());
        i.putExtra("issueid",issuesId.get(pos));
        startActivity(i);
    }
}