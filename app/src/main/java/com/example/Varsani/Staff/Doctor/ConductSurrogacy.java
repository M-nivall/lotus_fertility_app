package com.example.Varsani.Staff.Doctor;

import static com.example.Varsani.utils.Urls.URL_ASSIGN_MEDICAL_TEST;
import static com.example.Varsani.utils.Urls.URL_FINAL_PRESCRIPTION;
import static com.example.Varsani.utils.Urls.URL_GET_LAB_TECH;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ConductSurrogacy extends AppCompatActivity {
    private TextView tvParentName,tvParentNo,tvParentEmail,tvPartnerName,tvPartnerNumber,tvSurrogateName,
            tvSurrogateNumber,tvSurrogateEmail,tvID,tvScheduleDate,tvStatus,txv_type;
    private EditText etPrescription;
    private EditText et_1st_check_up,et_2nd_check_up,et_delivery_check_up;
    private Button btnDetails,btn_assign;
    private ArrayList<String> drivers;
    private ArrayList<String> driverFullNames;
    private  String schedule_id,parent_id,surrogate_id,schedule_type;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date,gender,studyMode;
    DatePickerDialog datePicker;

    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conduct_surrogacy);

        tvID = findViewById(R.id.tvID);
        tvStatus = findViewById(R.id.tvStatus);
        tvParentName = findViewById(R.id.tvParentName);
        tvParentNo = findViewById(R.id.tvParentNo);
        tvScheduleDate = findViewById(R.id.tvScheduleDate);
        tvParentEmail = findViewById(R.id.tvParentEmail);
        tvPartnerName = findViewById(R.id.tvPartnerName);
        etPrescription = findViewById(R.id.etPrescription);
        tvPartnerNumber = findViewById(R.id.tvPartnerNumber);
        tvSurrogateName = findViewById(R.id.tvSurrogateName);
        tvSurrogateNumber = findViewById(R.id.tvSurrogateNumber);
        tvSurrogateEmail = findViewById(R.id.tvSurrogateEmail);
        btnDetails = findViewById(R.id.btnDetails);
        btn_assign = findViewById(R.id.btn_assign);
        txv_type = findViewById(R.id.txv_type);
        et_1st_check_up = findViewById(R.id.et_1st_check_up);
        et_2nd_check_up = findViewById(R.id.et_2nd_check_up);
        et_delivery_check_up = findViewById(R.id.et_delivery_check_up);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        et_1st_check_up.setText(date);
        et_2nd_check_up.setText(date);
        et_delivery_check_up.setText(date);

        final Calendar calendar2 = Calendar.getInstance();
        final int day = calendar2.get(Calendar.DAY_OF_MONTH);
        final int year = calendar2.get(Calendar.YEAR);
        final int month = calendar2.get(Calendar.MONTH);

        datePicker = new DatePickerDialog(ConductSurrogacy.this);


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
        tvStatus.setText("Medical Fertility Test: Approved" );

        if (schedule_type.equals("egg_donor")) {
            txv_type.setText("Selected Egg Donor Details");
        }

        et_1st_check_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(ConductSurrogacy.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        et_1st_check_up.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();
            }
        });
        et_2nd_check_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(ConductSurrogacy.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        et_2nd_check_up.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();
            }
        });
        et_delivery_check_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(ConductSurrogacy.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        et_delivery_check_up.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();
            }
        });


        btn_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlertApprove(v);
            }
        });

    }

    public void approve() {
        btn_assign.setVisibility(View.GONE);
        final String prescription = etPrescription.getText().toString().trim();
        final String first_check=et_1st_check_up.getText().toString().trim();
        final String second_check=et_2nd_check_up.getText().toString().trim();
        final String delivery_check=et_delivery_check_up.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FINAL_PRESCRIPTION,
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
                params.put("parent_id", parent_id);
                params.put("surrogate_id", surrogate_id);
                params.put("prescription", prescription);
                params.put("schedule_type", schedule_type);
                params.put("first_check", first_check);
                params.put("second_check", second_check);
                params.put("delivery_check", delivery_check);
                Log.e("Params", "" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void getAlertApprove(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Assign");

        builder.setMessage("Submit");
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