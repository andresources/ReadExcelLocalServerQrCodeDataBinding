package com.rideshare.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.rideshare.MapsDirActivity;
import com.rideshare.R;
import com.rideshare.Utils;
import com.rideshare.activities.StudentRideDetailsActivity;
import com.rideshare.activities.ViewReviewsActivity;
import com.rideshare.activities.messagingactivity;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.EditProfilePojo;
import com.rideshare.models.RidesListPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAvailableRidesAdapter extends BaseAdapter {
    List<RidesListPojo> availablerides,searchride;
    String URL="http://rideshare.live/rideshare/";
    Context cnt;
    String session,dname;

    SharedPreferences sharedPreferences;

    public StudentAvailableRidesAdapter(List<RidesListPojo> availablerides, Context cnt) {
        this.searchride=availablerides;
        this.cnt = cnt;
        this.availablerides = new ArrayList<RidesListPojo>();
        this.availablerides.addAll(availablerides);

    }

    @Override
    public int getCount() {
        return availablerides.size();
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
        View obj2 = obj1.inflate(R.layout.list_student_available_ride, null);

        ImageView carImage=(ImageView)obj2.findViewById(R.id.carImage);
        Glide.with(cnt).load(URL+availablerides.get(pos).photo).into(carImage);

       TextView tvFrom = (TextView) obj2.findViewById(R.id.tvFrom);
        tvFrom.setText("From : " + availablerides.get(pos).getSource());

        TextView tvTo = (TextView) obj2.findViewById(R.id.tvTo);
        tvTo.setText("To : " +  availablerides.get(pos).getDestination());

        TextView tvDate = (TextView) obj2.findViewById(R.id.tvDate);
        tvDate.setText("Date : " +  availablerides.get(pos).getDat());

        TextView tvPrice = (TextView) obj2.findViewById(R.id.tvPrice);
        tvPrice.setText("Price : " +  availablerides.get(pos).getAmount()+"$");

        TextView tvTime = (TextView) obj2.findViewById(R.id.tvTime);
        tvTime.setText("Time : " +  availablerides.get(pos).getTim()+"PM");

       // dname=availablerides.get(pos).getEmail();

        Button btnBook=(Button)obj2.findViewById(R.id.btnBook);
        Button btnLoc=(Button)obj2.findViewById(R.id.btnLoc);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(cnt, StudentRideDetailsActivity.class);
                intent.putExtra("from",availablerides.get(pos).getSource());
                intent.putExtra("to",availablerides.get(pos).getDestination());
                intent.putExtra("date",availablerides.get(pos).getDat());
                intent.putExtra("carname",availablerides.get(pos).getCname());
                intent.putExtra("carcolor",availablerides.get(pos).getColor());
                intent.putExtra("carnumplate",availablerides.get(pos).getCplate());
                intent.putExtra("seats",availablerides.get(pos).getSeats());
                intent.putExtra("time",availablerides.get(pos).getTim());
                intent.putExtra("rid",availablerides.get(pos).getRid());
                intent.putExtra("uname",availablerides.get(pos).getEmail());
                cnt.startActivity(intent);
                //((Activity)cnt).finish();

            }
        });
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, MapsDirActivity.class);
                intent.putExtra("slat",availablerides.get(pos).getSlat());
                intent.putExtra("slog",availablerides.get(pos).getSlog());
                intent.putExtra("splace",availablerides.get(pos).getSource());
                intent.putExtra("dlat",availablerides.get(pos).getDlat());
                intent.putExtra("dlog",availablerides.get(pos).getDlog());
                intent.putExtra("dplace",availablerides.get(pos).getDestination());

                cnt.startActivity(intent);
               // Toast.makeText(cnt.getApplicationContext(), availablerides.get(pos).getSlat(),Toast.LENGTH_LONG).show();
            }
        });

        sharedPreferences = cnt.getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");

        ImageView driver_msg=(ImageView)obj2.findViewById(R.id.driver_msg);
        driver_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(cnt, messagingactivity.class);
                intent.putExtra("rid",availablerides.get(pos).getRid());
                intent.putExtra("from",session);
                intent.putExtra("to",availablerides.get(pos).getEmail());
                cnt.startActivity(intent);

            }
        });

        ImageView driver_details=(ImageView)obj2.findViewById(R.id.driver_details);
        driver_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDriverDetails(availablerides.get(pos).getEmail());
              //  getDriverRating(availablerides.get(pos).getEmail());
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
    public void driverDetails(String name,String email,final String phoneno){
        LayoutInflater inflater = LayoutInflater.from(cnt);
        View view = inflater.inflate(R.layout.alert_driver_info, null);

        Button btnOk = (Button) view.findViewById(R.id.btnOk);

        TextView tvName=(TextView)view.findViewById(R.id.tvName);
        TextView tvEmail=(TextView)view.findViewById(R.id.tvEmail);
        TextView tvPhoneno=(TextView)view.findViewById(R.id.tvPhoneno);
        TextView tvratings=(TextView)view.findViewById(R.id.tvratings);
        tvratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, ViewReviewsActivity.class);
                intent.putExtra("email",tvEmail.getText().toString());
                Toast.makeText(cnt,tvEmail.getText().toString() , Toast.LENGTH_SHORT).show();

                cnt.startActivity(intent);
            }
        });


        ImageView driver_call=(ImageView)view.findViewById(R.id.driver_call);
        driver_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneno));
                cnt.startActivity(intent);
            }
        });

        tvName.setText("Driver Name:  "+name);
        tvEmail.setText(email);
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



    public void rideFilter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        availablerides.clear();
        if (charText.length() == 0) {
            availablerides.addAll(searchride);
        } else {
            for (RidesListPojo wp : searchride) {
                if (wp.getSource().toLowerCase(Locale.getDefault()).contains(charText)) {
                    availablerides.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}