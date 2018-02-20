package com.figsinc.app.settings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.figsinc.app.R;

import java.util.List;

public class EmailNotificationAdapter extends RecyclerView.Adapter<EmailNotificationAdapter.MyViewHolder> {

    private List<String> titleList, subtileList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView_title, textView_subtitle;
        public Switch switch_notification;

        public MyViewHolder(View view) {
            super(view);
            textView_title = (TextView) view.findViewById(R.id.textView_title);
            textView_subtitle = (TextView) view.findViewById(R.id.textView_subtitle);
            switch_notification = (Switch)view.findViewById(R.id.switch_notification);

        }
    }


    public EmailNotificationAdapter(List<String> titleList, List<String> subtileList, Context context) {
        this.titleList = titleList;
        this.subtileList = subtileList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_email_notification, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView_title.setText(titleList.get(position));
        holder.textView_subtitle.setText(subtileList.get(position));
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }


}


