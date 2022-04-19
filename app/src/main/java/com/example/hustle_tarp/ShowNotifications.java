package com.example.hustle_tarp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowNotifications extends AppCompatActivity {

    ListView listViewNotifications;
    ArrayList<String> notifications=new ArrayList<>();
    ArrayList<String> keys=new ArrayList<>();
    String user_id;
    ArrayAdapter ad;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notifications);

        listViewNotifications=findViewById(R.id.listView_notifications_emp);

        imageView=findViewById(R.id.imageviewNotifications);
        imageView.setVisibility(View.INVISIBLE);

        user_id= FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("Notifications").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {

                    notifications n=snapshot1.getValue(notifications.class);
                    //Toast.makeText(ShowNotifications.this, ""+n.getMessage(), Toast.LENGTH_SHORT).show();
                    if(n.getUser_id().equals(user_id))
                    {
                        notifications.add(n.getMessage());
                        keys.add(snapshot1.getKey());
                        //Toast.makeText(ShowNotifications.this, ""+snapshot1.getKey(), Toast.LENGTH_SHORT).show();
                    }
                }
                ad=new ArrayAdapter(ShowNotifications.this,R.layout.notifications_custom_adaptor_layout_file,notifications);

                if(notifications.size()==0){
                    imageView.setVisibility(View.VISIBLE);
                }
                else{
                    imageView.setVisibility(View.INVISIBLE);
                }
                listViewNotifications.setAdapter(ad);
                listViewNotifications.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        new AlertDialog.Builder(ShowNotifications.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Are you sure?")
                                .setMessage("Delete this Notification?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int pos) {
                                        //Toast.makeText(ShowNotifications.this, ""+i+" "+notifications.size(), Toast.LENGTH_SHORT).show();
                                        deleteItem(i);
                                    }
                                })
                                .show();

                        return true;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void deleteItem(int pos)
    {
        notifications.remove(pos);
        String key=keys.get(pos);
        FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("Notifications").child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(),ShowNotifications.class));
                }
            }
        });
    }
}