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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    ArrayList<String> sol_submitted_parent=new ArrayList<>();
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
                //int i=0;
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    solSubmitted s=dataSnapshot.getValue(solSubmitted.class);

                    if(s.getIssueId().equals(issue_id))
                    {
                        //Toast.makeText(viewExpandedIssueAdmin.this, ""+s.getUserName(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(viewExpandedIssueAdmin.this, "here - "+dataSnapshot.getKey()+" "+(++i), Toast.LENGTH_SHORT).show();
                        sol_submitted_parent.add(dataSnapshot.getKey());
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
                updateTheInfoEmployee(pos);
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
    void updateTheInfoEmployee(int pos)
    {
        String date = array_list_sol_submitted.get(pos).getDate();
        //Toast.makeText(viewExpandedIssueAdmin.this, ""+array_list_sol_submitted.get(pos).getUserId(), Toast.LENGTH_SHORT).show();
        //FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("info-employee").child(array_list_sol_submitted.get(pos).getUserId()).child("tags").child("devansh randi").setValue(10);
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("info-employee").child(array_list_sol_submitted.get(pos).getUserId());
        databaseReference.get().addOnCompleteListener(
                new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            InfoEmployeeClass infoEmployeeClass=task.getResult().getValue(InfoEmployeeClass.class);

                            //update the credits according to the dates
                            int curr_credits=infoEmployeeClass.getPoints();
                            String name=infoEmployeeClass.getName();
                            curr_credits+=Integer.parseInt(credits);

                            HashMap<String,Integer> new_dates,new_tags;
                            new_dates=infoEmployeeClass.getDates();
                            new_tags=infoEmployeeClass.getTags();
                            if(new_dates.containsKey(date))
                            {
                                int curr_date_point=new_dates.get(date);
                                curr_date_point+=1;
                                new_dates.put(date,curr_date_point);
                            }
                            else
                            {
                                new_dates.put(date,1);
                            }
                            String[] curr_tags=tags.split(";");
                            for(String tags:curr_tags)
                            {
                                tags=tags.trim();
                                if(new_tags.containsKey(tags))
                                {
                                    int curr_tag_point=new_tags.get(tags);
                                    curr_tag_point+=1;
                                    new_tags.put(tags,curr_tag_point);
                                }
                                else
                                {
                                    new_tags.put(tags,1);
                                }
                            }
                            InfoEmployeeClass newInfoClass=new InfoEmployeeClass(curr_credits,name,new_tags,new_dates);
                            HashMap<String,Object> new_val_info=newInfoClass.getHashMap();
                            databaseReference.setValue(new_val_info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(viewExpandedIssueAdmin.this, "Success", Toast.LENGTH_SHORT).show();
                                        push_notifications(array_list_sol_submitted.get(pos).getUserId());
                                    }
                                }
                            });
                        }
                    }
                }
        );

    }
    void push_notifications(String accepted_user_id)
    {
        push_notifications_one_by_one(0,accepted_user_id);
    }
    void push_notifications_one_by_one(int index,String accepted_user_id)
    {
        if(index<array_list_sol_submitted.size())
        {
            String message;
            if(accepted_user_id.equals(array_list_sol_submitted.get(index).getUserId()))
                message="Congrats your submission for issue-'"+title+"' with due date "+duedate+" that you submitted on "+array_list_sol_submitted.get(index).getDate()+" has been accepted!! And you have been " +
                        "awarded "+credits+" credits!!";
            else
                message="Your submission for issue-'"+title+"' with due date "+duedate+" that you submitted on "+array_list_sol_submitted.get(index).getDate()+" has been rejected!!";
            notifications notifications=new notifications(array_list_sol_submitted.get(index).getUserId(),message);
            FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("Notifications").push().setValue(notifications.returnHashMap()).addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                push_notifications_one_by_one(index+1,accepted_user_id);
                            }
                            else
                            {
                                Toast.makeText(viewExpandedIssueAdmin.this, "Error", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
            );
        }
        else
        {
            delete_issues_sol_submitted(0);
        }

    }
    void delete_issues_sol_submitted(int index)
    {
        if(index<sol_submitted_parent.size())
        {
            FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("solSubmitted").child(sol_submitted_parent.get(index)).removeValue().addOnCompleteListener(
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                delete_issues_sol_submitted(index+1);
                            else
                            {
                                Toast.makeText(viewExpandedIssueAdmin.this, "Error", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
            );

        }
        else
        {
            delete_the_issue();
        }
    }
    void delete_the_issue()
    {
        FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("Issues").child(issue_id).removeValue().addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            finish();
                            startActivity(new Intent(getApplicationContext(),adminLandingPage.class));
                        }
                    }
                }
        );
    }
}