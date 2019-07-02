package com.example.sikandar.blooddonation;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView need_card, request_card, makerequest_card, availiability_card, feedback_card;
    private Button profile, notification, settings, logout, btnmenu;
    private TextView username, bg;
    private CircleImageView userpic;
    private RelativeLayout maincontent;
    private LinearLayout mainmenu;
    private Animation fromtop, frombottom;
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private static final int CHOOSE_IMAGE = 101;
    private DrawerLayout drawer;
    TextView mainName, BG;
    Uri uriProfileImage;
    String profileImageUrl;
    FirebaseAuth mAuth;
    private String currentUserID;
    private DatabaseReference mDatabase, reference;

    private ImageView mainImage;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        mainName = findViewById(R.id.name);
        mainImage = findViewById(R.id.mainImage);
        BG = (TextView) findViewById(R.id.bg);

        mDrawerlayout = findViewById(R.id.drawer);

        NavigationView navigationView = findViewById(R.id.nvDrawer);

        mainName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.name);
        mainImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.mainImage);
        BG = navigationView.getHeaderView(0).findViewById(R.id.bg);

        loadUserInformation();
        loadFromDB();

        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        Fragment myFragment = null;
        Class fragmentClass;
        fragmentClass = homefragment.class;

        try {
            myFragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.f1content, myFragment).commit();
        setTitle("Home");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(navigationView);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectitemdrawer(MenuItem menuItem) {
        Fragment myFragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.home1:
                fragmentClass = homefragment.class;
                break;
            case R.id.profile:
                fragmentClass = Profile.class;
                break;
            case R.id.notifications:
                fragmentClass = Notifications.class;
                break;
            case R.id.chat:
                fragmentClass = Showchat.class;
                break;
            case R.id.yourrequests:
                fragmentClass = Myrequest.class;
                break;
            case R.id.settings:
                fragmentClass = Settings.class;
                break;
            default:
                fragmentClass = homefragment.class;
                break;
        }

        try {
            myFragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.f1content, myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerlayout.closeDrawers();

        switch (menuItem.getItemId()) {
            case R.id.logout1:                                                   //changed
                new AlertDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("Do you really want to Logout?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(HomeActivity.this, "User Logout Successfull", Toast.LENGTH_SHORT).show();
                                userLogout();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
                break;


        }

        menuItem.setChecked(true);
        mDrawerlayout.closeDrawers();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectitemdrawer(item);
                return true;
            }
        });
    }

    private void loadUserInformation() {
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(mainImage);
            }
            if (user.getDisplayName() != null) {
                mainName.setText(user.getDisplayName());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                mainImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void userLogout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent1 = new Intent(this, LoginActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent1);
        //break;
    }

    private void loadFromDB() {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String myName = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();

                String myBloodGroup = Objects.requireNonNull(dataSnapshot.child("bloodgroup").getValue()).toString();


                mainName.setText(myName);
                BG.setText(myBloodGroup);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error Loading from db", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void status(String status) {

        reference = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }

    public void onBackPressed() {

        if (mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerlayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


}
