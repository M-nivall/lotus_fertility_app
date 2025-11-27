package com.example.Varsani.Staff.Doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Varsani.Clients.Login;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.R;
import com.example.Varsani.utils.SessionHandler;

import java.util.ArrayList;
import java.util.List;

public class SelectAction extends AppCompatActivity {
    private SessionHandler sessionHandler;
    private UserModel userModel;
    private ListView listView;
    private TextView txvSubjectName;
    private String checkID,surrogateID,parentID,scheduleID,surrogateName,userType;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action);

        listView = findViewById(R.id.listView);
        txvSubjectName = findViewById(R.id.txvSubjectName);

        sessionHandler = new SessionHandler(this);
        userModel = sessionHandler.getUserDetails();

        Intent in = getIntent();
        txvSubjectName.setText("Name: " + in.getStringExtra("surrogateName"));
        checkID = in.getStringExtra("checkID");
        scheduleID = in.getStringExtra("scheduleID");
        surrogateID = in.getStringExtra("surrogateID");
        parentID = in.getStringExtra("parentID");
        surrogateName = in.getStringExtra("surrogateName");
        userType = in.getStringExtra("userType");
        userType = in.getStringExtra("userType");
        userType = in.getStringExtra("userType");
        userType = in.getStringExtra("userType");

        // Original list of items
        String[] listItems = {"First Checkup", "Second Checkup", "Delivery Checkup"};

        // Check the user type (teacher or not )
        boolean isNotInstructor = (!userModel.getUser_type().equals("Instructor"));

        List<String> filteredList = new ArrayList<>();

        // Add items to the filtered list based on the user type
        for (String item : listItems) {
            // Hide "Post video tutorials" and "Post PDF tutorials" if the user not an instructor
            if (isNotInstructor && (item.equals("Post video tutorials") || item.equals("Post PDF tutorials"))) {
                continue;
            }
            // Otherwise, add the item to the filtered list
            filteredList.add(item);
        }

        // Convert the filtered list back to an array
        String[] filteredItems = filteredList.toArray(new String[0]);

        // Set the adapter with the filtered list of items
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                (getApplicationContext(), R.layout.list_listview, R.id.textView, filteredItems);
        listView.setAdapter(arrayAdapter);


        getList();
    }
    private void getList() {
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i) {
                case 0:
                    intent = new Intent(getApplicationContext(), FirstCheckup.class);
                    intent.putExtra("scheduleID", scheduleID);
                    intent.putExtra("checkID", checkID);
                    intent.putExtra("surrogateID", surrogateID);
                    intent.putExtra("parentID", parentID);
                    intent.putExtra("surrogateName", surrogateName);
                    intent.putExtra("userType", userType);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(getApplicationContext(), Login.class);
                    intent.putExtra("scheduleID", scheduleID);
                    intent.putExtra("checkID", checkID);
                    intent.putExtra("surrogateID", surrogateID);
                    intent.putExtra("parentID", parentID);
                    intent.putExtra("surrogateName", surrogateName);
                    intent.putExtra("userType", userType);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(getApplicationContext(), NewDonors.class);
                    intent.putExtra("scheduleID", scheduleID);
                    intent.putExtra("checkID", checkID);
                    intent.putExtra("surrogateID", surrogateID);
                    intent.putExtra("parentID", parentID);
                    intent.putExtra("surrogateName", surrogateName);
                    intent.putExtra("userType", userType);
                    startActivity(intent);
                    break;
            }


        });
    }
}