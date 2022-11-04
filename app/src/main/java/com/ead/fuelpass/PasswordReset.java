package com.ead.fuelpass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ead.fuelpass.database.DBHelper;
import com.ead.fuelpass.model.Reset;
import com.ead.fuelpass.nav.Navigation;
import com.ead.fuelpass.remote.CustomerService;
import com.ead.fuelpass.remote.OwnerService;
import com.ead.fuelpass.remote.RetrofitClient;
import com.ead.fuelpass.toast.Toasts;

import io.github.muddz.styleabletoast.StyleableToast;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Password reset
 */
public class PasswordReset extends AppCompatActivity {

    private CustomerService cusService;
    private OwnerService ownService;
    private DBHelper mydb;
    private Context context;
    private Navigation nav;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        mydb = new DBHelper(this);
        cusService = RetrofitClient.getClient().create(CustomerService.class);
        ownService = RetrofitClient.getClient().create(OwnerService.class);
        context = getApplicationContext();
        nav = new Navigation(context);

        TextView reg = findViewById(R.id.LoginURLR);
        reg.setOnClickListener(v -> startActivity(nav.login()));

        EditText passwordOldText = findViewById(R.id.OldPass);
        EditText passwordNewText = findViewById(R.id.NewPass);
        Button rst = findViewById(R.id.ResetBtn);

        rst.setOnClickListener(v -> {
            String password = passwordOldText.getText().toString().trim();
            String check = passwordNewText.getText().toString().trim();

            if (TextUtils.isEmpty(password)) {
                new StyleableToast
                        .Builder(getApplicationContext())
                        .text("Old Password Can Not Be Empty!")
                        .textColor(Color.WHITE)
                        .backgroundColor(Color.RED)
                        .show();
            } else if (TextUtils.isEmpty(check)) {
                new StyleableToast
                        .Builder(getApplicationContext())
                        .text("New Password  Can Not Be Empty!")
                        .textColor(Color.WHITE)
                        .backgroundColor(Color.RED)
                        .show();
            } else if (TextUtils.equals(password, check)) {
                new StyleableToast
                        .Builder(getApplicationContext())
                        .text("Password must not equal!")
                        .textColor(Color.WHITE)
                        .backgroundColor(Color.RED)
                        .show();
            } else if (check.length() < 6) {
                new StyleableToast
                        .Builder(getApplicationContext())
                        .text("New Password must contain more than 6 letters!")
                        .textColor(Color.WHITE)
                        .backgroundColor(Color.RED)
                        .show();
            } else {
                id = mydb.getId();
                Reset r = new Reset(id, password, check);
                ResetCusApi(r);

            }
        });


    }

    //reset customer password
    public void ResetCusApi(Reset r) {
        Call<ResponseBody> call = cusService.resetPass(r);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                if (response.errorBody() != null) {
                    ResetOwnApi(r);
                } else {
                    if (response.isSuccessful() && response.body() != null) {
                        Toasts.success(context, "password changed");
                        mydb.deleteUser(id);
                        startActivity(nav.login());
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                ResetOwnApi(r);
            }

        });
    }


    //reset owner password
    public void ResetOwnApi(Reset r) {
        Call<ResponseBody> call = ownService.resetPass(r);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.errorBody() != null) {
                    Toasts.error(context, "password could not change!");
                } else {
                    if (response.isSuccessful() && response.body() != null) {
                        Toasts.success(context, "password changed");
                        mydb.deleteUser(id);
                        startActivity(nav.login());
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toasts.error(context, "password could not change!");
            }

        });
    }

}