package com.example.Varsani.IntendedParent;

import static com.example.Varsani.utils.Urls.URL_ASSIGN_ATTORNEY;
import static com.example.Varsani.utils.Urls.URL_ASSIGN_LAB_TECH;
import static com.example.Varsani.utils.Urls.URL_GET_AGREEMENTS;
import static com.example.Varsani.utils.Urls.URL_GET_ATTORNEY;
import static com.example.Varsani.utils.Urls.URL_GET_LAB_TECH;
import static com.example.Varsani.utils.Urls.URL_MARK_SERVICE_COMPLETE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingItems extends AppCompatActivity {
    private TextView tvId,tvSurrogateName,tvBookingDate,tvPartnerName,tvAmount,tvStatus,tvAppointmentDate;
    private EditText edtAttorney,edt_remarks;
    private Button btnViewReceipt,btnProceed,btnViewAgreement,btnConfirmCompletion;
    private CardView card_notice;
    private LinearLayout layout_remarks;
    private ProgressBar progressBar;
    private ArrayList<String> drivers;
    private ArrayList<String> driverFullNames;
    private  String schedule_id,surrogate_id;
    private String pdfAgreement;
    private SessionHandler session;
    private UserModel user;
    private String files_url = Urls.ROOT_URL_AGREEMENTS;
    private String url;
    private String full_name,booking_date,service_fee,surrogate_fee,total_fee,payment_code,schedule_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_items);

        tvId = findViewById(R.id.tvId);
        tvSurrogateName = findViewById(R.id.tvSurrogateName);
        tvBookingDate = findViewById(R.id.tvBookingDate);
        tvPartnerName = findViewById(R.id.tvPartnerName);
        tvAmount = findViewById(R.id.tvAmount);
        tvStatus = findViewById(R.id.tvStatus);
        tvAppointmentDate = findViewById(R.id.tvAppointmentDate);
        btnViewReceipt = findViewById(R.id.btnViewReceipt);
        btnViewAgreement = findViewById(R.id.btnViewAgreement);
        btnProceed = findViewById(R.id.btnProceed);
        edtAttorney = findViewById(R.id.edtAttorney);
        edt_remarks = findViewById(R.id.edt_remarks);
        card_notice = findViewById(R.id.card_notice);
        layout_remarks = findViewById(R.id.layout_remarks);
        btnConfirmCompletion = findViewById(R.id.btnConfirmCompletion);
        progressBar = findViewById(R.id.progressBar);

        drivers = new ArrayList<>();
        driverFullNames = new ArrayList<>();

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        card_notice.setVisibility(View.GONE);
        layout_remarks.setVisibility(View.GONE);

        Intent intent = getIntent();
        schedule_id = intent.getStringExtra("schedule_id");
        surrogate_id = intent.getStringExtra("surrogate_id");
        full_name = intent.getStringExtra("full_name");
        booking_date = intent.getStringExtra("booking_date");
        String partner_name = intent.getStringExtra("partner_name");
        service_fee = intent.getStringExtra("service_fee");
        surrogate_fee = intent.getStringExtra("surrogate_fee");
        total_fee = intent.getStringExtra("total_fee");
        String schedule_status = intent.getStringExtra("schedule_status");
        String schedule_date = intent.getStringExtra("schedule_date");
        payment_code = intent.getStringExtra("payment_code");
        schedule_type = intent.getStringExtra("schedule_type");

        tvId.setText("#ID: " + schedule_id);
        tvSurrogateName.setText("Surrogate: " + full_name);
        tvBookingDate.setText("Booking Date: " + booking_date);
        tvPartnerName.setText("Partner Name: " + partner_name);
        tvAmount.setText("Amount: " + total_fee);
        tvStatus.setText("Status: " + schedule_status);
        tvId.setText("#ID: " + schedule_id);
        tvAppointmentDate.setText("Date Scheduled: " + schedule_date);

        if (schedule_type.equals("egg_donor")){
            tvSurrogateName.setText("Egg Donor: " + full_name);
        }


        if (schedule_status.equals("Proceed Attorney")){
            card_notice.setVisibility(View.VISIBLE);
        }

        if (schedule_status.equals("Proceed Attorney") || schedule_status.equals("Pending Attorney")) {
            btnViewAgreement.setVisibility(View.GONE);
            btnViewReceipt.setVisibility(View.GONE);
        }
        if (schedule_status.equals("Agreement Completed")){
            btnViewReceipt.setVisibility(View.GONE);
        }
        if (schedule_status.equals("Service Completed")){
            layout_remarks.setVisibility(View.VISIBLE);
        }
        if (schedule_status.equals("Completed")){
            layout_remarks.setVisibility(View.GONE);
        }

        edtAttorney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlertInstructors(v);
            }
        });

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlertApprove(v);
            }
        });

        btnViewAgreement.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });
        btnConfirmCompletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getConfirm(v);
            }
        });
        btnViewReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),PaymentReceipt.class);
                intent.putExtra("schedule_id", schedule_id);
                intent.putExtra("full_name", full_name);
                intent.putExtra("booking_date", booking_date);
                intent.putExtra("service_fee", service_fee);
                intent.putExtra("surrogate_fee", surrogate_fee);
                intent.putExtra("total_fee", total_fee);
                intent.putExtra("payment_code", payment_code);
                startActivity(intent);
            }
        });

        getLabTech();
        getMyPackages();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void approve() {
        final String username = edtAttorney.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please select an Attorney", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ASSIGN_ATTORNEY,
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
                params.put("scheduleID", schedule_id);
                params.put("surrogateID", surrogate_id);
                params.put("username", username);
                params.put("parentID", user.getClientID());
                Log.e("Params", "" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void getLabTech() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET_ATTORNEY,
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
    public void getMyPackages() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GET_AGREEMENTS,
                response -> {
                    try {

                        Log.e("RESPONSE ", response);
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("responseData");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsn = jsonArray.getJSONObject(i);
                                pdfAgreement = jsn.getString("pdfAgreement");

                                url=files_url + pdfAgreement;
                                Log.e("URL FILE"," "+url);
                            }

                        } else if (status.equals("0")) {

                            //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
                params.put("scheduleId", schedule_id);
                params.put("userId", user.getClientID());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void markComplete(){
        progressBar.setVisibility(View.VISIBLE);
        btnConfirmCompletion.setVisibility(View.GONE);
        final String remarks = edt_remarks.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_MARK_SERVICE_COMPLETE,
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
                                btnConfirmCompletion.setVisibility(View.VISIBLE);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();

                            progressBar.setVisibility(View.GONE);
                            btnConfirmCompletion.setVisibility(View.VISIBLE);
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
                btnConfirmCompletion.setVisibility(View.VISIBLE);
            }
        }){
            @Override
            protected Map<String,String>getParams()throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("scheduleID",schedule_id);
                params.put("remarks",remarks);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void getAlertInstructors(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Attorney");

        // Create a string array of full names for the dialog
        String[] fullNamesArray = driverFullNames.toArray(new String[0]);

        builder.setItems(fullNamesArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // When an instructor is selected, set the username in the EditText
                edtAttorney.setText(drivers.get(which)); // Get the corresponding username
            }
        });

        builder.show();
    }
    public void getAlertApprove(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Submit");

        builder.setMessage("Are you sure you want submit?");
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
    public void getConfirm(View v){
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Confirm Completion");
        builder.setNegativeButton("Close",null);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                markComplete();

                return;
            }
        });
        builder.show();

    }
}