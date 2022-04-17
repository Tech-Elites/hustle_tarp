package com.example.hustle_tarp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class redeemTheCredits extends AppCompatActivity {

    LinearLayout leave_holidays,coupons,accessories,subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_the_credits);
        leave_holidays=findViewById(R.id.leave_holiday);
        coupons=findViewById(R.id.leave_coupons);
        accessories=findViewById(R.id.Accessories);
        subscription=findViewById(R.id.Subscription);
        leave_holidays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(redeemTheCredits.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Initiate transaction of 1000c??")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                decreaseCredits(1000);
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

            }
        });
        coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(redeemTheCredits.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Initiate transaction of 700c??")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                decreaseCredits(700);
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
            }
        });
        accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(redeemTheCredits.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Initiate transaction of 300c??")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                decreaseCredits(300);
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
            }
        });
        subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(redeemTheCredits.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Initiate transaction of 500c??")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                decreaseCredits(500);
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
            }
        });
    }
    void decreaseCredits(int amount)
    {
        FirebaseUser u= FirebaseAuth.getInstance().getCurrentUser();
        if(u!=null)
        {
            String id=u.getUid();
            FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("info-employee").child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        InfoEmployeeClass info=task.getResult().getValue(InfoEmployeeClass.class);
                        if(info.getPoints()<amount)
                        {
                            new AlertDialog.Builder(redeemTheCredits.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Oops!!")
                                    .setMessage("Insufficient Funds")
                                    .setPositiveButton("Ok",null)
                                    .show();

                        }
                        else
                        {
                            info.setPoints(info.getPoints()-amount);
                            HashMap<String,Object> hashMap=info.getHashMap();
                            FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("info-employee").child(id).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    new AlertDialog.Builder(redeemTheCredits.this)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setTitle("Success!!")
                                            .setMessage("Your transaction was successful!!")
                                            .setPositiveButton("Ok",null)
                                            .show();

                                }
                            });
                        }
                    }
                }
            });
        }
    }
}