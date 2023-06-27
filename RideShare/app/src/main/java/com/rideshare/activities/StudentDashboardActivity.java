package com.rideshare.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.rideshare.R;
import com.rideshare.Utils;
import com.rideshare.adapters.StudentAvailableRidesAdapter;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.RidesListPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentDashboardActivity extends AppCompatActivity {

    List<RidesListPojo> ridesListPojo;
    StudentAvailableRidesAdapter studentAvailableRidesAdapter;
    ListView list_view;
    ProgressDialog progressDialog;
    EditText et_search;
    Button btnSearch;
    SharedPreferences sharedPreferences;
    String session;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        getSupportActionBar().setTitle("Student Dashboard");
        navigationView();


        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashboardActivity.this, SelectSearchOptionsActivity.class));
            }
        });

        list_view = (ListView) findViewById(R.id.list_view);

        ridesListPojo = new ArrayList<>();
        getAvailableRides();

    }

    public void getAvailableRides() {
        progressDialog = new ProgressDialog(StudentDashboardActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<RidesListPojo>> call = service.getAvailableRides(session);
        call.enqueue(new Callback<List<RidesListPojo>>() {
            @Override
            public void onResponse(Call<List<RidesListPojo>> call, Response<List<RidesListPojo>> response) {
                progressDialog.dismiss();
                if (response.body() == null) {
                    Toast.makeText(StudentDashboardActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    ridesListPojo = response.body();
                    studentAvailableRidesAdapter = new StudentAvailableRidesAdapter(ridesListPojo, StudentDashboardActivity.this);
                    list_view.setAdapter(studentAvailableRidesAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<RidesListPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(StudentDashboardActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigationView() {
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    /*case R.id.myrides:
                        Intent intent = new Intent(getApplicationContext(), MyPostRidesAActivity.class);
                        startActivity(intent);
                        break;*/
                    case R.id.ride_history:
                        Intent intent1 = new Intent(getApplicationContext(), UserRideHistoryActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ride_chat:
                        Intent add_car = new Intent(getApplicationContext(), StudentRequestActivity.class);
                        startActivity(add_car);
                        break;

                    case R.id.top_ride:
                        Intent top = new Intent(getApplicationContext(), TopDriversActivity.class);
                        startActivity(top);
                        break;



                    case R.id.edit_profile:
                        Intent view_jobs = new Intent(getApplicationContext(), EditProfileActivity.class);
                        startActivity(view_jobs);
                        break;

                    case R.id.ride_inbox:
                        Intent inbox = new Intent(getApplicationContext(), UserInboxActivity.class);
                        startActivity(inbox);
                        break;

                    case R.id.logout:
                        Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(logout);
                        finish();
                        break;

                    default:
                        return true;
                }
                dl.closeDrawer(GravityCompat.START);
                return true;

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        }
        if (id == R.id.driverDashboard) {
            Intent logout = new Intent(getApplicationContext(), UserDashBoardActivity.class);
            startActivity(logout);

        } else {
            dl.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


}