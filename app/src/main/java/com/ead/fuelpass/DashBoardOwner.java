package com.ead.fuelpass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import com.ead.fuelpass.adapters.AdapterTank;
import com.ead.fuelpass.database.DBHelper;
import com.ead.fuelpass.model.Tank;
import com.ead.fuelpass.model.TankData;
import com.ead.fuelpass.nav.Navigation;
import com.ead.fuelpass.remote.RetrofitClient;
import com.ead.fuelpass.remote.TankService;
import com.ead.fuelpass.toast.Toasts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardOwner extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Context context;
    private DBHelper mydb;
    private TankService service;
    private ArrayList<TankData> data;
    private Navigation nav;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_owner);
        Intent intent = getIntent();
        data = new ArrayList<>();
        context = getApplicationContext();
        mydb = new DBHelper(context);

        nav = new Navigation(context);
        service = RetrofitClient.getClient().create(TankService.class);
        Button create = findViewById(R.id.btnAddTank);
        Button back = findViewById(R.id.btnBackTank);

        create.setOnClickListener(v -> startActivity(nav.goToTankOw()));
        back.setOnClickListener(v -> startActivity(nav.homeOwner()));
        getData();

    }


    //get Station data
    public void getData() {

        Call<List<TankData>> call = service.getTankById(mydb.getStationId());
        call.enqueue(new Callback<List<TankData>>() {

            @Override
            public void onResponse(@NonNull Call<List<TankData>> call, @NonNull Response<List<TankData>> response) {

                if (response.errorBody() != null) {
                    Toasts.error(context, "no data!");
                }

                if (response.isSuccessful() && response.body() != null) {
                    data.addAll(response.body());
                    recyclerView = findViewById(R.id.recycler_view2);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setHasFixedSize(true);
                    ArrayList<Tank> t = new ArrayList<>();

                    for(TankData d:response.body())
                    {
                        t.add(new Tank(d.getFuelType(),d.isStatus(),d.getShed().getShedId()));
                    }


                    AdapterTank myAdapter = new AdapterTank(t,getApplicationContext());
                    recyclerView.setAdapter(myAdapter);

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<TankData>> call, @NonNull Throwable t) {
                Toasts.error(context, "no data!");
            }

        });
    }





}