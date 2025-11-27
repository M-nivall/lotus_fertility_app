package com.example.Varsani.Staff.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Staff.Finance.Model.SurrogateBookingModel;
import com.example.Varsani.Staff.Finance.OrderDetails;
import com.example.Varsani.Staff.Models.ClientOrderModel;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

import java.util.List;

public class AdapterClientOrders extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SurrogateBookingModel> items;

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

    public AdapterClientOrders(Context context, List<SurrogateBookingModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_ID,txv_intended_parent, txv_amount,txv_payment_code;
        public TextView txv_payment_status;


        public OriginalViewHolder(View v) {
            super(v);

            txv_payment_status =v.findViewById(R.id.txv_payment_status);
            txv_intended_parent =v.findViewById(R.id.txv_intended_parent);
            txv_ID =v.findViewById(R.id.txv_ID);
            txv_amount = v.findViewById(R.id.txv_amount);
            txv_payment_code = v.findViewById(R.id.txv_payment_code);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_client_orders, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            final OriginalViewHolder view = (OriginalViewHolder) holder;

            final SurrogateBookingModel o= items.get(position);

            view.txv_ID.setText("#ID: "+o.getScheduleId());
            view.txv_intended_parent.setText("Intended Parent: " + o.getParentName());
            view.txv_amount.setText("Amount: "+o.getTotalFee());
            view.txv_payment_status.setText("Status: "+o.getPaymentStatus());
            view.txv_payment_code.setText("Payment Code: "+o.getPaymentCode());


            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in = new Intent(ctx, OrderDetails.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Pass all values from the model
                    in.putExtra("scheduleId", o.getScheduleId());
                    in.putExtra("parentId", o.getParentId());
                    in.putExtra("surrogateId", o.getSurrogateId());
                    in.putExtra("partnerName", o.getPartnerName());
                    in.putExtra("scheduleDate", o.getScheduleDate());
                    in.putExtra("bookingDate", o.getBookingDate());
                    in.putExtra("serviceFee", o.getServiceFee());
                    in.putExtra("surrogateFee", o.getSurrogateFee());
                    in.putExtra("totalFee", o.getTotalFee());
                    in.putExtra("paymentCode", o.getPaymentCode());
                    in.putExtra("parentName", o.getParentName());
                    in.putExtra("parentPhone", o.getParentPhone());
                    in.putExtra("parentEmail", o.getParentEmail());
                    in.putExtra("surrogateName", o.getSurrogateName());
                    in.putExtra("surrogatePhone", o.getSurrogatePhone());
                    in.putExtra("surrogateEmail", o.getSurrogateEmail());
                    in.putExtra("paymentStatus", o.getPaymentStatus());
                    in.putExtra("scheduleType", o.getSchedule_type());

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