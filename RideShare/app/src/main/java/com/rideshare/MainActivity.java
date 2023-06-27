package com.rideshare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(),MapsActivity.class),101);
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100) {
            Toast.makeText(getApplicationContext(), data.getStringExtra("place") + data.getDoubleExtra("lat", 0) + data.getDoubleExtra("lng", 0), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), data.getStringExtra("place"), Toast.LENGTH_SHORT).show();
        }
        }
}