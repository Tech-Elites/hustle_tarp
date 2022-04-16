package com.example.hustle_tarp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeamDetailsAdminEachMember extends AppCompatActivity {

    TextView tv;
    String uid;

    ArrayList<String> tags;
    ArrayList<Integer> tagsPoints;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details_admin_each_member);
        tv=findViewById(R.id.teamDetailsAdminEach);

        Bundle b = getIntent().getExtras();
        uid=b.getString("uid");
        tv.setText(uid);

        tags=new ArrayList<>();
        tagsPoints=new ArrayList<>();
        find();
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    


}