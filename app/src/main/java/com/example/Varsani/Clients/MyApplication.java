package com.example.Varsani.Clients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

public class MyApplication extends AppCompatActivity {

    private SessionHandler session;
    private UserModel user;
    private TextView tv_status_value,tv_medical_date_value;
    private LinearLayout layout_medical_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_application);
        getSupportActionBar().setSubtitle("My Application");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_status_value = findViewById(R.id.tv_status_value);
        tv_medical_date_value = findViewById(R.id.tv_medical_date_value);
        layout_medical_date = findViewById(R.id.layout_medical_date);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        layout_medical_date.setVisibility(View.GONE);


        myApplication();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void myApplication() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_MY_APPLICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");
                            if (status.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("orders");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsn = jsonArray.getJSONObject(i);
                                    String surrogateStatus = jsn.getString("surrogateStatus");
                                    String medicalDate = jsn.getString("medicalDate");

                                    tv_status_value.setText("Status: " + surrogateStatus);

                                    if (surrogateStatus.equalsIgnoreCase("Awaiting medical")) {
                                        layout_medical_date.setVisibility(View.VISIBLE);

                                        tv_medical_date_value.setText("Date: " + medicalDate);
                                    }
                                }
                            } else if (status.equals("0")) {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AbstractMethodError {
                Map<String, String> params = new HashMap<>();
                params.put("clientID", user.getClientID());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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
