package com.rideshare.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rideshare.R;
import com.rideshare.Utils;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.CarDetailsPojo;
import com.rideshare.models.ResponseData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostaRideActivity extends AppCompatActivity {
    EditText et_from,et_to,et_no_of_seats,et_amount,et_vehicle_id;
    TextView tv_date;
    Button btn_post;
    int mYear,mMonth,mDay;
    String DAY,MONTH,YEAR;
    SharedPreferences sharedPreferences;
    String session;
    Spinner spinTypeofVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posta_ride);


        getSupportActionBar().setTitle("Post a ride");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");

        et_from=(EditText)findViewById(R.id.et_from);
        et_to=(EditText)findViewById(R.id.et_to);
        et_no_of_seats=(EditText)findViewById(R.id.et_no_of_seats);
        et_amount=(EditText)findViewById(R.id.et_amount);
        et_vehicle_id=(EditText)findViewById(R.id.et_vehicle_id);

        spinTypeofVehicle=(Spinner)findViewById(R.id.spinTypeofVehicle);

        getCarNames();
        tv_date=(TextView)findViewById(R.id.tv_date);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
            }
        });

        btn_post=(Button)findViewById(R.id.btn_post);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    ProgressDialog progressDialog;
    private void PostaRide () {
        String from = et_from.getText().toString();
        String to = et_to.getText().toString();
        String noofseats = et_no_of_seats.getText().toString();
        String amount = et_amount.getText().toString();
        String vehicleID = et_vehicle_id.getText().toString();

        progressDialog = new ProgressDialog(PostaRideActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.postaride("","","","",from,to,"typeofvehicle",noofseats,amount,vehicleID,"","");

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(PostaRideActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(PostaRideActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PostaRideActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    ArrayList<String> cid;
    private void getCarNames() {
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<CarDetailsPojo>> call = apiService.getmycars(session);
        call.enqueue(new Callback<List<CarDetailsPojo>>() {
            @Override
            public void onResponse(Call<List<CarDetailsPojo>> call, Response<List<CarDetailsPojo>> response) {
                if (response.isSuccessful()) {
                    final List<CarDetailsPojo> carname=response.body();
                    if(carname!=null) {
                        if(carname.size()>0) {
                            ArrayList<String> cname = new ArrayList<String>();
                            cid = new ArrayList<String>();
                            //cname.add("Select Car");
                            for (int i = 0; i < carname.size(); i++) {
                                cname.add(carname.get(i).getCname());
                                cid.add(carname.get(i).getCname());
                            }
                            ArrayAdapter<String> adp = new ArrayAdapter<String>(PostaRideActivity.this, android.R.layout.simple_spinner_dropdown_item, cname);
                            spinTypeofVehicle.setAdapter(adp);
                            spinTypeofVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                                    Toast.makeText(PostaRideActivity.this, ""+cid.get(spinTypeofVehicle.getSelectedItemPosition()), Toast.LENGTH_SHORT).show();



                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    //serverData();

                                }
                            });


                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CarDetailsPojo>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }

    public void datepicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        DAY = dayOfMonth + "";
                        MONTH = monthOfYear + 1 + "";
                        YEAR = year + "";

                        tv_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
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