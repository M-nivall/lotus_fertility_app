package com.example.Varsani.Donor.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Adapters.AdapterServices;
import com.example.Varsani.Clients.Adapters.AdapterSurrogates;
import com.example.Varsani.Clients.Models.ProductModal;
import com.example.Varsani.Clients.Models.ServicesModal;
import com.example.Varsani.Clients.Models.SurrogateModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.Clients.SingleItem;
import com.example.Varsani.Clients.SingleService;
import com.example.Varsani.Donor.Models.DonorModal;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterDonors extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<DonorModal> items;
    private List<DonorModal> searchView;

    private Context ctx;
    ProgressDialog progressDialog;
    private AdapterDonors.OnItemClickListener mOnItemClickListener;
    private AdapterDonors.OnMoreButtonClickListener onMoreButtonClickListener;

    private SessionHandler session;
    private UserModel user;
    private String clientId = "";
    private String surrogateID = "";
    private int count = 1;

    private EditText quantity;
    public static final String TAG = "Item_adapter";

    public void setOnItemClickListener(final AdapterDonors.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void setOnMoreButtonClickListener(final AdapterDonors.OnMoreButtonClickListener onMoreButtonClickListener) {
        this.onMoreButtonClickListener = onMoreButtonClickListener;
    }

    public AdapterDonors(Context context, List<DonorModal> items) {
        this.items = items;
        this.searchView = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_surrogate;
        public TextView tv_full_name, tv_location, tv_age;
        public TextView tv_price;
        private Button btn_select_surrogate;


        public OriginalViewHolder(View v) {
            super(v);
            image_surrogate = v.findViewById( R.id.image_surrogate);
            tv_full_name =v.findViewById(R.id.tv_full_name);
            tv_location =v.findViewById(R.id.tv_location);
            tv_price = v.findViewById(R.id.tv_price);
            tv_age = v.findViewById(R.id.tv_age);
            btn_select_surrogate = v.findViewById(R.id.btn_select_surrogate);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_products, parent, false);
        vh = new AdapterDonors.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterDonors.OriginalViewHolder) {
            final AdapterDonors.OriginalViewHolder view = (AdapterDonors.OriginalViewHolder) holder;

            final DonorModal p = items.get(position);
            String url = Urls.ROOT_URL_UPLOADS;
            Picasso.get()
                    .load(url+p.getPhotoUrl())
                    .fit()
                    .centerCrop()
                    .into(view.image_surrogate);
            view.tv_full_name.setText(p.getFullName());
            view.tv_location.setText("Location: "+p.getTown() + ", "+p.getCounty());
            view.tv_age.setText("Age: " + p.getAge() + " Years");
            view.tv_price.setText("Fee: "+p.getFee() + " /=");
            session = new SessionHandler(ctx);
            user = session.getUserDetails();
            try{
                clientId= user.getClientID();
            }catch (RuntimeException e){

            }
//            view.add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    count = count+1;
//                    Log.e("count", "count "+ count);
//                    view.num.setText( Integer.toString(count));
//                }
//            });
//            view.minus.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(count>1){
//                        count = count-1;
//                        view.num.setText( Integer.toString(count));
//                    }
//                }
//            });
            view.btn_select_surrogate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (user.getUser_type().equals("Intended Parents")){

                        surrogateID = p.getDonorId();
                        String fullName=p.getFullName();
                        String price=p.getFee();
                        String image=p.getPhotoUrl();
                        String height=p.getHeight();
                        String bloodType=p.getBloodType();
                        String weight=p.getWeight();
                        String education=p.getEducation();
                        String town=p.getTown();
                        String county=p.getCounty();
                        String age=p.getAge();
                        String role=p.getRole();
                        Intent in = new Intent(ctx, SingleItem.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.putExtra("surrogateID", surrogateID);
                        in.putExtra("fullName", fullName);
                        in.putExtra("price", price);
                        in.putExtra("image", image);
                        in.putExtra("height", height);
                        in.putExtra("bloodType", bloodType);
                        in.putExtra("weight", weight);
                        in.putExtra("education", education);
                        in.putExtra("town", town);
                        in.putExtra("county", county);
                        in.putExtra("age", age);
                        in.putExtra("role", role);
                        ctx.startActivity(in);

                    }else {

                        Toast.makeText( ctx,"Please login as a Intended Parent to Continue",Toast.LENGTH_SHORT ).show();

                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, DonorModal obj, int pos);
    }

    public interface OnMoreButtonClickListener {
        void onItemClick(View view, DonorModal obj, MenuItem item);
    }
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    items = searchView;
                } else {

                    ArrayList<DonorModal> filteredList = new ArrayList<>();

                    for (DonorModal androidVersion : items) {

                        if (androidVersion.getFullName().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    items = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = items;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                items = (ArrayList<DonorModal>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
