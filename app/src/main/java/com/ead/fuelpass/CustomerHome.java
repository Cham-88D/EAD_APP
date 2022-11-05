package com.ead.fuelpass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CustomerHome extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    MapsFragment mapFragment = new MapsFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    StationFragment stationListFragment = new StationFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        bottomNavigationView  = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,mapFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.map:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,mapFragment).commit();
                    return true;
                case R.id.stations:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,stationListFragment).commit();
                    return true;
                case R.id.settings:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,settingsFragment).commit();
                    return true;
            }

            return false;
        });


    }
}