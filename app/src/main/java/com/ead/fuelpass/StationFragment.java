package com.ead.fuelpass;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ead.fuelpass.adapters.AdapterO;
import com.ead.fuelpass.adapters.AdapterStation;
import com.ead.fuelpass.database.DBHelper;
import com.ead.fuelpass.model.Queue;
import com.ead.fuelpass.model.QueueCount;
import com.ead.fuelpass.model.QueueData;
import com.ead.fuelpass.model.StationData;
import com.ead.fuelpass.model.TankData;
import com.ead.fuelpass.remote.QueueService;
import com.ead.fuelpass.remote.RetrofitClient;
import com.ead.fuelpass.remote.StationService;
import com.ead.fuelpass.remote.TankService;
import com.ead.fuelpass.toast.Toasts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Display station list for customer
 */
public class StationFragment extends Fragment {

    private RecyclerView recyclerView;
    private DBHelper mydb;
    private Context context;
    private StationService service;
    private ArrayList<StationData> data;
    private TankService serviceX;
    private QueueService serviceQ;

    public StationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        data = new ArrayList<>();
        context = getActivity();
        mydb = new DBHelper(context);
        service = RetrofitClient.getClient().create(StationService.class);
        serviceX = RetrofitClient.getClient().create(TankService.class);
        serviceQ = RetrofitClient.getClient().create(QueueService.class);
        return inflater.inflate(R.layout.fragment_station, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(!mydb.getStationId().equals(""))
        {
            mydb.deleteStation(mydb.getStationId());

        }
        getData(view);

    }

    //get Station data
    public void getData(View view) {

        Call<List<StationData>> call = service.getAll();
        call.enqueue(new Callback<List<StationData>>() {

            @Override
            public void onResponse(@NonNull Call<List<StationData>> call, @NonNull Response<List<StationData>> response) {

                if (response.errorBody() != null) {
                    Toasts.error(context, "no data!");
                }

                if (response.isSuccessful() && response.body() != null) {
                    data.addAll(response.body());
                    setX(response.body());
                    recyclerView = view.findViewById(R.id.recycler_view3);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setHasFixedSize(true);
                    AdapterStation myAdapter = new AdapterStation(data,getContext());
                    recyclerView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<StationData>> call, @NonNull Throwable t) {
                Toasts.error(context, "no data!");
            }

        });
    }


    //set station data
    public void setX(List<StationData> X)
    {

        for(StationData d:X)
        {
            getDataQueue(d.getShedId());
        }


    }



    //get Tank data
    public void getDataQueue(String id) {

        Call<List<TankData>> call = serviceX.getTankById(id);
        call.enqueue(new Callback<List<TankData>>() {

            @Override
            public void onResponse(@NonNull Call<List<TankData>> call, @NonNull Response<List<TankData>> response) {
                if (response.errorBody() != null) {
                    Toasts.error(context, "no data!");
                }

                if (response.isSuccessful() ) {
                    for (TankData d : response.body()) {
                        queueData(d);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<List<TankData>> call, @NonNull Throwable t) {
                Toasts.error(context, "no data!");
            }

        });
    }


    //get and set Queue Data
    public void queueData(TankData d) {


        Call<QueueCount> call = serviceQ.queueCount(new Queue("d",d.getTankId(), mydb.getId(), "joined"));
        call.enqueue(new Callback<QueueCount>() {
            @Override
            public void onResponse(Call<QueueCount> call, Response<QueueCount> response) {


                if (response.errorBody() != null) {

                    String status2;
                    if (d.isStatus()) {
                        status2 = "Available";

                    } else {
                        status2 = "Not Available";
                    }


                    QueueData qd = new QueueData("",d.getTankId(),d.getShed().getShedId(), status2,"", d.getFuelType(), "", 0);
                    mydb.insertQueue(qd);

                }

                if (response.isSuccessful() && response.body() != null) {
                    String status2;
                    if (d.isStatus()) {
                        status2 = "Available";

                    } else {
                        status2 = "Not Available";
                    }




                    QueueData qd = new QueueData(response.body().getId(),d.getTankId(), d.getShed().getShedId(), status2,"joined", d.getFuelType(), response.body().getTime(), response.body().getCount());
                    mydb.insertQueue(qd);
                }
            }

            @Override
            public void onFailure(Call<QueueCount> call, Throwable t) {
                Toasts.error(context, "no data!");
            }
        });


    }















}