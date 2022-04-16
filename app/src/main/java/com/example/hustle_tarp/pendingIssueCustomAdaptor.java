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

public class pendingIssueCustomAdaptor extends ArrayAdapter<pendingIssue> {

    public pendingIssueCustomAdaptor(@NonNull Context context, ArrayList<pendingIssue> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;



        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.pending_issues_custom_layout, parent, false);
        }

        try{
            pendingIssue issue = getItem(position);

            TextView title = currentItemView.findViewById(R.id.pending_issue_title_custom_layout);
            TextView desc=currentItemView.findViewById(R.id.pending_issue_desc_custom_layout);
            TextView due_date=currentItemView.findViewById(R.id.pending_issue_due_date_custom_layout);
            title.setText(issue.getName());
            desc.setText(issue.getDesc());
            due_date.setText(issue.getDate_of_submission());

        }catch (Exception e){
            System.out.println(e);
        }

        return currentItemView;
    }
}

