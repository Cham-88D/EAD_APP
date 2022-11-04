package com.ead.fuelpass;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ead.fuelpass.database.DBHelper;
import com.ead.fuelpass.nav.Navigation;


/**
 * Owner account settings
 */

public class OwnerSettingsFragment extends Fragment {

    private Navigation nav;
    private DBHelper mydb;

    public OwnerSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context context = getActivity();
        nav = new Navigation(context);
        mydb = new DBHelper(context);
        View rootView = inflater.inflate(R.layout.fragment_owner_settings, container, false);

        Button buttonPass = (Button) rootView.findViewById(R.id.buttonPassO);
        Button buttonOut = (Button) rootView.findViewById(R.id.buttonOutO);
        Button buttonCreate = (Button) rootView.findViewById(R.id.buttonCreateO);

        buttonPass.setOnClickListener(v -> startActivity(nav.goToReset()));
        buttonOut.setOnClickListener(v -> logOut());
        buttonCreate.setOnClickListener(v -> startActivity(nav.createStation()));

        return rootView;
    }

    //log out
    public void logOut() {
        if (mydb.deleteUser(mydb.getId()) != null) {
            mydb.deleteStation(mydb.getStationId());
            startActivity(nav.login());
        }
    }

}