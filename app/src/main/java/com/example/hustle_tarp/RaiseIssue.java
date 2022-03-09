package com.example.hustle_tarp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RaiseIssue extends AppCompatActivity {
    DatabaseReference databaseReference;
    EditText title_editText,description_editText,credits_editText,link_editText,duedate_editText;
    String title,description,credits,link,duedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_issue);
        //assign the edit views here
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("Issues");
        title_editText=findViewById(R.id.raise_issue_title);
        description_editText=findViewById(R.id.raise_issue_desc);
        credits_editText=findViewById(R.id.raise_issue_credits);
        link_editText=findViewById(R.id.raise_issue_link);
        duedate_editText=findViewById(R.id.raise_issue_duedate);
    }
    public void raise_issue(View v){
        title=title_editText.getText().toString();
        description=description_editText.getText().toString();
        credits=credits_editText.getText().toString();
        link=link_editText.getText().toString();
        duedate=duedate_editText.getText().toString();
        if(!TextUtils.isEmpty(title)&&!TextUtils.isEmpty(description)&&!TextUtils.isEmpty(credits)&&!TextUtils.isEmpty(title)&&
                !TextUtils.isEmpty(title)){
            Issues issues=new Issues(title,description,credits,link,duedate);
            HashMap<String,String> h_map=issues.getHashMap();
            databaseReference.push().setValue(h_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        new AlertDialog.Builder(RaiseIssue.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("Ok",null)
                                .setMessage("Issue has been raised successfully")
                                .show();
                    }
                }
            });
        }
        else
        {

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Fill all the details!!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }
}