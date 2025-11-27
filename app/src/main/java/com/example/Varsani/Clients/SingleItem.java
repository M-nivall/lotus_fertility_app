package com.example.Varsani.Clients;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SingleItem extends AppCompatActivity {
    private static final String TAG = "Add to cart";
    private SessionHandler session;
    private UserModel user;

    private String surrogateID;
    private String fullName;
    private String height,weight,education,town,county,age,role;
    private String bloodType;
    private String image;
    private String price;

    private EditText quantity;

    private Button btn_proceed;
    private RelativeLayout layout_single;

    private TextView txv_fee,txv_full_name,txv_age,txv_blood_type,txv_height,txv_weight,txv_education,txv_location;
    private ImageView image_photo;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        getSupportActionBar().setSubtitle(fullName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        txv_full_name=findViewById(R.id.txv_full_name);
        txv_fee=findViewById(R.id.txv_fee);
        txv_age=findViewById(R.id.txv_age);
        txv_blood_type=findViewById(R.id.txv_blood_type);
        image_photo=findViewById(R.id.image_photo);
        txv_height = findViewById(R.id.txv_height);
        txv_weight = findViewById(R.id.txv_weight);
        txv_education = findViewById(R.id.txv_education);
        txv_location = findViewById(R.id.txv_location);
        progressBar=findViewById(R.id.progressBar);
        //btn_proceed=findViewById(R.id.btn_add_cart);
        btn_proceed = findViewById(R.id.btn_proceed);
        layout_single=findViewById(R.id.layout_bottom);

        Intent intent = getIntent();
        surrogateID = intent.getStringExtra("surrogateID");
        fullName = intent.getStringExtra("fullName");
        height = intent.getStringExtra("height");
        bloodType = intent.getStringExtra("bloodType");
        weight = intent.getStringExtra("weight");
        education = intent.getStringExtra("education");
        town = intent.getStringExtra("town");
        county = intent.getStringExtra("county");
        age = intent.getStringExtra("age");
        bloodType = intent.getStringExtra("bloodType");
        image = intent.getStringExtra("image");
        price = intent.getStringExtra("price");
        role = intent.getStringExtra("role");

        progressBar.setVisibility(View.GONE);
        txv_full_name.setText("Name: " + fullName);
        txv_fee.setText("Fee: "+price+ "/=");
        txv_age.setText("Age: " + age + " Years");
        txv_blood_type.setText("Blood Group: " + bloodType);
        txv_height.setText("Height: " + height + "cm");
        txv_weight.setText("Weight: " + weight + "kg");
        txv_education.setText("Education Level: " + education);
        txv_location.setText("Location: " + town + ", " + county);

        String url = Urls.ROOT_URL_UPLOADS;
        Picasso.get().load(url+image )
                .fit()
                .centerCrop()
                .into(image_photo );

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CheckOut.class);
                intent.putExtra("surrogateID", surrogateID);
                intent.putExtra("fullName", fullName);
                intent.putExtra("age", age);
                intent.putExtra("price", price);
                intent.putExtra("role", role);
                startActivity(intent);
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
    public void qtyPrompts(){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompts, null);

        //Create the AlertDialog with a reference to edit it later
        final AlertDialog dialog = new AlertDialog.Builder(this)
//                .setView(v)
                .setCancelable(false)
                .setPositiveButton("Add", null) //Set to null. We override the onclick
                .setNegativeButton("Close", null)
                .create();
        dialog.setView(promptsView);

        quantity =  promptsView.findViewById(R.id.edt_quantity);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(quantity.getText().toString())) {
                            quantity.setError( "Enter item quantity" );
                        }else{
                            dialog.dismiss();
                            progressBar.setVisibility(View.VISIBLE);
                            addToCart();
                        }
                    }
                });
            }
        });
        dialog.show();
    }
    public void addToCart(){
        btn_proceed.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        final String itemQty = quantity.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.URL_ADD_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("response ",response);

                            String msg = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1){
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btn_proceed.setVisibility(View.GONE);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btn_proceed.setVisibility(View.VISIBLE);
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "error"+e.toString(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            btn_proceed.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btn_proceed.setVisibility(View.VISIBLE);


            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("clientID", user.getClientID());
                params.put("productID", surrogateID);
                params.put("quantity", itemQty);
                Log.e(TAG, "params is "+params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
