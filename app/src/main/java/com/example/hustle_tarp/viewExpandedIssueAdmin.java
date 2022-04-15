package com.example.hustle_tarp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class viewExpandedIssueAdmin extends AppCompatActivity {
    String title,desc,credits,link,duedate,issue_id;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView title_expandedViewIssuesAdmin, desc_expandedViewIssuesAdmin, credits_expandedViewIssuesAdmin,
            link_expandedViewIssuesAdmin, duedate_expandedViewIssuesAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expanded_issue_admin);
        Intent i=getIntent();
        firebaseDatabase= FirebaseDatabase.getInstance();
        title=i.getStringExtra("title");

        desc=i.getStringExtra("desc");
        credits=i.getStringExtra("credits");
        link=i.getStringExtra("link");
        duedate=i.getStringExtra("duedate");
        issue_id=i.getStringExtra("issueid");
        title_expandedViewIssuesAdmin =findViewById(R.id.title_admin_expanded_view);
        desc_expandedViewIssuesAdmin =findViewById(R.id.desc_admin_expanded_view);
        credits_expandedViewIssuesAdmin =findViewById(R.id.credits_admin_expanded_view);
        link_expandedViewIssuesAdmin =findViewById(R.id.link_admin_expanded_view);
        duedate_expandedViewIssuesAdmin =findViewById(R.id.duedate_admin_expanded_view);
        title_expandedViewIssuesAdmin.setText("Issue: "+title);
        desc_expandedViewIssuesAdmin.setText(desc);
        credits_expandedViewIssuesAdmin.setText("Credits: "+credits);
        link_expandedViewIssuesAdmin.setText(link);
        duedate_expandedViewIssuesAdmin.setText("Due:"+duedate);

    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(viewExpandedIssueAdmin.this,adminLandingPage.class));
    }
}