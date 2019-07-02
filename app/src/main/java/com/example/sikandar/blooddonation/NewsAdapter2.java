package com.example.sikandar.blooddonation;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter2 extends RecyclerView.Adapter<NewsAdapter2.NewsViewHolder> implements Filterable {


    Context mContext;
    List<Request> mData ;
    List<Request> mDataFiltered ;
    boolean isDark = false;


    public NewsAdapter2(Context mContext, List<Request> mData, boolean isDark) {
        this.mContext = mContext;
        this.mData = mData;
        this.isDark = isDark;
        this.mDataFiltered=mData;
    }

    public NewsAdapter2(Context mContext, List<Request> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered=mData;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_news2,viewGroup,false);
        return new NewsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int position) {

        // bind data here
        final Request request=mData.get(position);
        newsViewHolder.tv_title.setText(request.getTitle());
        newsViewHolder.tv_content.setText(request.getDescription());
        newsViewHolder.tv_date.setText(request.getCurrentdate());
        newsViewHolder.tv_time.setText(request.getCurrenttime());
        newsViewHolder.required.setText(request.getBloodgroup());
       newsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, Userdetail.class);
                intent.putExtra("name", request.getName());
                intent.putExtra("bloodgroup", request.getUserbloodgroup());
                intent.putExtra("contact", request.getContact());
                intent.putExtra("userid", request.getUserid());
                mContext.startActivity(intent);
            }
        });

        // we apply animation to views here
        // first lets create an animation for user photo

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
        // newsViewHolder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        newsViewHolder.container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));



        newsViewHolder.tv_title.setText(mDataFiltered.get(position).getTitle());
        newsViewHolder.tv_content.setText(mDataFiltered.get(position).getDescription());
        newsViewHolder.tv_date.setText(mDataFiltered.get(position).getCurrentdate());
        newsViewHolder.tv_time.setText(mDataFiltered.get(position).getCurrenttime());
        newsViewHolder.required.setText(mDataFiltered.get(position).getBloodgroup());



    }

    @Override
    public int getItemCount() {

        return mDataFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key=constraint.toString();
                if(key.isEmpty()){
                    mDataFiltered=mData;
                }
                else{
                    List<Request> listfiltered=new ArrayList<>();
                    for(Request row: mData){
                        if(row.getCurrentdate().toLowerCase().contains(key.toLowerCase())||
                                row.getTitle().toLowerCase().contains(key.toLowerCase()) ||
                                row.getBloodgroup().contains(key.toLowerCase())){
                            listfiltered.add(row);
                        }
                    }
                    mDataFiltered=listfiltered;

                }

                FilterResults filterResults=new FilterResults();
                filterResults.values=mDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataFiltered= (List<Request>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {



        TextView tv_title,tv_content,tv_date,tv_time, required;
        RelativeLayout container;





        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_description);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time=itemView.findViewById(R.id.tv_timee);
            required=itemView.findViewById(R.id.required_bg);

            if (isDark) {
                setDarkTheme();
            }



        }


        private void setDarkTheme() {

            container.setBackgroundResource(R.drawable.card_bg_dark);

        }



    }
}

