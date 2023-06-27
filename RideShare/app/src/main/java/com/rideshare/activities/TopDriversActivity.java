package com.rideshare.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rideshare.R;
import com.rideshare.Utils;
import com.rideshare.adapters.ToppersAdapter;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.ToppersPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopDriversActivity extends AppCompatActivity {

    List<ToppersPojo> inboxPojos;
    ToppersAdapter inboxAdapter;
    ListView list_view;
    ProgressDialog progressDialog;
    EditText et_search;
    SharedPreferences sharedPreferences;
    String session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_drivers);

        getSupportActionBar().setTitle("Top Drivers");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");

        list_view=(ListView)findViewById(R.id.list_view);

        inboxPojos=new ArrayList<>();
        getReguestedRides();
    }
    public void getReguestedRides(){
        progressDialog = new ProgressDialog(TopDriversActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<ToppersPojo>> call = service.topriders();
        call.enqueue(new Callback<List<ToppersPojo>>() {
            @Override
            public void onResponse(Call<List<ToppersPojo>> call, Response<List<ToppersPojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(TopDriversActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    inboxPojos=response.body();
                    inboxAdapter =new ToppersAdapter(inboxPojos, TopDriversActivity.this);
                    list_view.setAdapter(inboxAdapter);

                }
            }
            @Override
            public void onFailure(Call<List<ToppersPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TopDriversActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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