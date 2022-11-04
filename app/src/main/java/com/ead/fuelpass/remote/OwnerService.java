package com.ead.fuelpass.remote;


import com.ead.fuelpass.model.Login;
import com.ead.fuelpass.model.Owner;
import com.ead.fuelpass.model.Reset;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface OwnerService {

    //owner login
    @POST("shedmaster/login")
    Call<Owner> log(@Body Login log);

    //owner registartion
    @POST("shedmaster/register")
    Call<ResponseBody> createUser(@Body Owner ow);

    //owner password reset
    @PUT("shedmaster/reset")
    Call<ResponseBody> resetPass(@Body Reset rs);

}
