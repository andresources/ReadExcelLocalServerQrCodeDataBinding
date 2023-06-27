package com.rideshare.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.rideshare.R;

public class RideConfirmationActivity extends AppCompatActivity {
    EditText et_vehicle_no,et_from,et_to,et_no_of_seats,et_amount;
    Button btn_confirm_ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_confirmation);

        getSupportActionBar().setTitle("Ride Info");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_vehicle_no=(EditText)findViewById(R.id.et_vehicle_no);
        et_from=(EditText)findViewById(R.id.et_from);
        et_to=(EditText)findViewById(R.id.et_to);
        et_no_of_seats=(EditText)findViewById(R.id.et_no_of_seats);
        et_amount=(EditText)findViewById(R.id.et_amount);

        et_vehicle_no.setText(getIntent().getStringExtra("vehicle"));
        et_no_of_seats.setText(getIntent().getStringExtra("seats"));
        et_amount.setText(getIntent().getStringExtra("price")+" $");

        btn_confirm_ride=(Button)findViewById(R.id.btn_confirm_ride);
        btn_confirm_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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