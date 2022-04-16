package com.example.hustle_tarp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TeamDetailsAdminEachMember extends AppCompatActivity {

    TextView tv;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details_admin_each_member);
        tv=findViewById(R.id.teamDetailsAdminEach);

        Bundle b = getIntent().getExtras();
        uid=b.getString("uid");
        tv.setText(uid);
    }

}