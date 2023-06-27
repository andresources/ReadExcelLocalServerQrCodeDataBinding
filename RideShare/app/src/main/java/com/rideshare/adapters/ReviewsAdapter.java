package com.rideshare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rideshare.R;
import com.rideshare.models.ReviewsPojo;

import java.util.List;

public class ReviewsAdapter extends BaseAdapter {
    List<ReviewsPojo> inboxPojos;
    Context cnt;

    public ReviewsAdapter(List<ReviewsPojo> inboxpojo, Context cnt) {
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
        View obj2 = obj1.inflate(R.layout.child_reviews, null);


        TextView tvname = (TextView) obj2.findViewById(R.id.tvname);
        tvname.setText(inboxPojos.get(pos).getName());

        TextView tvmsg = (TextView) obj2.findViewById(R.id.tvmsg);
        tvmsg.setText(inboxPojos.get(pos).getMsg());

        RatingBar rv_rating= (RatingBar)obj2.findViewById(R.id.rv_rating);
        rv_rating.setRating(Float.parseFloat(inboxPojos.get(pos).getRating()));

        return obj2;
    }


}