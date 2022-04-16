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

public class customAdaptorAdminExpandedViewLIstView extends ArrayAdapter<String> {

    public customAdaptorAdminExpandedViewLIstView(@NonNull Context context, ArrayList<String> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.sol_submitted_admin_list_customview, parent, false);
        }

        try{
            String prompt_userName = getItem(position).toString();

            TextView text_prompt = currentItemView.findViewById(R.id.view_xyz_solution_prompt);
            String prompt_text="View "+prompt_userName+"'s solution";
            text_prompt.setText(prompt_text);

        }catch (Exception e){
            System.out.println(e);
        }

        return currentItemView;
    }
}
