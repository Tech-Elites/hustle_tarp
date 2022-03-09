package com.example.hustle_tarp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link feedPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class feedPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public feedPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment feedPage.
     */
    // TODO: Rename and change types and number of parameters
    public static feedPage newInstance(String param1, String param2) {
        feedPage fragment = new feedPage();
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
        return inflater.inflate(R.layout.fragment_feed_page, container, false);
    }


    CustomAdaptorViewIssues customAdaptorViewIssues;
    ListView listView_viewIssues;
    ProgressBar progressBar;
    ArrayList<Issues> issuesArrayList=new ArrayList<>();
    ArrayList<String> issuesId=new ArrayList<>();
    DatabaseReference databaseReference;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Log.println("here");
        listView_viewIssues=getView().findViewById(R.id.view_issue_listView);
        progressBar=getView().findViewById(R.id.progressBarEmployeeFeed);
        progressBar.setVisibility(View.VISIBLE);
        find_the_issues();
    }
    void find_the_issues(){
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("Issues");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //int i=0;
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    //i++;
                    issuesArrayList.add(dataSnapshot.getValue(Issues.class));
                    issuesId.add(dataSnapshot.getKey());
                    //Toast.makeText(getActivity(), ""+i, Toast.LENGTH_SHORT).show();
                }
                populateTheList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void populateTheList(){
        try {
            progressBar.setVisibility(View.INVISIBLE);
            customAdaptorViewIssues=new CustomAdaptorViewIssues(getActivity(),issuesArrayList);
            listView_viewIssues.setAdapter(customAdaptorViewIssues);
            listView_viewIssues.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    OnClickListView(i);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void OnClickListView(int pos){
        Intent i=new Intent(getActivity(),ViewExpandedIssueEmployee.class);
        Issues issues=issuesArrayList.get(pos);
        i.putExtra("title",issues.getTitle());
        i.putExtra("desc",issues.getDescription());
        i.putExtra("credits",issues.getCredits());
        i.putExtra("link",issues.getLink());
        i.putExtra("duedate",issues.getDue_date());
        i.putExtra("issueid",issuesId.get(pos));
        startActivity(i);
    }
}