package com.figsinc.app.analyze.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.figsinc.app.R;
import com.figsinc.app.analyze.model.TrendingFunds;
import com.figsinc.app.analyze.trendingfunds.FundsInfoActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.figsinc.app.analyze.trendingfunds.FundsInfoActivity.KEY_EXTRA_FUNDS_LIPPER_NO;


/**
     * {@link RecyclerView.Adapter} that can display a {@link TrendingFunds} and makes a call to the
     * <p>
     * TODO: Replace the implementation with code for your data type.
     */
    public class SearchFundsRecyclerViewAdapter extends RecyclerView.Adapter<SearchFundsRecyclerViewAdapter.ViewHolder> {

        private final List<TrendingFunds> mValues;
        private Context context;

        public SearchFundsRecyclerViewAdapter(List<TrendingFunds> items, final Context context) {
            mValues = items;
            this.context = context;

        }

        @Override
        public SearchFundsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_search_fund_stock, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SearchFundsRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);

            holder.tvIncName.setText(holder.mItem.getFundsName());

            holder.tvIncName.append("(NAV: "+holder.mItem.getCF_LAST() +")");

            holder.tvCompanyName.setText(holder.mItem.getOverview());


            holder.root_card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FundsInfoActivity.class);
                    intent.putExtra(KEY_EXTRA_FUNDS_LIPPER_NO, holder.mItem.getLippernos());
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return mValues.size();

        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public TrendingFunds mItem;

            @BindView(R.id.tvIncName)
            TextView tvIncName;

            @BindView(R.id.tv_company_name)
            TextView tvCompanyName;

            @BindView(R.id.root_card_view)
            CardView root_card_view;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                mView = view;

            }
        }
    }
