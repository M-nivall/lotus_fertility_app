package com.example.Varsani.Clients;

import static com.example.Varsani.utils.Urls.URL_GET_FIRST_CHECK;
import static com.example.Varsani.utils.Urls.URL_GET_PRESCRIPTIONS;
import static com.example.Varsani.utils.Urls.URL_GET_SECOND_CHECK;
import static com.example.Varsani.utils.Urls.URL_GET_THIRD_CHECK;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class CheckupItems extends AppCompatActivity {
    private TextView first_date,second_date,third_date;
    private String scheduleID,first_check,second_check,delivery_check;
    private TextView first_status,edt_first_comment;
    private TextView second_status,edt_second_comment;
    private TextView third_status,edt_third_comment;
    private String check_status,remark;
    private TextView tvDoctorPrescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkup_items);

        first_date = findViewById(R.id.first_date);
        second_date = findViewById(R.id.second_date);
        third_date = findViewById(R.id.third_date);

        tvDoctorPrescription =findViewById(R.id.tvDoctorPrescription);

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
        //String surrogateName=in.getStringExtra("surrogateName");
        first_check=in.getStringExtra("firstCheck");
        second_check=in.getStringExtra("secondCheck");
        delivery_check=in.getStringExtra("deliveryCheck");

        first_date.setText("Date: " + first_check);
        second_date.setText("Date: " + second_check);
        third_date.setText("Date: " + delivery_check);

        firstStatus();
        secondStatus();
        thirdStatus();
        prescription();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void firstStatus(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_GET_FIRST_CHECK,
                new Response.Listener<String>() {
                    @SuppressLint("SuspiciousIndentation")
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
                                     check_status=jsn.getString("check_status");
                                     remark=jsn.getString("remark");

                                    if (check_status.equals("Approved")) {
                                        first_status.setText("Status: " + check_status);
                                        edt_first_comment.setText("Remark: " + remark);
                                    }
                                    if (check_status.equals("Pending")) {
                                        edt_first_comment.setVisibility(View.GONE);
                                        first_status.setText("Status: " + check_status);
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
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
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
                                        second_status.setText("Status: " + check_status);
                                        edt_second_comment.setText("Remark: " + remark);

                                    } if (check_status.equals("Pending")) {
                                        edt_second_comment.setVisibility(View.GONE);
                                        second_status.setText("Status: " + check_status);
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
                                        third_status.setText("Status: " + check_status);
                                        edt_third_comment.setText("Remark: " + remark);

                                    } if (check_status.equals("Pending")) {
                                        edt_third_comment.setVisibility(View.GONE);
                                        third_status.setText("Status: " + check_status);
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
    public void prescription(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_GET_PRESCRIPTIONS,
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
                                    String prescription=jsn.getString("prescription");
                                    tvDoctorPrescription.setText(prescription);


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
}