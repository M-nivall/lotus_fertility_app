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
import com.example.Varsani.Clients.Adapters.AdapterCheckups;
import com.example.Varsani.Clients.Models.CheckupModal;
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

public class ScheduledCheckups extends AppCompatActivity {
    private List<CheckupModal> list;
    private AdapterCheckups adapterCheckups;

    private SessionHandler session;
    private UserModel user;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_checkups);

        getSupportActionBar().setSubtitle("Scheduled Checkup");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();


        list=new ArrayList<>();
        recyclerView.setLayoutManager( new LinearLayoutManager( getApplicationContext() ) );
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);

        checkup();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void checkup(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_SCHEDULED_CHECKUP,
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

                                    String checkID = jsn.getString("checkID");
                                    String scheduleID = jsn.getString("scheduleID");
                                    String parentID = jsn.getString("parentID");
                                    String first_check = jsn.getString("first_check");
                                    String second_check = jsn.getString("second_check");
                                    String delivery_check = jsn.getString("delivery_check");
                                    String check_status = jsn.getString("check_status");

                                    CheckupModal checkupModal = new CheckupModal(
                                            checkID,scheduleID,parentID,first_check,second_check,delivery_check,check_status
                                    );

                                    list.add(checkupModal);
                                }
                                adapterCheckups = new AdapterCheckups(getApplicationContext(), list);
                                recyclerView.setAdapter(adapterCheckups);
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