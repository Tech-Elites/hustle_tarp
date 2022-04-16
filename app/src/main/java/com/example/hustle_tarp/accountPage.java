package com.example.hustle_tarp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        nameTv=getView().findViewById(R.id.nameOfTheEmployeeAccountDetails);
        pointsTv=getView().findViewById(R.id.noOfCreditsAccountDetails);
        pendingTv=getView().findViewById(R.id.noofPendingAppliAccountDetails);
        progressBar=getView().findViewById(R.id.employeeAccountDetailsProgress);
        progressBar.setVisibility(View.VISIBLE);
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
                pendingTv.setText("Pending: ");
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}