package com.rideshare.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rideshare.R;
import com.rideshare.Utils;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRideDetailsActivity extends AppCompatActivity {
    TextView tvFrom,tvTo,tvDate,tvCarname,tvCarColor,tvCarNumPlate,tvCarSeats,tvTime,tvDrivername;
    EditText etMessage;
    String rideId,Uname,session;
    SharedPreferences sharedPreferences;
    Button btnMakeRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_ride_details);

        getSupportActionBar().setTitle("Ride Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvFrom=(TextView)findViewById(R.id.tvFrom);
        tvTo=(TextView)findViewById(R.id.tvTo);
        tvDate=(TextView)findViewById(R.id.tvDate);
        tvCarname=(TextView)findViewById(R.id.tvCarname);
        tvCarColor=(TextView)findViewById(R.id.tvCarColor);
        tvCarNumPlate=(TextView)findViewById(R.id.tvCarNumPlate);
        tvCarSeats=(TextView)findViewById(R.id.tvCarSeats);
        tvTime=(TextView)findViewById(R.id.tvTime);

        etMessage=(EditText)findViewById(R.id.etMessage);
        rideId=getIntent().getStringExtra("rid");
        Uname=getIntent().getStringExtra("uname");
        tvDrivername=(TextView)findViewById(R.id.tvDrivername);
        tvDrivername.setText("Driver Email: "+Uname);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");

        tvFrom.setText("From:  "+getIntent().getStringExtra("from"));
        tvTo.setText("To:  "+getIntent().getStringExtra("to"));
        tvDate.setText("Date:  "+getIntent().getStringExtra("date"));
        tvCarname.setText("Car Name:  "+getIntent().getStringExtra("carname"));
        tvCarColor.setText("Color:  "+getIntent().getStringExtra("carcolor"));
        tvCarNumPlate.setText("No.Plate:  "+getIntent().getStringExtra("carnumplate"));
        tvCarSeats.setText("Seats:  "+getIntent().getStringExtra("seats"));
        tvTime.setText("Time:  "+getIntent().getStringExtra("time"));

        //Toast.makeText(this, ""+rideId, Toast.LENGTH_SHORT).show();

        btnMakeRequest=(Button)findViewById(R.id.btnMakeRequest);
        btnMakeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeaRequest();
            }
        });


    }

    ProgressDialog pd;
    public  void makeaRequest() {
        pd= new ProgressDialog(StudentRideDetailsActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = apiService.bookaRide(rideId,session,Uname,etMessage.getText().toString(),"Requested");

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(StudentRideDetailsActivity.this, "Ride Requested Succussfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StudentRideDetailsActivity.this, StudentDashboardActivity.class));
                    finish();
                } else {
                    Toast.makeText(StudentRideDetailsActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(StudentRideDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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