package com.example.sikandar.blooddonation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class AvailiabilityActivity extends AppCompatActivity {
    private SwitchCompat switchCompat;
    boolean stateswitch;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availiability);
        Toolbar toolbar = findViewById(R.id.headerrequest);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences("PREFS", 0);
        stateswitch = preferences.getBoolean("switchCompact", true);
        switchCompat = findViewById(R.id.switcher);
        switchCompat.setChecked(stateswitch);

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateswitch = !stateswitch;
                switchCompat.setChecked(stateswitch);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("switchCompact", stateswitch);
                editor.apply();
                if (stateswitch == true) {
                    Toast.makeText(AvailiabilityActivity.this, "Availabilty set to on", Toast.LENGTH_LONG).show();

                    String availability = "on";

                    FirebaseDatabase.getInstance().getReference("Users/" +FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("availability")
                            .setValue(availability);

                    Intent intent=new Intent(AvailiabilityActivity.this, ShowDonors.class);
                    intent.putExtra("availability", availability);

                }
                else{
                    Toast.makeText(AvailiabilityActivity.this, "Availabilty set to off", Toast.LENGTH_LONG).show();

                    String availability = "off";


                    FirebaseDatabase.getInstance().getReference("Users/" +FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("availability")
                            .setValue(availability);

                    Intent intent = new Intent(AvailiabilityActivity.this, ShowDonors.class);
                    intent.putExtra("availability", availability);
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
