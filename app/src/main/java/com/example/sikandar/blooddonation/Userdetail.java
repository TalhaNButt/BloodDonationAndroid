package com.example.sikandar.blooddonation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Userdetail extends AppCompatActivity {
    Intent intent;
    TextView tv_name, tv_bloodgroup,tv_contact;
    ImageButton call;
    Button chat;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetail);
        intent = getIntent();

        final String name = intent.getStringExtra("name");
        final String bloodgroup = intent.getStringExtra("bloodgroup");
        final String contact = intent.getStringExtra("contact");
        final String userid = intent.getStringExtra("userid");

        tv_contact = findViewById(R.id.tv_phone);
        tv_name = findViewById(R.id.tv_name);
        tv_bloodgroup = findViewById(R.id.tv_bg);
        call = findViewById(R.id.call);
        tv_name.setText(name);
        tv_bloodgroup.setText(bloodgroup);
        tv_contact.setText(contact);
        chat = findViewById(R.id.chat);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Userdetail.this, PhoneActivity.class);
                intent.putExtra("tv_contact", tv_contact.getText().toString());
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Userdetail.this, MessageActivity.class);
                Toast.makeText(Userdetail.this, userid, Toast.LENGTH_SHORT).show();
                intent.putExtra("userid", userid);
                startActivity(intent);

            }
        });

    }


}
