package com.example.Varsani.IntendedParent.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Clients.Adapters.AdapterInvoices;
import com.example.Varsani.Clients.InvoiceItems;
import com.example.Varsani.Clients.Models.OrdersModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.IntendedParent.BookingItems;
import com.example.Varsani.IntendedParent.Models.MyBookingModal;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterBookings extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MyBookingModal> items;

    private Context ctx;
    ProgressDialog progressDialog;
//    private OnItemClickListener mOnItemClickListener;
//    private OnMoreButtonClickListener onMoreButtonClickListener;

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

    public AdapterBookings(Context context, List<MyBookingModal> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_ID, txv_booking_date,txv_surrogate,txv_status;


        public OriginalViewHolder(View v) {
            super(v);

            txv_ID =v.findViewById(R.id.txv_ID);
            txv_booking_date = v.findViewById(R.id.txv_booking_date);
            txv_status = v.findViewById(R.id.txv_status);
            txv_surrogate = v.findViewById(R.id.txv_surrogate);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_items, parent, false);
        vh = new AdapterBookings.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterBookings.OriginalViewHolder) {
            final AdapterBookings.OriginalViewHolder view = (AdapterBookings.OriginalViewHolder) holder;

            final MyBookingModal o = items.get(position);

            view.txv_ID.setText("#ID: "+o.getScheduleId());
            view.txv_status.setText("Status: " + o.getScheduleStatus());
            view.txv_booking_date.setText("Booking Date: "+o.getBookingDate());
            if (o.getSchedule_type().equals("egg_donor")) {
                view.txv_surrogate.setText("Egg Donor: " + o.getFull_name());
            } else {
                view.txv_surrogate.setText("Surrogate: " + o.getFull_name());
            }

            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(ctx, BookingItems.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Passing values using getters
                    in.putExtra("schedule_id", o.getScheduleId());
                    in.putExtra("surrogate_id", o.getSurrogateId());
                    in.putExtra("partner_name", o.getPartnerName());
                    in.putExtra("partner_contact", o.getPartnerContact());
                    in.putExtra("schedule_date", o.getScheduleDate());
                    in.putExtra("booking_date", o.getBookingDate());
                    in.putExtra("service_fee", o.getServiceFee());
                    in.putExtra("surrogate_fee", o.getSurrogateFee());
                    in.putExtra("total_fee", o.getTotalFee());
                    in.putExtra("payment_code", o.getPaymentCode());
                    in.putExtra("schedule_status", o.getScheduleStatus());
                    in.putExtra("full_name", o.getFull_name());
                    in.putExtra("schedule_type", o.getSchedule_type());

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
