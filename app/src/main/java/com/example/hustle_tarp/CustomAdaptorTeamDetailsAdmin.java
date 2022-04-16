package com.example.hustle_tarp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;

public class CustomAdaptorTeamDetailsAdmin extends ArrayAdapter<InfoEmployeeClass> {

    public CustomAdaptorTeamDetailsAdmin(@NonNull Context context, ArrayList<InfoEmployeeClass> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;



        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout_teamdetails_admin, parent, false);
        }

        try{
            InfoEmployeeClass issue = getItem(position);

            TextView lname = currentItemView.findViewById(R.id.teamdetailsAdminName);

            lname.setText(issue.getName());

        }catch (Exception e){
            System.out.println(e);
        }

        return currentItemView;
    }
}