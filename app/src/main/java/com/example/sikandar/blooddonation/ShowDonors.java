package com.example.sikandar.blooddonation;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ShowDonors extends AppCompatActivity {
    private DatabaseReference reference, reference1;
    private RecyclerView recyclerView;
    private List<User> mData;
    private UserAdapter userAdapter;
    private ChildEventListener mchild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_donors);

        Toolbar toolbar = findViewById(R.id.headerrequest);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String avail = getIntent().getStringExtra("availability");
        final String bloodgroup = getIntent().getStringExtra("bloodgroup");

        reference = FirebaseDatabase.getInstance().getReference("Users");
        Query queries = reference.orderByChild("availability").equalTo("on");
        //Query queries1 = reference.orderByChild("bloodgroup").equalTo(bloodgroup);

        recyclerView = findViewById(R.id.news_rv1);
        mData = new ArrayList<>();

        queries.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                if(user.getBloodgroup().equals(bloodgroup)) {
                    Toast.makeText(ShowDonors.this, "In Snapshot", Toast.LENGTH_SHORT).show();
                    mData.add(user);
                    userAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // adapter ini and setup
        userAdapter = new UserAdapter(this,mData, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);
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

