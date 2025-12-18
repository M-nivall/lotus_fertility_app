package com.example.Varsani.Clients;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.Suppliers.RegSuppliers;
import com.example.Varsani.utils.Urls;
import com.example.Varsani.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private Button btn_register;
    private ProgressBar progress_bar;
    private EditText et_full_name,et_email,et_phone_number,et_dob,et_county,et_town,
            et_address,et_username,et_password,et_conf_password;
    private RadioGroup rgGender;
    private Spinner spinner_role;
    private ArrayList<String> arrayCounties;
    private ArrayList<String> arrayTowns;
    private  String countyName,townName,countyID;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private String selectedGender;

    DatePickerDialog datePicker;
    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_full_name=findViewById(R.id.et_full_name);
        et_email=findViewById(R.id.et_email);
        et_phone_number=findViewById(R.id.et_phone_number);
        et_dob=findViewById(R.id.et_dob);
        spinner_role=findViewById(R.id.spinner_role);
        et_county=findViewById(R.id.et_county);
        et_town=findViewById(R.id.et_town);
        et_address=findViewById(R.id.et_address);
        et_username=findViewById(R.id.et_username);
        rgGender = findViewById(R.id.rg_gender);
        et_password=findViewById(R.id.et_password);
        et_conf_password=findViewById(R.id.et_conf_password);
        progress_bar=findViewById(R.id.progress_bar);
        btn_register=findViewById(R.id.btn_register);

        progress_bar.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.user_roles,android.R.layout.simple_spinner_dropdown_item);
        spinner_role.setAdapter(adapter);

        et_county.setFocusable(false);
        et_town.setFocusable(false);

        arrayCounties =new ArrayList<>();
        arrayTowns=new ArrayList<>();

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        et_dob.setText(date);

        final Calendar calendar2 = Calendar.getInstance();
        final int day = calendar2.get(Calendar.DAY_OF_MONTH);
        final int year = calendar2.get(Calendar.YEAR);
        final int month = calendar2.get(Calendar.MONTH);

        datePicker = new DatePickerDialog(Register.this);

        rgGender.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_male) {
                selectedGender = "Male";
            } else if (checkedId == R.id.rb_female) {
                selectedGender = "Female";
            } else if (checkedId == R.id.rb_other) {
                selectedGender = "Other";
            }
        });

        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
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
                            Toast.makeText(Register.this, "You must be at least 18 years old ", Toast.LENGTH_SHORT).show();
                            btn_register.setVisibility(View.GONE); // Disable the checkout button
                        } else {
                            et_dob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            btn_register.setVisibility(View.VISIBLE); // Enable the checkout button
                        }
                    }
                }, year, month, day);

                // Set maximum date to today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                // Show the dialog
                datePicker.show();
            }
        });

        et_county.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_bar.setVisibility(View.VISIBLE);
                et_town.setVisibility(View.GONE);
                et_town.setText("");
                getAlertCounties(v);
            }
        });

        et_town.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String checkCounty =et_county.getText().toString().trim();
                if(TextUtils.isEmpty(checkCounty)){
                    Toast.makeText(getApplicationContext(),"Enter county to continue ",Toast.LENGTH_SHORT).show();
                }else{
                    getAlertTowns(v);

                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        getCounties();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getAlertCounties(View v){
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("County ");
        final String[] array = arrayCounties.toArray(new String[arrayCounties.size()]);
        builder.setNegativeButton("Close",null);
        builder.setSingleChoiceItems( array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                et_county.setText(array[i]);
                dialogInterface.dismiss();
                countyName=array[i];

                Toast.makeText(getApplicationContext(),countyName,Toast.LENGTH_SHORT).show();
                getTowns();


            }
        });
        builder.show();

    }
    public void getAlertTowns(View v){
        final android.app.AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Town");
        final String[] array = arrayTowns.toArray(new String[arrayTowns.size()]);
        builder.setSingleChoiceItems( array, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                et_town.setText(array[i]);
                dialogInterface.dismiss();
                townName=array[i];
                builder.setNegativeButton("Close",null);

                getTowns();

            }
        });
        builder.show();

    }

    public void register(){
        btn_register.setVisibility(View.GONE);
        progress_bar.setVisibility(View.VISIBLE);

        final String full_name = et_full_name.getText().toString().trim();
        final String email = et_email.getText().toString().trim();
        final String role=spinner_role.getSelectedItem().toString().trim();
        final String phone_number = et_phone_number.getText().toString().trim();
        final String dob = et_dob.getText().toString().trim();
        final String county = et_county.getText().toString().trim();
        final String town = et_town.getText().toString().trim();
        final String address = et_address.getText().toString().trim();
        final String username = et_username.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        final String conf_password = et_conf_password.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if(TextUtils.isEmpty(full_name)){
            Toast.makeText(getApplicationContext(), "Enter full name", Toast.LENGTH_SHORT).show();
            btn_register.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);
            return;

        }

        if(TextUtils.isEmpty(role)){
            Toast.makeText(getApplicationContext(), "Select role", Toast.LENGTH_SHORT).show();
            btn_register.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);
            return;

        }
        if(TextUtils.isEmpty(dob)){
            Toast.makeText(getApplicationContext(), "Enter date of birth", Toast.LENGTH_SHORT).show();
            btn_register.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);
            return;

        }

        if(TextUtils.isEmpty(username)){
            Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_SHORT).show();
            btn_register.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);
            return;

        }
        if(TextUtils.isEmpty(phone_number)){
            Toast.makeText(getApplicationContext(), "Enter phone number", Toast.LENGTH_SHORT).show();
            btn_register.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);
            return;
        }
        if(phone_number.length()>10 ||phone_number.length()<10){
            Toast.makeText(getApplicationContext(), "Phone number should contain 10 digits", Toast.LENGTH_SHORT).show();
            btn_register.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);

            return;
        }
        if(TextUtils.isEmpty(county)){
            Toast.makeText(getApplicationContext(), "Select County", Toast.LENGTH_SHORT).show();
            btn_register.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);
            return;

        }
        if(TextUtils.isEmpty(town)){
            Toast.makeText(getApplicationContext(), "Enter town", Toast.LENGTH_SHORT).show();
            btn_register.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);
            return;

        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Enter email address", Toast.LENGTH_SHORT).show();
            btn_register.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);
            return;

        }
        if (!email.matches(emailPattern)){
            Toast.makeText(getApplicationContext(), "In valid email address", Toast.LENGTH_SHORT).show();
            btn_register.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);
            return;
        }
        if(TextUtils.isEmpty(address)) {
            Toast.makeText(getApplicationContext(), "Enter physical address", Toast.LENGTH_SHORT).show();
            btn_register.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Password is required", Toast.LENGTH_SHORT).show();
            btn_register.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);
            return;
        }

        if(!password.equals(conf_password)) {
            Toast.makeText(getApplicationContext(), "Password mismatch", Toast.LENGTH_SHORT).show();
            btn_register.setVisibility(View.VISIBLE);
            progress_bar.setVisibility(View.GONE);
            return;
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_REG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response","is" + response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if(status.equals("1")){
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),Login.class);
                                startActivity(intent);
                                btn_register.setVisibility(View.VISIBLE);
                                progress_bar.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

                                btn_register.setVisibility(View.VISIBLE);
                                progress_bar.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                            btn_register.setVisibility(View.VISIBLE);
                            progress_bar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                btn_register.setVisibility(View.VISIBLE);
                progress_bar.setVisibility(View.GONE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fullName",full_name);
                params.put("email",email);
                params.put("role",role);
                params.put("selectedGender",selectedGender);
                params.put("phoneNumber",phone_number);
                params.put("dob",dob);
                params.put("county",county);
                params.put("town",town);
                params.put("address",address);
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
    public void getCounties(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_GET_COUNTIES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("Response"," "+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            if(status.equals("1")){
                                JSONArray jsonArray=jsonObject.getJSONArray("counties");
                                for (int i =0; i <jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String county=jsn.getString("countyName");
                                    arrayCounties.add(county);

                                }
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void getTowns(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Urls.URL_GET_TOWNS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            Log.e("Response",""+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            if (status.equals("1")){
                                JSONArray jsonArray= jsonObject.getJSONArray("towns");
                                arrayTowns.clear();
                                progress_bar.setVisibility(View.GONE);
                                et_town.setVisibility(View.VISIBLE);
                                for(int i=0;i <jsonArray.length();i++){
                                    JSONObject jsn=jsonArray.getJSONObject(i);
                                    String towns=jsn.getString("townName");
                                    String ID=jsn.getString("countyID");
                                    arrayTowns.add(towns);

                                    countyID=ID;
                                }
                            }else{
                                String msg=jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String,String>params=new HashMap<>();
                params.put("countyName",countyName);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
