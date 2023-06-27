package com.rideshare.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rideshare.R;
import com.rideshare.Utils;
import com.rideshare.activities.StudentRideDetailsActivity;
import com.rideshare.activities.messagingactivity;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.EditProfilePojo;
import com.rideshare.models.SearchDetailsPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAdapter extends BaseAdapter {
    List<SearchDetailsPojo> searchDetails;
    Context cnt;
    String URL="http://mytutorings.in/rideshare/";
    String session;
    SharedPreferences sharedPreferences;


    public SearchAdapter(List<SearchDetailsPojo> searchDetails, Context cnt) {
        this.searchDetails = searchDetails;
        this.cnt = cnt;
    }

    @Override
    public int getCount() {
        return searchDetails.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        LayoutInflater obj1 = (LayoutInflater) cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2 = obj1.inflate(R.layout.adapter_search_ride, null);

        ImageView carImage=(ImageView)obj2.findViewById(R.id.carImage);
        Glide.with(cnt).load(URL+searchDetails.get(pos).photo).into(carImage);

        TextView tvFrom = (TextView) obj2.findViewById(R.id.tvFrom);
        tvFrom.setText("From : " + searchDetails.get(pos).getSource());

        TextView tvTo = (TextView) obj2.findViewById(R.id.tvTo);
        tvTo.setText("To : " +  searchDetails.get(pos).getDestination());

        TextView tvDate = (TextView) obj2.findViewById(R.id.tvDate);
        tvDate.setText("Date : " +  searchDetails.get(pos).getDat()+"AM");


        TextView tvTime = (TextView) obj2.findViewById(R.id.tvTime);
        tvTime.setText("Time : " +  searchDetails.get(pos).getTim());

        TextView tvPrice = (TextView) obj2.findViewById(R.id.tvPrice);
        tvPrice.setText("Price : " + "CAD $" +searchDetails.get(pos).getAmount());



        Button btnBook=(Button)obj2.findViewById(R.id.btnBook);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(cnt, StudentRideDetailsActivity.class);
                intent.putExtra("from",searchDetails.get(pos).getSource());
                intent.putExtra("to",searchDetails.get(pos).getDestination());
                intent.putExtra("date",searchDetails.get(pos).getDat());
                intent.putExtra("carname",searchDetails.get(pos).getCname());
                intent.putExtra("carcolor",searchDetails.get(pos).getColor());
                intent.putExtra("carnumplate",searchDetails.get(pos).getCplate());
                intent.putExtra("seats",searchDetails.get(pos).getSeats());
                intent.putExtra("time",searchDetails.get(pos).getTim());
                intent.putExtra("rid",searchDetails.get(pos).getRid());
                intent.putExtra("uname",searchDetails.get(pos).getEmail());
                cnt.startActivity(intent);
                ((Activity)cnt).finish();

            }
        });

        sharedPreferences = cnt.getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");

        ImageView driver_msg=(ImageView)obj2.findViewById(R.id.driver_msg);
        driver_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(cnt, messagingactivity.class);
                intent.putExtra("rid",searchDetails.get(pos).getRid());
                intent.putExtra("from",session);
                intent.putExtra("to",searchDetails.get(pos).getEmail());
                cnt.startActivity(intent);

            }
        });


        ImageView driver_details=(ImageView)obj2.findViewById(R.id.driver_details);
        driver_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDriverDetails(searchDetails.get(pos).getEmail());
            }
        });


        return obj2;
    }

    ProgressDialog progressDialog;
    List<EditProfilePojo> driverDetails;
    public void getDriverDetails(String Email){
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<EditProfilePojo>> call = service.getdriver(Email);
        call.enqueue(new Callback<List<EditProfilePojo>>() {
            @Override
            public void onResponse(Call<List<EditProfilePojo>> call, Response<List<EditProfilePojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(cnt,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    driverDetails=response.body();
                    EditProfilePojo user1 = driverDetails.get(0);
                    driverDetails(user1.getName(),user1.getEmail(),user1.getPhone());


                }
            }
            @Override
            public void onFailure(Call<List<EditProfilePojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(cnt, "Something went wrong...Please try later!"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void driverDetails(String name,String email,String phoneno){
        LayoutInflater inflater = LayoutInflater.from(cnt);
        View view = inflater.inflate(R.layout.alert_driver_info, null);

        Button btnOk = (Button) view.findViewById(R.id.btnOk);

        TextView tvName=(TextView)view.findViewById(R.id.tvName);
        TextView tvEmail=(TextView)view.findViewById(R.id.tvEmail);
        TextView tvPhoneno=(TextView)view.findViewById(R.id.tvPhoneno);


        tvName.setText("Driver Name:  "+name);
        tvEmail.setText("Driver Email:  "+email);
        tvPhoneno.setText("Driver Phone:  "+phoneno);


        final AlertDialog alertDialog = new AlertDialog.Builder(cnt).setView(view).create();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //Toast.makeText(context, "Dismissed..!!", Toast.LENGTH_SHORT).show();
            }
        });
        Window window = alertDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        alertDialog.show();
    }


}