package com.example.Varsani.Staff.Doctor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Staff.Models.ClientItemsModal;
import com.example.Varsani.R;
import com.example.Varsani.Staff.Adapters.AdapterClientItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.Varsani.utils.Urls.ROOT_URL_UPLOADS;

import static com.example.Varsani.utils.Urls.URL_ASSIGN_LAB_TECH;
import static com.example.Varsani.utils.Urls.URL_GET_LAB_TECH;

public class SurrogateDetails extends AppCompatActivity {
    private  TextView tv_fullName,tv_surrogateId,tv_height,tv_weight,tv_bloodType,tv_medication,
            tv_maritalStatus,tv_education,tv_numChildren,tv_phoneNo,tv_email,tv_dateBirth;
    private Button btn_medicalPdf,btn_nationalId,btn_profilePic,btn_approve;
    private  EditText et_medicalDate,et_labTechnician;
    private TextView txv_role;
    private String medicalPdfUrl = ""; // Set these values after fetching from backend
    private String nationalIdUrl = "";
    private String profilePicUrl = "";
    private  String surrogateId,role;
    private String idImageName,photoImageName,medicalImageName;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date,gender,studyMode;
    private ArrayList<String> drivers;
    private ArrayList<String> driverFullNames;

    DatePickerDialog datePicker;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surrogate_details);

        tv_fullName = findViewById(R.id.tv_fullName);
        tv_surrogateId = findViewById(R.id.tv_surrogateId);
        tv_height = findViewById(R.id.tv_height);
        tv_weight = findViewById(R.id.tv_weight);
        tv_bloodType = findViewById(R.id.tv_bloodType);
        tv_medication = findViewById(R.id.tv_medication);
        tv_maritalStatus = findViewById(R.id.tv_maritalStatus);
        tv_education = findViewById(R.id.tv_education);
        tv_numChildren = findViewById(R.id.tv_numChildren);
        tv_phoneNo = findViewById(R.id.tv_phoneNo);
        tv_email = findViewById(R.id.tv_email);
        txv_role = findViewById(R.id.txv_role);
        tv_dateBirth = findViewById(R.id.tv_dateBirth);
        et_medicalDate = findViewById(R.id.et_medicalDate);
        et_labTechnician = findViewById(R.id.et_labTechnician);
        btn_medicalPdf = findViewById(R.id.btn_medicalPdf);
        btn_nationalId = findViewById(R.id.btn_nationalId);
        btn_profilePic = findViewById(R.id.btn_profilePic);
        btn_approve = findViewById(R.id.btn_approve);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        et_medicalDate.setText(date);

        final Calendar calendar2 = Calendar.getInstance();
        final int day = calendar2.get(Calendar.DAY_OF_MONTH);
        final int year = calendar2.get(Calendar.YEAR);
        final int month = calendar2.get(Calendar.MONTH);

        datePicker = new DatePickerDialog(SurrogateDetails.this);

        drivers = new ArrayList<>();
        driverFullNames = new ArrayList<>();

        Intent intent=getIntent();

        surrogateId=intent.getStringExtra("surrogateId");
        String height=intent.getStringExtra("height");
        String weight=intent.getStringExtra("weight");
        String bloodType=intent.getStringExtra("bloodType");
        String medication=intent.getStringExtra("medication");
        String maritalStatus=intent.getStringExtra("maritalStatus");
        String education=intent.getStringExtra("education");
        String numChildren=intent.getStringExtra("numChildren");
        idImageName=intent.getStringExtra("medicalImage");
        photoImageName=intent.getStringExtra("photoImage");
        medicalImageName=intent.getStringExtra("medicalImage");
        String fullName=intent.getStringExtra("fullName");
        String phoneNo=intent.getStringExtra("phoneNo");
        String email=intent.getStringExtra("email");
        String gender=intent.getStringExtra("gender");
        String dateBirth=intent.getStringExtra("dateBirth");
        role=intent.getStringExtra("role");

        if (role.equals("egg_donor")){
            txv_role.setText("Donor Details");
        }

        tv_fullName.setText("Name: " + fullName );
        tv_surrogateId.setText("#ID: " + surrogateId );
        tv_height.setText("Height: " + height + " cm");
        tv_weight.setText("Weight: " + weight + " cm");
        tv_bloodType.setText("Blood Group: " + bloodType );
        tv_medication.setText("Medical Condition: " + medication );
        tv_maritalStatus.setText("Marital Status: " + maritalStatus );
        tv_education.setText("Education Level: " + education );
        tv_numChildren.setText("Children: " + numChildren );
        tv_phoneNo.setText("Phone No: " + phoneNo );
        tv_email.setText("Email: " + email );
        tv_dateBirth.setText("Date Of Birth: " + dateBirth );

        et_medicalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(SurrogateDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        et_medicalDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();
            }
        });
        et_labTechnician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlertInstructors(v);
            }
        });

        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlertApprove(v);
            }
        });

        getLabTech();

        fetchFileUrls();

        btn_medicalPdf.setOnClickListener(v -> openFile(medicalPdfUrl));
        btn_nationalId.setOnClickListener(v -> openFile(nationalIdUrl));
        btn_profilePic.setOnClickListener(v -> openFile(profilePicUrl));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void fetchFileUrls() {
        // Construct the full URLs
        medicalPdfUrl = ROOT_URL_UPLOADS + "/" + medicalImageName;
        nationalIdUrl = ROOT_URL_UPLOADS + "/" + idImageName;
        profilePicUrl = ROOT_URL_UPLOADS + "/" + photoImageName;
    }
    private void openFile(String url) {
        if (url == null || url.isEmpty()) {
            // Handle case where URL is not available
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
    public void approve() {
        final String username = et_labTechnician.getText().toString().trim();
        final String medicalDate=et_medicalDate.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please select Lab Technician", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ASSIGN_LAB_TECH,
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
                params.put("surrogateId", surrogateId);
                params.put("username", username);
                params.put("medicalDate", medicalDate);
                params.put("role", role);
                Log.e("Params", "" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
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
    public void getAlertInstructors(View v) {
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
        builder.setTitle("Approve");

        builder.setMessage("Are you sure you want to approve?");
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
