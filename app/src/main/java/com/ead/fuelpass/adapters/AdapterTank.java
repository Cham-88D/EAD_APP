package com.ead.fuelpass.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ead.fuelpass.R;
import com.ead.fuelpass.model.Tank;
import com.ead.fuelpass.nav.Navigation;
import com.ead.fuelpass.remote.RetrofitClient;
import com.ead.fuelpass.remote.TankService;
import com.ead.fuelpass.toast.Toasts;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Tank list adapter
 */
public class AdapterTank extends RecyclerView.Adapter<AdapterTank.ViewHolder>  {


    private final ArrayList<Tank> tankList;
    private final Context context;
    private TankService tankService;
    private Navigation nav;

    public AdapterTank(ArrayList<Tank> tankList, Context context) {
        this.tankList = new ArrayList<>(tankList);
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        tankService = RetrofitClient.getClient().create(TankService.class);
        View view = LayoutInflater.from(context).inflate(R.layout.tank_card, parent, false);
        nav = new Navigation(context);
        return new AdapterTank.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.typeTxt.setText(tankList.get(position).getFuelType());
        boolean st = tankList.get(position).isStatus();

        if(st)
        {
            holder.statusTxt.setText("Available");
            holder.btn.setText("Over");
            tankList.get(position).setStatus(false);
        }
        else
        {
            holder.statusTxt.setText("Not Available");
            holder.btn.setText("Available");
            tankList.get(position).setStatus(true);
        }
        holder.btn.setOnClickListener(v ->update(tankList.get(position),holder));


    }

    @Override
    public int getItemCount() {
        return tankList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView typeTxt;
        TextView statusTxt;
        Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            typeTxt = itemView.findViewById(R.id.fuelTypeT);
            statusTxt = itemView.findViewById(R.id.statusT);
            btn = itemView.findViewById(R.id.changeT);

        }
    }


    //update api call
    public void update(Tank t,ViewHolder h)
    {
        Call<ResponseBody> call = tankService.stat(t);

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                if (response.errorBody() != null) {
                    Toasts.error(context, "error!");
                }

                if (response.isSuccessful() && response.body() != null) {
                    Toasts.success(context, "updated");
                    h.itemView.getContext().startActivity(nav.ownDashS());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toasts.error(context, "error!");
            }

        });
    }

}
