package com.figsinc.app.analyze.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.figsinc.app.R;
import com.figsinc.app.analyze.model.Stock;
import com.figsinc.app.analyze.model.TrendingStocks;
import com.figsinc.app.analyze.trendingstocks.StockInfoActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.figsinc.app.analyze.trendingstocks.StockInfoActivity.KEY_EXTRA_CF_RIC;
import static com.figsinc.app.analyze.trendingstocks.StockInfoActivity.KEY_EXTRA_SUB_INDUSTRY_CODE;
import static com.figsinc.app.analyze.trendingstocks.StockInfoActivity.KEY_EXTRA_TICKER_EXCHANGE;
import static com.figsinc.app.analyze.trendingstocks.StockInfoActivity.KEY_EXTRA_TRENDING_STOCKS_PARSE;

/**
     * {@link RecyclerView.Adapter} that can display a {@link TrendingStocks} and makes a call to the
     * <p>
     * TODO: Replace the implementation with code for your data type.
     */
    public class SearchStocksRecyclerViewAdapter extends RecyclerView.Adapter<SearchStocksRecyclerViewAdapter.ViewHolder> {

        private final List<TrendingStocks> mValues;
        private Context context;

        public SearchStocksRecyclerViewAdapter(List<TrendingStocks> items, final Context context) {
            mValues = items;
            this.context = context;

        }

        @Override
        public SearchStocksRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_search_fund_stock, parent, false);
            return new SearchStocksRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SearchStocksRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);

            holder.tvIncName.setText(holder.mItem.getCompanyName());

            holder.tvIncName.append("(Last Price:" + holder.mItem.getCF_LAST() + ")");
            holder.tvCompanyName.setText(holder.mItem.getOverview());

            holder.root_card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putString(KEY_EXTRA_TICKER_EXCHANGE, holder.mItem.getTicker_Exchange());
                    b.putString(KEY_EXTRA_SUB_INDUSTRY_CODE, holder.mItem.getSub_industry_code());
                    b.putString(KEY_EXTRA_CF_RIC, holder.mItem.getCF_RIC());
                    context.startActivity(new Intent(context, StockInfoActivity.class).putExtras(b));
                }
            });

        }

        @Override
        public int getItemCount() {
            return mValues.size();

        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            @BindView(R.id.tvIncName)
            TextView tvIncName;

            @BindView(R.id.tv_company_name)
            TextView tvCompanyName;

            @BindView(R.id.root_card_view)
            CardView root_card_view;


            public TrendingStocks mItem;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                mView = view;

            }
        }
    }

