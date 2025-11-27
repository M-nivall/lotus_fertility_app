package com.example.Varsani.Staff.Doctor;

import static com.example.Varsani.utils.Urls.URL_APPROVE_FIRST_CHECKUP;
import static com.example.Varsani.utils.Urls.URL_APPROVE_SECOND_CHECKUP;
import static com.example.Varsani.utils.Urls.URL_APPROVE_THIRD_CHECKUP;
import static com.example.Varsani.utils.Urls.URL_FINAL_PRESCRIPTION;
import static com.example.Varsani.utils.Urls.URL_GET_FIRST_CHECK;
import static com.example.Varsani.utils.Urls.URL_GET_PARENT;
import static com.example.Varsani.utils.Urls.URL_GET_SECOND_CHECK;
import static com.example.Varsani.utils.Urls.URL_GET_THIRD_CHECK;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
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

import java.util.HashMap;
import java.util.Map;


public class FirstCheckup extends AppCompatActivity {
    private TextView first_date,second_date,third_date;
    private EditText edt_first_remark,edt_second_remark,edt_third_remark;
    private Button first_btn,second_btn,third_btn;
    private String scheduleID,first_check,second_check,delivery_check;
    private TextView first_status,edt_first_comment;
    private TextView second_status,edt_second_comment;
    private TextView third_status,edt_third_comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_checkup);

        first_date = findViewById(R.id.first_date);
        second_date = findViewById(R.id.second_date);
        third_date = findViewById(R.id.third_date);
        edt_first_remark = findViewById(R.id.edt_first_remark);
        edt_second_remark = findViewById(R.id.edt_second_remark);
        edt_third_remark = findViewById(R.id.edt_third_remark);
        first_btn = findViewById(R.id.first_btn);
        second_btn = findViewById(R.id.second_btn);
        third_btn = findViewById(R.id.third_btn);

        first_status = findViewById(R.id.first_status);
        edt_first_comment = findViewById(R.id.edt_first_comment);

        second_status = findViewById(R.id.second_status);
        edt_second_comment = findViewById(R.id.edt_second_comment);

        third_status = findViewById(R.id.third_status);
        edt_third_comment = findViewById(R.id.edt_third_comment);


        Intent in=getIntent();
        scheduleID=in.getStringExtra("scheduleID");
        String checkID=in.getStringExtra("checkID");
        String surrogateID=in.getStringExtra("surrogateID");
        String parentID=in.getStringExtra("parentID");
        String surrogateName=in.getStringExtra("surrogateName");
        first_check=in.getStringExtra("first_check");
        second_check=in.getStringExtra("second_check");
        delivery_check=in.getStringExtra("delivery_check");

        first_date.setText("Date: " + first_check);
        second_date.setText("Date: " + second_check);
        third_date.setText("Date: " + delivery_check);

        first_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertFirst(v);
            }
        });
        second_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertSecond(v);
            }
        });
        third_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertThird(v);
            }
        });
        firstStatus();
        secondStatus();
        thirdStatus();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void approveFirstCheckup() {
        first_btn.setVisibility(View.GONE);
        final String first_remark = edt_first_remark.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_APPROVE_FIRST_CHECKUP,
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
                params.put("scheduleID", scheduleID);
                params.put("first_remark", first_remark);
                params.put("first_check", first_check);
                Log.e("Params", "" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void approveSecondCheckup() {
        second_btn.setVisibility(View.GONE);
        final String second_remark = edt_second_remark.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_APPROVE_SECOND_CHECKUP,
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
                params.put("scheduleID", scheduleID);
                params.put("second_remark", second_remark);
                params.put("second_check", second_check);
                Log.e("Params", "" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void approveThirdCheckup() {
        third_btn.setVisibility(View.GONE);
        final String third_remark = edt_third_remark.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_APPROVE_THIRD_CHECKUP,
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
                params.put("scheduleID", scheduleID);
                params.put("third_remark", third_remark);
                params.put("delivery_check", delivery_check);
                Log.e("Params", "" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void firstStatus(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_GET_FIRST_CHECK,
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
                                    String check_status=jsn.getString("check_status");
                                    String remark=jsn.getString("remark");

                                    if (check_status.equals("Approved")) {
                                        first_status.setVisibility(View.VISIBLE);
                                        edt_first_comment.setVisibility(View.VISIBLE);
                                        edt_first_remark.setVisibility(View.GONE);
                                        first_btn.setVisibility(View.GONE);
                                        first_status.setText("Status: " + check_status);
                                        edt_first_comment.setText("Remark: " + remark);
                                    }


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
                params.put("scheduleID",scheduleID);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void secondStatus(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_GET_SECOND_CHECK,
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
                                    String check_status=jsn.getString("check_status");
                                    String remark=jsn.getString("remark");

                                    if (check_status.equals("Approved")) {
                                        second_status.setVisibility(View.VISIBLE);
                                        edt_second_comment.setVisibility(View.VISIBLE);
                                        edt_second_remark.setVisibility(View.GONE);
                                        second_btn.setVisibility(View.GONE);
                                        second_status.setText("Status: " + check_status);
                                        edt_second_comment.setText("Remark: " + remark);
                                    }


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
                params.put("scheduleID",scheduleID);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void thirdStatus(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_GET_THIRD_CHECK,
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
                                    String check_status=jsn.getString("check_status");
                                    String remark=jsn.getString("remark");

                                    if (check_status.equals("Approved")) {
                                        third_status.setVisibility(View.VISIBLE);
                                        edt_third_comment.setVisibility(View.VISIBLE);
                                        edt_third_remark.setVisibility(View.GONE);
                                        third_btn.setVisibility(View.GONE);
                                        third_status.setText("Status: " + check_status);
                                        edt_third_comment.setText("Remark: " + remark);
                                    }


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
                params.put("scheduleID",scheduleID);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void alertFirst(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Assign");

        builder.setMessage("Submit");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //progressBar1.setVisibility(View.VISIBLE);
                approveFirstCheckup();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
    public void alertSecond(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Assign");

        builder.setMessage("Submit");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //progressBar1.setVisibility(View.VISIBLE);
                approveSecondCheckup();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
    public void alertThird(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Assign");

        builder.setMessage("Submit");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //progressBar1.setVisibility(View.VISIBLE);
                approveThirdCheckup();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

}