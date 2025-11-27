package com.example.Varsani.Clients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Adapters.AdapterInvoices;
import com.example.Varsani.IntendedParent.Models.InvoiceModal;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.example.Varsani.Clients.Adapters.AdapterOrders;
import com.example.Varsani.Clients.Models.OrdersModal;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Invoice extends AppCompatActivity {

    private List<InvoiceModal> list;
    private AdapterInvoices adapterInvoices;

    private SessionHandler session;
    private UserModel user;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        getSupportActionBar().setSubtitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();


        list=new ArrayList<>();
        recyclerView.setLayoutManager( new LinearLayoutManager( getApplicationContext() ) );
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);

        orders();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void orders(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_GET_INVOICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("orders");
                                for(int i=0; i <jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String schedule_id=jsn.getString("schedule_id");
                                    String schedule_date=jsn.getString("schedule_date");
                                    String booking_date=jsn.getString("booking_date");
                                    String service_fee=jsn.getString("service_fee");
                                    String surrogate_fee=jsn.getString("surrogate_fee");
                                    String total_fee=jsn.getString("total_fee");
                                    String schedule_type=jsn.getString("schedule_type");
                                    String parent_name=jsn.getString("parent_name");
                                    String surrogate_name=jsn.getString("surrogate_name");
                                    String payment_status=jsn.getString("payment_status");

                                    InvoiceModal invoiceModal=new InvoiceModal(schedule_id,schedule_date,booking_date,
                                            service_fee,surrogate_fee,total_fee,schedule_type,parent_name,
                                            surrogate_name,payment_status);
                                    list.add(invoiceModal);
                                }
                                adapterInvoices=new AdapterInvoices(getApplicationContext(),list);
                                recyclerView.setAdapter(adapterInvoices);
                                progressBar.setVisibility(View.GONE);
                            }else if(status.equals("0")){
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams()throws  AbstractMethodError{
                Map<String,String> params=new HashMap<>();
                params.put("clientID",user.getClientID());
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
