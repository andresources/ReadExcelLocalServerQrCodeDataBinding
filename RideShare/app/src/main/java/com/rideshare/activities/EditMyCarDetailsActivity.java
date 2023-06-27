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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rideshare.R;
import com.rideshare.Utils;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.ResponseData;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMyCarDetailsActivity extends AppCompatActivity{
    Spinner spinCarBrand,spinNoOfSeats,SpinCarColour;
    EditText etNUmberPlate;
    Button btn_upload,btnAddCar;
    SharedPreferences sharedPreferences;
    String session,carname,colour,numberplate,carimage,seats;
    String[] cname,carcolor,noofseats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_car_details);

        getSupportActionBar().setTitle("Edit CarDetails");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");

        cname=getResources().getStringArray(R.array.CarBrand);
        carcolor=getResources().getStringArray(R.array.carColour);
        noofseats=getResources().getStringArray(R.array.noOfSeats);

        carname=getIntent().getStringExtra("carname");
        colour=getIntent().getStringExtra("carcolour");
        numberplate=getIntent().getStringExtra("numberplate");
        carimage=getIntent().getStringExtra("carimage");
        seats=getIntent().getStringExtra("noofseats");


        spinCarBrand=(Spinner)findViewById(R.id.spinCarBrand);
        SpinCarColour=(Spinner)findViewById(R.id.SpinCarColour);
        spinNoOfSeats=(Spinner)findViewById(R.id.spinNoOfSeats);
        etNUmberPlate=(EditText)findViewById(R.id.etNUmberPlate);

        etNUmberPlate.setText(numberplate);

        int cnamebra = new ArrayList<String>(Arrays.asList(cname)).indexOf(getIntent().getStringExtra("carname"));
        spinCarBrand.setSelection(cnamebra);

        int ccolor = new ArrayList<String>(Arrays.asList(carcolor)).indexOf(getIntent().getStringExtra("carcolour"));
        SpinCarColour.setSelection(ccolor);

        int cseats = new ArrayList<String>(Arrays.asList(noofseats)).indexOf(getIntent().getStringExtra("noofseats"));
        spinNoOfSeats.setSelection(cseats);


        btnAddCar=(Button)findViewById(R.id.btnAddCar);
        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateMyCarDetails();

            }
        });
    }

    ProgressDialog progressDialog;
    private void UpdateMyCarDetails () {
        String carbrand=spinCarBrand.getSelectedItem().toString();
        String carColour=SpinCarColour.getSelectedItem().toString();
        String NoOfSeats=spinNoOfSeats.getSelectedItem().toString();
        String numplate=etNUmberPlate.getText().toString();

        progressDialog = new ProgressDialog(EditMyCarDetailsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.updateCarDetails(carbrand,session,NoOfSeats,carColour,numplate);

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(EditMyCarDetailsActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EditMyCarDetailsActivity.this,CardetailsActivity.class));
                    finish();
                } else {
                    Toast.makeText(EditMyCarDetailsActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditMyCarDetailsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

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