package com.example.Varsani.Clients;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Base64;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Varsani.MainActivity;
import com.example.Varsani.VolleyMultipartRequest;
import com.example.Varsani.utils.SessionHandler;
import com.example.Varsani.utils.Urls;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ApplicationForm extends AppCompatActivity {
    private  TextInputEditText et_height,et_weight,
            et_more_details,et_medication,et_num_children;
    private TextView txv_type;
    private  Button btn_submit,btn_upload_id,btn_upload_medical,btn_upload_photo;
    private  ProgressBar progressBar;
    private  RadioGroup  radio_group_medication,radio_group_children;
    private TextInputLayout et_medication_layout,et_num_children_layout;
    private SessionHandler session;
    private UserModel user;
    private static final int PICK_ID_IMAGE_REQUEST = 1;
    private static final int PICK_MEDICAL_IMAGE_REQUEST = 2;
    private static final int PICK_PHOTO_IMAGE_REQUEST = 3;
    private Uri idImageUri, medicalImageUri, photoImageUri;
    private  String role;
    private Spinner spinner_blood_group,spinner_marital_status,spinner_education_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form);

        getSupportActionBar().setTitle("Send  Quotation Request ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        radio_group_medication = findViewById(R.id.radio_group_medication);
        et_medication_layout = findViewById(R.id.et_medication_layout);
        et_medication = findViewById(R.id.et_medication);
        radio_group_children = findViewById(R.id.radio_group_children);
        et_num_children_layout = findViewById(R.id.et_num_children_layout);
        et_num_children = findViewById(R.id.et_num_children);
        txv_type = findViewById(R.id.txv_type);
        et_more_details = findViewById(R.id.et_more_details);
        btn_submit = findViewById(R.id.btn_submit);
        btn_upload_id = findViewById(R.id.btn_upload_id);
        btn_upload_medical = findViewById(R.id.btn_upload_medical);
        btn_upload_photo = findViewById(R.id.btn_upload_photo);
        progressBar = findViewById(R.id.progress_bar);

        spinner_blood_group=findViewById(R.id.spinner_blood_group);
        spinner_marital_status=findViewById(R.id.spinner_marital_status);
        spinner_education_level=findViewById(R.id.spinner_education_level);

        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.blood_group,android.R.layout.simple_spinner_dropdown_item);
        spinner_blood_group.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterMarital= ArrayAdapter.createFromResource(this,R.array.marital_status,android.R.layout.simple_spinner_dropdown_item);
        spinner_marital_status.setAdapter(adapterMarital);

        ArrayAdapter<CharSequence> adapterEducation= ArrayAdapter.createFromResource(this,R.array.education_level,android.R.layout.simple_spinner_dropdown_item);
        spinner_education_level.setAdapter(adapterEducation);

        Intent intent = getIntent();

        role = intent.getStringExtra("role");
        if (role != null) {
            if (role.equals("egg_donor")) {
                txv_type.setText("Egg Donor Application Form");
            }
        }

        radio_group_medication.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_m_yes) {
                et_medication_layout.setVisibility(View.VISIBLE);
                et_medication.setText("");
            } else if (checkedId == R.id.radio_m_no) {
                et_medication_layout.setVisibility(View.GONE);
                et_medication.setText("N/A");
            }
        });
        radio_group_children.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_child_yes) {
                et_num_children_layout.setVisibility(View.VISIBLE);
                et_num_children.setText("");
            } else if (checkedId == R.id.radio_child_no) {
                et_num_children_layout.setVisibility(View.GONE);
                et_num_children.setText("N/A");
            }
        });
        btn_upload_id.setOnClickListener(v -> selectFile(PICK_ID_IMAGE_REQUEST));
        btn_upload_medical.setOnClickListener(v -> selectFile(PICK_MEDICAL_IMAGE_REQUEST));
        btn_upload_photo.setOnClickListener(v -> selectFile(PICK_PHOTO_IMAGE_REQUEST));

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertApply(v);

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

    private void selectFile(int requestCode) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
        } else {
            openFileChooser(requestCode);
        }
    }
    private void openFileChooser(int requestCode) {
        Intent intent = new Intent();
        if (requestCode == PICK_MEDICAL_IMAGE_REQUEST) {
            intent.setType("application/pdf"); // For PDFs
        } else {
            intent.setType("image/*"); // For images
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedFileUri = data.getData();
            if (requestCode == PICK_ID_IMAGE_REQUEST) {
                idImageUri = selectedFileUri;
                Toast.makeText(this, "ID Image Selected", Toast.LENGTH_SHORT).show();
            } else if (requestCode == PICK_MEDICAL_IMAGE_REQUEST) {
                medicalImageUri = selectedFileUri;
                Toast.makeText(this, "Medical PDF Selected", Toast.LENGTH_SHORT).show();
            } else if (requestCode == PICK_PHOTO_IMAGE_REQUEST) {
                photoImageUri = selectedFileUri;
                Toast.makeText(this, "Photo Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void alertApply(final  View v){
        AlertDialog.Builder  builder=new AlertDialog.Builder(v.getContext());
        builder.setTitle("Submit you Application ")
                .setNegativeButton("Close",null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        applyNow();
                        dialog.dismiss();
                    }
                }).create().show();
    }
    public void applyNow() {

        progressBar.setVisibility(View.VISIBLE);
        btn_submit.setVisibility(View.GONE);

        final String height = et_height.getText().toString().trim();
        final String weight = et_weight.getText().toString().trim();
        final String medication = et_medication.getText().toString().trim();
        final String bloodType=spinner_blood_group.getSelectedItem().toString().trim();
        final String marital_status=spinner_marital_status.getSelectedItem().toString().trim();
        final String education=spinner_education_level.getSelectedItem().toString().trim();
        final String num_children = et_num_children.getText().toString().trim();
        final String more_details = et_more_details.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(height)) {
            showToast("Please enter your height");
            return;
        }
        if (TextUtils.isEmpty(weight)) {
            showToast("Please enter your weight");
            return;
        }
        if (TextUtils.isEmpty(bloodType)) {
            showToast("Please enter your blood group type");
            return;
        }
        if (radio_group_medication.getCheckedRadioButtonId() == -1) {
            showToast("Please select if you are on any medication");
            return;
        }
        if (radio_group_children.getCheckedRadioButtonId() == -1) {
            showToast("Please select if you have any children");
            return;
        }
        if (TextUtils.isEmpty(marital_status)) {
            showToast("Please enter your marital status");
            return;
        }
        if (TextUtils.isEmpty(education)) {
            showToast("Please enter your highest level of study");
            return;
        }
        if (TextUtils.isEmpty(more_details)) {
            showToast("Please tell us more about you");
            return;
        }

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Urls.URL_SUBMIT_APPLICATION,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(new String(response.data));
                        String status = jsonObject.getString("status");
                        String msg = jsonObject.getString("message");

                        if ("1".equals(status)) {
                            showSuccessDialog(msg);
                        } else {
                            showToast(msg);
                            btn_submit.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showToast(e.toString());
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    btn_submit.setVisibility(View.VISIBLE);
                    error.printStackTrace();
                    showToast(error.toString());
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userID", user.getClientID());
                params.put("height", height);
                params.put("weight", weight);
                params.put("bloodType", bloodType);
                params.put("medication", medication);
                params.put("maritalStatus", marital_status);
                params.put("education", education);
                params.put("numChildren", num_children);
                params.put("moreDetails", more_details);
                params.put("role", role);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                if (idImageUri != null) {
                    String idImageName = getFileName(idImageUri);
                    params.put("idImage", new DataPart(idImageName, getFileDataFromUri(idImageUri)));
                }
                if (medicalImageUri != null) {
                    String medicalImageName = getFileName(medicalImageUri);
                    params.put("medicalImage", new DataPart(medicalImageName, getFileDataFromUri(medicalImageUri)));
                }
                if (photoImageUri != null) {
                    String photoImageName = getFileName(photoImageUri);
                    params.put("photoImage", new DataPart(photoImageName, getFileDataFromUri(photoImageUri)));
                }

                return params;
            }

        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(volleyMultipartRequest);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        btn_submit.setVisibility(View.VISIBLE);
    }

    private void showSuccessDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ApplicationForm.this);
        builder.setTitle("Success")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    dialog.dismiss();
                });
        builder.setCancelable(false).create().show();
    }
    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String fileName = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (fileName == null) {
            fileName = uri.getPath();
            int cut = fileName.lastIndexOf('/');
            if (cut != -1) {
                fileName = fileName.substring(cut + 1);
            }
        }
        return fileName;
    }


    private byte[] getFileDataFromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
