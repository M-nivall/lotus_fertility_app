package com.example.Varsani.Staff.Pharmacist.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Attorney.Adapters.AdapterAppointment;
import com.example.Varsani.Staff.Attorney.AppointmentItems;
import com.example.Varsani.Staff.Attorney.Model.AppointmentModel;
import com.example.Varsani.Staff.Pharmacist.Model.PrescriptionModel;
import com.example.Varsani.Staff.Pharmacist.PrescriptionItems;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterPrescription extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<PrescriptionModel> items;

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

    public AdapterPrescription(Context context, List<PrescriptionModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_Id,txv_schedule_date,txv_status;


        public OriginalViewHolder(View v) {
            super(v);

            txv_Id = v.findViewById(R.id.txv_Id);
            txv_schedule_date = v.findViewById(R.id.txv_schedule_date);
            txv_status = v.findViewById(R.id.txv_status);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_prescription_items, parent, false);
        vh = new AdapterPrescription.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterPrescription.OriginalViewHolder) {
            final AdapterPrescription.OriginalViewHolder view = (AdapterPrescription.OriginalViewHolder) holder;

            final PrescriptionModel o= items.get(position);

            view.txv_Id.setText("#ID: " + o.getScheduleId());
            view.txv_schedule_date.setText("Date: " + o.getScheduleDate());
            view.txv_status.setText("Status: Pending");
            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(ctx, PrescriptionItems.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("scheduleId", o.getScheduleId());
                    in.putExtra("parentId", o.getParentId());
                    in.putExtra("surrogateId", o.getSurrogateId());
                    in.putExtra("partnerName", o.getPartnerName());
                    in.putExtra("partnerNo", o.getPartnerContact());
                    in.putExtra("scheduleDate", o.getScheduleDate());
                    in.putExtra("prescription", o.getPrescription());
                    in.putExtra("scheduleType", o.getSchedule_type());
                    in.putExtra("medicineStatus", o.getMedicine_status());

                    ctx.startActivity(in);


                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
