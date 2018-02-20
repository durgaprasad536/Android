package com.figsinc.app.analyze;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.figsinc.app.R;
import com.figsinc.app.analyze.trendingstocks.CapitalStructureListViewBarChartActivity;
import com.figsinc.app.analyze.trendingstocks.EfficiencyListViewBarChartActivity;
import com.figsinc.app.analyze.trendingstocks.ProfitabilityListViewBarChartActivity;
import com.figsinc.app.analyze.trendingstocks.ValuationListViewBarChartActivity;

import java.util.List;

import static com.figsinc.app.analyze.trendingstocks.ValuationListViewBarChartActivity.KEY_TICKER_EXCHANGE;

public class SmallListAdapter extends RecyclerView.Adapter<SmallListAdapter.MyViewHolder> {

    private List<String> filterList;
    private Context context;
    public String tickerExchange;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView filterItem;
        public LinearLayout linearParent;

        public MyViewHolder(View view) {
            super(view);
            filterItem = (TextView) view.findViewById(R.id.entities_filter_item);
            linearParent = (LinearLayout)view.findViewById(R.id.linearParent);
        }
    }


    public SmallListAdapter(final List<String> sectorList,final Context context,final String tickerExchange) {
        this.filterList = sectorList;
        this.context = context;
        this.tickerExchange = tickerExchange;
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
                        Intent intent = new Intent(context, ValuationListViewBarChartActivity.class);
                        intent.putExtra(KEY_TICKER_EXCHANGE,tickerExchange);
                        context.startActivity(intent);
                        break;

                    case 1:
                        Intent intent2 = new Intent(context, ProfitabilityListViewBarChartActivity.class);
                        intent2.putExtra(KEY_TICKER_EXCHANGE,tickerExchange);
                        context.startActivity(intent2);
                        break;

                    case 2:
                        Intent intent3 = new Intent(context, EfficiencyListViewBarChartActivity.class);
                        intent3.putExtra(KEY_TICKER_EXCHANGE,tickerExchange);
                        context.startActivity(intent3);
                        break;

                    case 3:
                        Intent intent4 = new Intent(context, CapitalStructureListViewBarChartActivity.class);
                        intent4.putExtra(KEY_TICKER_EXCHANGE,tickerExchange);
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


