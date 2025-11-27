package com.example.Varsani.Staff.Doctor.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.R;
import com.example.Varsani.Staff.Doctor.FirstCheckup;
import com.example.Varsani.Staff.Doctor.Models.CheckupModel;
import com.example.Varsani.Staff.Doctor.SelectAction;

import java.util.List;

public class AdapterCheckup extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<CheckupModel> items;
    private Context ctx;
    private String packageId;

    public AdapterCheckup(Context context, List<CheckupModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        private TextView txv_Id, txv_name;
        public OriginalViewHolder(View v) {
            super(v);
            txv_Id = v.findViewById(R.id.txv_Id);
            txv_name = v.findViewById(R.id.txv_name);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_check_items, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final CheckupModel data = items.get(position);

            view.txv_Id.setText("ID#: " + data.getScheduleID());
            view.txv_name.setText("Name: " + data.getSurrogateName());


            //if(data.getUnitStatus().equals("Approved")){
            //   view.img_thumb_up.setVisibility(View.VISIBLE);
            //}
            view.itemView.setOnClickListener(v -> {
                //if (data.getUnitStatus().equals("Approved"))
                //{
                Intent in = new Intent(ctx, FirstCheckup.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("checkID", data.getCheckupID());
                in.putExtra("scheduleID", data.getScheduleID());
                in.putExtra("surrogateID", data.getSurrogateID());
                in.putExtra("parentID", data.getParentID());
                in.putExtra("surrogateName", data.getSurrogateName());
                in.putExtra("userType", data.getUser());
                in.putExtra("first_check", data.getFirst_check());
                in.putExtra("second_check", data.getSecond_check());
                in.putExtra("delivery_check", data.getDelivery_check());
                ctx.startActivity(in);
                //}
                //else {
                //   Toast.makeText(ctx, "Wait for approval", Toast.LENGTH_SHORT).show();
                //}
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
