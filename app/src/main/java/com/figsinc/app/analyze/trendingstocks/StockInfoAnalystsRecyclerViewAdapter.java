package com.figsinc.app.analyze.trendingstocks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.figsinc.app.analyze.model.TrendingAnalysts;
import com.figsinc.app.analyze.trendinganalysts.AnalystInfoActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
     * {@link RecyclerView.Adapter} that can display a {@link TrendingAnalysts} and makes a call to the
     * <p>
     * TODO: Replace the implementation with code for your data type.
     */
    public class StockInfoAnalystsRecyclerViewAdapter extends RecyclerView.Adapter<StockInfoAnalystsRecyclerViewAdapter.ViewHolder> {

        private final List<TrendingAnalysts> mValues;
        private final Context context;


        public StockInfoAnalystsRecyclerViewAdapter(List<TrendingAnalysts> items, final Context context) {
            mValues = items;
            this.context = context;

        }

        @Override
        public  StockInfoAnalystsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_stock_recommendedby, parent, false);
            return new  StockInfoAnalystsRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final StockInfoAnalystsRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);

            //System.out.println(" ======>>> picture URL " + Constants.trendingAnalystsProfileImage +holder.mItem.getAnalystid()+".png");


            holder.tv_analyst_name.setText(holder.mItem.getAnalystname());

            holder.tv_analyst_companyname.setText("Company Name:"+holder.mItem.getAnayst_compay_name());

            holder.linear_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,AnalystInfoActivity.class);
                    intent.putExtra("AnalystID",holder.mItem.getAnalystid());
                    intent.putExtra("imageUrl",Constants.trendingAnalystsProfileImage +holder.mItem.getAnalystid()+".png");
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

            @BindView(R.id.tv_analyst_name)
            TextView tv_analyst_name;

            @BindView(R.id.tv_analyst_companyname)
            TextView tv_analyst_companyname;

            @BindView(R.id.linear_item)
            LinearLayout linear_item;

            public TrendingAnalysts mItem;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                mView = view;

            }
        }
    }
