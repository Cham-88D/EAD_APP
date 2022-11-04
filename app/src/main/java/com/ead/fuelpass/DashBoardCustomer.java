package com.ead.fuelpass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.ead.fuelpass.adapters.AdapterQueue;
import com.ead.fuelpass.database.DBHelper;
import com.ead.fuelpass.nav.Navigation;
import com.ead.fuelpass.remote.QueueService;
import com.ead.fuelpass.remote.RetrofitClient;
import com.ead.fuelpass.remote.TankService;

public class DashBoardCustomer extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Context context;
    private DBHelper mydb;
    private TankService service;
    private QueueService serviceQ;
    private Navigation nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_customer);
        context = getApplicationContext();
        mydb = new DBHelper(context);
        nav = new Navigation(context);
        service = RetrofitClient.getClient().create(TankService.class);
        serviceQ = RetrofitClient.getClient().create(QueueService.class);
        Button back = findViewById(R.id.btnBackCus);
        back.setOnClickListener(v -> startActivity(nav.homeCustomer()));
        setAdpt();

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    public void setAdpt() {


       recyclerView = findViewById(R.id.recycler_view4);
       recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
       recyclerView.setHasFixedSize(true);
       AdapterQueue myAdapter = new AdapterQueue(mydb.getQueue(mydb.getStationId()),getApplicationContext());
       recyclerView.setAdapter(myAdapter);

    }



}


