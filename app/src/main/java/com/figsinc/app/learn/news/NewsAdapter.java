package com.figsinc.app.learn.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.figsinc.app.R;
import com.figsinc.app.learn.Model.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private List<News> SectorList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView news_published_date;
        public TextView profile_name;
        public ImageView imageview_logo;

        public LinearLayout relative_topSection;

        public MyViewHolder(View view) {
            super(view);
            profile_name = (TextView) view.findViewById(R.id.profile_name);
            news_published_date = (TextView) view.findViewById(R.id.news_published_date);
            imageview_logo = (ImageView) view.findViewById(R.id.profile_image);
            relative_topSection = (LinearLayout)view.findViewById(R.id.relative_topSection);
        }
    }


    public NewsAdapter(List<News> sectorList, Context context) {
        this.SectorList = sectorList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.maufacturing_news_child_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Glide.with(context).load(SectorList.get(position).getImage()).
                into(holder.imageview_logo);
        holder.profile_name.setText(SectorList.get(position).getName());
        holder.news_published_date.setText(SectorList.get(position).getDatePublished());
        holder.relative_topSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,NewsDetailActivity.class);
                intent.putExtra("DetailsUrl",SectorList.get(position).getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return SectorList.size();
    }


}


