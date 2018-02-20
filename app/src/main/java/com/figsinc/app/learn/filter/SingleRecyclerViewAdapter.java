package com.figsinc.app.learn.filter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.figsinc.app.R;
import com.figsinc.app.learn.Model.Filter;

import java.util.ArrayList;
import java.util.List;

public class SingleRecyclerViewAdapter extends RecyclerView.Adapter<SingleRecyclerViewAdapter.DataObjectHolder> {

    List<Filter> mData = new ArrayList<>();
    private static SingleClickListener sClickListener;
    private static int sSelected = 0;

    public SingleRecyclerViewAdapter(ArrayList<Filter> mData) {
        this.mData = mData;
    }

    static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextView;
        RadioButton mRadioButton;

        public DataObjectHolder(View itemView) {
            super(itemView);
            this.mTextView = (TextView) itemView.findViewById(R.id.entities_filter_item);
            this.mRadioButton = (RadioButton) itemView.findViewById(R.id.single_list_item_check_button);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            sSelected = getAdapterPosition();
            sClickListener.onItemClickListener(getAdapterPosition(), view);
        }
    }

    public String selectedItem() {
        System.out.println("  sSelected  ====>>>>" + sSelected);
        notifyDataSetChanged();
        String name = "";
        if (sSelected != -1) {
            name = mData.get(sSelected).getName();
        }
        return name;
    }

    protected void setFirstItem(){
        sSelected = 0;
        notifyDataSetChanged();
    }

    void setOnItemClickListener(SingleClickListener clickListener) {
        sClickListener = clickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_list_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }


    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.mTextView.setText(mData.get(position).getName());

        holder.mRadioButton.setChecked(sSelected == position);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    interface SingleClickListener {
        void onItemClickListener(int position, View view);
    }

}