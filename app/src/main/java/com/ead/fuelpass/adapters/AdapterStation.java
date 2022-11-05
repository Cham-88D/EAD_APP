package com.ead.fuelpass.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTxt.setText(stationList.get(position).getName());
        holder.locationTxt.setText(stationList.get(position).getLocation());
        holder.btn.setOnClickListener(v ->holder.itemView.getContext().startActivity(saveStation(stationList.get(position).getShedId())));
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


    //save station id to db
    public Intent saveStation(String data)
    {

        if(!mydb.getStationId().equals(""))
        {
            mydb.deleteStation(mydb.getStationId());
            mydb.insertStation(data);
        }else{
            mydb.insertStation(data);
        }


        return nav.cusDash();
    }




}
