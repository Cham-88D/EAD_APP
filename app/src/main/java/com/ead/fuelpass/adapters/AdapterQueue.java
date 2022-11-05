package com.ead.fuelpass.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ead.fuelpass.R;
import com.ead.fuelpass.database.DBHelper;
import com.ead.fuelpass.model.Queue;
import com.ead.fuelpass.model.Queue2;
import com.ead.fuelpass.model.QueueCount;
import com.ead.fuelpass.model.QueueData;
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
 * Queue view adapter
 */
public class
AdapterQueue extends RecyclerView.Adapter<AdapterQueue.ViewHolder>{

    private final ArrayList<QueueData> queryList;
    private final Context context;
    private QueueService service;
    private Navigation nav;
    private DBHelper mydb;


    public AdapterQueue(ArrayList<QueueData> queryList, Context context) {
        this.context = context;
        this.queryList = queryList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        service = RetrofitClient.getClient().create(QueueService.class);
        mydb = new DBHelper(context);
        View view = LayoutInflater.from(context).inflate(R.layout.queue_card, parent, false);
        nav = new Navigation(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.typeTxt.setText(queryList.get(position).getType());
       holder.statusTxt.setText(queryList.get(position).getStatus());
       holder.countTxt.setText(String.valueOf(queryList.get(position).getCount()));

        if(!queryList.get(position).getStatus().equals("Available"))
        {
            holder.btn.setEnabled(false);
            holder.btn.setText("Can't join");
            holder.btn2.setVisibility(View.INVISIBLE);
        }
        if(!mydb.getTankId().equals(""))
        {
            holder.btn.setEnabled(false);
            holder.btn.setText("Joined");
            holder.btn2.setVisibility(View.INVISIBLE);
        }

        if(queryList.get(position).getTank_id().equals(mydb.getTankId()))
        {
            holder.btn2.setVisibility(View.VISIBLE);
            holder.btn.setVisibility(View.INVISIBLE);
            holder.btn2.setText("leave");
        }


        holder.btn.setOnClickListener(v->update(queryList.get(position)));
        //holder.btn2.setOnClickListener(v->remove(queryList.get(position)));
    }

    @Override
    public int getItemCount() {
        return   this.queryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView  typeTxt;
        TextView statusTxt;
        TextView countTxt;
        Button btn;
        Button btn2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            typeTxt = itemView.findViewById(R.id.fuelTypeC);
            statusTxt = itemView.findViewById(R.id.statusC);
            countTxt = itemView.findViewById(R.id.countC);
            btn = itemView.findViewById(R.id.changeC);
            btn2 = itemView.findViewById(R.id.changeC2);
        }
    }



    //get queue data
   public void update(QueueData t) {
        if(t.getCount()==0)
        {
            Queue q = new Queue(t.getTank_id(),mydb.getId(),t.getStatus());
            Call<Queue2> call = service.joinQueue(q);
            call.enqueue(new Callback<Queue2>() {

                @Override
                public void onResponse(Call<Queue2> call, Response<Queue2> response) {
                    mydb.insertTank(response.body().getTank().getTankId());
                    Intent myIntent =   nav.homeCustomer();
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(myIntent);
                }

                @Override
                public void onFailure(Call<Queue2> call, Throwable t) {
                    Toasts.error(context, "error!");
                }

            });
        }


       if(t.getCount()!=0)
       {
           Queue q = new Queue(t.getTank_id(),mydb.getId(),t.getStatus());
           Call<Queue2> call = service.updateQueue(q);
           call.enqueue(new Callback<Queue2>() {

               @Override
               public void onResponse(Call<Queue2> call, Response<Queue2> response) {
                   Intent myIntent =   nav.homeCustomer();
                   myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(myIntent);
               }

               @Override
               public void onFailure(Call<Queue2> call, Throwable t) {
                   Toasts.error(context, "error!");
               }

           });
       }



    }

}
