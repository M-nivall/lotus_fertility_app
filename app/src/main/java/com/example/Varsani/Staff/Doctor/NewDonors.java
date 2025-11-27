package com.example.Varsani.Staff.Doctor;

import static com.example.Varsani.utils.Urls.URL_NEW_DONORS;
import static com.example.Varsani.utils.Urls.URL_QUOTATION_REQUEST;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Doctor.Adapters.AdapterSurrogate;
import com.example.Varsani.Staff.Doctor.Models.SurrogateModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewDonors extends AppCompatActivity {

    private List<SurrogateModel> list;
    private AdapterSurrogate adapterSurrogate;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_donors);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("Service  Requests");
        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);

        list=new ArrayList<>();
        recyclerView.setLayoutManager( new LinearLayoutManager( getApplicationContext() ) );
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);


        newOrders();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void newOrders(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_NEW_DONORS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if(status.equals("1")){

                                JSONArray jsonArray=jsonObject.getJSONArray("details");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsn = jsonArray.getJSONObject(i);

                                    // Fetching data from JSON object
                                    String surrogateId = jsn.getString("surrogateId");
                                    String height = jsn.getString("height");
                                    String weight = jsn.getString("weight");
                                    String bloodType = jsn.getString("bloodType");
                                    String medication = jsn.getString("medication");
                                    String maritalStatus = jsn.getString("maritalStatus");
                                    String education = jsn.getString("education");
                                    String numChildren = jsn.getString("numchildren");
                                    String moreDetails = jsn.getString("moreDetails");
                                    String idImage = jsn.getString("idImage");
                                    String medicalImage = jsn.getString("medicalImage");
                                    String photoImage = jsn.getString("photoImage");
                                    String surrogateStatus = jsn.getString("surrogateStatus");
                                    String fullName = jsn.getString("fullName");
                                    String phoneNo = jsn.getString("phoneNo");
                                    String email = jsn.getString("email");
                                    String gender = jsn.getString("gender");
                                    String dateBirth = jsn.getString("dateBirth");
                                    String county = jsn.getString("county");
                                    String role = jsn.getString("role");

                                    // Creating an object of SurrogateModel (example name)
                                    SurrogateModel surrogateModel = new SurrogateModel(
                                            surrogateId, height, weight, bloodType, medication, maritalStatus,
                                            education, numChildren, moreDetails, idImage, medicalImage,
                                            photoImage, surrogateStatus, fullName, phoneNo, email,
                                            gender, dateBirth, county, role
                                    );

                                    // Adding the object to the list
                                    list.add(surrogateModel);
                                }

                                adapterSurrogate=new AdapterSurrogate(getApplicationContext(),list);
                                recyclerView.setAdapter(adapterSurrogate);
                                progressBar.setVisibility(View.GONE);


                            }else{
                                Toast toast=Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast=Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                            Log.e("ERROR E ", e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast=Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
                Log.e("ERROR E ", error.toString());
            }
        });
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