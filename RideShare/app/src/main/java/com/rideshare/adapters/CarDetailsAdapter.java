package com.rideshare.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rideshare.R;
import com.rideshare.activities.CardetailsActivity;
import com.rideshare.activities.EditMyCarDetailsActivity;
import com.rideshare.api.ApiService;
import com.rideshare.api.RetroClient;
import com.rideshare.models.CarDetailsPojo;
import com.rideshare.models.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarDetailsAdapter extends BaseAdapter {
    List<CarDetailsPojo> mycardetails;
    Context cnt;
    String imgUrl="http://mytutorings.in/rideshare/";

    public CarDetailsAdapter(List<CarDetailsPojo> carDetailsPojo, Context cnt) {
        this.mycardetails = carDetailsPojo;
        this.cnt = cnt;
    }

    @Override
    public int getCount() {
        return mycardetails.size();
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
        View obj2 = obj1.inflate(R.layout.adapter__car_details, null);

        TextView tvName = (TextView) obj2.findViewById(R.id.tvName);
        tvName.setText("Car Name  :"+mycardetails.get(pos).getCname());

        TextView tvColor = (TextView) obj2.findViewById(R.id.tvColor);
        tvColor.setText("Car Color  :"+mycardetails.get(pos).getColor());


        TextView TvCplate = (TextView) obj2.findViewById(R.id.TvCplate);
        TvCplate.setText("Number Plate  :"+mycardetails.get(pos).getCplate());

        ImageView imageView=(ImageView)obj2.findViewById(R.id.imageView);
        Glide.with(cnt).load(imgUrl+mycardetails.get(pos).getPhoto()).into(imageView);

        Button imageEdit=(Button)obj2.findViewById(R.id.imageEdit);
        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(cnt, EditMyCarDetailsActivity.class);
                intent.putExtra("carname",mycardetails.get(pos).getCname());
                intent.putExtra("carcolour",mycardetails.get(pos).getColor());
                intent.putExtra("numberplate",mycardetails.get(pos).getCplate());
                intent.putExtra("noofseats",mycardetails.get(pos).getSeats());
                intent.putExtra("carimage",mycardetails.get(pos).getPhoto());
                cnt.startActivity(intent);

            }
        });
        Button imageDelete=(Button)obj2.findViewById(R.id.imageDelete);
        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCar(mycardetails.get(pos).getCid());

               // Toast.makeText(cnt,mycardetails.get(pos).getCid(),Toast.LENGTH_SHORT).show();
            }

        });



        return obj2;
    }

    ProgressDialog progressDialog;
    public void deleteCar(String CarID){
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.deleteCar(CarID);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();

                if (response.body().status.equals("true")) {
                    Intent intent=new Intent(cnt, CardetailsActivity.class);
                    cnt.startActivity(intent);
                    ((Activity)cnt).finish();
                    Toast.makeText(cnt,"Deleted successfully",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(cnt, response.body().message, Toast.LENGTH_LONG).show();
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