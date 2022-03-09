package com.example.hustle_tarp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getActionBar().hide();
        progressBar=findViewById(R.id.progressBarMainActivity);
        progressBar.setVisibility(View.VISIBLE);
//        ProgressBar progressBar=findViewById(R.id.pro)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        startActivity(new Intent(this,bottomNavigationPage.class));
        new CountDownTimer(3000,1000)
        {
            public void onTick(long milliseconds)
            {
            }
            public void onFinish()
            {

                FirebaseUser u= FirebaseAuth.getInstance().getCurrentUser();
                if(u!=null)
                {
                    //Toast.makeText(this, "here "+u.getUid(), Toast.LENGTH_LONG).show();

                    FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("Workers").child(u.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            Employee uu=task.getResult().getValue(Employee.class);

                            if(uu.getStatus().compareTo("Employee")==0)
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                Intent i = new Intent(MainActivity.this,employeeLandingPage.class);
                                finish();
                                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());


                            }
                            else
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                Intent i = new Intent(MainActivity.this, adminLandingPage.class);
                                finish();
                                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                            }

                        }
                    });
                }
                else
                {
                    Intent i = new Intent(MainActivity.this, LoginPage.class);
                    finish();
                    progressBar.setVisibility(View.INVISIBLE);
                    startActivity(i, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());

                }

            }

        }.start();


    }
}