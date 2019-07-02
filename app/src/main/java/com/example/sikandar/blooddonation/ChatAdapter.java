package com.example.sikandar.blooddonation;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.NewsViewHolder> {
    Context mContext;
    List<User> mData;
    private boolean ischat;
    String thelastMessage;

    public ChatAdapter(Context mContext, List<User> mData, boolean ischat) {
        this.mContext = mContext;
        this.mData = mData;
        this.ischat=ischat;
    }



    @NonNull
    @Override
    public ChatAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_chat,viewGroup,false);
        return new ChatAdapter.NewsViewHolder(layout);
    }


    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.NewsViewHolder newsViewHolder, int position) {
        final User user = mData.get(position);
        newsViewHolder.tv_name.setText(user.getName());

        if(ischat){
            lastMessage(user.getId(), newsViewHolder.last_msg);
            setThelastMessage(user.getId(), newsViewHolder.last_msg);
        }
        else{
            newsViewHolder.last_msg.setVisibility(View.GONE);
        }

        if(ischat){
            if(user.getStatus().equals("online")){
                newsViewHolder.img_on.setVisibility(View.VISIBLE);
                newsViewHolder.img_off.setVisibility(View.GONE);
            }
            else{
                newsViewHolder.img_on.setVisibility(View.GONE);
                newsViewHolder.img_off.setVisibility(View.VISIBLE);
            }
        }
        else{
            newsViewHolder.img_on.setVisibility(View.GONE);
            newsViewHolder.img_off.setVisibility(View.GONE);
        }
        newsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_on, img_off;

        TextView tv_name, last_msg;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            img_on=itemView.findViewById(R.id.img_on);
            img_off=itemView.findViewById(R.id.img_off);
            last_msg = itemView.findViewById(R.id.last_msg);



        }
    }
    private void setThelastMessage(final String userid, final TextView last_msg){
        thelastMessage="default";
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("ChatList");
        reference1.child(firebaseUser.getUid()).child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Chat chat = dataSnapshot1.getValue(Chat.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
                        thelastMessage = chat.getMessage();

                    }
                }
                switch (thelastMessage){
                    case "default":
                        last_msg.setText("Recovering messages..");
                        break;
                    default:
                        last_msg.setText(thelastMessage);
                        break;
                }
                thelastMessage="default";

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void lastMessage(final String userid, final TextView last_msg){
        thelastMessage="default";
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("ChatList");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String a = snapshot1.getKey();
                        if (a.equals(firebaseUser.getUid())) {
                            for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                                Chat chat = snapshot2.getValue(Chat.class);
                                if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                                        chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
                                    thelastMessage = chat.getMessage();
                                }
                            }
                        }
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                            Chat chat = snapshot2.getValue(Chat.class);
                            if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                                    chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
                                thelastMessage = chat.getMessage();
                            }
                        }


                    }
                }
                switch (thelastMessage){
                    case "default":
                        last_msg.setText("No Message");
                        break;
                    default:
                        last_msg.setText(thelastMessage);
                        break;
                }
                thelastMessage="default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
