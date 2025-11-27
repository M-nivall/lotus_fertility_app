package com.example.Varsani.Staff.Doctor;

import static com.example.Varsani.utils.Urls.URL_CHECKUPS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Doctor.Adapters.AdapterCheckup;
import com.example.Varsani.Staff.Doctor.Models.CheckupModel;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Checkups extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<CheckupModel> list;
    private AdapterCheckup adapter;
    private SessionHandler session;
    private UserModel user;
    private TextView txv_status;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkups);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setSubtitle("Student");

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        txv_status = findViewById(R.id.txv_status);


        txv_status.setVisibility(View.GONE);


        session = new SessionHandler(this);
        user = session.getUserDetails();

        list = new ArrayList<>();


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);

        getMyPackages();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getMyPackages() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHECKUPS,
                response -> {
                    try {

                        Log.e("RESPONSE ", response);
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("responseData");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsn = jsonArray.getJSONObject(i);
                                String checkupID = jsn.getString("checkupID");
                                String scheduleID = jsn.getString("scheduleID");
                                String surrogateID = jsn.getString("surrogateID");
                                String parentID = jsn.getString("parentID");
                                String surrogateName = jsn.getString("surrogateName");
                                String user = jsn.getString("user");
                                String first_check = jsn.getString("first_check");
                                String second_check = jsn.getString("second_check");
                                String delivery_check = jsn.getString("delivery_check");

                                CheckupModel model = new CheckupModel( checkupID,scheduleID,surrogateID,parentID,
                                        surrogateName,user,first_check,second_check,delivery_check);
                                list.add(model);
                            }
                            adapter = new AdapterCheckup(getApplicationContext(), list);
                            recyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);

                        } else if (status.equals("0")) {
                            String msg = jsonObject.getString("message");
                            progressBar.setVisibility(View.GONE);
                            txv_status.setVisibility(View.VISIBLE);

                            txv_status.setText(msg);
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            error.printStackTrace();
            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("instructorId", user.getClientID());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}