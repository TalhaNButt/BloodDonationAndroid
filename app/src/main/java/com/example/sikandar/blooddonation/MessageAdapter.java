package com.example.sikandar.blooddonation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.NewsViewHolder> {
   public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;

    private Context mContext;
    private List<Chat> mChat;



    FirebaseUser firebaseUser;

    public MessageAdapter(Context mContext, List<Chat> mChat){
        this.mChat = mChat;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MessageAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == MSG_TYPE_RIGHT) {
            View layout;
            layout = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MessageAdapter.NewsViewHolder(layout);
        }
        else{
            View layout;
            layout = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, viewGroup, false);
            return new MessageAdapter.NewsViewHolder(layout);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.NewsViewHolder newsViewHolder, int position) {
       Chat chat = mChat.get(position);
       newsViewHolder.show_message.setText(chat.getMessage());

       if (chat.getMessage() == null){
           newsViewHolder.show_message.setVisibility(View.GONE);
           newsViewHolder.txt_seen.setVisibility(View.GONE);
           newsViewHolder.profile_image.setVisibility(View.GONE);
       }
       newsViewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);

       if(position == mChat.size()-1){
           if(chat.isIsseen()){
               newsViewHolder.txt_seen.setText("Seen");
           }
           else{
               newsViewHolder.txt_seen.setText("Delivered");
           }
       }
       else{
           newsViewHolder.txt_seen.setVisibility(View.GONE);
       }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }



    public class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_image;
        public TextView txt_seen;
        TextView show_message;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);



        }
    }

    @Override
    public int getItemViewType(int position) {
       firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

       if(mChat.get(position).getSender().equals(firebaseUser.getUid())){
           return MSG_TYPE_RIGHT;
       }
       else{
           return MSG_TYPE_LEFT;
       }
    }
}