package com.example.hustle_tarp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link teamDetailsPageAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class teamDetailsPageAdmin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public teamDetailsPageAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment teamDetailsPageAdmin.
     */
    // TODO: Rename and change types and number of parameters
    public static teamDetailsPageAdmin newInstance(String param1, String param2) {
        teamDetailsPageAdmin fragment = new teamDetailsPageAdmin();
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
        return inflater.inflate(R.layout.fragment_team_details_page_admin, container, false);
    }



    DatabaseReference databaseReference;
    ArrayList<InfoEmployeeClass> leaderboardList1=new ArrayList<>();
    ArrayList<InfoEmployeeClass> leaderboardList2=new ArrayList<>();
    ArrayList<String> uidList1=new ArrayList<>();
    ArrayList<String> uidList2=new ArrayList<>();
    CustomAdaptorTeamDetailsAdmin customAdaptorTeamDetailsAdmin1,customAdaptorTeamDetailsAdmin2;
    ListView listView1,listView2;
    ProgressBar pb;
    int c;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pb=getView().findViewById(R.id.progressBarTeamDetailsAdmin);
        pb.setVisibility(View.VISIBLE);
        c=0;
        fillArray();
    }


    void fillArray(){
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Team Alpha").child("info-employee");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //int i=0;
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
//                    Toast.makeText(getActivity() , dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    if(c%2==0) {
                        leaderboardList1.add(dataSnapshot.getValue(InfoEmployeeClass.class));
                        uidList1.add(dataSnapshot.getKey());
                    }
                    else {
                        leaderboardList2.add(dataSnapshot.getValue(InfoEmployeeClass.class));
                        uidList2.add(dataSnapshot.getKey());
                    }
                    c++;
                }
                show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void show(){
        try {
            customAdaptorTeamDetailsAdmin1=new CustomAdaptorTeamDetailsAdmin(getActivity(),leaderboardList1);
            customAdaptorTeamDetailsAdmin2=new CustomAdaptorTeamDetailsAdmin(getActivity(),leaderboardList2);
            listView1=getView().findViewById(R.id.teamDetalsAdminList1);
            listView1.setAdapter(customAdaptorTeamDetailsAdmin1);
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(getActivity(),TeamDetailsAdminEachMember.class);
                    intent.putExtra("uid",uidList1.get(i));
                    startActivity(intent);
                }
            });
            listView2=getView().findViewById(R.id.teamDetalsAdminList2);
            listView2.setAdapter(customAdaptorTeamDetailsAdmin2);
            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(getActivity(),TeamDetailsAdminEachMember.class);
                    intent.putExtra("uid",uidList2.get(i));
                    startActivity(intent);
                }
            });
            pb.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
