package com.example.Varsani.Clients.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Clients.CheckupItems;
import com.example.Varsani.Clients.Models.CheckupModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.IntendedParent.Adapters.AdapterBookings;
import com.example.Varsani.IntendedParent.BookingItems;
import com.example.Varsani.IntendedParent.Models.MyBookingModal;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterCheckups extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<CheckupModal> items;

    private Context ctx;
    ProgressDialog progressDialog;
//    private OnItemClickListener mOnItemClickListener;
//    private OnMoreButtonClickListener onMoreButtonClickListener;

    //

    private SessionHandler session;
    private UserModel user;
    private String clientId = "";
    private String orderID = "";

    public static final String TAG = "Orders adapter";

//    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
//        this.mOnItemClickListener = mItemClickListener;
//    }
//
//    public void setOnMoreButtonClickListener(final OnMoreButtonClickListener onMoreButtonClickListener) {
//        this.onMoreButtonClickListener = onMoreButtonClickListener;
//    }

    public AdapterCheckups(Context context, List<CheckupModal> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_ID,txv_status;


        public OriginalViewHolder(View v) {
            super(v);

            txv_ID =v.findViewById(R.id.txv_ID);
            txv_status = v.findViewById(R.id.txv_status);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_check_items, parent, false);
        vh = new AdapterCheckups.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterCheckups.OriginalViewHolder) {
            final AdapterCheckups.OriginalViewHolder view = (AdapterCheckups.OriginalViewHolder) holder;

            final CheckupModal o = items.get(position);

            view.txv_ID.setText("#ID: "+o.getScheduleID());
            view.txv_status.setText("Status: " + o.getCheck_status());


            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(ctx, CheckupItems.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Passing values using getters
                    in.putExtra("checkID", o.getCheckID());
                    in.putExtra("scheduleID", o.getScheduleID());
                    in.putExtra("parentID", o.getParentID());
                    in.putExtra("firstCheck", o.getFirst_check());
                    in.putExtra("secondCheck", o.getSecond_check());
                    in.putExtra("deliveryCheck", o.getDelivery_check());
                    in.putExtra("checkStatus", o.getCheck_status());

                    ctx.startActivity(in);
                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

//    public interface OnItemClickListener {
//        void onItemClick(View view, ProductModal obj, int pos);
//    }
//
//    public interface OnMoreButtonClickListener {
//        void onItemClick(View view, ProductModal obj, MenuItem item);
//    }
}
