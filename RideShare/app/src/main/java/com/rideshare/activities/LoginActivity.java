package com.rideshare.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class LoginActivity extends AppCompatActivity {
    Button bt_login;
    TextView tv_reg_here,tv_forget_pass;
    EditText et_uname,et_password;
    Spinner spin_role;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bt_login=(Button)findViewById(R.id.bt_login);
        tv_reg_here=(TextView)findViewById(R.id.tv_reg_here);
        tv_forget_pass=(TextView)findViewById(R.id.tv_forget_pass);

        et_uname=(EditText)findViewById(R.id.et_uname);
        et_password=(EditText)findViewById(R.id.et_password);
        spin_role=(Spinner)findViewById(R.id.spin_role);


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_uname.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Enter Valid Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(et_password.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Enter Valid Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                //startActivity(new Intent(LoginActivity.this, UserDashBoardActivity.class));
                loginFunction();

            }
        });

        tv_reg_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        tv_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));

            }
        });

    }
    public  void loginFunction() {

        pd= new ProgressDialog(LoginActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();


        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);

        Call<ResponseData> call = apiService.userLogin(et_uname.getText().toString(),et_password.getText().toString());

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor et=sharedPreferences.edit();
                    et.putString("uname",et_uname.getText().toString());
                    et.commit();
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}