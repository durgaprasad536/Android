package com.figsinc.app.analyze.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public class SearchAnalystsRecyclerViewAdapter extends RecyclerView.Adapter<SearchAnalystsRecyclerViewAdapter.ViewHolder> {

        private final List<TrendingAnalysts> mValues;
        private final Context context;


        public SearchAnalystsRecyclerViewAdapter(List<TrendingAnalysts> items, final Context context) {
            mValues = items;
            this.context = context;

        }

        @Override
        public  SearchAnalystsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_search_analysts, parent, false);
            return new  SearchAnalystsRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SearchAnalystsRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);

            //System.out.println(" ======>>> picture URL " + Constants.trendingAnalystsProfileImage +holder.mItem.getAnalystid()+".png");
            Glide.with(context)
                    .load(Constants.trendingAnalystsProfileImage +holder.mItem.getAnalystid()+".png")
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.profileImage);

            holder.profileName.setText(holder.mItem.getAnalystname());
            String analystScore = String.format("%.2f",Double.parseDouble(holder.mItem.getFigs_analyst_score()));
            holder.profileName.append("(Analyst Score: " +analystScore +")");

            holder.profile_Company_name.setText("Company Name:"+holder.mItem.getAnayst_compay_name());

            holder.card_viewRoot.setOnClickListener(new View.OnClickListener() {
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

            @BindView(R.id.profile_image)
            ImageView profileImage;

            @BindView(R.id.profile_name)
            TextView profileName;

            @BindView(R.id.profile_Company_name)
            TextView profile_Company_name;

            @BindView(R.id.card_viewRoot)
            CardView card_viewRoot;

            public TrendingAnalysts mItem;


            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                mView = view;

            }
        }
    }
