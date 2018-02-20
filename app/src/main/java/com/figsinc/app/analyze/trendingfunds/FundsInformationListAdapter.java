package com.figsinc.app.analyze.trendingfunds;

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

public class FundsInformationListAdapter extends RecyclerView.Adapter<FundsInformationListAdapter.MyViewHolder> {

    private List<String> filterList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView filterItem;
        public LinearLayout linearParent;

        public MyViewHolder(View view) {
            super(view);
            filterItem = (TextView) view.findViewById(R.id.entities_filter_item);
            linearParent = (LinearLayout)view.findViewById(R.id.linearParent);
        }
    }


    public FundsInformationListAdapter(List<String> sectorList, Context context) {
        this.filterList = sectorList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.small_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.filterItem.setText(filterList.get(position));

        holder.linearParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (position){
                    case 0:
                        Intent intent = new Intent(context, FundsBasicInfoActivity.class);
                        context.startActivity(intent);
                        break;

                    case 1:
                        Intent intent2 = new Intent(context, FundsManagementFeesActivity.class);
                        context.startActivity(intent2);
                        break;

                    case 2:
                        Intent intent3 = new Intent(context, FundsYearlyPerformanceActivity.class);
                        context.startActivity(intent3);
                        break;

                    case 3:
                        Intent intent4 = new Intent(context, FundsLipperScoreCardActivity.class);
                        context.startActivity(intent4);
                        break;

                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return filterList.size();
    }


}


