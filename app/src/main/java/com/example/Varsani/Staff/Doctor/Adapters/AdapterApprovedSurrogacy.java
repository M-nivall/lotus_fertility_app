package com.example.Varsani.Staff.Doctor.Adapters;

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
import com.example.Varsani.Staff.Doctor.ConductSurrogacy;
import com.example.Varsani.Staff.Doctor.Models.PendingSurrogacyModel;
import com.example.Varsani.Staff.Doctor.Models.SurrogacyModel;
import com.example.Varsani.Staff.Doctor.SurrogacyDetails;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterApprovedSurrogacy extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<PendingSurrogacyModel> items;

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

    public AdapterApprovedSurrogacy(Context context, List<PendingSurrogacyModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_parent_name, txv_surrogate_name,txv_schedule_date,txv_Status;


        public OriginalViewHolder(View v) {
            super(v);

            txv_parent_name =v.findViewById(R.id.txv_parent_name);
            txv_surrogate_name =v.findViewById(R.id.txv_surrogate_name);
            txv_schedule_date = v.findViewById(R.id.txv_schedule_date);
            txv_Status = v.findViewById(R.id.txv_Status);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_approved_items, parent, false);
        vh = new AdapterApprovedSurrogacy.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterApprovedSurrogacy.OriginalViewHolder) {
            final AdapterApprovedSurrogacy.OriginalViewHolder view = (AdapterApprovedSurrogacy.OriginalViewHolder) holder;

            final PendingSurrogacyModel o= items.get(position);

            view.txv_parent_name.setText("Intended Parent: "+o.getParentName());
            view.txv_schedule_date.setText("Scheduled On: " + o.getScheduleDate());
            view.txv_Status.setText("Medical Test: Approved");
            if (o.getSchedule_type().equals("egg_donor")) {
                view.txv_surrogate_name.setText("Egg Donor: "+o.getSurrogateName());
            } else {
                view.txv_surrogate_name.setText("Surrogate Selected: "+o.getSurrogateName());
            }



            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in = new Intent(ctx, ConductSurrogacy.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Pass data using the getter methods
                    in.putExtra("schedule_id", o.getScheduleId());
                    in.putExtra("parent_id", o.getParentId());
                    in.putExtra("surrogate_id", o.getSurrogateId());
                    in.putExtra("partner_name", o.getPartnerName());
                    in.putExtra("partner_contact", o.getPartnerContact());
                    in.putExtra("schedule_date", o.getScheduleDate());
                    in.putExtra("booking_date", o.getBookingDate());
                    in.putExtra("parent_name", o.getParentName());
                    in.putExtra("parent_phone", o.getParentPhone());
                    in.putExtra("parent_email", o.getParentEmail());
                    in.putExtra("surrogate_name", o.getSurrogateName());
                    in.putExtra("surrogate_phone", o.getSurrogatePhone());
                    in.putExtra("surrogate_email", o.getSurrogateEmail());
                    in.putExtra("schedule_type", o.getSchedule_type());

                    // Start the activity
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
