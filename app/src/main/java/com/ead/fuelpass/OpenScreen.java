package com.ead.fuelpass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class OpenScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_screen);
        TextView log =  findViewById(R.id.LoginURLOp);
        log.setOnClickListener(v -> nextActivity());

        Button regD =  findViewById(R.id.RegBtnDr);
        regD.setOnClickListener(v -> regDriver());

        Button regO =  findViewById(R.id.RegBtnOwn);
        regO.setOnClickListener(v -> regOwner());



    }

    public void nextActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void regDriver(){
        Intent intent = new Intent(this, RegisterCustomer.class);
        startActivity(intent);
    }

    public void regOwner(){
        Intent intent = new Intent(this, RegisterOwner.class);
        startActivity(intent);
    }
}