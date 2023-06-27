package com.rideshare.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rideshare.R;
import com.rideshare.Utils;
import com.rideshare.adapters.RideHistoryAdapter;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.MyRideHistoryPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverRideHistoryActivity extends AppCompatActivity {
    List<MyRideHistoryPojo> myRideHistoryPojo;
    ListView list_view;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String Session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myride_history);

        sharedPreferences=getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        Session=sharedPreferences.getString("uname","def_val");

        getSupportActionBar().setTitle("Ride History");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);


        myRideHistoryPojo=new ArrayList<>();
        getMyRideHistory();
    }

    public void getMyRideHistory(){
        progressDialog = new ProgressDialog(DriverRideHistoryActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<MyRideHistoryPojo>> call = service.driverRideHistory(Session);
        call.enqueue(new Callback<List<MyRideHistoryPojo>>() {
            @Override
            public void onResponse(Call<List<MyRideHistoryPojo>> call, Response<List<MyRideHistoryPojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(DriverRideHistoryActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    myRideHistoryPojo = response.body();
                    list_view.setAdapter(new RideHistoryAdapter(myRideHistoryPojo, DriverRideHistoryActivity.this));

                }
            }
            @Override
            public void onFailure(Call<List<MyRideHistoryPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DriverRideHistoryActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}