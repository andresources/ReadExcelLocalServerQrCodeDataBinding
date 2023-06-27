package com.rideshare.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rideshare.R;
import com.rideshare.Utils;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.ResponseData;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMyPostRidesActivity extends AppCompatActivity {
    Spinner spinTypeofVehicle;
    SharedPreferences sharedPreferences;
    Button btnUpdate;
    TextView tv_date, tv_time;
    int mYear, mMonth, mDay;
    String DAY, MONTH, YEAR;
    EditText et_no_of_seats, et_amount,spinFrom,SpinTo;
    String session,getRid;
    String[] typeofvehicle,source,destination;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_post_rides);

        getSupportActionBar().setTitle("Edit Ride");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def_val");

        getRid=getIntent().getStringExtra("rid");
        //Toast.makeText(this, ""+getRid, Toast.LENGTH_SHORT).show();


        typeofvehicle=getResources().getStringArray(R.array.CarBrand);
        source=getResources().getStringArray(R.array.from);
        destination=getResources().getStringArray(R.array.to);


      /*  spinTypeofVehicle = (Spinner) findViewById(R.id.spinTypeofVehicle);
        int vehiclename = new ArrayList<String>(Arrays.asList(typeofvehicle)).indexOf(getIntent().getStringExtra("source"));
        spinTypeofVehicle.setSelection(vehiclename);*/


        spinFrom = (EditText) findViewById(R.id.spinFrom);
//        int sourcee = new ArrayList<String>(Arrays.asList(source)).indexOf(getIntent().getStringExtra("source"));
//        spinFrom.setSelection(sourcee);


        SpinTo = (EditText) findViewById(R.id.SpinTo);
//        int destinationn = new ArrayList<String>(Arrays.asList(destination)).indexOf(getIntent().getStringExtra("destination"));
//        SpinTo.setSelection(destinationn);



        et_no_of_seats = (EditText) findViewById(R.id.et_no_of_seats);
        et_amount = (EditText) findViewById(R.id.et_amount);
        tv_date=(TextView)findViewById(R.id.tv_date);
        tv_time=(TextView)findViewById(R.id.tv_time);

        et_no_of_seats.setText(getIntent().getStringExtra("seats"));
        et_amount.setText(getIntent().getStringExtra("price"));
        tv_date.setText(getIntent().getStringExtra("date"));
        tv_time.setText(getIntent().getStringExtra("time"));

        spinFrom.setText(getIntent().getStringExtra("source"));
        SpinTo.setText(getIntent().getStringExtra("destination"));


        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
            }
        });

        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setmTimePicker();
            }
        });

        btnUpdate=(Button)findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateMyPostRides();
              //  Toast.makeText(EditMyPostRidesActivity.this, tv_date.getText().toString(), Toast.LENGTH_LONG).show();

            }
        });
    }

    ProgressDialog progressDialog;
    private void UpdateMyPostRides() {
        String source=spinFrom.getText().toString();
        String dest=SpinTo.getText().toString();
        String seats=et_no_of_seats.getText().toString();
        String amount=et_amount.getText().toString();
        String dat=tv_date.getText().toString();
        String time=tv_time.getText().toString();

        progressDialog = new ProgressDialog(EditMyPostRidesActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.editmypostride(source,dest,seats,dat,amount,time,getRid);

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(EditMyPostRidesActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EditMyPostRidesActivity.this,UserDashBoardActivity.class));
                    finish();
                } else {
                    Toast.makeText(EditMyPostRidesActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditMyPostRidesActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

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

                      //  tv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        tv_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }

    public void setmTimePicker() {

        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(EditMyPostRidesActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                tv_time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

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