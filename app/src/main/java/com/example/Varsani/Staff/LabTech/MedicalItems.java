package com.example.Varsani.Staff.LabTech;

import static com.example.Varsani.utils.Urls.URL_APPROVE_MEDICAL;
import static com.example.Varsani.utils.Urls.URL_MARK_ORDER;
import static com.example.Varsani.utils.Urls.URL_REQUEST_EQUIPMENTS;
import static com.example.Varsani.utils.Urls.URL_SEND_QUOTATION;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MedicalItems extends AppCompatActivity {

    private TextView tvSurrogateId, tvMedicalDate, tvBloodGroup, tvMedication,
            tvFullName, tvPhoneNo, tvEmail,txv_type;
    private CardView cardView_equipments,cardView_results;
    private EditText etTestDescription,edt_materials;
    private Button btnApprove,btnReject,btn_request_equipment;
    private RadioGroup rgMedicalRequirements;
    private RadioButton rbYes, rbNo;
    private ProgressBar progressBar;
    private String surrogateId,user,scheduleID;
    private CheckBox chk1,chk2,chk3,chk4,chk5,chk6,chk7,chk8,chk9,chk10;
    private String materials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_items);

        // Initialize TextViews
        txv_type = findViewById(R.id.txv_type);
        tvSurrogateId = findViewById(R.id.tvSurrogateId);
        tvMedicalDate = findViewById(R.id.tvMedicalDate);
        tvBloodGroup = findViewById(R.id.tvBloodGroup);
        tvMedication = findViewById(R.id.tvMedication);
        tvFullName = findViewById(R.id.tvFullName);
        tvPhoneNo = findViewById(R.id.tvPhoneNo);
        tvEmail = findViewById(R.id.tvEmail);
        etTestDescription = findViewById(R.id.etTestDescription);
        edt_materials = findViewById(R.id.edt_materials);
        btnReject = findViewById(R.id.btnReject);
        btnApprove = findViewById(R.id.btnApprove);
        btn_request_equipment = findViewById(R.id.btn_request_equipment);
        rgMedicalRequirements = findViewById(R.id.rgMedicalRequirements);
        rbYes = findViewById(R.id.rbYes);
        rbNo = findViewById(R.id.rbNo);
        progressBar = findViewById(R.id.progressBar);
        cardView_equipments = findViewById(R.id.cardView_equipments);
        cardView_results = findViewById(R.id.cardView_results);

        chk1 = (CheckBox) findViewById(R.id.checkBox);
        chk2 = (CheckBox) findViewById(R.id.checkBox2);
        chk3 = (CheckBox) findViewById(R.id.checkBox3);
        chk4 = (CheckBox) findViewById(R.id.checkBox4);
        chk5 = (CheckBox) findViewById(R.id.checkBox5);
        chk6 = (CheckBox) findViewById(R.id.checkBox6);

        cardView_equipments.setVisibility(View.GONE);
        cardView_results.setVisibility(View.GONE);

        // Retrieve data from Intent
        surrogateId = getIntent().getStringExtra("surrogateId");
        String medicalDate = getIntent().getStringExtra("medicalDate");
        String scheduleStatus = getIntent().getStringExtra("scheduleStatus");
        String bloodGroup = getIntent().getStringExtra("bloodGroup");
        String medication = getIntent().getStringExtra("medication");
        String fullName = getIntent().getStringExtra("fullName");
        String phoneNo = getIntent().getStringExtra("phoneNo");
        String email = getIntent().getStringExtra("email");
        user = getIntent().getStringExtra("user");
        String equipmentStatus = getIntent().getStringExtra("equipmentStatus");
        scheduleID = getIntent().getStringExtra("scheduleID");

        // Set data to TextViews
        tvSurrogateId.setText("#ID: " + surrogateId);
        tvMedicalDate.setText("Medical Date: " + medicalDate);
        tvBloodGroup.setText("Blood Group: " + bloodGroup);
        tvMedication.setText("Medical Condition: " + medication);
        tvFullName.setText("Name: " + fullName);
        tvPhoneNo.setText("Phone No: " + phoneNo);
        tvEmail.setText("Email: " + email);

        if (user.equals("egg_donor")) {
            txv_type.setText("Egg Donor Details");
        }
        if (equipmentStatus.equals("Pending")) {
            cardView_equipments.setVisibility(View.VISIBLE);
        }
        if (equipmentStatus.equals("Approved")) {
            cardView_results.setVisibility(View.VISIBLE);
        }

        rgMedicalRequirements.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbNo) {
                    btnReject.setVisibility(View.VISIBLE);
                    btnApprove.setVisibility(View.GONE);
                } else if (checkedId == R.id.rbYes) {
                    btnReject.setVisibility(View.GONE);
                    btnApprove.setVisibility(View.VISIBLE);
                }
            }
        });

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlert(v);
            }
        });

        StringBuilder result=new StringBuilder();
        StringBuilder materials=new StringBuilder();

        chk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk1.isChecked()) {
                    if (materials.length() > 0) {
                        materials.append(", ");
                    }
                    materials.append("Centrifuge");
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
                    materials.append("UltraSound Gel");
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
                    materials.append("Test Kit");
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
                    materials.append("Vacutainer");
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
                    materials.append("Stereomicroscope");
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
                    materials.append("Crossmatching");
                    edt_materials.setText(materials);
                }
            }
        });

        btn_request_equipment.setOnClickListener(new View.OnClickListener() {
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
    public void approve(){
        progressBar.setVisibility(View.VISIBLE);
        btnApprove.setVisibility(View.GONE);
        final String testDescription=etTestDescription.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_APPROVE_MEDICAL,
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
                                btnApprove.setVisibility(View.VISIBLE);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();

                            progressBar.setVisibility(View.GONE);
                            btnApprove.setVisibility(View.VISIBLE);
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
                btnApprove.setVisibility(View.VISIBLE);
            }
        }){
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("surrogateId",surrogateId);
                params.put("testDescription",testDescription);
                params.put("role",user);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void request(){
        //progressBar1.setVisibility(View.VISIBLE);
        btn_request_equipment.setVisibility(View.GONE);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_REQUEST_EQUIPMENTS,
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
                                btn_request_equipment.setVisibility(View.VISIBLE);
                            }


                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();

                            //progressBar1.setVisibility(View.GONE);
                            btn_request_equipment.setVisibility(View.VISIBLE);
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
                btn_request_equipment.setVisibility(View.VISIBLE);
            }
        }){
            @Override
            protected Map<String,String>getParams()throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("surrogateId",surrogateId);
                params.put("materials",materials);
                params.put("scheduleID",scheduleID);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void getAlert(View v){
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Confirm to approve surrogate ");
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
        builder.setTitle("Request Equipments Now!!!");
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
