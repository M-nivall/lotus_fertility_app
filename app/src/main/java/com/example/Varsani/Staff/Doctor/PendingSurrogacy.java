package com.example.Varsani.Staff.Doctor;

import static com.example.Varsani.utils.Urls.URL_APPROVED_SURROGACY;
import static com.example.Varsani.utils.Urls.URL_PENDING_SURROGACY;

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
import com.example.Varsani.Staff.Doctor.Adapters.AdapterApprovedSurrogacy;
import com.example.Varsani.Staff.Doctor.Adapters.AdapterSurrogacy;
import com.example.Varsani.Staff.Doctor.Models.PendingSurrogacyModel;
import com.example.Varsani.Staff.Doctor.Models.SurrogacyModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PendingSurrogacy extends AppCompatActivity {
    private List<PendingSurrogacyModel> list;
    private AdapterApprovedSurrogacy adapterApprovedSurrogacy;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_surrogacy);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("Surrogacy Appointments");
        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);

        list=new ArrayList<>();
        recyclerView.setLayoutManager( new LinearLayoutManager( getApplicationContext() ) );
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);


        approvedSurrogacy();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void approvedSurrogacy(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_PENDING_SURROGACY,
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
                                    String schedule_id = jsn.getString("schedule_id");
                                    String parent_id = jsn.getString("parent_id");
                                    String surrogate_id = jsn.getString("surrogate_id");
                                    String partner_name = jsn.getString("partner_name");
                                    String partner_contact = jsn.getString("partner_contact");
                                    String schedule_date = jsn.getString("schedule_date");
                                    String booking_date = jsn.getString("booking_date");
                                    String parent_name = jsn.getString("parent_name");
                                    String parent_phone = jsn.getString("parent_phone");
                                    String parent_email = jsn.getString("parent_email");
                                    String surrogate_name = jsn.getString("surrogate_name");
                                    String surrogate_phone = jsn.getString("surrogate_phone");
                                    String surrogate_email = jsn.getString("surrogate_email");
                                    String schedule_type = jsn.getString("schedule_type");

                                    // Creating an object of SurrogacyModel
                                    PendingSurrogacyModel pendingSurrogacyModel = new PendingSurrogacyModel(
                                            schedule_id, parent_id, surrogate_id, partner_name, partner_contact,
                                            schedule_date, booking_date, parent_name, parent_phone, parent_email,
                                            surrogate_name, surrogate_phone, surrogate_email,schedule_type
                                    );

                                    // Adding the object to the list
                                    list.add(pendingSurrogacyModel);
                                }


                                adapterApprovedSurrogacy=new AdapterApprovedSurrogacy(getApplicationContext(),list);
                                recyclerView.setAdapter(adapterApprovedSurrogacy);
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