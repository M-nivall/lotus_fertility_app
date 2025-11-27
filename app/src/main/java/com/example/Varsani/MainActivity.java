package com.example.Varsani;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.Varsani.Clients.CompletedServices;
import com.example.Varsani.Clients.ContactUs;
import com.example.Varsani.Clients.Invoice;
import com.example.Varsani.Clients.ScheduledCheckups;
import com.example.Varsani.Clients.Search;
import com.example.Varsani.Clients.ServiceItems;
import com.example.Varsani.Clients.SupplierLogin;
import com.example.Varsani.Clients.home.HomeFragment;
import com.example.Varsani.Donor.TrackMyApplication;
import com.example.Varsani.IntendedParent.MyBookings;
import com.example.Varsani.IntendedParent.TrackCheckups;
import com.example.Varsani.Staff.Dashboard;
import com.example.Varsani.Staff.SelectLogin;
import com.example.Varsani.Clients.About;
import com.example.Varsani.Clients.Adapters.AdapterProducts;
import com.example.Varsani.Clients.CartItems;
import com.example.Varsani.Clients.Feedback;
import com.example.Varsani.Clients.Help_in;
import com.example.Varsani.Clients.Login;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.Clients.MyApplication;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.Varsani.Clients.Profile;
import com.example.Varsani.Clients.Register;
import com.example.Varsani.Staff.StaffLogin;
import com.example.Varsani.Suppliers.MyRequests;
import com.example.Varsani.Suppliers.RegSuppliers;
import com.example.Varsani.Suppliers.Requests;
import com.example.Varsani.utils.SessionHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private UserModel user;
    private SessionHandler session;
    private AdapterProducts adapterProducts;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_bar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        FloatingActionButton fab = findViewById(R.id.fab);

        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();
        homeFragment = new HomeFragment();

        fab.setOnClickListener(view -> {
            if (session.isLoggedIn()) {
                startActivity(new Intent(getApplicationContext(), CartItems.class));
            } else {
                Snackbar.make(view, "You must login to view cart", Snackbar.LENGTH_LONG).show();
            }
        });

        if (session.isLoggedIn()) {
            if (!user.getUser_type().equals("Intended Parents") &&
                    !user.getUser_type().equals("Surrogate Mother") &&
                    !user.getUser_type().equals("Egg Donor")) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                finish();
            }
        }

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
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(getApplicationContext(), Profile.class));
        } else if (id == R.id.nav_request) {
            startActivity(new Intent(getApplicationContext(), MyRequests.class));
        } else if (id == R.id.nav_register) {
            startActivity(new Intent(getApplicationContext(), Register.class));
        } else if (id == R.id.nav_reg_supplier) {
            startActivity(new Intent(getApplicationContext(), RegSuppliers.class));
        } else if (id == R.id.nav_login) {
            startActivity(new Intent(getApplicationContext(), Login.class));
        } else if (id == R.id.nav_contact) {
            startActivity(new Intent(getApplicationContext(), ContactUs.class));
        } else if (id == R.id.nav_supplier_login) {
            startActivity(new Intent(getApplicationContext(), SupplierLogin.class));
        } else if (id == R.id.nav_bookings) {
            startActivity(new Intent(getApplicationContext(), ServiceItems.class));
        } else if (id == R.id.nav_completion) {
            startActivity(new Intent(getApplicationContext(), CompletedServices.class));
        } else if (id == R.id.nav_invoice) {
            startActivity(new Intent(getApplicationContext(), Invoice.class));
        } else if (id == R.id.nav_track_check_up) {
            startActivity(new Intent(getApplicationContext(), TrackCheckups.class));
        } else if (id == R.id.nav_my_booking) {
            startActivity(new Intent(getApplicationContext(), MyBookings.class));
        } else if (id == R.id.nav_scheduled_checkups) {
            startActivity(new Intent(getApplicationContext(), ScheduledCheckups.class));
        } else if (id == R.id.nav_my_application) {
            if ("Surrogate Mother".equals(user.getUser_type())) {
                startActivity(new Intent(getApplicationContext(), MyApplication.class));
            } else if ("Egg Donor".equals(user.getUser_type())) {
                startActivity(new Intent(getApplicationContext(), TrackMyApplication.class));
            } else {
                startActivity(new Intent(getApplicationContext(), Requests.class));
            }
        } else if (id == R.id.nav_feedback) {
            startActivity(new Intent(getApplicationContext(), Feedback.class));
        } else if (id == R.id.nav_staff_login) {
            startActivity(new Intent(getApplicationContext(), StaffLogin.class));
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(getApplicationContext(), Help_in.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(getApplicationContext(), About.class));
        } else if (id == R.id.nav_logout) {
            alertLogout();
        }

        drawer.closeDrawer(GravityCompat.START, true);
        return true;
    }

    private void alertLogout() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Do you want to logout?");
        alertDialog.setCancelable(false);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes logout", (dialog, which) -> {
            session.logoutUser();
            Toast.makeText(getApplicationContext(), "You are logged out", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());
        });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }

    private void checkMenuVisibility() {
        // Hide all by default
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setVisible(false);
        }

        // Always visible items
        navigationView.getMenu().findItem(R.id.nav_home).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_contact).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_help).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_about).setVisible(true);

        if (session.isLoggedIn()) {
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_request).setVisible(true);

            switch (user.getUser_type()) {
                case "Surrogate Mother":
                    navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_feedback).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_my_application).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_scheduled_checkups).setVisible(true);
                    break;
                case "Intended Parents":
                    navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_feedback).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_invoice).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_my_booking).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_track_check_up).setVisible(true);
                    break;
                case "Egg Donor":
                    navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_feedback).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_my_application).setVisible(true);
                    break;
                case "Client":
                    navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
                    navigationView.getMenu().findItem(R.id.nav_feedback).setVisible(true);
                    break;
            }
        } else {
            navigationView.getMenu().findItem(R.id.nav_register).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_staff_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_supplier_login).setVisible(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), Search.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
