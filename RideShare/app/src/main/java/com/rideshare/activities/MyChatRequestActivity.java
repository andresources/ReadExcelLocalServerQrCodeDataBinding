package com.rideshare.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.rideshare.R;

public class MyChatRequestActivity extends AppCompatActivity {

    ListView list_view;
    ProgressDialog progressDialog;
   // List<msgs> a1;
    SharedPreferences sharedPreferences;

    String session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chat_request);
    }
}