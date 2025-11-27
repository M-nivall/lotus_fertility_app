package com.example.Varsani.Staff.Store_mrg.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Adapters.AdapterGetStock;
import com.example.Varsani.Staff.Models.GetStockModel;
import com.example.Varsani.utils.SessionHandler;

import java.util.ArrayList;
import java.util.List;

public class AdapterMedicineStock extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<GetStockModel> items;
    private List<GetStockModel> searchList;

    private Context ctx;
    ProgressDialog progressDialog;
//    private OnItemClickListener mOnItemClickListener;
//    private OnMoreButtonClickListener onMoreButtonClickListener;

    //

    private SessionHandler session;
    private UserModel user;
    private String clientId = "";
    private String orderID = "";

    public static final String TAG = " adapter";

//    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
//        this.mOnItemClickListener = mItemClickListener;
//    }
//
//    public void setOnMoreButtonClickListener(final OnMoreButtonClickListener onMoreButtonClickListener) {
//        this.onMoreButtonClickListener = onMoreButtonClickListener;
//    }

    public AdapterMedicineStock(Context context, List<GetStockModel> items) {
        this.items = items;
        this.searchList = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView txv_title,txv_stock, txv_price;


        public OriginalViewHolder(View v) {
            super(v);

            txv_title =v.findViewById(R.id.txv_title);
            txv_price = v.findViewById(R.id.txv_price);
            txv_stock = v.findViewById(R.id.txv_stock);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_stock, parent, false);
        vh = new AdapterMedicineStock.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof AdapterMedicineStock.OriginalViewHolder) {
            final AdapterMedicineStock.OriginalViewHolder view = (AdapterMedicineStock.OriginalViewHolder) holder;

            final GetStockModel o= items.get(position);

            view.txv_title.setText(o.getProductName());
            view.txv_price.setText("@ KSH "+o.getPrice());
            view.txv_stock.setText("Doses: "+o.getStock());

            view.txv_price.setVisibility(View.GONE);
     /*       view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(ctx, AddStock.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putExtra("stockID", o.getStockID());
                    in.putExtra("price", o.getPrice());
                    in.putExtra("productName", o.getProductName());
                    in.putExtra("stock", o.getStock());
                    ctx.startActivity(in);

                }
            });

      */
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    items = searchList;
                } else {

                    ArrayList<GetStockModel> filteredList = new ArrayList<>();

                    for (GetStockModel androidVersion : items) {

                        if (androidVersion.getProductName().toLowerCase().contains(charString)) {

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
                items = (ArrayList<GetStockModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
