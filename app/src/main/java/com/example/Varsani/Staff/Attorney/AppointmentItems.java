package com.example.Varsani.Staff.Attorney;

import static com.example.Varsani.utils.Urls.URL_UPLOAD_AGREEMENT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.R;
import com.example.Varsani.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AppointmentItems extends AppCompatActivity {
    private TextView tvParentName,tvParentNo,tvParentEmail,tvPartnerName,tvPartnerNumber,tvPartnerEmail,
            tvSurrogateName,tvSurrogateNumber,tvSurrogateEmail,tvSelectedFile,txv_type;
    private Button btnSelectFile,btnUploadAgreement;
    private ProgressBar progressBar;
    private String scheduleId,Id;
    private RequestQueue rQueue;
    Uri uri;
    String displayName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_items);

        tvParentName = findViewById(R.id.tvParentName);
        tvParentNo = findViewById(R.id.tvParentNo);
        tvParentEmail = findViewById(R.id.tvParentEmail);
        tvPartnerName = findViewById(R.id.tvPartnerName);
        tvPartnerNumber = findViewById(R.id.tvPartnerNumber);
        tvPartnerEmail = findViewById(R.id.tvPartnerEmail);
        tvSurrogateName = findViewById(R.id.tvSurrogateName);
        tvSurrogateNumber = findViewById(R.id.tvSurrogateNumber);
        tvSurrogateEmail = findViewById(R.id.tvSurrogateEmail);
        tvSelectedFile = findViewById(R.id.tvSelectedFile);
        btnSelectFile = findViewById(R.id.btnSelectFile);
        btnUploadAgreement = findViewById(R.id.btnUploadAgreement);
        progressBar = findViewById(R.id.progressBar);
        txv_type = findViewById(R.id.txv_type);

        Intent intent = getIntent();
        Id = intent.getStringExtra("Id");
        scheduleId = intent.getStringExtra("scheduleId");
        String parentId = intent.getStringExtra("parentId");
        String surrogateId = intent.getStringExtra("surrogateId");
        String partnerName = intent.getStringExtra("partnerName");
        String partnerNo = intent.getStringExtra("partnerNo");
        String scheduleDate = intent.getStringExtra("scheduleDate");
        String parentName = intent.getStringExtra("parentName");
        String parentNo = intent.getStringExtra("parentNo");
        String parentEmail = intent.getStringExtra("parentEmail");
        String surrogateName = intent.getStringExtra("surrogateName");
        String surrogateNo = intent.getStringExtra("surrogateNo");
        String surrogateEmail = intent.getStringExtra("surrogateEmail");
        String scheduleType = intent.getStringExtra("scheduleType");

        tvParentName.setText("Name: " + parentName);
        tvParentNo.setText("Phone No: " + parentNo);
        tvParentEmail.setText("Email: " + parentEmail);
        tvPartnerNumber.setText("Phone No: " + partnerNo);
        tvPartnerName.setText("Name: " + partnerName);
        tvSurrogateName.setText("Name: " + surrogateName);
        tvSurrogateNumber.setText("Phone No: " + surrogateNo);
        tvSurrogateEmail.setText("Email: " + surrogateEmail);
        tvParentName.setText("Name: " + parentName);

        if (scheduleType.equals("egg_donor")){
            txv_type.setText("Selected Egg Donor");
        }

        btnSelectFile.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.setType("application/pdf");
            startActivityForResult(i, 1);
        });
        btnUploadAgreement.setOnClickListener(v -> uploadPDF(displayName, uri));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("name  ", displayName);


                        if (!TextUtils.isEmpty(displayName)) {
                           // img_pdf.setVisibility(View.VISIBLE);
                        }
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("name  ", displayName);
            }

            if (!TextUtils.isEmpty(displayName)) {
                Log.d("Selected File", displayName);
                tvSelectedFile.setText("Selected File: " + displayName);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
    private void uploadPDF(final String pdfname, Uri pdffile) {

        InputStream iStream = null;

        progressBar.setVisibility(View.VISIBLE);
        btnSelectFile.setVisibility(View.GONE);
        btnUploadAgreement.setVisibility(View.GONE);


        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL_UPLOAD_AGREEMENT,
                    response -> {
                        Log.d("ressssssoo", new String(response.data));
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            jsonObject.toString().replace("\\\\", "");
                            finish();
//                                Intent in=new Intent(getApplicationContext(),ReportsPendingApproval.class);
//                                startActivity(in);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            progressBar.setVisibility(View.GONE);
                            btnSelectFile.setVisibility(View.VISIBLE);
                            btnUploadAgreement.setVisibility(View.VISIBLE);

                            Log.e("E ", "" + e);
                        }
                    },
                    error -> {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);
                        btnSelectFile.setVisibility(View.VISIBLE);
                        btnUploadAgreement.setVisibility(View.VISIBLE);
                        Log.e("ERROR ", "" + error);
                    }) {

                /*
                 * If you want to add more parameters with the pdf
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Id", Id);
                    params.put("scheduleId", scheduleId);
                    Log.e("PARAMS ", "" + params);
                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    params.put("filename", new DataPart(pdfname, inputData));
                    Log.e("FILE NAME ", "" + params);
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(AppointmentItems.this);
            rQueue.add(volleyMultipartRequest);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}