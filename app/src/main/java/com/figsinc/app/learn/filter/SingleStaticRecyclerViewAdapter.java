package com.figsinc.app.learn.filter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.figsinc.app.R;

import java.util.ArrayList;
import java.util.List;

public class SingleStaticRecyclerViewAdapter extends RecyclerView.Adapter<SingleStaticRecyclerViewAdapter.DataObjectHolder> {

    private List<String> mData = new ArrayList<>();
    private SingleStaticClickListener sClickListener;
    private int sSelected = 0;

    public SingleStaticRecyclerViewAdapter(ArrayList<String>mData) {
        this.mData = mData;
    }

    static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextView;
        RadioButton mRadioButton;
        SingleStaticClickListener sClickListener;

        public DataObjectHolder(View itemView, SingleStaticClickListener sClickListener) {
            super(itemView);
            this.sClickListener=sClickListener;
            this.mTextView = (TextView) itemView.findViewById(R.id.entities_filter_item);
            this.mRadioButton = (RadioButton) itemView.findViewById(R.id.single_list_item_check_button);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //sSelected = getAdapterPosition();

            sClickListener.onItemClickListener(getAdapterPosition(), view);
        }
    }

    public String selectedItem(int position) {
        sSelected=position;
        System.out.println("  sSelected  ====>>>>" + sSelected);
        notifyDataSetChanged();
        String name = "";
        if (sSelected != -1) {
            name = mData.get(sSelected);
        }
        return name;
    }

    public String setFirstItem(){
        sSelected = 0;
        String name="";
        if(mData.size()>0){
            name=mData.get(0);
        }
        notifyDataSetChanged();
        return  name;
    }

   public  void setOnItemClickListener(SingleStaticClickListener clickListener) {
        sClickListener = clickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_list_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view,sClickListener);
        return dataObjectHolder;
    }


    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.mTextView.setText(mData.get(position));
        holder.mRadioButton.setChecked(sSelected == position);
        System.out.println("  sSelected  ====>>>>" + sSelected);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface SingleStaticClickListener {
       public void onItemClickListener(int position, View view);
    }

}