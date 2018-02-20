package com.figsinc.app.settings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.figsinc.app.R;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.MyViewHolder> {

    private List<String> titleList, subtileList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView_title, textView_subtitle;
        public LinearLayout root_linear;

        public MyViewHolder(View view) {
            super(view);
            textView_title = (TextView) view.findViewById(R.id.textView_title);
            textView_subtitle = (TextView) view.findViewById(R.id.textView_subtitle);
            root_linear = (LinearLayout)view.findViewById(R.id.root_linear);

        }
    }


    public SettingsAdapter(List<String> titleList, List<String> subtileList, Context context) {
        this.titleList = titleList;
        this.subtileList = subtileList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_settings, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView_title.setText(titleList.get(position));
        holder.textView_subtitle.setText(subtileList.get(position));

        holder.root_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (position){
                    case 0:
                        Intent personalInfo = new Intent(context,PersonalInfoAcitivity.class);
                        context.startActivity(personalInfo);
                        break;
                   /* case 1:
                        Intent linkedAccount = new Intent(context,LinkedAccountActivity.class);
                        context.startActivity(linkedAccount);
                        break;
                    case 1:
                        //Intent accountPrivacy = new Intent(context,AccountPrivacyActivity.class);
                       // context.startActivity(accountPrivacy);
                        break;*/
                    case 1:
                        Intent emailNotification = new Intent(context,EmailNotificationActivity.class);
                        context.startActivity(emailNotification);
                        break;
                    case 2:
                        Intent subscription = new Intent(context,SubscriptionActivity.class);
                        context.startActivity(subscription);
                        break;

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }


}


