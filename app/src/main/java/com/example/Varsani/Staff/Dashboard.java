package com.example.Varsani.Staff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.MainActivity;
import com.example.Varsani.R;
import com.example.Varsani.Suppliers.MyRequests;
import com.example.Varsani.Staff.Attorney.NewAppointments;
import com.example.Varsani.Staff.Doctor.*;
import com.example.Varsani.Staff.Finance.*;
import com.example.Varsani.Staff.LabTech.*;
import com.example.Varsani.Staff.Pharmacist.NewPrescriptions;
import com.example.Varsani.Staff.Store_mrg.*;
import com.example.Varsani.Staff.Technician.DonorTests;
import com.example.Varsani.utils.SessionHandler;
import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View contextView;
    private SessionHandler session;
    private UserModel user;
    private int k = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_bar);
        setSupportActionBar(toolbar);

        contextView = findViewById(android.R.id.content);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this::handleNavigationItemSelected);
        drawer.closeDrawers();

        checkMenuVisibility();
    }

    private boolean handleNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
        } else if (id == R.id.nav_new_orders) {
            startActivity(new Intent(getApplicationContext(), NewOrders.class));
        } else if (id == R.id.nav_approvedOrders) {
            startActivity(new Intent(getApplicationContext(), ApprovedOrders.class));
        } else if (id == R.id.nav_new_serv_payments) {
            startActivity(new Intent(getApplicationContext(), NewServicePayments.class));
        } else if (id == R.id.nav_approved_serv_payments) {
            startActivity(new Intent(getApplicationContext(), ApprovedServPayments.class));
        } else if (id == R.id.nav_supplier_payments) {
            startActivity(new Intent(getApplicationContext(), SupplyPayments.class));
        } else if (id == R.id.nav_stock) {
            startActivity(new Intent(getApplicationContext(), ViewStock.class));
        } else if (id == R.id.nav_med_stock) {
            startActivity(new Intent(getApplicationContext(), MedicineStock.class));
        } else if (id == R.id.nav_new_surrogates) {
            startActivity(new Intent(getApplicationContext(), NewSurrogates.class));
        } else if (id == R.id.nav_new_donor) {
            startActivity(new Intent(getApplicationContext(), NewDonors.class));
        } else if (id == R.id.nav_approved_surrogacy) {
            startActivity(new Intent(getApplicationContext(), ApprovedSurrogacy.class));
        } else if (id == R.id.nav_med_test) {
            startActivity(new Intent(getApplicationContext(), MedicalTests.class));
        } else if (id == R.id.nav_donor_test) {
            startActivity(new Intent(getApplicationContext(), DonorTests.class));
        } else if (id == R.id.nav_fertility_test) {
            startActivity(new Intent(getApplicationContext(), FertilityTests.class));
        } else if (id == R.id.nav_new_appointments) {
            startActivity(new Intent(getApplicationContext(), NewAppointments.class));
        } else if (id == R.id.nav_new_prescription) {
            startActivity(new Intent(getApplicationContext(), NewPrescriptions.class));
        } else if (id == R.id.nav_pending_surrogacy) {
            startActivity(new Intent(getApplicationContext(), PendingSurrogacy.class));
        } else if (id == R.id.nav_check_up) {
            startActivity(new Intent(getApplicationContext(), Checkups.class));
        } else if (id == R.id.nav_materials) {
            startActivity(new Intent(getApplicationContext(), RequestedMaterials.class));
        } else if (id == R.id.nav_equipments) {
            startActivity(new Intent(getApplicationContext(), RequestedEquipments.class));
        } else if (id == R.id.nav_medicine) {
            startActivity(new Intent(getApplicationContext(), RequestedMedicine.class));
        } else if (id == R.id.nav_staff_feedback) {
            startActivity(new Intent(getApplicationContext(), Stafffeedback.class));
        } else if (id == R.id.nav_supplies) {
            startActivity(new Intent(getApplicationContext(), RequestItems.class));
        } else if (id == R.id.nav_supply_requests) {
            startActivity(new Intent(getApplicationContext(), MyRequests.class));
        } else if (id == R.id.nav_logout) {
            alertLogout();
        }

        drawer.closeDrawer(GravityCompat.START, true);
        return true;
    }

    public void alertLogout() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Do you want to logout?");
        alertDialog.setCancelable(false);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes logout", (dialog, which) -> {
            session.logoutUser();
            Toast toast = Toast.makeText(getApplicationContext(), "You are logged out", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressedExitApp();
            return true;
        }
        return super.onKeyDown(keyCode, e);
    }

    @Override
    protected void onStart() {
        super.onStart();
        k = 0;
    }

    private void onBackPressedExitApp() {
        k++;
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Exit App");
        alertDialog.setIcon(R.drawable.ic_notifications);
        alertDialog.setMessage("Do you really want to exit?");
        alertDialog.setCancelable(false);

        if (k == 1) {
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialog, which) -> {
                finish();
                Intent homeScreenIntent = new Intent(Intent.ACTION_MAIN);
                homeScreenIntent.addCategory(Intent.CATEGORY_HOME);
                homeScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homeScreenIntent);
            });
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", (dialog, which) -> {
                dialog.cancel();
                k = 0;
            });
            alertDialog.show();
        }
    }

    private void checkMenuVisibility() {
        // Hide all items first
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setVisible(false);
        }

        navigationView.getMenu().findItem(R.id.nav_staff_feedback).setVisible(true);

        if (session.isLoggedIn()) {
            String type = user.getUser_type();
            if ("Finance".equals(type)) {
                navigationView.getMenu().findItem(R.id.nav_new_orders).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_supplier_payments).setVisible(true);
            } else if ("Shipping Manager".equals(type)) {
                navigationView.getMenu().findItem(R.id.nav_orders_to_shipp).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_shipping_orders).setVisible(true);
            } else if ("Inventory Manager".equals(type)) {
                navigationView.getMenu().findItem(R.id.nav_stock).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_supplies).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_materials).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_equipments).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_medicine).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_med_stock).setVisible(true);
            } else if ("Doctor".equals(type)) {
                navigationView.getMenu().findItem(R.id.nav_new_surrogates).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_approved_surrogacy).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_pending_surrogacy).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_new_donor).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_check_up).setVisible(true);
            } else if ("Lab Technician".equals(type)) {
                navigationView.getMenu().findItem(R.id.nav_med_test).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_fertility_test).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_donor_test).setVisible(true);
            } else if ("Attorney".equals(type)) {
                navigationView.getMenu().findItem(R.id.nav_new_appointments).setVisible(true);
            } else if ("Pharmacist".equals(type)) {
                navigationView.getMenu().findItem(R.id.nav_new_prescription).setVisible(true);
            } else if ("Technician".equals(type)) {
                navigationView.getMenu().findItem(R.id.nav_quot_visit).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_assigned_services).setVisible(true);
            } else if ("Supplier".equals(type)) {
                navigationView.getMenu().findItem(R.id.nav_supply_requests).setVisible(true);
            }
        }
    }
}
