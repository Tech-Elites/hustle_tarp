package com.example.hustle_tarp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ViewExpandedIssueEmployee extends AppCompatActivity {
    String title,desc,credits,link,duedate,issue_id,link_solution,comments_solution,tags;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView title_expandedViewIssuesEmployee,desc_expandedViewIssuesEmployee,credits_expandedViewIssuesEmployee,tags_expandedViewIssuesEmployee,
    link_expandedViewIssuesEmployee,duedate_expandedViewIssuesEmployee;
    public void finishButtonEmployee(View v)
    {
        Toast.makeText(this, "here in employee", Toast.LENGTH_SHORT).show();
        databaseReference=firebaseDatabase.getReference().child("Team Alpha").child("solSubmitted");
        LayoutInflater factory = LayoutInflater.from(this);
        final View submitSolDialogView = factory.inflate(R.layout.submit_sol_employee_custom_alert_dialog, null);
        final AlertDialog submitSol = new AlertDialog.Builder(this).create();
        submitSol.setView(submitSolDialogView);
        submitSolDialogView.findViewById(R.id.submitSolutionButtonAlertDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                EditText link,comments;
                link=submitSolDialogView.findViewById(R.id.solutionLinkAlertDialogSubSolution);
                comments=submitSolDialogView.findViewById(R.id.commentAltertDialogSubSolution);
                if(TextUtils.isEmpty(link.getText())||TextUtils.isEmpty(comments.getText()))
                {
                    Toast.makeText(ViewExpandedIssueEmployee.this, "Fill all the details first!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    link_solution=link.getText().toString();
                    comments_solution=comments.getText().toString();
                    new android.app.AlertDialog.Builder(ViewExpandedIssueEmployee.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Submit solution??")
                            .setMessage("Are you sure??")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    submitSol.dismiss();
                                    submitSolution();
                                }
                            })
                            .setNegativeButton("No",null)
                            .show();
                }
            }
        });

        submitSol.show();
    }
    void submitSolution()
    {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            String id=firebaseUser.getUid();

            firebaseDatabase.getReference().child("Team Alpha").child("Workers").child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        Employee e;
                        e=task.getResult().getValue(Employee.class);
                        String name=e.getName();
                        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        solSubmitted solSubmitted=new solSubmitted(id,name,link_solution,comments_solution,issue_id,date);
                        HashMap<String,String> hashMap=solSubmitted.getHashMap();
                        Toast.makeText(ViewExpandedIssueEmployee.this, ""+hashMap, Toast.LENGTH_SHORT).show();
                        firebaseDatabase.getReference().child("Team Alpha").child("solSubmitted").push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    finish();
                                    startActivity(new Intent(ViewExpandedIssueEmployee.this,employeeLandingPage.class));
                                }
                            }
                        });
                    }
                }
            });

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expanded_issue_employee);
        Intent i=getIntent();
        firebaseDatabase=FirebaseDatabase.getInstance();
        title=i.getStringExtra("title");

        desc=i.getStringExtra("desc");
        credits=i.getStringExtra("credits");
        link=i.getStringExtra("link");
        duedate=i.getStringExtra("duedate");
        issue_id=i.getStringExtra("issueid");
        tags=i.getStringExtra("tags");
        tags_expandedViewIssuesEmployee=findViewById(R.id.tags_employee_expanded_view);
        tags_expandedViewIssuesEmployee.setText(tags);
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
        tags_expandedViewIssuesEmployee.setText(tags);
        Toast.makeText(this, ""+issue_id, Toast.LENGTH_SHORT).show();
    }
    //overriding the back button as the previous activity is finished
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ViewExpandedIssueEmployee.this,employeeLandingPage.class));
    }
}