package com.example.Varsani.Staff.Pharmacist;

import static com.example.Varsani.utils.Urls.URL_APPROVE_FERTILITY;
import static com.example.Varsani.utils.Urls.URL_CONFIRM_DISPENSE;
import static com.example.Varsani.utils.Urls.URL_GET_PARENT;
import static com.example.Varsani.utils.Urls.URL_GET_SURROGATE;
import static com.example.Varsani.utils.Urls.URL_REQUEST_EQUIPMENT;
import static com.example.Varsani.utils.Urls.URL_REQUEST_MEDICINE;
import static com.example.Varsani.utils.Urls.URL_UPLOAD_AGREEMENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Attorney.AppointmentItems;
import com.example.Varsani.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PrescriptionItems extends AppCompatActivity {
    private TextView tvParentName,tvParentNo,tvParentEmail,tvPartnerName,tvPartnerNumber,tvPartnerEmail,
            tvSurrogateName,tvSurrogateNumber,tvSurrogateEmail,txv_type,tvDoctorPrescription;
    private CardView cardView_medicine,cardView_prescription;
    private EditText edt_materials;
    private TextView tvPrescriptionDetails;
    private Button btnConfirmDispense,btn_request;
    private ProgressBar progressBar;
    private String scheduleId,parentId,surrogateId;
    private RequestQueue rQueue;
    Uri uri;
    String displayName = null;
    private String scheduleType;
    private CheckBox chk1,chk2,chk3,chk4,chk5,chk6,chk7,chk8,chk9,chk10;
    private String materials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_items);


        tvParentName = findViewById(R.id.tvParentName);
        tvParentNo = findViewById(R.id.tvParentNo);
        tvParentEmail = findViewById(R.id.tvParentEmail);
        tvPartnerName = findViewById(R.id.tvPartnerName);
        tvPartnerNumber = findViewById(R.id.tvPartnerNumber);
        tvPartnerEmail = findViewById(R.id.tvPartnerEmail);
        tvSurrogateName = findViewById(R.id.tvSurrogateName);
        tvSurrogateNumber = findViewById(R.id.tvSurrogateNumber);
        tvSurrogateEmail = findViewById(R.id.tvSurrogateEmail);
        tvPrescriptionDetails = findViewById(R.id.tvPrescriptionDetails);
        progressBar = findViewById(R.id.progressBar);
        btnConfirmDispense = findViewById(R.id.btnConfirmDispense);
        btn_request = findViewById(R.id.btn_request);
        txv_type = findViewById(R.id.txv_type);
        tvDoctorPrescription = findViewById(R.id.tvDoctorPrescription);
        edt_materials = findViewById(R.id.edt_materials);
        cardView_medicine = findViewById(R.id.cardView_medicine);
        cardView_prescription = findViewById(R.id.cardView_prescription);

        cardView_prescription.setVisibility(View.GONE);
        cardView_medicine.setVisibility(View.GONE);

        chk1 = (CheckBox) findViewById(R.id.checkBox);
        chk2 = (CheckBox) findViewById(R.id.checkBox2);
        chk3 = (CheckBox) findViewById(R.id.checkBox3);
        chk4 = (CheckBox) findViewById(R.id.checkBox4);
        chk5 = (CheckBox) findViewById(R.id.checkBox5);
        chk6 = (CheckBox) findViewById(R.id.checkBox6);

        Intent intent = getIntent();
        scheduleId = intent.getStringExtra("scheduleId");
        parentId = intent.getStringExtra("parentId");
        surrogateId = intent.getStringExtra("surrogateId");
        String partnerName = intent.getStringExtra("partnerName");
        String partnerNo = intent.getStringExtra("partnerNo");
        String scheduleDate = intent.getStringExtra("scheduleDate");
        String prescription = intent.getStringExtra("prescription");
        scheduleType = intent.getStringExtra("scheduleType");
        String medicineStatus = intent.getStringExtra("medicineStatus");

        tvPartnerNumber.setText("Phone No: " + partnerNo);
        tvPartnerName.setText("Name: " + partnerName);
        tvPrescriptionDetails.setText(prescription);

        if (scheduleType.equals("egg_donor")) {
            txv_type.setText("Egg Donor Details");
        }
        if (medicineStatus.equals("Pending")) {
            cardView_medicine.setVisibility(View.VISIBLE);
            tvDoctorPrescription.setText(prescription);
        }
        if (medicineStatus.equals("Approved")) {
            cardView_prescription.setVisibility(View.VISIBLE);
        }

        parentDetails();
        surrogateDetails();

        StringBuilder result=new StringBuilder();
        StringBuilder materials=new StringBuilder();

        chk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk1.isChecked()) {
                    if (materials.length() > 0) {
                        materials.append(", ");
                    }
                    materials.append("Ibuprofen");
                    edt_materials.setText(materials);
                }
            }
        });

        chk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk2.isChecked()) {
                    if (materials.length() > 0) {
                        materials.append(", ");
                    }
                    materials.append("Paracetamol");
                    edt_materials.setText(materials);
                }
            }
        });

        chk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk3.isChecked()) {
                    if (materials.length() > 0) {
                        materials.append(", ");
                    }
                    materials.append("Naproxen");
                    edt_materials.setText(materials);
                }
            }
        });

        chk4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk4.isChecked()) {
                    if (materials.length() > 0) {
                        materials.append(", ");
                    }
                    materials.append("Acetaminophen");
                    edt_materials.setText(materials);
                }
            }
        });

        chk5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk5.isChecked()) {
                    if (materials.length() > 0) {
                        materials.append(", ");
                    }
                    materials.append("Fluoxetine");
                    edt_materials.setText(materials);
                }
            }
        });
        chk6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk6.isChecked()) {
                    if (materials.length() > 0) {
                        materials.append(", ");
                    }
                    materials.append("Estrogen");
                    edt_materials.setText(materials);
                }
            }
        });

        btnConfirmDispense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlert(v);
            }
        });
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertMaterial(v);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void surrogateDetails(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_GET_SURROGATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("details");
                                for(int i=0;i <jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String surrogateName=jsn.getString("full_name");
                                    String surrogateNo=jsn.getString("phone_no");
                                    String surrogateEmail=jsn.getString("email");

                                    tvSurrogateName.setText("Name: " + surrogateName);
                                    tvSurrogateNumber.setText("Phone No: " + surrogateNo);
                                    tvSurrogateEmail.setText("Email: " + surrogateEmail);
                                }
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0,250);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast= Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0,250);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("surrogateId",surrogateId);
                params.put("role",scheduleType);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void parentDetails(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_GET_PARENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("details");
                                for(int i=0;i <jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String parentName=jsn.getString("full_name");
                                    String parentNo=jsn.getString("phone_no");
                                    String parentEmail=jsn.getString("email");

                                    tvParentName.setText("Name: " + parentName);
                                    tvParentNo.setText("Phone No: " + parentNo);
                                    tvParentEmail.setText("Email: " + parentEmail);
                                }
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0,250);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast= Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0,250);
                toast.show();
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("parentId",parentId);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void approve(){
        progressBar.setVisibility(View.VISIBLE);
        btnConfirmDispense.setVisibility(View.GONE);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_CONFIRM_DISPENSE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")){

                                Toast toast= Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                finish();
                            }else{

                                Toast toast= Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                progressBar.setVisibility(View.GONE);
                                btnConfirmDispense.setVisibility(View.VISIBLE);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();

                            progressBar.setVisibility(View.GONE);
                            btnConfirmDispense.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast= Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
                progressBar.setVisibility(View.GONE);
                btnConfirmDispense.setVisibility(View.VISIBLE);
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("scheduleId",scheduleId);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void request(){
        //progressBar1.setVisibility(View.VISIBLE);
        btn_request.setVisibility(View.GONE);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_REQUEST_MEDICINE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")) {

                                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 250);
                                toast.show();
                                finish();
                            }
                            else{

                                Toast toast= Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                //progressBar1.setVisibility(View.GONE);
                                btn_request.setVisibility(View.VISIBLE);
                            }


                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();

                            //progressBar1.setVisibility(View.GONE);
                            btn_request.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast= Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,250);
                toast.show();
                //progressBar1.setVisibility(View.GONE);
                btn_request.setVisibility(View.VISIBLE);
            }
        }){
            @Override
            protected Map<String,String>getParams()throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("surrogateId",surrogateId);
                params.put("materials",materials);
                params.put("scheduleId",scheduleId);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void getAlert(View v){
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Confirm to approve");
        builder.setNegativeButton("Close",null);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                approve();

                return;
            }
        });
        builder.show();

    }
    public void alertMaterial(View v){
        materials= edt_materials.getText().toString();

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Request Medicine Now!!!");
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                request();


                return;
            }
        });
        builder.show();

    }

}