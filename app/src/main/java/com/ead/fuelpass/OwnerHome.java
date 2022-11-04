package com.ead.fuelpass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class OwnerHome extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    OwnerSettingsFragment settingsFragment = new OwnerSettingsFragment();
    AddStationFragment addStationFragment= new AddStationFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home);

        bottomNavigationView  = findViewById(R.id.bottom_navigation_o);
        getSupportFragmentManager().beginTransaction().replace(R.id.container1,addStationFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.stations0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container1,addStationFragment).commit();
                        return true;
                    case R.id.settings0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container1,settingsFragment).commit();
                        return true;
                }
                return false;
            }
        });


    }
}