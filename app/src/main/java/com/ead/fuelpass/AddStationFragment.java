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
import com.ead.fuelpass.database.DBHelper;
import com.ead.fuelpass.model.StationData;
import com.ead.fuelpass.remote.RetrofitClient;
import com.ead.fuelpass.remote.StationService;
import com.ead.fuelpass.toast.Toasts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Owner fuel station list
 */
public class AddStationFragment extends Fragment {

    private  RecyclerView recyclerView;
    private DBHelper mydb;
    private Context context;
    private StationService service;
    private  ArrayList<StationData> data;

    public AddStationFragment() {
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
        return inflater.inflate(R.layout.fragment_add_station, container, false);
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

        Call<List<StationData>> call = service.getById(mydb.getId());
        call.enqueue(new Callback<List<StationData>>() {

            @Override
            public void onResponse(@NonNull Call<List<StationData>> call, @NonNull Response<List<StationData>> response) {

                if (response.errorBody() != null) {
                    Toasts.error(context, "no data!");
                }

                if (response.isSuccessful() && response.body() != null) {
                    data.addAll(response.body());
                    recyclerView = view.findViewById(R.id.recycler_view1);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setHasFixedSize(true);
                    AdapterO myAdapter = new AdapterO(data,getContext());
                    recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<StationData>> call, @NonNull Throwable t) {
                Toasts.error(context, "no data!");
            }

        });
    }

}