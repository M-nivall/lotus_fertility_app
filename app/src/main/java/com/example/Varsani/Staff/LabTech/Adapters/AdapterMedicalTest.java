package com.example.Varsani.Staff.LabTech.Adapters;

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
import com.example.Varsani.Staff.LabTech.MedicalItems;
import com.example.Varsani.Staff.LabTech.Models.MedicalTestModel;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterMedicalTest extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<MedicalTestModel> items;

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

    public AdapterMedicalTest(Context context, List<MedicalTestModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_Id,txv_name, txv_medical_date,txv_status;


        public OriginalViewHolder(View v) {
            super(v);

            txv_name =v.findViewById(R.id.txv_name);
            txv_medical_date = v.findViewById(R.id.txv_medical_date);
            txv_Id = v.findViewById(R.id.txv_Id);
            txv_status = v.findViewById(R.id.txv_status);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_medical_test, parent, false);
        vh = new AdapterMedicalTest.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterMedicalTest.OriginalViewHolder) {
            final AdapterMedicalTest.OriginalViewHolder view = (AdapterMedicalTest.OriginalViewHolder) holder;

            final MedicalTestModel o= items.get(position);

            if (o.getUser().equals("egg_donor")) {
                view.txv_Id.setText("Donor ID: " + o.getSurrogateId());
            } else {
                view.txv_Id.setText("Surrogate Id: " + o.getSurrogateId());
            }
            view.txv_medical_date.setText("Medical Date: " + o.getMedicalDate());
            view.txv_name.setText("Name: " + o.getFullName());
            view.txv_status.setText("Status: " + o.getScheduleStatus());
            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(ctx, MedicalItems.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("surrogateId", o.getSurrogateId());
                    in.putExtra("medicalDate", o.getMedicalDate());
                    in.putExtra("scheduleStatus", o.getScheduleStatus());
                    in.putExtra("bloodGroup", o.getBloodGroup());
                    in.putExtra("medication", o.getMedication());
                    in.putExtra("fullName", o.getFullName());
                    in.putExtra("phoneNo", o.getPhoneNo());
                    in.putExtra("email", o.getEmail());
                    in.putExtra("user", o.getUser());
                    in.putExtra("equipmentStatus", o.getEquipment_status());
                    in.putExtra("scheduleID", o.getScheduleID());

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
