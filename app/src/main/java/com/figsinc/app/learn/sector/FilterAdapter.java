package com.figsinc.app.learn.sector;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.figsinc.app.R;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {

    private List<String> filterList;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView filterItem;

        public MyViewHolder(View view) {
            super(view);
            filterItem = (TextView) view.findViewById(R.id.entities_filter_item);
        }
    }


    public FilterAdapter(List<String> sectorList, Context context) {
        this.filterList = sectorList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.filterItem.setText(filterList.get(position));
    }


    @Override
    public int getItemCount() {
        return filterList.size();
    }


}


