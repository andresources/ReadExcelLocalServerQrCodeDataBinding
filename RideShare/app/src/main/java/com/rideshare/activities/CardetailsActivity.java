package com.rideshare.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rideshare.R;
import com.rideshare.Utils;
import com.rideshare.adapters.CarDetailsAdapter;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.CarDetailsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardetailsActivity extends AppCompatActivity {
    TextView tv_add_work;
    ImageView image_add;
    List<CarDetailsPojo> carDetailsPojo;
    ListView list_view;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String Session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardetails);
        sharedPreferences=getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        Session=sharedPreferences.getString("uname","def_val");

        getSupportActionBar().setTitle("My Cars");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);
        tv_add_work=(TextView)findViewById(R.id.tv_add_work);
        tv_add_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CardetailsActivity.this,AddCarActivity.class));
                finish();
            }
        });
        image_add=(ImageView)findViewById(R.id.image_add);
        image_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CardetailsActivity.this,AddCarActivity.class));
                finish();


            }
        });

        carDetailsPojo=new ArrayList<>();
        getMycarDetails();
    }

    public void getMycarDetails(){
        progressDialog = new ProgressDialog(CardetailsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<CarDetailsPojo>> call = service.getmycars(Session);
        call.enqueue(new Callback<List<CarDetailsPojo>>() {
            @Override
            public void onResponse(Call<List<CarDetailsPojo>> call, Response<List<CarDetailsPojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(CardetailsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    carDetailsPojo = response.body();
                    list_view.setAdapter(new CarDetailsAdapter(carDetailsPojo, CardetailsActivity.this));

                }
            }
            @Override
            public void onFailure(Call<List<CarDetailsPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CardetailsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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