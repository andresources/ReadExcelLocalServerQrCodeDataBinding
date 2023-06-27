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
import com.rideshare.adapters.MyPostRidesAdapter;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.MyPostRidesPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostRidesAActivity extends AppCompatActivity {

    List<MyPostRidesPojo> myPostRidesPojos;
    MyPostRidesAdapter myPostRidesAdapter;
    ListView list_view;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String Session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides_list);


        getSupportActionBar().setTitle("My Post Rides");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedPreferences=getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        Session=sharedPreferences.getString("uname","def_val");

        list_view=(ListView)findViewById(R.id.list_view);

        myPostRidesPojos=new ArrayList<>();
        myPostRides();

    }
    public void myPostRides(){
        progressDialog = new ProgressDialog(MyPostRidesAActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<MyPostRidesPojo>> call = service.getmypostrides(Session);
        call.enqueue(new Callback<List<MyPostRidesPojo>>() {
            @Override
            public void onResponse(Call<List<MyPostRidesPojo>> call, Response<List<MyPostRidesPojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(MyPostRidesAActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    myPostRidesPojos = response.body();
                    list_view.setAdapter(new MyPostRidesAdapter(myPostRidesPojos, MyPostRidesAActivity.this));

                }
            }
            @Override
            public void onFailure(Call<List<MyPostRidesPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MyPostRidesAActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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