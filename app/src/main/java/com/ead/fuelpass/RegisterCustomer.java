package com.ead.fuelpass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ead.fuelpass.model.Customer;
import com.ead.fuelpass.nav.Navigation;
import com.ead.fuelpass.remote.CustomerService;
import com.ead.fuelpass.remote.RetrofitClient;
import com.ead.fuelpass.toast.Toasts;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Registration customer
 */
public class RegisterCustomer extends AppCompatActivity {

    private CustomerService cusService;
    private Context context;
    private Navigation nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_customer);

        cusService = RetrofitClient.getClient().create(CustomerService.class);
        context = getApplicationContext();
        nav = new Navigation(context);

        TextView reg = findViewById(R.id.logURLD);
        reg.setOnClickListener(v -> startActivity(nav.login()));

        EditText emailText = findViewById(R.id.RegEmail);
        EditText passwordText = findViewById(R.id.RegPass);
        EditText nicText = findViewById(R.id.RegNIC);
        EditText passCheckText = findViewById(R.id.RegPassChk);
        Button register = findViewById(R.id.RegBtn);

        register.setOnClickListener(v -> {
            String email = emailText.getText().toString().trim();
            String password = passwordText.getText().toString().trim();
            String nic = nicText.getText().toString().trim();
            String check = passCheckText.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toasts.error(context, "Email Can Not Be Empty!");
            } else if (TextUtils.isEmpty(password)) {
                Toasts.error(context, "Password Can Not Be Empty!");
            } else if (TextUtils.isEmpty(nic)) {
                Toasts.error(context, "NIC Can Not Be Empty!");
            } else if (TextUtils.isEmpty(check)) {
                Toasts.error(context, "Password Check Can Not Be Empty!");
            } else if (!TextUtils.equals(password, check)) {
                Toasts.error(context, "Password must equal!");
            } else if (password.length() < 6) {
                Toasts.error(context, "Password must contain more than 6 letters!");
            } else {
                Customer cus = new Customer(nic, email, password);
                regApi(cus);
            }
        });
    }


    //registration api call
    public void regApi(Customer cus) {
        Call<ResponseBody> call = cusService.createUser(cus);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                if (response.errorBody() != null) {
                    Toasts.error(context, "Registration failed");
                }

                if (response.isSuccessful() && response.body() != null) {
                    Toasts.success(context, "Account Created");
                    startActivity(nav.login());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toasts.error(context, "Registration failed");
            }
        });

    }

}