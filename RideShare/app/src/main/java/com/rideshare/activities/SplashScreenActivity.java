package com.rideshare.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rideshare.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //design screen
        setContentView(R.layout.activity_splash_screen);

        final int ScreenDisplay = 3000;
        Thread t1=new Thread(){
            int wait1=0;
            public void run(){
                try{
                    while(wait1<=ScreenDisplay )
                    {
                        sleep(100);
                        wait1+=100;
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    Intent in= new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(in);
                    finish();

                }
            }
        };
        t1.start();
    }
}