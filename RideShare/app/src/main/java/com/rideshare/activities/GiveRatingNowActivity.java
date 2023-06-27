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
import android.widget.RatingBar;
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

public class GiveRatingNowActivity extends AppCompatActivity {

    RatingBar rv_rating;
    EditText et_rid,et_demail,et_name,et_email,et_msg;
    Button bt_submit;
    SharedPreferences sharedPreferences;
    String session;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_rating_now);

        getSupportActionBar().setTitle("Rating");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");

        et_rid=(EditText)findViewById(R.id.et_rid);
        et_demail=(EditText)findViewById(R.id.et_demail);
        et_name=(EditText)findViewById(R.id.et_name);
        et_email=(EditText)findViewById(R.id.et_email);
        et_msg=(EditText)findViewById(R.id.et_msg);

        et_rid.setText(getIntent().getStringExtra("rid"));
        et_demail.setText(getIntent().getStringExtra("driver"));
        et_email.setText(session);

        rv_rating=(RatingBar)findViewById(R.id.rv_rating);
        bt_submit=(Button) findViewById(R.id.bt_submit);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_rid.getText().toString().isEmpty()){
                    Toast.makeText(GiveRatingNowActivity.this, "Please enter Ride Id", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(et_demail.getText().toString().isEmpty()){
                    Toast.makeText(GiveRatingNowActivity.this, "Please enter Driver email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(et_name.getText().toString().isEmpty()){
                    Toast.makeText(GiveRatingNowActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(et_email.getText().toString().isEmpty()){
                    Toast.makeText(GiveRatingNowActivity.this, "enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(et_msg.getText().toString().isEmpty()){
                    Toast.makeText(GiveRatingNowActivity.this, "enter message", Toast.LENGTH_SHORT).show();
                    return;
                }

                submit();
            }
        });

    }

    private void submit() {

        String rid = et_rid.getText().toString();
        String demail = et_demail.getText().toString();
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String msg = et_msg.getText().toString();
        String rating =String.valueOf(rv_rating.getRating());

        progressDialog = new ProgressDialog(GiveRatingNowActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.addRating(rid,demail,name,email,msg,rating);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(GiveRatingNowActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(GiveRatingNowActivity.this, StudentDashboardActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(GiveRatingNowActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GiveRatingNowActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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