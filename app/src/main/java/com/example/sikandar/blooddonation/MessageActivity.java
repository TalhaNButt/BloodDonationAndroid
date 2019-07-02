package com.example.sikandar.blooddonation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikandar.blooddonation.Notification.Client;
import com.example.sikandar.blooddonation.Notification.Data;
import com.example.sikandar.blooddonation.Notification.MyResponse;
import com.example.sikandar.blooddonation.Notification.Sender;
import com.example.sikandar.blooddonation.Notification.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {
    TextView name;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    Intent intent;
    ImageButton btn_send;
    EditText text_send;
    MessageAdapter messageAdapter;
    List<Chat> mchat;
    int count = 0;
    RecyclerView recyclerView;
    ValueEventListener seenlistener;

    APIService apiService;
    boolean notify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar = findViewById(R.id.headerrequest);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);


        recyclerView = findViewById(R.id.recyclechat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        name = findViewById(R.id.name);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        intent = getIntent();
        final String userid = intent.getStringExtra("userid");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if(!msg.equals("")){
                    sendMessage(firebaseUser.getUid(), userid, msg);
                }
                else{
                    Toast.makeText(MessageActivity.this, "Please Type a message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                name.setText(user.getName());
                readMessage(firebaseUser.getUid(), userid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        seenMessage(userid);


    }

    private void seenMessage(final String userid){
        reference = FirebaseDatabase.getInstance().getReference("ChatList");
        seenlistener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                        String a = dataSnapshot2.getKey();
                        String b = snapshot.getKey();
                        if (a.equals(firebaseUser.getUid()) && b.equals(userid)) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot2.getChildren()) {
                                Chat chat = dataSnapshot1.getValue(Chat.class);
                                if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid)) {
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("isseen", true);
                                    dataSnapshot1.getRef().updateChildren(hashMap);
                                }
                                else {
                                    for (DataSnapshot dataSnapshot4 : dataSnapshot2.getChildren()) {
                                        Chat chat2 = dataSnapshot4.getValue(Chat.class);
                                        if (chat2.getReceiver().equals(firebaseUser.getUid()) && chat2.getSender().equals(userid)) {
                                            HashMap<String, Object> hashMap = new HashMap<>();
                                            hashMap.put("isseen", true);
                                            dataSnapshot4.getRef().updateChildren(hashMap);
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage(String sender, final String receiver, final String message){
        notify = true;
        final String userid = intent.getStringExtra("userid");
        final Chat chat = new Chat();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        chat.setReceiver(receiver);
        chat.setSender(sender);
        chat.setIsseen(false);
        chat.setMessage(message);
        final DatabaseReference finalReference = reference;
        reference.child("ChatList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren())
                    {
                        String a= dataSnapshot2.getKey();
                        String b=dataSnapshot1.getKey();
                        if (a.equals(firebaseUser.getUid()) && b.equals(userid)){
                            String c=dataSnapshot1.getKey();
                            chat.setReceiver(c);
                            chat.setSender(a);
                            finalReference.child("ChatList").child(userid).child(firebaseUser.getUid()).push().setValue(chat);
                            count++;
                        }
                    }
                }

                if (count==0){
                    chat.setReceiver(userid);
                    chat.setSender(firebaseUser.getUid());
                    chat.setMessage(message);
                    finalReference.child("ChatList").child(firebaseUser.getUid()).child(userid).push().setValue(chat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final String msg = message;
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (notify) {
                    sendNotification(receiver, user.getName(), msg);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void sendNotification(final String receiver, final String name, final String message){
        final String userid = intent.getStringExtra("userid");
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(firebaseUser.getUid(), R.mipmap.ic_launcher, "Someone needs Blood!!",name+": "+message, userid  );
                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                            if(response.code()==200){
                                if(response.body().success !=1){
                                    Toast.makeText(MessageActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void readMessage(final String myid, final String userid){
        mchat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("ChatList");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot2 : snapshot.getChildren()) {
                        String a = dataSnapshot2.getKey();
                        if (a.equals(firebaseUser.getUid())) {
                            Chat chat2 = dataSnapshot2.getValue(Chat.class);
                            String b = snapshot.getKey();
                            chat2.setReceiver(b);
                            chat2.setSender(a);
                            if (chat2.getReceiver().equals(myid) && chat2.getSender().equals(userid) || chat2.getReceiver()
                                    .equals(userid) && chat2.getSender().equals(myid)) {
                                mchat.add(chat2);
                            }
                        }

                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                Chat chat = dataSnapshot3.getValue(Chat.class);
                                if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) || chat.getReceiver()
                                        .equals(userid) && chat.getSender().equals(myid)) {
                                    mchat.add(chat);
                            }
                        }

                        messageAdapter = new MessageAdapter(MessageActivity.this, mchat);
                        recyclerView.setAdapter(messageAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void status(String status){

        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap=new HashMap<>();
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
        reference.removeEventListener(seenlistener);
        status("offline");
    }
}
