package com.ead.fuelpass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.ead.fuelpass.database.DBHelper;
import com.ead.fuelpass.model.Station;
import com.ead.fuelpass.nav.Navigation;
import com.ead.fuelpass.remote.RetrofitClient;
import com.ead.fuelpass.remote.StationService;
import com.ead.fuelpass.toast.Toasts;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Create stations
 */
public class CreateStationActivity extends AppCompatActivity {

    private StationService stationService;
    private Context context;
    private Navigation nav;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_station);

        stationService = RetrofitClient.getClient().create(StationService.class);
        context = getApplicationContext();
        nav = new Navigation(context);
        mydb = new DBHelper(this);

        EditText nameText = findViewById(R.id.StName);
        EditText locationText = findViewById(R.id.StLocation);
        Button create = findViewById(R.id.createStBtn);
        Button back = findViewById(R.id.backHomeBtn);

        create.setOnClickListener(v -> {

            String name = nameText.getText().toString().trim();
            String location = locationText.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                Toasts.error(context, "Name Can Not Be Empty!");

            } else if (TextUtils.isEmpty(location)) {
                Toasts.error(context, "Location Can Not Be Empty!");

            } else {
                Station station = new Station(name, location, mydb.getId());
                stationCreator(station);
            }
        });


        back.setOnClickListener(v -> startActivity(nav.goToHome()));


    }


    // create station
    public void stationCreator(Station s) {
        Call<ResponseBody> call = stationService.create(s);

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                if (response.errorBody() != null) {
                    Toasts.error(context, "error!");
                }

                if (response.isSuccessful() && response.body() != null) {
                    Toasts.success(context, "Station Created");
                    startActivity(nav.goToHome());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toasts.error(context, "error!");
            }

        });
    }


}