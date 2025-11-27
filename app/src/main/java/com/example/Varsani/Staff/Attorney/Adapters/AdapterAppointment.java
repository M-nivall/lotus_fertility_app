package com.example.Varsani.Staff.Attorney.Adapters;

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
import com.example.Varsani.Staff.Attorney.AppointmentItems;
import com.example.Varsani.Staff.Attorney.Model.AppointmentModel;
import com.example.Varsani.Staff.LabTech.Adapters.AdapterMedicalTest;
import com.example.Varsani.Staff.LabTech.MedicalItems;
import com.example.Varsani.Staff.LabTech.Models.MedicalTestModel;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterAppointment extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<AppointmentModel> items;

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

    public AdapterAppointment(Context context, List<AppointmentModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_Id,txv_parent, txv_surrogate,txv_schedule_date,txv_status;


        public OriginalViewHolder(View v) {
            super(v);

            txv_parent =v.findViewById(R.id.txv_parent);
            txv_surrogate = v.findViewById(R.id.txv_surrogate);
            txv_Id = v.findViewById(R.id.txv_Id);
            txv_schedule_date = v.findViewById(R.id.txv_schedule_date);
            txv_status = v.findViewById(R.id.txv_status);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_appoint_items, parent, false);
        vh = new AdapterAppointment.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterAppointment.OriginalViewHolder) {
            final AdapterAppointment.OriginalViewHolder view = (AdapterAppointment.OriginalViewHolder) holder;

            final AppointmentModel o= items.get(position);

            view.txv_Id.setText("#ID: " + o.getSurrogateId());
            view.txv_parent.setText("Intended Parent: " + o.getParentName());
            view.txv_schedule_date.setText("Scheduled On: " + o.getScheduleDate());
            view.txv_status.setText("Status: Pending");
            if (o.getSchedule_type().equals("egg_donor")) {
                view.txv_surrogate.setText("Egg Donor: " + o.getSurrogateName());
            } else {
                view.txv_surrogate.setText("surrogate: " + o.getSurrogateName());
            }
            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(ctx, AppointmentItems.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("Id", o.getId());
                    in.putExtra("scheduleId", o.getScheduleId());
                    in.putExtra("parentId", o.getParentId());
                    in.putExtra("surrogateId", o.getSurrogateId());
                    in.putExtra("partnerName", o.getPartnerName());
                    in.putExtra("partnerNo", o.getPartnerContact());
                    in.putExtra("scheduleDate", o.getScheduleDate());
                    in.putExtra("parentName", o.getParentName());
                    in.putExtra("parentNo", o.getParentPhone());
                    in.putExtra("parentEmail", o.getParentEmail());
                    in.putExtra("surrogateName", o.getSurrogateName());
                    in.putExtra("surrogateNo", o.getSurrogatePhone());
                    in.putExtra("surrogateEmail", o.getSurrogateEmail());
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

}
