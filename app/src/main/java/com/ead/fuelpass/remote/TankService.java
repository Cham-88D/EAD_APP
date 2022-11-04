package com.ead.fuelpass.remote;


import com.ead.fuelpass.model.Reset;
import com.ead.fuelpass.model.Tank;
import com.ead.fuelpass.model.TankData;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


/**
 * Tank service APIs
 */

public interface TankService {

    //create tank
    @POST("tank/create")
    Call<ResponseBody> createTank(@Body Tank tank);

    //get all tanks
    @GET("tank/findAll")
    Call<TankData> getAll();

    //get tank by id
    @GET("tank/findAllByShed/{id}")
    Call<List<TankData>> getTankById(@Path("id") String id);

    // update status
    @PUT("tank/status")
    Call<ResponseBody> stat(@Body Tank tank);


}
