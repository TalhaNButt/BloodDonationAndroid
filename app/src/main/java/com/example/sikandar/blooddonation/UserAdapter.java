package com.example.sikandar.blooddonation;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.NewsViewHolder> {
    Context mContext;
    List<User> mData;
    private boolean ischat;

    public UserAdapter(Context mContext, List<User> mData, boolean ischat) {
        this.mContext = mContext;
        this.mData = mData;
        this.ischat=ischat;
    }



    @NonNull
    @Override
    public UserAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_user,viewGroup,false);
        return new NewsViewHolder(layout);
    }


    @Override
    public void onBindViewHolder(@NonNull UserAdapter.NewsViewHolder newsViewHolder, int position) {
        final User user = mData.get(position);
        newsViewHolder.tv_name.setText(user.getName());
        newsViewHolder.tv_bg.setText(user.getBloodgroup());
        newsViewHolder.tv_contact.setText(String.valueOf(user.getPhone()));
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
   /*     newsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                mContext.startActivity(intent);
            }
        });*/
        newsViewHolder.userdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, Userdetail.class);
                intent.putExtra("userid", user.getId());
                intent.putExtra("name", user.getName());
                intent.putExtra("bloodgroup", user.getBloodgroup());
                intent.putExtra("contact", user.getPhone());
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
        Button userdetail;
        TextView tv_name, tv_bg, tv_contact;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_bg = itemView.findViewById(R.id.tv_bg);
            tv_contact = itemView.findViewById(R.id.tv_contact);
            img_on=itemView.findViewById(R.id.img_on);
            img_off=itemView.findViewById(R.id.img_off);
            userdetail=itemView.findViewById(R.id.userdetail);


        }
    }
}