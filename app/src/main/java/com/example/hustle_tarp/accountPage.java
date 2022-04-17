package com.example.hustle_tarp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link accountPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class accountPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public accountPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment accountPage.
     */
    // TODO: Rename and change types and number of parameters
    public static accountPage newInstance(String param1, String param2) {
        accountPage fragment = new accountPage();
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
        return inflater.inflate(R.layout.fragment_account_page, container, false);
    }

    String currentuser,name,points;
    TextView nameTv,pointsTv,pendingTv;
    ProgressBar progressBar;
    ListView listViewPending;
    pendingIssueCustomAdaptor pendingIssueCustomAdaptor;
    ArrayList<pendingIssue> pendingIssueArrayList=new ArrayList<>();
    HashMap<String,String> issue_id_to_due_date=new HashMap<>();
    Button redeemButton;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        nameTv=getView().findViewById(R.id.nameOfTheEmployeeAccountDetails);
        pointsTv=getView().findViewById(R.id.noOfCreditsAccountDetails);
        pendingTv=getView().findViewById(R.id.noofPendingAppliAccountDetails);
        progressBar=getView().findViewById(R.id.employeeAccountDetailsProgress);
        progressBar.setVisibility(View.VISIBLE);
        listViewPending=getView().findViewById(R.id.listViewForPendingApplications);
        redeemButton=getView().findViewById(R.id.redeemButton);
        redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redeemButton();
            }
        });
        getName();
    }

    void getName(){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("Workers").child(currentuser);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name=snapshot.child("name").getValue().toString();
                nameTv.setText(name);
                getPoints();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void getPoints(){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("info-employee").child(currentuser);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                points=snapshot.child("points").getValue().toString();
                pointsTv.setText("Points: "+points);
                //pendingTv.setText("Pending: ");
                //progressBar.setVisibility(View.INVISIBLE);
                getPendingIssues();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    int no_of_pending_issues;
    void getPendingIssues(){
        FirebaseUser u=FirebaseAuth.getInstance().getCurrentUser();
        if(u!=null)
        {
            no_of_pending_issues=0;
            String user_id=u.getUid();
            FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("solSubmitted").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren())
                    {
                        solSubmitted solSubmitted=dataSnapshot.getValue(com.example.hustle_tarp.solSubmitted.class);
                        if(solSubmitted.getUserId().equals(user_id))
                        {
                            no_of_pending_issues++;
                            issue_id_to_due_date.put(solSubmitted.getIssueId(),solSubmitted.getDate());
                        }
                    }
                    fill_the_pending_issues_list();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    void fill_the_pending_issues_list()
    {
        pendingTv.setText("Pending: "+no_of_pending_issues);
        FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("Issues").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Issues issues=dataSnapshot.getValue(Issues.class);
                    if(issue_id_to_due_date.containsKey(dataSnapshot.getKey()))
                    {
                        pendingIssueArrayList.add(new pendingIssue(issues.getTitle(),issues.getDescription(),issue_id_to_due_date.get(dataSnapshot.getKey())));
                    }
                }
                fill_the_list();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Toast.makeText(getActivity(), ""+issue_id_to_due_date, Toast.LENGTH_SHORT).show();
    }
    void fill_the_list()
    {
        try {
            progressBar.setVisibility(View.INVISIBLE);
            pendingIssueCustomAdaptor=new pendingIssueCustomAdaptor(getActivity(),pendingIssueArrayList);
            listViewPending.setAdapter(pendingIssueCustomAdaptor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void redeemButton()
    {
        startActivity(new Intent(getActivity(),redeemTheCredits.class));
    }
}