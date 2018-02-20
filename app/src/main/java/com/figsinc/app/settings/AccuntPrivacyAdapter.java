package com.figsinc.app.settings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.figsinc.app.R;

import java.util.List;

public class AccuntPrivacyAdapter extends RecyclerView.Adapter<AccuntPrivacyAdapter.MyViewHolder> {

    private List<String> titleList, subtileList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView_title, textView_subtitle;
        public Switch Switch_info;
        public Spinner spinner_Select;

        public MyViewHolder(View view) {
            super(view);
            textView_title = (TextView) view.findViewById(R.id.textView_title);
            textView_subtitle = (TextView) view.findViewById(R.id.textView_subtitle);
            Switch_info = (Switch) view.findViewById(R.id.Switch_info);
            spinner_Select = (Spinner) view.findViewById(R.id.spinner_Select);

        }
    }

    public AccuntPrivacyAdapter(List<String> titleList, List<String> subtileList, Context context) {
        this.titleList = titleList;
        this.subtileList = subtileList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_account_privacy, parent, false);

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


