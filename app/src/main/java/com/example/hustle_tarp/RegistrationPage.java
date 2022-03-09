package com.example.hustle_tarp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationPage extends AppCompatActivity {
    ProgressBar p1;
    String name,status="Employee",dob,email,password;
    EditText name_n,dob_d,email_e,password_p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        p1=findViewById(R.id.progressBarRegistrationPage);
        name_n=findViewById(R.id.name_registration_page);
        dob_d=findViewById(R.id.DOB_registration_page);
        email_e=findViewById(R.id.email_registration_page);
        password_p=findViewById(R.id.password_registration_page);
        p1.setVisibility(View.INVISIBLE);
    }
    public void register(String name,String dob,String email,String password){
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(dob) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Details not filled")
                    .setMessage("Fill all the details to proceed.")
                    .setPositiveButton("Ok",null)
                    .show();
        }
        else{
            p1.setVisibility(View.VISIBLE);
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("Workers");
                        Employee e=new Employee(name,status,dob);
                        HashMap h=e.getHashMap();
                        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                        if(u!=null){
                            String uid=u.getUid();
                            databaseReference.child(uid).setValue(h).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegistrationPage.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(RegistrationPage.this, "Error2", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                    else{
                        Toast.makeText(RegistrationPage.this, "Error", Toast.LENGTH_SHORT).show();
                        p1.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }

    public void registerButton(View view){
        name=name_n.getText().toString();
        dob=dob_d.getText().toString();
        email=email_e.getText().toString();
        password=password_p.getText().toString();
        register(name,dob,email,password);
    }
}