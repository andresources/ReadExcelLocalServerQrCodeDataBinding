package com.rideshare.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rideshare.R;
import com.rideshare.activities.GiveRatingNowActivity;
import com.rideshare.models.MyRideHistoryPojo;

import java.util.List;

public class RideHistoryAdapter extends BaseAdapter {
    List<MyRideHistoryPojo> ar;
    Context cnt;

    public RideHistoryAdapter(List<MyRideHistoryPojo> ar, Context cnt) {
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
        View obj2 = obj1.inflate(R.layout.list_rider_history_list, null);

        TextView tvFrom = (TextView) obj2.findViewById(R.id.tvFrom);
        tvFrom.setText("From : " + ar.get(pos).getSource());

        TextView tvTo = (TextView) obj2.findViewById(R.id.tvTo);
        tvTo.setText("To : " + ar.get(pos).getDestination());

        TextView tvAmount = (TextView) obj2.findViewById(R.id.tvAmount);
        tvAmount.setText("Amount : " + ar.get(pos).getAmount()+"$");

        TextView tvDate = (TextView) obj2.findViewById(R.id.tvDate);
        tvDate.setText("Date : " + ar.get(pos).getDat());


        TextView tvTime = (TextView) obj2.findViewById(R.id.tvTime);
        tvTime.setText("Time : " + ar.get(pos).getTim());


        Button btnratenow = (Button) obj2.findViewById(R.id.btnratenow);

        btnratenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, GiveRatingNowActivity.class);
                intent.putExtra("rid",ar.get(pos).getRid());
                intent.putExtra("driver",ar.get(pos).getTouser());

                cnt.startActivity(intent);
            }
        });



        return obj2;
    }
}