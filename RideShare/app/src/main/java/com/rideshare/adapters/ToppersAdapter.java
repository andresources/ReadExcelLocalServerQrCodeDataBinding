package com.rideshare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rideshare.R;
import com.rideshare.models.ToppersPojo;

import java.util.List;

public class ToppersAdapter extends BaseAdapter {
    List<ToppersPojo> inboxPojos;
    Context cnt;

    public ToppersAdapter(List<ToppersPojo> inboxpojo, Context cnt) {
        this.inboxPojos = inboxpojo;
        this.cnt = cnt;
    }

    @Override
    public int getCount() {
        return inboxPojos.size();
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
        View obj2 = obj1.inflate(R.layout.child_toppers, null);


        TextView tvEmail = (TextView) obj2.findViewById(R.id.tvEmail);
        tvEmail.setText(inboxPojos.get(pos).getDemail());

        RatingBar rv_rating= (RatingBar)obj2.findViewById(R.id.rv_rating);
        rv_rating.setRating(Float.parseFloat(inboxPojos.get(pos).getStar()));

        return obj2;
    }


}