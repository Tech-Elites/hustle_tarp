package com.example.hustle_tarp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link leaderboardPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class leaderboardPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public leaderboardPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment leaderboardPage.
     */
    // TODO: Rename and change types and number of parameters
    public static leaderboardPage newInstance(String param1, String param2) {
        leaderboardPage fragment = new leaderboardPage();
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
        return inflater.inflate(R.layout.fragment_leaderboard_page, container, false);
    }

    DatabaseReference databaseReference;
    ArrayList<InfoEmployeeClass> leaderboardList=new ArrayList<>();
    CustomAdaptorLeaderboard customAdaptorLeaderboard;
    ListView listView;
    ProgressBar pb;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pb=getView().findViewById(R.id.progressBarEmployeeLeaderboard);
        pb.setVisibility(View.VISIBLE);
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
                    leaderboardList.add(dataSnapshot.getValue(InfoEmployeeClass.class));
                }
                show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void show(){
        Collections.sort(leaderboardList, Collections.reverseOrder());
        customAdaptorLeaderboard=new CustomAdaptorLeaderboard(getActivity(),leaderboardList);
        listView=getView().findViewById(R.id.leaderboardEmployeeList);
        listView.setAdapter(customAdaptorLeaderboard);
        pb.setVisibility(View.INVISIBLE);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getActivity() , "dodo", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}