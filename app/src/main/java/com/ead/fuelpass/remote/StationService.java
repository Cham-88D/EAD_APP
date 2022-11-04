package com.ead.fuelpass.remote;


import com.ead.fuelpass.model.Station;
import com.ead.fuelpass.model.StationData;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Station APIs
 */
public interface StationService {

    //get all Station list
    @GET("shed/findAll")
    Call<List<StationData>> getAll();

    //get station list by owner
    @GET("shed/findAllByMaster/{id}")
    Call<List<StationData>> getById(@Path("id") String id);

    //create station
    @POST("shed/create")
    Call<ResponseBody> create(@Body Station st);

}
