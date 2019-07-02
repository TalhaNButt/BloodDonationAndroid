package com.example.sikandar.blooddonation;

import android.support.v4.app.FragmentContainer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class SettingsActivity extends AppCompatActivity {

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //frameLayout = findViewById(R.id.fragment_container);

        if(findViewById(R.id.fragment_container)!= null){

            if(savedInstanceState != null){
                return;

                //getFragmentManager().beginTransaction().add(R.id.fragment_container, new Settings()).commit();
            }
        }
    }
}
