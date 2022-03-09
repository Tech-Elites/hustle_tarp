package com.example.hustle_tarp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ViewExpandedIssueEmployee extends AppCompatActivity {
    String title,desc,credits,link,duedate,issue_id;
    TextView title_expandedViewIssuesEmployee,desc_expandedViewIssuesEmployee,credits_expandedViewIssuesEmployee,
    link_expandedViewIssuesEmployee,duedate_expandedViewIssuesEmployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expanded_issue_employee);
        Intent i=getIntent();
        title=i.getStringExtra("title");
        desc=i.getStringExtra("desc");
        credits=i.getStringExtra("credits");
        link=i.getStringExtra("link");
        duedate=i.getStringExtra("duedate");
        issue_id=i.getStringExtra("issueid");
        title_expandedViewIssuesEmployee=findViewById(R.id.title_employee_expanded_view);
        desc_expandedViewIssuesEmployee=findViewById(R.id.desc_employee_expanded_view);
        credits_expandedViewIssuesEmployee=findViewById(R.id.credits_employee_expanded_view);
        link_expandedViewIssuesEmployee=findViewById(R.id.link_employee_expanded_view);
        duedate_expandedViewIssuesEmployee=findViewById(R.id.duedate_employee_expanded_view);
        title_expandedViewIssuesEmployee.setText("Issue: "+title);
        desc_expandedViewIssuesEmployee.setText(desc);
        credits_expandedViewIssuesEmployee.setText("Credits: "+credits);
        link_expandedViewIssuesEmployee.setText(link);
        duedate_expandedViewIssuesEmployee.setText("Due:"+duedate);
        Toast.makeText(this, ""+issue_id, Toast.LENGTH_SHORT).show();
    }
}