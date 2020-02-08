package com.geekbox.souvrecalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread myThread = new Thread(){

            @Override
            public void run() {
                try{
                    sleep(2000);
                    Intent intent = new Intent(getApplicationContext(), com.geekbox.souvrecalculator.MainActivity.class);

                    Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                            android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                    startActivity(intent, bundle);
                    finish();
                }
                catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        };

        myThread.start();
    }
}
