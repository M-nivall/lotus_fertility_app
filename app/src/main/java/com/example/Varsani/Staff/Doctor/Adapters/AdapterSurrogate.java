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
import com.example.Varsani.Staff.Doctor.Models.SurrogateModel;
import com.example.Varsani.Staff.Doctor.SurrogateDetails;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterSurrogate extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SurrogateModel> items;

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

    public AdapterSurrogate(Context context, List<SurrogateModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_name, txv_tell,txv_email,txv_Status;


        public OriginalViewHolder(View v) {
            super(v);

            txv_name =v.findViewById(R.id.txv_name);
            txv_tell =v.findViewById(R.id.txv_tell);
            txv_email = v.findViewById(R.id.txv_email);
            txv_Status = v.findViewById(R.id.txv_Status);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_new_surrogate, parent, false);
        vh = new AdapterSurrogate.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterSurrogate.OriginalViewHolder) {
            final AdapterSurrogate.OriginalViewHolder view = (AdapterSurrogate.OriginalViewHolder) holder;

            final SurrogateModel o= items.get(position);

            view.txv_name.setText("Client: "+o.getFullName());
            view.txv_tell.setText("Phone No "+o.getPhoneNo());
            view.txv_email.setText("Email: " + o.getEmail());
            view.txv_Status.setText("Status: " + o.getSurrogateStatus());



            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in = new Intent(ctx, SurrogateDetails.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Pass data using the getter methods
                    in.putExtra("surrogateId", o.getSurrogateId());
                    in.putExtra("height", o.getHeight());
                    in.putExtra("weight", o.getWeight());
                    in.putExtra("bloodType", o.getBloodType());
                    in.putExtra("medication", o.getMedication());
                    in.putExtra("maritalStatus", o.getMaritalStatus());
                    in.putExtra("education", o.getEducation());
                    in.putExtra("numChildren", o.getNumChildren());
                    in.putExtra("moreDetails", o.getMoreDetails());
                    in.putExtra("idImage", o.getIdImage());
                    in.putExtra("medicalImage", o.getMedicalImage());
                    in.putExtra("photoImage", o.getPhotoImage());
                    in.putExtra("surrogateStatus", o.getSurrogateStatus());
                    in.putExtra("fullName", o.getFullName());
                    in.putExtra("phoneNo", o.getPhoneNo());
                    in.putExtra("email", o.getEmail());
                    in.putExtra("gender", o.getGender());
                    in.putExtra("dateBirth", o.getDateBirth());
                    in.putExtra("county", o.getCounty());
                    in.putExtra("role", o.getRole());

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
