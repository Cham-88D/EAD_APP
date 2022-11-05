package com.ead.fuelpass;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ead.fuelpass.cons.Constants;
import com.ead.fuelpass.database.DBHelper;
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
 * User account settings
 */
public class SettingsFragment extends Fragment {

    private Navigation nav;
    private  DBHelper mydb;
    private CustomerService cusService;
    private  Context context;
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();
        nav = new Navigation(context);
        mydb = new DBHelper(context);
        cusService = RetrofitClient.getClient().create(CustomerService.class);

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button buttonPass = (Button) rootView.findViewById(R.id.buttonPass);
        Button buttonDel = (Button) rootView.findViewById(R.id.buttonDel);
        Button buttonOut = (Button) rootView.findViewById(R.id.buttonOut);

        buttonPass.setOnClickListener(v -> startActivity(nav.goToReset()));
        buttonDel.setOnClickListener(v -> showAlert());
        buttonOut.setOnClickListener(v -> logOut());

        return rootView;
    }


    //log out
    public void logOut()
    {

        mydb.deleteQueue();
        if(mydb.deleteUser(mydb.getId()) !=null){
            startActivity(nav.login());
        }
    }


    //delete account
    public void deleteAccount()
    {

        Call<ResponseBody> call = cusService.deleteAccount(mydb.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.errorBody() != null) {
                    Toasts.error(context, "Could not delete!");
                } else {
                    if (response.isSuccessful() && response.body() != null) {
                        logOut();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toasts.error(context, "Could not delete!");
            }
        });
    }

    //display delete alert
    public void showAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm");
        builder.setMessage("Are you want to delete?");
        builder.setPositiveButton("YES", (dialog, which) -> deleteAccount());
        builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

}