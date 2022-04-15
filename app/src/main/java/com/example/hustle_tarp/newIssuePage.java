package com.example.hustle_tarp;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link newIssuePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class newIssuePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public newIssuePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment newIssuePage.
     */
    // TODO: Rename and change types and number of parameters
    public static newIssuePage newInstance(String param1, String param2) {
        newIssuePage fragment = new newIssuePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_issue_page, container, false);
    }

    DatabaseReference databaseReference;
    EditText title_editText,description_editText,credits_editText,link_editText,duedate_editText;
    String title,description,credits,link,duedate;
    Button b;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("Issues");
        title_editText=getView().findViewById(R.id.raise_issue_title);
        description_editText=getView().findViewById(R.id.raise_issue_desc);
        credits_editText=getView().findViewById(R.id.raise_issue_credits);
        link_editText=getView().findViewById(R.id.raise_issue_link);
        duedate_editText=getView().findViewById(R.id.raise_issue_duedate);
        b=getView().findViewById(R.id.raiseIssue);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raise_issue();
            }
        });
    }

    public void raise_issue(){
        title=title_editText.getText().toString();
        description=description_editText.getText().toString();
        credits=credits_editText.getText().toString();
        link=link_editText.getText().toString();
        duedate=duedate_editText.getText().toString();
        if(!TextUtils.isEmpty(title)&&!TextUtils.isEmpty(description)&&!TextUtils.isEmpty(credits)&&!TextUtils.isEmpty(title)&&
                !TextUtils.isEmpty(title)){
            Issues issues=new Issues(title,description,credits,link,duedate);
            HashMap<String,String> h_map=issues.getHashMap();
            databaseReference.push().setValue(h_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        new AlertDialog.Builder(getContext())
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setMessage("Issue has been raised successfully")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        title_editText.setText("");
                                        description_editText.setText("");
                                        credits_editText.setText("");
                                        link_editText.setText("");
                                        duedate_editText.setText("");
                                    }
                                })
                                .show();
                    }
                }
            });
        }
        else
        {

            new AlertDialog.Builder(getContext())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Fill all the details!!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }
}