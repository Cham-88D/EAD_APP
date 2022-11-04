package com.ead.fuelpass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ead.fuelpass.cons.Constants;
import com.ead.fuelpass.database.DBHelper;
import com.ead.fuelpass.model.Customer;
import com.ead.fuelpass.model.Login;
import com.ead.fuelpass.model.Owner;
import com.ead.fuelpass.nav.Navigation;
import com.ead.fuelpass.remote.CustomerService;
import com.ead.fuelpass.remote.OwnerService;
import com.ead.fuelpass.remote.RetrofitClient;
import com.ead.fuelpass.toast.Toasts;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Login
 */
public class LoginActivity extends AppCompatActivity {

    private CustomerService cusService;
    private OwnerService ownService;
    private DBHelper mydb;
    private Context context;
    private Navigation nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        mydb = new DBHelper(this);
        cusService = RetrofitClient.getClient().create(CustomerService.class);
        ownService = RetrofitClient.getClient().create(OwnerService.class);
        context = getApplicationContext();
        nav = new Navigation(context);

        TextView reg = findViewById(R.id.RegURL);
        reg.setOnClickListener(v ->  startActivity(nav.registration()));

        EditText emailText = findViewById(R.id.LoginEmail);
        EditText passwordText = findViewById(R.id.LoginPass);
        Button login = findViewById(R.id.LoginBtn);


        login.setOnClickListener(v -> {
            String email = emailText.getText().toString().trim();
            String password = passwordText.getText().toString().trim();
            Login lg;
            if (TextUtils.isEmpty(email)) {
                Toasts.error(context, "Email Can Not Be Empty!");
            } else if (TextUtils.isEmpty(password)) {
                Toasts.error(context, "Password Can Not Be Empty!");
            } else {
                lg = new Login(email, password);
                loginCustomerApi(lg);
            }

        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(nav.redirectLogged()!=null)
        {
            startActivity(nav.redirectLogged());
        }
    }

    // customer login api call
    public void loginCustomerApi(Login lg) {
        Call<Customer> call = cusService.log(lg);
        call.enqueue(new Callback<Customer>() {

            @Override
            public void onResponse(@NonNull Call<Customer> call, @NonNull Response<Customer> response) {

                if (response.errorBody() != null) {
                    loginOwnerApi(lg);
                } else {
                    if (response.isSuccessful() && response.body() != null) {
                        mydb.insertUser(response.body().getId(), Constants.USER_CUSTOMER,response.body().getEmail());
                        startActivity(nav.homeCustomer());
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<Customer> call, @NonNull Throwable t) {
                loginOwnerApi(lg);
            }

        });
    }

    // owner login api call
    public void loginOwnerApi(Login lg) {
        Call<Owner> call = ownService.log(lg);
        call.enqueue(new Callback<Owner>() {

            @Override
            public void onResponse(@NonNull Call<Owner> call, @NonNull Response<Owner> response) {

                if (response.errorBody() != null) {
                    Toasts.error(context, "User not found!");
                }

                if (response.isSuccessful() && response.body() != null) {
                    mydb.insertUser(response.body().getId(), Constants.USER_OWNER,response.body().getEmail());
                    startActivity(nav.homeOwner());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Owner> call, @NonNull Throwable t) {
                Toasts.error(context, "User not found!");
            }

        });
    }

}