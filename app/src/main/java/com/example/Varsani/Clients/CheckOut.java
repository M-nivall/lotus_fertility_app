package com.example.Varsani.Clients;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.Varsani.MainActivity;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CheckOut extends AppCompatActivity {
    private SessionHandler session;
    private UserModel user;

    private TextView txv_name,txv_phoneNo,txv_email;
    private TextView txv_surrogate_name,txv_fee,txv_surrogate_age;
    private EditText edt_partner_name,edt_partner_contact,edt_date_birth,edt_payment_code,edt_schedule_date;
    private TextView txv_surrogate_fee,txv_service_fee,txv_total_fee;
    private ProgressBar progressBar;
    private Button btn_submit;
    private String surrogateID,price;
    private Integer service_fee = 5000;
    private Integer total_fee;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog datePicker;
    private String date,role;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        getSupportActionBar().setTitle("Schedule Form");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //layout_checkout=findViewById(R.id.layout_checkout);
        //layout_card=findViewById(R.id.layout_card);
        //layout_bottom=findViewById(R.id.layout_bottom);
        progressBar=findViewById(R.id.progressBar);
       // progressCheckout=findViewById(R.id.progressCheckout);
        btn_submit=findViewById(R.id.btn_submit);
        txv_email=findViewById(R.id.txv_email);
        txv_name=findViewById(R.id.txv_name);
        txv_phoneNo=findViewById(R.id.txv_phoneNo);

        txv_surrogate_name=findViewById(R.id.txv_surrogate_name);
        txv_surrogate_age=findViewById(R.id.txv_surrogate_age);
        txv_fee=findViewById(R.id.txv_fee);

        edt_partner_name=findViewById(R.id.edt_partner_name);
        edt_partner_contact=findViewById(R.id.edt_partner_contact);
        edt_date_birth=findViewById(R.id.edt_date_birth);
        edt_payment_code=findViewById(R.id.edt_payment_code);
        edt_schedule_date=findViewById(R.id.edt_schedule_date);

        txv_surrogate_fee=findViewById(R.id.txv_surrogate_fee);
        txv_service_fee=findViewById(R.id.txv_service_fee);
        txv_total_fee=findViewById(R.id.txv_total_fee);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        //layout_bottom.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        //progressCheckout.setVisibility(View.VISIBLE);
        //layout_checkout.setVisibility(View.GONE);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        edt_schedule_date.setText(date);
        edt_date_birth.setText(date);

        final Calendar calendar2 = Calendar.getInstance();
        final int day = calendar2.get(Calendar.DAY_OF_MONTH);
        final int year = calendar2.get(Calendar.YEAR);
        final int month = calendar2.get(Calendar.MONTH);

        datePicker = new DatePickerDialog(CheckOut.this);

        txv_name.setText("Name: "+user.getFirstname());
        txv_email.setText("Email: " + user.getEmail());
        txv_phoneNo.setText("Tell: " + user.getPhoneNo());

        edt_payment_code.setFilters(new InputFilter[] {new InputFilter.AllCaps()});


        Intent intent = getIntent();

        surrogateID = intent.getStringExtra("surrogateID");
        String fullName = intent.getStringExtra("fullName");
        String age = intent.getStringExtra("age");
        price = intent.getStringExtra("price");
        role = intent.getStringExtra("role");

        total_fee = Integer.parseInt(price) + service_fee;

        txv_surrogate_name.setText("Name: " + fullName);
        txv_surrogate_age.setText("Age: " + age + " Years");
        txv_fee.setText("Fee: " + price);

        txv_surrogate_fee.setText("Surrogate Fee: " + price);
        txv_service_fee.setText("Service Fee: " + service_fee);
        txv_total_fee.setText("Total Fee: " + total_fee);


        edt_date_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(CheckOut.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // Create a calendar instance with the selected date
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);

                        // Get the current date
                        Calendar currentDate = Calendar.getInstance();

                        // Calculate age
                        int age = currentDate.get(Calendar.YEAR) - selectedDate.get(Calendar.YEAR);
                        if (currentDate.get(Calendar.DAY_OF_YEAR) < selectedDate.get(Calendar.DAY_OF_YEAR)) {
                            age--; // Adjust if the current date is before the birth date in the year
                        }

                        // Check if age is below 18
                        if (age < 18) {
                            Toast.makeText(CheckOut.this, "You must be at least 18 years old ", Toast.LENGTH_SHORT).show();
                            btn_submit.setVisibility(View.GONE); // Disable the checkout button
                        } else {
                            edt_date_birth.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            btn_submit.setVisibility(View.VISIBLE); // Enable the checkout button
                        }
                    }
                }, year, month, day);

                // Set maximum date to today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                // Show the dialog
                datePicker.show();
            }
        });
        edt_schedule_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(CheckOut.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        edt_schedule_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

                // show the dialog
                datePicker.show();
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertOrderNow(v);

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

    public void alertOrderNow(final  View v){
        AlertDialog.Builder  builder=new AlertDialog.Builder(v.getContext());
        builder.setTitle("Submit ")
                .setNegativeButton("Close",null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        orderNow();
                        dialog.dismiss();
                    }
                }).create().show();
    }

    public void orderNow() {

        progressBar.setVisibility(View.VISIBLE);
        btn_submit.setVisibility(View.GONE);
        final String partner_name=edt_partner_name.getText().toString().trim();
        final String partner_contact=edt_partner_contact.getText().toString().trim();
        final String partner_birth=edt_date_birth.getText().toString().trim();
        //final String payment_code=edt_payment_code.getText().toString().trim();
        final String schedule_date=edt_schedule_date.getText().toString().trim();

        if(TextUtils.isEmpty(partner_name)){
            Toast.makeText(getApplicationContext(),"Please enter your Partner Name",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn_submit.setVisibility(View.VISIBLE);
            return;
        } if(TextUtils.isEmpty(partner_contact)){
            Toast.makeText(getApplicationContext(),"Please enter your Partner Contact",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn_submit.setVisibility(View.VISIBLE);
            return;
        }
        if(TextUtils.isEmpty(partner_birth)){
            Toast.makeText(getApplicationContext(),"Please enter your partner birth date",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn_submit.setVisibility(View.VISIBLE);
            return;
        }
        if(TextUtils.isEmpty(schedule_date)){
            Toast.makeText(getApplicationContext(),"Please select Schedule date",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn_submit.setVisibility(View.VISIBLE);
            return;
        }
        //if (radioGroup.getCheckedRadioButtonId() == -1) {
            // no radio buttons are checked
         //   progressBar.setVisibility(View.GONE);
          //  btn_submit.setVisibility(View.VISIBLE);
          // Toast.makeText(getApplicationContext(),"Please select a shipping method",Toast.LENGTH_SHORT).show();
         //   return;
       // }
        /*if(TextUtils.isEmpty(payment_code)){
            Toast.makeText(getApplicationContext(),"Please enter Payment code",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btn_submit.setVisibility(View.VISIBLE);
            return;
        }
        if(payment_code.length()>10 ||payment_code.length()<10){
            Toast.makeText(getApplicationContext(), "Payment code  should contain 10 digits", Toast.LENGTH_SHORT).show();
            btn_submit.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
              return;
        }
        if(!payment_code.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")){
            Toast.makeText(getApplicationContext(), "Mpesa code should have  characters and digit",
                    Toast.LENGTH_LONG).show();
            btn_submit.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }

         */


        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_BOOK_APPOINTMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");

                            if(status.equals("1")){
                                progressBar.setVisibility(View.GONE);
                                //layout_card.setVisibility(View.GONE);
                                AlertDialog.Builder  builder=new AlertDialog.Builder(CheckOut.this);
                                builder.setTitle("Success ");
                                builder.setMessage(msg)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(getApplicationContext(), CheckOut.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                startActivity(i);
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.setCancelable(false)
                                        .create().show();
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            }else if(status.equals("0")){
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btn_submit.setVisibility(View.VISIBLE);

                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Log.e("Error",e.toString());
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("Error",error.toString());
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("clientID",user.getClientID());
                params.put("surrogateID",surrogateID);
                params.put("service_fee", String.valueOf(service_fee));
                params.put("surrogate_fee",price);
                params.put("total_fee", String.valueOf(total_fee));
                params.put("partner_name",partner_name);
                params.put("partner_contact",partner_contact);
                params.put("partner_birth",partner_birth);
                //params.put("payment_code", payment_code);
                params.put("schedule_date", schedule_date);
                params.put("role", role);
                Log.e("values",""+params);
                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}
