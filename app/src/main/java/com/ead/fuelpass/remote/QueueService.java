package com.ead.fuelpass.remote;

import com.ead.fuelpass.model.Queue;
import com.ead.fuelpass.model.QueueCount;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface QueueService {

    @POST("queue/create")
    Call<Queue> joinQueue(@Body Queue q);


    @PUT("queue/update")
    Call<Queue> updateQueue(@Body Queue q);


    @POST("queue/count")
    Call<QueueCount> queueCount(@Body Queue q);
}
