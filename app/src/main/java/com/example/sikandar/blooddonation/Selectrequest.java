
package com.example.sikandar.blooddonation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class Selectrequest extends AppCompatActivity implements View.OnClickListener {
    private Button agroup, angroup, bgroup, bngroup, ogroup, ongroup, findrequest;
    private String bloodgroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectrequest);

        agroup = findViewById(R.id.agroup);
        angroup = findViewById(R.id.angroup);
        bgroup = findViewById(R.id.bgroup);
        bngroup = findViewById(R.id.bngroup);
        ogroup = findViewById(R.id.ogroup);
        ongroup = findViewById(R.id.ongroup);
        findrequest = findViewById(R.id.findrequest);

        Toolbar toolbar = findViewById(R.id.headerrequest);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        agroup.setOnClickListener(this);
        angroup.setOnClickListener(this);
        bgroup.setOnClickListener(this);
        bngroup.setOnClickListener(this);
        ogroup.setOnClickListener(this);
        ongroup.setOnClickListener(this);
        findrequest.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v == agroup) {
            agroup.setBackground(getDrawable(R.drawable.bg_color));
            agroup.setTextColor(getResources().getColor(R.color.white));
            angroup.setBackground(getDrawable(R.drawable.bg_button_back));
            angroup.setTextColor(getResources().getColor(R.color.colorRed));
            bgroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bgroup.setTextColor(getResources().getColor(R.color.colorRed));
            bngroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bngroup.setTextColor(getResources().getColor(R.color.colorRed));
            ogroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ogroup.setTextColor(getResources().getColor(R.color.colorRed));
            ongroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ongroup.setTextColor(getResources().getColor(R.color.colorRed));

            bloodgroup = "A+";
        }
        if (v == angroup) {

            angroup.setBackground(getDrawable(R.drawable.bg_color));
            angroup.setTextColor(getResources().getColor(R.color.white));
            agroup.setBackground(getDrawable(R.drawable.bg_button_back));
            agroup.setTextColor(getResources().getColor(R.color.colorRed));
            bgroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bgroup.setTextColor(getResources().getColor(R.color.colorRed));
            bngroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bngroup.setTextColor(getResources().getColor(R.color.colorRed));
            ogroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ogroup.setTextColor(getResources().getColor(R.color.colorRed));
            ongroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ongroup.setTextColor(getResources().getColor(R.color.colorRed));

            bloodgroup = "A-";
        }
        if (v == bgroup) {
            bgroup.setBackground(getDrawable(R.drawable.bg_color));
            bgroup.setTextColor(getResources().getColor(R.color.white));
            agroup.setBackground(getDrawable(R.drawable.bg_button_back));
            agroup.setTextColor(getResources().getColor(R.color.colorRed));
            angroup.setBackground(getDrawable(R.drawable.bg_button_back));
            angroup.setTextColor(getResources().getColor(R.color.colorRed));
            bngroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bngroup.setTextColor(getResources().getColor(R.color.colorRed));
            ogroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ogroup.setTextColor(getResources().getColor(R.color.colorRed));
            ongroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ongroup.setTextColor(getResources().getColor(R.color.colorRed));

            bloodgroup = "B+";
        }
        if (v == bngroup) {
            bngroup.setBackground(getDrawable(R.drawable.bg_color));
            bngroup.setTextColor(getResources().getColor(R.color.white));
            agroup.setBackground(getDrawable(R.drawable.bg_button_back));
            agroup.setTextColor(getResources().getColor(R.color.colorRed));
            angroup.setBackground(getDrawable(R.drawable.bg_button_back));
            angroup.setTextColor(getResources().getColor(R.color.colorRed));
            bgroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bgroup.setTextColor(getResources().getColor(R.color.colorRed));
            ogroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ogroup.setTextColor(getResources().getColor(R.color.colorRed));
            ongroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ongroup.setTextColor(getResources().getColor(R.color.colorRed));

            bloodgroup = "B-";
        }
        if (v == ogroup) {
            ogroup.setBackground(getDrawable(R.drawable.bg_color));
            ogroup.setTextColor(getResources().getColor(R.color.white));
            agroup.setBackground(getDrawable(R.drawable.bg_button_back));
            agroup.setTextColor(getResources().getColor(R.color.colorRed));
            angroup.setBackground(getDrawable(R.drawable.bg_button_back));
            angroup.setTextColor(getResources().getColor(R.color.colorRed));
            bgroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bgroup.setTextColor(getResources().getColor(R.color.colorRed));
            bngroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bngroup.setTextColor(getResources().getColor(R.color.colorRed));
            ongroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ongroup.setTextColor(getResources().getColor(R.color.colorRed));

            bloodgroup = "O+";
        }
        if (v == ongroup) {
            ongroup.setBackground(getDrawable(R.drawable.bg_color));
            ongroup.setTextColor(getResources().getColor(R.color.white));
            agroup.setBackground(getDrawable(R.drawable.bg_button_back));
            agroup.setTextColor(getResources().getColor(R.color.colorRed));
            angroup.setBackground(getDrawable(R.drawable.bg_button_back));
            angroup.setTextColor(getResources().getColor(R.color.colorRed));
            bgroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bgroup.setTextColor(getResources().getColor(R.color.colorRed));
            bngroup.setBackground(getDrawable(R.drawable.bg_button_back));
            bngroup.setTextColor(getResources().getColor(R.color.colorRed));
            ogroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ogroup.setTextColor(getResources().getColor(R.color.colorRed));

            bloodgroup = "O-";
        }
        if(v == findrequest){
            Intent intent=new Intent(Selectrequest.this, RequestActivity.class);
            intent.putExtra("bloodgroup", bloodgroup);
            startActivity(intent);
        }
    }


}
