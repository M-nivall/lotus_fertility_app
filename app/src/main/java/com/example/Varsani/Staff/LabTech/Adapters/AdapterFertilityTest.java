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
import com.example.Varsani.Staff.LabTech.FertilityItems;
import com.example.Varsani.Staff.LabTech.MedicalItems;
import com.example.Varsani.Staff.LabTech.Models.FertilityTestModel;
import com.example.Varsani.Staff.LabTech.Models.MedicalTestModel;
import com.example.Varsani.utils.SessionHandler;

import java.util.List;

public class AdapterFertilityTest extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<FertilityTestModel> items;

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

    public AdapterFertilityTest(Context context, List<FertilityTestModel> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_Id,txv_test, txv_status;


        public OriginalViewHolder(View v) {
            super(v);

            txv_test =v.findViewById(R.id.txv_test);
            txv_status = v.findViewById(R.id.txv_status);
            txv_Id = v.findViewById(R.id.txv_Id);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_fertility_test, parent, false);
        vh = new AdapterFertilityTest.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterFertilityTest.OriginalViewHolder) {
            final AdapterFertilityTest.OriginalViewHolder view = (AdapterFertilityTest.OriginalViewHolder) holder;

            final FertilityTestModel o= items.get(position);

            view.txv_Id.setText("#ID: " + o.getTestId());
            view.txv_status.setText("Status: " + o.getTestStatus());
            view.txv_test.setText("Test: Fertility Test");
            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(ctx, FertilityItems.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("testID", o.getTestId());
                    in.putExtra("scheduleId", o.getScheduleId());
                    in.putExtra("surrogateId", o.getSurrogateId());
                    in.putExtra("parentId", o.getParentId());
                    in.putExtra("testStatus", o.getTestStatus());
                    in.putExtra("scheduleType", o.getSchedule_type());
                    in.putExtra("equipmentStatus", o.getEquipment_status());

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
