package com.example.Varsani.Staff.Finance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.Varsani.utils.Urls.URL_GET_APPROVE_ORDERS;
import static com.example.Varsani.utils.Urls.URL_GET_CLIENT_ITEMS;

public class OrderDetails extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView txtSurrogateFee,txtServiceFee,txtTotalFee,txtPaymentCode,txtPaymentDate,txtPaymentStatus,
            txtParentName,txtParentPhone,txtParentEmail,txtSurrogateName,txtSurrogatePhone,txtSurrogateEmail;
    private TextView txv_type;
    private List<ClientItemsModal>list;
    private AdapterClientItems adapterClientItems;
    private Button btnApprove;
    private String scheduleId,parentId,surrogateId,bookingDate,serviceFee,surrogateFee,totalFee,paymentCode,scheduleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        getSupportActionBar().setSubtitle("Payment Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar=findViewById(R.id.progressBar);
        txtSurrogateFee=findViewById(R.id.txtSurrogateFee);
        txtServiceFee=findViewById(R.id.txtServiceFee);
        txtTotalFee=findViewById(R.id.txtTotalFee);
        txtPaymentCode=findViewById(R.id.txtPaymentCode);
        txtPaymentDate=findViewById(R.id.txtPaymentDate);
        txtPaymentStatus=findViewById(R.id.txtPaymentStatus);
        txtParentName=findViewById(R.id.txtParentName);
        txtParentPhone=findViewById(R.id.txtParentPhone);
        txtParentEmail=findViewById(R.id.txtParentEmail);
        txtSurrogateName=findViewById(R.id.txtSurrogateName);
        txtSurrogatePhone=findViewById(R.id.txtSurrogatePhone);
        txtSurrogateEmail=findViewById(R.id.txtSurrogateEmail);
        btnApprove=findViewById(R.id.btnApprove);
        txv_type = findViewById(R.id.txv_type);

        Intent intent=getIntent();

        scheduleId=intent.getStringExtra("scheduleId");
        parentId=intent.getStringExtra("parentId");
        surrogateId=intent.getStringExtra("surrogateId");
        String partnerName=intent.getStringExtra("partnerName");
        String scheduleDate=intent.getStringExtra("scheduleDate");
        bookingDate=intent.getStringExtra("bookingDate");
        serviceFee=intent.getStringExtra("serviceFee");
        surrogateFee=intent.getStringExtra("surrogateFee");
        totalFee=intent.getStringExtra("totalFee");
        paymentCode=intent.getStringExtra("paymentCode");
        String parentName=intent.getStringExtra("parentName");
        String parentPhone=intent.getStringExtra("parentPhone");
        String parentEmail=intent.getStringExtra("parentEmail");
        String surrogateName=intent.getStringExtra("surrogateName");
        String surrogatePhone=intent.getStringExtra("surrogatePhone");
        String surrogateEmail=intent.getStringExtra("surrogateEmail");
        String paymentStatus=intent.getStringExtra("paymentStatus");
        scheduleType=intent.getStringExtra("scheduleType");

        txtSurrogateFee.setText("Surrogate Fee: " + surrogateFee);
        txtServiceFee.setText("Service Fee: " + serviceFee);
        txtTotalFee.setText("Total Fee: " + totalFee);
        txtPaymentCode.setText("Payment Code: " + paymentCode);
        txtPaymentDate.setText("Payment Date: " + bookingDate);
        txtPaymentStatus.setText("Status: " + paymentStatus);
        txtParentName.setText("Name: " + parentName );
        txtParentPhone.setText("Phone No: " + parentPhone );
        txtParentEmail.setText("Email: " + parentEmail);
        txtSurrogateName.setText("Name: " + surrogateName );
        txtSurrogatePhone.setText("Phone No: " + surrogatePhone );
        txtSurrogateEmail.setText("Email: " + surrogateEmail );

        if (scheduleType.equals("egg_donor")) {
            txtSurrogateFee.setText("Egg Donor Fee: " + surrogateFee);
            txv_type.setText("Selected Egg Donor");
        }



        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertApprove();
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


    public void approvePayment(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_GET_APPROVE_ORDERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("RESPONSE",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            String msg=jsonObject.getString("message");
                            if (status.equals("1")){

                                Toast toast= Toast.makeText(OrderDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                                finish();
                            }else{

                                Toast toast= Toast.makeText(OrderDetails.this, msg, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,250);
                                toast.show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Toast toast= Toast.makeText(OrderDetails.this, e.toString(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP,0,250);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
               Toast toast= Toast.makeText(OrderDetails.this, error.toString(), Toast.LENGTH_SHORT);
               toast.setGravity(Gravity.TOP,0,250);
               toast.show();
            }
        }){
            @Override
            protected Map<String,String>getParams()throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("scheduleId",scheduleId);
                params.put("parentId",parentId);
                params.put("surrogateId",surrogateId);
                params.put("bookingDate",bookingDate);
                params.put("serviceFee",serviceFee);
                params.put("surrogateFee",surrogateFee);
                params.put("totalFee",totalFee);
                params.put("paymentCode",paymentCode);
                params.put("scheduleType",scheduleType);
                Log.e("PARAMS",""+params);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void alertApprove(){
        android.app.AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Approve Payment");
        alertDialog.setCancelable(false);
        alertDialog.setButton2("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                return;
            }
        });
        alertDialog.setButton("Approve ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                approvePayment();
                return;
            }
        });
        alertDialog.show();
    }
}
