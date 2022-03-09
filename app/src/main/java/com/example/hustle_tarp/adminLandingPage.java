package com.example.hustle_tarp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class adminLandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_landing_page);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        NavController navController = Navigation.findNavController(this,  R.id.fragment2);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}