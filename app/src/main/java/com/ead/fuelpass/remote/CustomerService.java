package com.ead.fuelpass.remote;


import com.ead.fuelpass.model.Login;
import com.ead.fuelpass.model.Customer;
import com.ead.fuelpass.model.Reset;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Customer APIs
 */
public interface CustomerService {

    //customer login
    @POST("customer/login")
    Call<Customer> log(@Body Login log);

    //customer registration
    @POST("customer/register")
    Call<ResponseBody> createUser(@Body Customer cus);

    // customer password reset
    @PUT("customer/reset")
    Call<ResponseBody> resetPass(@Body Reset rs);

    //customer account delete
    @DELETE("customer/delete/{id}")
    Call<ResponseBody> deleteAccount(@Path("id") String id);
}
