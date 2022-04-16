package com.example.hustle_tarp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.HashMap;

public class RegistrationPage extends AppCompatActivity {
    ProgressBar p1;
    String name,status="Employee",dob,email,password;
    EditText name_n,dob_d,email_e,password_p;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        p1=findViewById(R.id.progressBarRegistrationPage);
        name_n=findViewById(R.id.name_registration_page);
        initDatePicker();
        dateButton = findViewById(R.id.registerDobButton);
        dob=(getTodaysDate());
        dateButton.setText(dob);
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
                                        add_info_employee(uid,name);
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
    void add_info_employee(String user_id,String name)
    {
        int points=0;
        HashMap<String,Integer> tags=new HashMap<>();
        tags.put("NULL",0);
        HashMap<String,Integer> dates=new HashMap<>();
        dates.put("NULL",0);
        InfoEmployeeClass infoEmployeeClass=new InfoEmployeeClass(points,name,tags,dates);
        FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("info-employee").child(user_id).setValue(infoEmployeeClass.getHashMap()).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RegistrationPage.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        );
    }
    public void registerButton(View view){
        name=name_n.getText().toString();
        //dob=dob_d.getText().toString();
        email=email_e.getText().toString();
        password=password_p.getText().toString();
        register(name,dob,email,password);
    }

    private String getTodaysDate()
    {
        return "";
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                dob = makeDateString(day, month, year);
                dateButton.setText(dob);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return day + "/" + Integer.toString(month)+ "/" + year;
    }

    public void takeTime(View view) {
        try{
            datePickerDialog.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }
}