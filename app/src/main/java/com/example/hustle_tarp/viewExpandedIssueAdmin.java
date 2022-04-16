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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class viewExpandedIssueAdmin extends AppCompatActivity {
    String title,desc,credits,link,duedate,issue_id,tags;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView title_expandedViewIssuesAdmin, desc_expandedViewIssuesAdmin, credits_expandedViewIssuesAdmin,
            link_expandedViewIssuesAdmin, duedate_expandedViewIssuesAdmin,tags_expandedViewIssuesAdmin;
    ListView list_sol_submitted_prompt;
    ArrayList<solSubmitted> array_list_sol_submitted=new ArrayList<>();
    ArrayList<String> userNames=new ArrayList<>();
    ProgressBar progressBar;
    customAdaptorAdminExpandedViewLIstView customAdaptorAdminExpandedViewLIstView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expanded_issue_admin);
        Intent i=getIntent();
        firebaseDatabase= FirebaseDatabase.getInstance();
        title=i.getStringExtra("title");
        list_sol_submitted_prompt=findViewById(R.id.listView_submitted_solutions_expanded_view_admin);
        desc=i.getStringExtra("desc");
        credits=i.getStringExtra("credits");
        link=i.getStringExtra("link");
        duedate=i.getStringExtra("duedate");
        issue_id=i.getStringExtra("issueid");
        tags=i.getStringExtra("tags");
        tags_expandedViewIssuesAdmin=findViewById(R.id.tags_admin_expanded_view);
        tags_expandedViewIssuesAdmin.setText(tags);
        progressBar=findViewById(R.id.progressBarTextPromptAdminExpandedViewIssue);
        progressBar.setVisibility(View.VISIBLE);
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
        find_all_sols();

    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(viewExpandedIssueAdmin.this,adminLandingPage.class));
    }

    void find_all_sols()
    {
        //Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("solSubmitted");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //add all the issues with the given issue id
                //Toast.makeText(viewExpandedIssueAdmin.this, ""+snapshot, Toast.LENGTH_SHORT).show();
//                Toast.makeText(viewExpandedIssueAdmin.this, ""+issue_id, Toast.LENGTH_SHORT).show();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    solSubmitted s=dataSnapshot.getValue(solSubmitted.class);
                    if(s.getIssueId().equals(issue_id))
                    {
                        //Toast.makeText(viewExpandedIssueAdmin.this, ""+s.getUserName(), Toast.LENGTH_SHORT).show();

                        array_list_sol_submitted.add(s);
                        userNames.add(s.getUserName());
                    }
                }
                fill_the_list();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void fill_the_list()
    {

        try {
            progressBar.setVisibility(View.INVISIBLE);
            customAdaptorAdminExpandedViewLIstView=new customAdaptorAdminExpandedViewLIstView(this,userNames);
            list_sol_submitted_prompt.setAdapter(customAdaptorAdminExpandedViewLIstView);
            list_sol_submitted_prompt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    show_alert_dialog_box(i);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void show_alert_dialog_box(int pos){
        LayoutInflater factory = LayoutInflater.from(this);
        final View acceptSolDialogView = factory.inflate(R.layout.custom_layout_alert_dialog_accept_sol_admin, null);
        final AlertDialog acceptSol = new AlertDialog.Builder(this).create();
        acceptSol.setView(acceptSolDialogView);
        TextView comment=acceptSolDialogView.findViewById(R.id.comment_sol_admin_alert_dialog);
        TextView link=acceptSolDialogView.findViewById(R.id.link_sol_admin_alert_dialog);
        comment.setText(array_list_sol_submitted.get(pos).getComments());
        link.setText(array_list_sol_submitted.get(pos).getLink());
        acceptSolDialogView.findViewById(R.id.positive_alert_dialog_accept_sol_admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                //Toast.makeText(viewExpandedIssueAdmin.this, ""+array_list_sol_submitted.get(pos).getUserId(), Toast.LENGTH_SHORT).show();
                //FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("info-employee").child(array_list_sol_submitted.get(pos).getUserId()).child("tags").child("devansh randi").setValue(10);
                //Toast.makeText(viewExpandedIssueAdmin.this, "date "+date+" "+duedate, Toast.LENGTH_SHORT).show();
                acceptSol.cancel();

            }
        });
        acceptSolDialogView.findViewById(R.id.negative_alert_dialog_accept_sol_admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptSol.cancel();

            }
        });

        acceptSol.show();
    }
}