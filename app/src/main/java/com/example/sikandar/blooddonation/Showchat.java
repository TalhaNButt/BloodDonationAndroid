package com.example.sikandar.blooddonation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sikandar.blooddonation.Notification.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class Showchat extends Fragment {
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<User> mData;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    private List<String> userlist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_showchat, container, false);
        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        userlist=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("ChatList").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userlist.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String a=snapshot.getKey();
                        userlist.add(a);

                }

                chatList();
            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



     DatabaseReference reference= FirebaseDatabase.getInstance().getReference("ChatList");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren())
                    {
                       String a=dataSnapshot2.getKey();
                       if (a.equals(firebaseUser.getUid())){
                           String b=dataSnapshot1.getKey();
                           userlist.add(b);
                           break;

                       }
          }

          }
          chatList();

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

   /*     DatabaseReference reference3=FirebaseDatabase.getInstance().getReference("ChatList").child(firebaseUser.getUid());
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    userlist.clear();
                    String a=dataSnapshot1.getKey();
                        userlist.add(a);
                    Toast.makeText(getContext(), ""+userlist, Toast.LENGTH_SHORT).show();

                        }


                    }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/



        updateToken(FirebaseInstanceId.getInstance().getToken());

        return view;
    }

    private void updateToken(String token){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1=new Token(token);
        reference.child(firebaseUser.getUid()).setValue(token1);
    }

    private void chatList(){
        mData=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mData.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user =snapshot.getValue(User.class);
                    for(String a:userlist){
                        if(user.getId()!=null && user.getId().equals(a)){
                            mData.add(user);
                        }
                    }
                }
                chatAdapter=new ChatAdapter(getContext(), mData, true);
                recyclerView.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
