package com.rideshare.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rideshare.R;
import com.rideshare.activities.EditMyPostRidesActivity;
import com.rideshare.activities.MyPostRidesAActivity;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.MyPostRidesPojo;
import com.rideshare.models.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostRidesAdapter extends BaseAdapter {
    List<MyPostRidesPojo> ar;
    Context cnt;

    public MyPostRidesAdapter(List<MyPostRidesPojo> ar, Context cnt) {
        this.ar = ar;
        this.cnt = cnt;
    }

    @Override
    public int getCount() {
        return ar.size();
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
        View obj2 = obj1.inflate(R.layout.list_riders_list, null);

        TextView tvSource = (TextView) obj2.findViewById(R.id.tvSource);
        tvSource.setText("Source : " + ar.get(pos).getSource());

        TextView tv_seats = (TextView) obj2.findViewById(R.id.tv_seats);
        tv_seats.setText("Seats : " + ar.get(pos).getSeats());

        TextView tv_price = (TextView) obj2.findViewById(R.id.tv_price);
        tv_price.setText("Amount : " + ar.get(pos).getAmount()+"$");

        TextView tvDestination = (TextView) obj2.findViewById(R.id.tvDestination);
        tvDestination.setText("Destination : " + ar.get(pos).getDestination());

        TextView tvDate = (TextView) obj2.findViewById(R.id.tvDate);
        tvDate.setText("Date : " + ar.get(pos).getDat());


        Button btnEdit = (Button) obj2.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(cnt, EditMyPostRidesActivity.class);
                intent.putExtra("source", ar.get(pos).getSource());
                intent.putExtra("destination", ar.get(pos).getDestination());
                intent.putExtra("price", ar.get(pos).getAmount());
                intent.putExtra("date", ar.get(pos).getDat());
                intent.putExtra("time", ar.get(pos).getTim());
                intent.putExtra("seats", ar.get(pos).getSeats());
                intent.putExtra("rid", ar.get(pos).getRid());
                cnt.startActivity(intent);

            }
        });


        Button btndelete = (Button) obj2.findViewById(R.id.btndelete);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertBox(ar.get(pos).getRid());


            }
        });


        return obj2;
    }

    public void AlertBox(final String Id) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(cnt);
        builder1.setMessage("Do you want to delete this Ride.");
        builder1.setCancelable(true);

        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteRide(Id);
                //dialog.cancel();
            }
        });

        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    ProgressDialog progressDialog;

    public void deleteRide(String ID) {
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.deletemypostride(ID);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if (response.body() == null) {
                    Toast.makeText(cnt, "Server issue", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(cnt, MyPostRidesAActivity.class);
                    cnt.startActivity(intent);
                    ((Activity) cnt).finish();
                    Toast.makeText(cnt, " Ride Deleted successfully", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(cnt, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}