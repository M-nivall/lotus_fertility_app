package com.example.Varsani.Staff.Doctor;

import static com.example.Varsani.utils.Urls.URL_ASSIGN_LAB_TECH;
import static com.example.Varsani.utils.Urls.URL_ASSIGN_MEDICAL_TEST;
import static com.example.Varsani.utils.Urls.URL_GET_LAB_TECH;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SurrogacyDetails extends AppCompatActivity {
    private TextView tvParentName,tvParentNo,tvParentEmail,tvPartnerName,tvPartnerNumber,tvSurrogateName,
            tvSurrogateNumber,tvSurrogateEmail,tvID,tvScheduleDate,tvStatus,txv_type;
    private EditText et_labTechnician;
    private Button btnDetails,btn_assign;
    private ArrayList<String> drivers;
    private ArrayList<String> driverFullNames;
    private  String schedule_id,parent_id,surrogate_id,schedule_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surrogacy_details);

        tvID = findViewById(R.id.tvID);
        tvStatus = findViewById(R.id.tvStatus);
        tvParentName = findViewById(R.id.tvParentName);
        tvParentNo = findViewById(R.id.tvParentNo);
        tvScheduleDate = findViewById(R.id.tvScheduleDate);
        tvParentEmail = findViewById(R.id.tvParentEmail);
        tvPartnerName = findViewById(R.id.tvPartnerName);
        tvPartnerNumber = findViewById(R.id.tvPartnerNumber);
        tvSurrogateName = findViewById(R.id.tvSurrogateName);
        et_labTechnician = findViewById(R.id.et_labTechnician);
        tvSurrogateNumber = findViewById(R.id.tvSurrogateNumber);
        tvSurrogateEmail = findViewById(R.id.tvSurrogateEmail);
        txv_type = findViewById(R.id.txv_type);
        btnDetails = findViewById(R.id.btnDetails);
        btn_assign = findViewById(R.id.btn_assign);

        drivers = new ArrayList<>();
        driverFullNames = new ArrayList<>();

        Intent intent = getIntent();
        schedule_id = intent.getStringExtra("schedule_id");
        parent_id = intent.getStringExtra("parent_id");
        surrogate_id = intent.getStringExtra("surrogate_id");
        String partner_name = intent.getStringExtra("partner_name");
        String partner_contact = intent.getStringExtra("partner_contact");
        String schedule_date = intent.getStringExtra("schedule_date");
        String booking_date = intent.getStringExtra("booking_date");
        String parent_name = intent.getStringExtra("parent_name");
        String parent_phone = intent.getStringExtra("parent_phone");
        String parent_email = intent.getStringExtra("parent_email");
        String surrogate_name = intent.getStringExtra("surrogate_name");
        String surrogate_phone = intent.getStringExtra("surrogate_phone");
        String surrogate_email = intent.getStringExtra("surrogate_email");
        schedule_type = intent.getStringExtra("schedule_type");

        tvID.setText("#ID: " + schedule_id);
        tvScheduleDate.setText("Scheduled On: " + schedule_date);
        tvParentName.setText("Intended Parent: " + parent_name);
        tvParentNo.setText("Phone No: " + parent_phone);
        tvParentEmail.setText("Email: " + parent_email);
        tvPartnerName.setText("Name: " + partner_name);
        tvPartnerNumber.setText("Phone No: " + partner_contact);
        tvSurrogateName.setText("Name: " + surrogate_name);
        tvSurrogateNumber.setText("Phone No: " + surrogate_phone);
        tvSurrogateEmail.setText("Email: " + surrogate_email);
        tvParentName.setText("Intended Parent: " + parent_name);
        tvParentName.setText("Intended Parent: " + parent_name);
        tvStatus.setText("Status: Pending Fertility Medical Test" );

        if (schedule_type.equals("egg_donor")) {
            txv_type.setText("Selected Egg Donor Details");
        }

        et_labTechnician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlertTechs(v);
            }
        });
        btn_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlertApprove(v);
            }
        });

        getLabTech();
    }
    public void getLabTech() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET_LAB_TECH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");

                            if (status.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("details");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsn = jsonArray.getJSONObject(i);
                                    String username = jsn.getString("username");
                                    String fullName = jsn.getString("fullName"); // Assume this field is in the JSON response
                                    drivers.add(username);
                                    driverFullNames.add(fullName); // Add the full name to the list
                                }
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 250);
                                toast.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 250);
                toast.show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void approve() {
        btn_assign.setVisibility(View.GONE);
        final String username = et_labTechnician.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please select Lab Technician", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ASSIGN_MEDICAL_TEST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String msg = jsonObject.getString("message");
                            if (status.equals("1")) {
                                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 250);
                                toast.show();
                                finish();
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 250);
                                toast.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast toast = Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 250);
                toast.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("schedule_id", schedule_id);
                params.put("surrogate_id", surrogate_id);
                params.put("parent_id", parent_id);
                params.put("username", username);
                params.put("schedule_type", schedule_type);
                Log.e("Params", "" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void getAlertTechs(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Lab Technician");

        // Create a string array of full names for the dialog
        String[] fullNamesArray = driverFullNames.toArray(new String[0]);

        builder.setItems(fullNamesArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // When an instructor is selected, set the username in the EditText
                et_labTechnician.setText(drivers.get(which)); // Get the corresponding username
            }
        });

        builder.show();
    }
    public void getAlertApprove(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Assign");

        builder.setMessage("Assign Fertility Test?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //progressBar1.setVisibility(View.VISIBLE);
                approve();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}