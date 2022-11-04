package com.ead.fuelpass.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageButton;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ead.fuelpass.R;
import com.ead.fuelpass.database.DBHelper;
import com.ead.fuelpass.model.StationData;
import com.ead.fuelpass.nav.Navigation;

import java.util.ArrayList;

/**
 * Adapter class for fuel station list
 */
public class AdapterO extends RecyclerView.Adapter<AdapterO.ViewHolder> {

    private final ArrayList<StationData> stationList;
    private final Context context;
    private Navigation nav;


    public AdapterO(ArrayList<StationData> stationList, Context context) {
        this.stationList = new ArrayList<>(stationList);
        this.context = context;
    }


    @NonNull
    @Override
    public AdapterO.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.owner_card, parent, false);
        nav = new Navigation(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterO.ViewHolder holder, int position) {

        holder.nameTxt.setText(stationList.get(position).getName());
        holder.locationTxt.setText(stationList.get(position).getLocation());
        holder.btn.setOnClickListener(v ->holder.itemView.getContext().startActivity(nav.ownDash(stationList.get(position).getShedId())));
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


}
