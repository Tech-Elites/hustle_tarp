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

public class CustomAdaptorViewIssues extends ArrayAdapter<Issues> {

    public CustomAdaptorViewIssues(@NonNull Context context, ArrayList<Issues> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;



        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_viewissues_list_view, parent, false);
        }

        try{
            Issues issue = getItem(position);

            TextView title = currentItemView.findViewById(R.id.title_custom_listView);
            TextView desc = currentItemView.findViewById(R.id.description_custom_listView);
            TextView credits=currentItemView.findViewById(R.id.credits_custom_ListView);
            TextView due_date=currentItemView.findViewById(R.id.dueDate_listView);

            title.setText(issue.getTitle());
            desc.setText(issue.getDescription());
            credits.setText("Credits: "+issue.getCredits());
            due_date.setText("Due: "+issue.getDue_date());

        }catch (Exception e){
            System.out.println(e);
        }

        return currentItemView;
    }
}