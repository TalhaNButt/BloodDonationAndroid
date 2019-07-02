package com.example.sikandar.blooddonation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ProgressBar;

public class WelcomeActivity extends Activity {
private static int SPLASH_TIME_OUT=1500;
private ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        pbar= findViewById(R.id.progressBar2);
        pbar.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeintent=new Intent(WelcomeActivity.this, LoginActivity.class );
                startActivity(homeintent);
                finish();
            }

            }, SPLASH_TIME_OUT);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                finish();
            }
        }).start();
    }
    private void doWork(){
        for(int progress=0; progress<=120;progress+=25){
            try {
                Thread.sleep(300);
                pbar.setProgress(progress);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
