package com.example.Varsani.IntendedParent;

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
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.IntendedParent.Adapters.AdapterBookings;
import com.example.Varsani.IntendedParent.Models.MyBookingModal;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBookings extends AppCompatActivity {
    private List<MyBookingModal> list;
    private AdapterBookings adapterBookings;

    private SessionHandler session;
    private UserModel user;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);
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
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_MY_BOOKINGS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("orders");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsn = jsonArray.getJSONObject(i);

                                    String schedule_id = jsn.getString("schedule_id");
                                    String surrogate_id = jsn.getString("surrogate_id");
                                    String partner_name = jsn.getString("partner_name");
                                    String partner_contact = jsn.getString("partner_contact");
                                    String schedule_date = jsn.getString("schedule_date");
                                    String booking_date = jsn.getString("booking_date");
                                    String service_fee = jsn.getString("service_fee");
                                    String surrogate_fee = jsn.getString("surrogate_fee");
                                    String total_fee = jsn.getString("total_fee");
                                    String payment_code = jsn.getString("payment_code");
                                    String schedule_status = jsn.getString("schedule_status");
                                    String full_name = jsn.getString("full_name");
                                    String schedule_type = jsn.getString("schedule_type");

                                    // Create MyBookingModal object with correct parameters
                                    MyBookingModal myBookingModal = new MyBookingModal(
                                            schedule_id, surrogate_id, partner_name, partner_contact,
                                            schedule_date, booking_date, service_fee, surrogate_fee,
                                            total_fee, payment_code, schedule_status, full_name, schedule_type
                                    );

                                    list.add(myBookingModal);
                                }
                                adapterBookings = new AdapterBookings(getApplicationContext(), list);
                                recyclerView.setAdapter(adapterBookings);
                                progressBar.setVisibility(View.GONE);
                            }
                            else if(status.equals("0")){
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