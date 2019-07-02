package com.example.sikandar.blooddonation;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class MakeRequestActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText title;
    private EditText description;
    private Button makerequest;
    DatabaseReference reference, reference1;
    private Button agroup, angroup, bgroup, bngroup, ogroup, ongroup;
    private ImageButton location;
    long maxId = 0;
    Request request;
    private FirebaseAuth mAuth;
    private String bloodgroup;
    private String currentdate;
    private String currenttime;
    private double lat;
    private double lon;
    private ProgressDialog progress;

    public static final String SHARED_PREFS = "sharedPrefs";

    private String Title;
    private String Description;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);

        makerequest = findViewById(R.id.makerequest);
        title = findViewById(R.id.titlee);
        description = findViewById(R.id.description);
        agroup = findViewById(R.id.agroup);
        angroup = findViewById(R.id.angroup);
        bgroup = findViewById(R.id.bgroup);
        bngroup = findViewById(R.id.bngroup);
        ogroup = findViewById(R.id.ogroup);
        ongroup = findViewById(R.id.ongroup);

        location = findViewById(R.id.location_makereq);
        location.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.headerrequest);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        agroup.setOnClickListener(this);
        angroup.setOnClickListener(this);
        bgroup.setOnClickListener(this);
        bngroup.setOnClickListener(this);
        ogroup.setOnClickListener(this);
        ongroup.setOnClickListener(this);
        makerequest.setOnClickListener(this);

        progress = new ProgressDialog(MakeRequestActivity.this);

        lat = getIntent().getDoubleExtra("lat",0);
        lon = getIntent().getDoubleExtra("lon",0);

        Calendar calendar = Calendar.getInstance();
        currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        Calendar calendar1 = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss aa");
        currenttime = format.format(calendar1.getTime());

        request = new Request();

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Request").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        reference1 = FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxId = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        loadData();
        updateViews();
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        if(v == agroup){
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

            bloodgroup="A+";
        }
        if(v == angroup) {

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

            bloodgroup="A-";
        }
        if(v == bgroup){
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

            bloodgroup="B+";
        }
        if(v == bngroup){
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

            bloodgroup="B-";
        }
        if(v == ogroup){
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

            bloodgroup="O+";
        }
        if(v == ongroup){
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

            bloodgroup="O-";
        }


        if(v == location){
            Toast.makeText(this, "Location Clicked", Toast.LENGTH_SHORT).show();
            saveData();
            Intent i = new Intent(MakeRequestActivity.this,MakeReqMaps.class);
            startActivity(i);
        }

        if(v == makerequest) {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();               //changed
            final String key = firebaseUser.getUid();
            final String id = reference.push().getKey();

            request.setTitle(title.getText().toString().trim());
            request.setDescription(description.getText().toString().trim());
            request.setBloodgroup(bloodgroup);
            request.setCurrentdate(currentdate);
            request.setCurrenttime(currenttime);
            request.setLat(lat);
            request.setLon(lon);
            request.setId(id);
            request.setUserid(key);

            //reference.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).push().setValue(request);
            //reference1.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).push().setValue(request);

            reference1.child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        String name = (String) dataSnapshot.getValue();
                        request.setName(name);
                        reference.child(id).setValue(request);
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            reference1.child("phone").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        String contact = (String) dataSnapshot.getValue();
                        request.setContact(contact);
                        reference.child(id).setValue(request);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            reference1.child("bloodgroup").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        String userbloodgroup = (String) dataSnapshot.getValue();
                        request.setUserbloodgroup(userbloodgroup);
                        reference.child(id).setValue(request);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            reference.child(id).setValue(request);

            Toast.makeText(MakeRequestActivity.this, "Request has been published. Kindly wait for the response.", Toast.LENGTH_LONG).show();

            delData();

            title.setText("");
            description.setText("");
            ogroup.setBackground(getDrawable(R.drawable.bg_button_back));
            ogroup.setTextColor(getResources().getColor(R.color.colorRed));
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

            bloodgroup = null;

            switch (v.getId()) {
                case R.id.makerequest:
                    progress.setMessage("Loading...");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(true);
                    progress.show();
                    new Thread() {

                        public void run() {
                            Intent i = new Intent(MakeRequestActivity.this, HomeActivity.class);
                            startActivity(i);
                            progress.dismiss();
                        }

                    }.start();

                    break;

            }
        }
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("title", title.getText().toString());
        editor.putString("description", description.getText().toString());

        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Title = sharedPreferences.getString("title", "");
        Description = sharedPreferences.getString("description", "");
    }

    public void updateViews() {
        title.setText(Title);
        description.setText(Description);
    }

    public void delData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}

