package com.rideshare.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rideshare.R;
import com.rideshare.Utils;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.EditProfilePojo;
import com.rideshare.models.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    EditText et_first_name,et_email,et_phone_no,et_password;
    Button btn_update_profile;
    RadioButton radioMale,radioFemale;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    List<EditProfilePojo> a1;
    ResponseData a2;
    String gender;
    ImageView imageview;
    String url="http://mytutorings.in/rideshare/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_first_name=(EditText)findViewById(R.id.et_first_name);
        et_email=(EditText)findViewById(R.id.et_email);
        et_phone_no=(EditText)findViewById(R.id.et_phone_no);
        et_password=(EditText)findViewById(R.id.et_password);
        imageview=(ImageView)findViewById(R.id.imageview);

        et_email.setFocusable(false);

        radioMale=(RadioButton)findViewById(R.id.radioMale);
        radioFemale=(RadioButton)findViewById(R.id.radioFemale);
        btn_update_profile=(Button)findViewById(R.id.btn_update_profile);
        genderSelect();

        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();

            }
        });

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String session = sharedPreferences.getString("uname", "def-val");
        progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<EditProfilePojo>> call = service.editProfile(session);



        call.enqueue(new Callback<List<EditProfilePojo>>() {
            @Override
            public void onResponse(Call<List<EditProfilePojo>> call, Response<List<EditProfilePojo>> response) {
                progressDialog.dismiss();
                a1 = response.body();
                EditProfilePojo user1 = a1.get(0);

                if(user1.gender.equals("Male")){
                    radioMale.setChecked(true);
                }
                else {
                    radioFemale.setChecked(true);
                }

                et_first_name.setText(user1.getName());
                et_phone_no.setText(user1.getPhone());
                et_email.setText(user1.getEmail());
                et_password.setText(user1.getPassword());
                Toast.makeText(EditProfileActivity.this, ""+url+user1.getPhoto(), Toast.LENGTH_SHORT).show();
                Glide.with(EditProfileActivity.this).load(url+user1.getPhoto()).into(imageview);

            }

            @Override
            public void onFailure(Call<List<EditProfilePojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void submitData () {
        String name = et_first_name.getText().toString();
        String email = et_email.getText().toString();
        String phone = et_phone_no.getText().toString();
        String password = et_password.getText().toString();

        progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        String session= sharedPreferences.getString("uname","def-val");
        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.updateProfile(name,email,phone,gender,password);

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                progressDialog.dismiss();
                a2 = response.body();

                if (response.body().status.equals("true")) {
                    Toast.makeText(EditProfileActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(EditProfileActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void genderSelect(){
        if (radioMale.isChecked()) {
            gender="Male";
        } else {
            gender="Female";
        }
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