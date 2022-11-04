package com.ead.fuelpass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.ead.fuelpass.database.DBHelper;
import com.ead.fuelpass.model.Station;
import com.ead.fuelpass.model.Tank;
import com.ead.fuelpass.nav.Navigation;
import com.ead.fuelpass.remote.RetrofitClient;
import com.ead.fuelpass.remote.StationService;
import com.ead.fuelpass.remote.TankService;
import com.ead.fuelpass.toast.Toasts;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTank extends AppCompatActivity {

    private TankService tankService;
    private Context context;
    private Navigation nav;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tank);


        tankService = RetrofitClient.getClient().create(TankService.class);
        context = getApplicationContext();
        nav = new Navigation(context);
        mydb = new DBHelper(this);

        EditText typeText = findViewById(R.id.tankType);
        Button create = findViewById(R.id.createTankBtn);
        Button back = findViewById(R.id.tankBack);

        create.setOnClickListener(v -> {

            String type = typeText.getText().toString().trim();

            if (TextUtils.isEmpty(type)) {
                Toasts.error(context, "Fuel Type Can Not Be Empty!");

            } else {
                Tank tk = new Tank(type, true, mydb.getStationId());
                tankCreator(tk);
            }
        });


        back.setOnClickListener(v -> startActivity(nav.goToDashOw()));

    }

    //Create tank
    public void tankCreator(Tank t) {
        Call<ResponseBody> call = tankService.createTank(t);

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                if (response.errorBody() != null) {
                    Toasts.error(context, "error!");
                }

                if (response.isSuccessful() && response.body() != null) {
                    Toasts.success(context, "Tank Created");
                    startActivity(nav.goToDashOw());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toasts.error(context, "error!");
            }

        });
    }


}