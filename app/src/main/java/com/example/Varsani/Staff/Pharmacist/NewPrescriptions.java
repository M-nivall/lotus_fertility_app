package com.example.Varsani.Staff.Pharmacist;

import static com.example.Varsani.utils.Urls.URL_NEW_APPOINTMENTS;
import static com.example.Varsani.utils.Urls.URL_NEW_PRESCRIPTION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Attorney.Adapters.AdapterAppointment;
import com.example.Varsani.Staff.Attorney.Model.AppointmentModel;
import com.example.Varsani.Staff.Pharmacist.Adapter.AdapterPrescription;
import com.example.Varsani.Staff.Pharmacist.Model.PrescriptionModel;
import com.example.Varsani.utils.SessionHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewPrescriptions extends AppCompatActivity {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private List<PrescriptionModel> list;
    private AdapterPrescription adapterPrescription;
    private SessionHandler session;
    private UserModel user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_prescriptions);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Assigned Medical Test");
        progressBar=findViewById(R.id.progressBar);
        recyclerView=findViewById(R.id.recyclerView);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        list=new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        newAppointments();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void newAppointments() {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_NEW_PRESCRIPTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if(status.equals("1")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("details");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsn = jsonArray.getJSONObject(i);

                                    // Fetch all values from JSON
                                    String scheduleId = jsn.getString("schedule_id");
                                    String parentId = jsn.getString("parent_id");
                                    String surrogateId = jsn.getString("surrogate_id");
                                    String partnerName = jsn.getString("partner_name");
                                    String partnerContact = jsn.getString("partner_contact");
                                    String scheduleDate = jsn.getString("schedule_date");
                                    String prescription = jsn.getString("prescription");
                                    String schedule_type = jsn.getString("schedule_type");
                                    String medicine_status = jsn.getString("medicine_status");

                                    // Create AppointmentModel object
                                    PrescriptionModel prescriptionModel = new PrescriptionModel(
                                            scheduleId, parentId, surrogateId, partnerName, partnerContact,
                                            scheduleDate,prescription, schedule_type,medicine_status
                                    );

                                    list.add(prescriptionModel);
                                }

                                progressBar.setVisibility(View.GONE);
                                adapterPrescription = new AdapterPrescription(getApplicationContext(), list);
                                recyclerView.setAdapter(adapterPrescription);
                            } else{
                                Toast toast=Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                            }

                        }catch(Exception e){
                            e.printStackTrace();
                            Toast toast=Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast=Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("staffID",user.getClientID());
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