package com.ead.fuelpass.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.ead.fuelpass.R;
import com.ead.fuelpass.database.DBHelper;
import com.ead.fuelpass.model.Queue;
import com.ead.fuelpass.model.QueueCount;
import com.ead.fuelpass.model.QueueData;
import com.ead.fuelpass.model.StationData;
import com.ead.fuelpass.model.TankData;
import com.ead.fuelpass.nav.Navigation;
import com.ead.fuelpass.remote.QueueService;
import com.ead.fuelpass.remote.RetrofitClient;
import com.ead.fuelpass.remote.TankService;
import com.ead.fuelpass.toast.Toasts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Customer station list adapter
 */
public class AdapterStation extends RecyclerView.Adapter<AdapterStation.ViewHolder> {

    private final ArrayList<StationData> stationList;
    private final Context context;
    private Navigation nav;
    private DBHelper mydb;
    private TankService service;
    private QueueService serviceQ;


    public AdapterStation(ArrayList<StationData> stationList, Context context) {
        this.stationList = stationList;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.owner_card, parent, false);
        mydb = new DBHelper(context);
        nav = new Navigation(context);
        service = RetrofitClient.getClient().create(TankService.class);
        serviceQ = RetrofitClient.getClient().create(QueueService.class);


        for(StationData d:this.stationList)
        {
            getData(d.getShedId());
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(!mydb.getTankId().equals(""))
        {
            holder.itemView.getContext().startActivity(nav.cusDash(mydb.getTankId()));
        }
        holder.nameTxt.setText(stationList.get(position).getName());
        holder.locationTxt.setText(stationList.get(position).getLocation());
        holder.btn.setOnClickListener(v ->holder.itemView.getContext().startActivity(nav.cusDash(stationList.get(position).getShedId())));
    }

    @Override
    public int getItemCount() {
        return this.stationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt;
        TextView locationTxt;
        AppCompatImageButton btn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.stationName);
            locationTxt = itemView.findViewById(R.id.stationLoc);
            btn = itemView.findViewById(R.id.addA);

        }
    }


    //get Tank data
    public void getData(String id) {

        Call<List<TankData>> call = service.getTankById(id);
        call.enqueue(new Callback<List<TankData>>() {

            @Override
            public void onResponse(@NonNull Call<List<TankData>> call, @NonNull Response<List<TankData>> response) {
                if (response.errorBody() != null) {
                    Toasts.error(context, "no data!");
                }

                if (response.isSuccessful() && response.body() != null) {
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


    //get Queue Data
    public void queueData(TankData d) {

        String status;
        if (d.isStatus()) {
            status = "Available";
        } else {
            status = "Not Available";
            ;
        }
        Call<QueueCount> call = serviceQ.queueCount(new Queue(d.getTankId(), mydb.getId(), status));
        call.enqueue(new Callback<QueueCount>() {
            @Override
            public void onResponse(Call<QueueCount> call, Response<QueueCount> response) {


                if (response.errorBody() != null) {

                    String status2;
                    if (d.isStatus()) {
                        status2 = "Available";

                    } else {
                        status2 = "Not Available";
                        ;
                    }


                    QueueData qd = new QueueData(d.getTankId(),d.getShed().getShedId(), status2, d.getFuelType(), "", 0);
                    mydb.insertQueue(qd);

                }

                if (response.isSuccessful() && response.body() != null) {
                    String status2;
                    if (d.isStatus()) {
                        status2 = "Available";

                    } else {
                        status2 = "Not Available";
                        ;
                    }

                    QueueData qd = new QueueData(d.getTankId(), d.getShed().getShedId(), status2, d.getFuelType(), response.body().getTime(), response.body().getCount());
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
