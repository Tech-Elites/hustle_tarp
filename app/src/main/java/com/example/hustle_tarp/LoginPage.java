package com.example.hustle_tarp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;


public class LoginPage extends AppCompatActivity {
    ProgressBar p;
    FirebaseAuth firebaseAuth;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        p=findViewById(R.id.progressBarLoginPage);
        firebaseAuth = FirebaseAuth.getInstance();
        p.setVisibility(View.INVISIBLE);
    }

    void login(String email,String password){
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Details not filled")
                    .setMessage("Fill all the details to proceed.")
                    .setPositiveButton("Ok",null)
                    .show();
        }
        else{
            p.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        FirebaseUser u = firebaseAuth.getCurrentUser();
                        if (u != null) {
                            Toast.makeText(LoginPage.this, "here", Toast.LENGTH_SHORT).show();
                            FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("Workers").child(u.getUid()).
                                    get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
//                                   userinfo uu=task.getResult().getValue(userinfo.class);
//                                    Intent i = new Intent(LoginPage.this, bottomNavigationPage.class);
//                                    finish();
//                                    startActivity(i, ActivityOptions.makeSceneTransitionAnimation(LoginPage.this).toBundle());
                                    Employee e=task.getResult().getValue(Employee.class);
//                                    Toast.makeText(LoginPage.this, ""+e.getName(), Toast.LENGTH_SHORT).show();
                                    p.setVisibility(View.INVISIBLE);
                                    if(e.getStatus().equals("Employee")){
                                        startActivity(new Intent(getApplicationContext(), employeeLandingPage.class));
                                    }
                                    else{
                                        startActivity(new Intent(getApplicationContext(), adminLandingPage.class));
                                    }

                                }
                            });
                        }
                    }
                    else{
                        new AlertDialog.Builder(LoginPage.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Error logging in.")
                                .setMessage("Invalid login credentials. Please check your credentials and try again.")
                                .setPositiveButton("Yes",null)
                                .show();
                        p.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }
    public void LoginPageLoginButton(View view){
        EditText t1= findViewById(R.id.LoginPageEmail);
        email=t1.getText().toString();
        EditText t2= findViewById(R.id.LoginPagePassword);
        password=t2.getText().toString();
        login(email, password);
    }

    public void RegisterButton(View view){
        startActivity(new Intent(this,RegistrationPage.class));
    }
}