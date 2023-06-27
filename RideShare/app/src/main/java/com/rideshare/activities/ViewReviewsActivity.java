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
import com.rideshare.adapters.ReviewsAdapter;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.ReviewsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewReviewsActivity extends AppCompatActivity {

    List<ReviewsPojo> inboxPojos;
    ReviewsAdapter inboxAdapter;
    ListView list_view;
    ProgressDialog progressDialog;
    EditText et_search;
    SharedPreferences sharedPreferences;
    String session,dname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);
        getSupportActionBar().setTitle("Reviews and Ratings");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");

        list_view=(ListView)findViewById(R.id.list_view);

        dname=getIntent().getStringExtra("email");

        inboxPojos=new ArrayList<>();
        getReviews(dname);
    }
    public void getReviews(final String email){
        progressDialog = new ProgressDialog(ViewReviewsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<ReviewsPojo>> call = service.getreviews(email);
        call.enqueue(new Callback<List<ReviewsPojo>>() {
            @Override
            public void onResponse(Call<List<ReviewsPojo>> call, Response<List<ReviewsPojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(ViewReviewsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    inboxPojos=response.body();
                    inboxAdapter =new ReviewsAdapter(inboxPojos, ViewReviewsActivity.this);
                    list_view.setAdapter(inboxAdapter);

                }
            }
            @Override
            public void onFailure(Call<List<ReviewsPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ViewReviewsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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