package com.example.sikandar.blooddonation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PhoneActivity extends AppCompatActivity {
    private TextView num;
    private ImageButton callit;
    private static final int REQUEST_CALL=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        Toolbar toolbar = findViewById(R.id.headerrequest);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        num=findViewById(R.id.mynumber);
        String tv_contact = getIntent().getStringExtra("tv_contact");
        callit=findViewById(R.id.callit);
        num.setText(tv_contact);
        callit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhonecall();

            }

        });


    }
    private void makePhonecall(){
        String number=num.getText().toString();
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(PhoneActivity.this, Manifest.permission.CALL_PHONE)
                    !=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(PhoneActivity.this, new String[]
                        {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }
            else {
                String dial= "tel:" +number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        }
        else{
            Toast.makeText(PhoneActivity.this, "Phone number do not exist", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makePhonecall();
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
