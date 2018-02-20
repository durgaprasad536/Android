package com.figsinc.app.analyze.trendinganalysts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
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
import com.figsinc.app.analyze.model.Sentiments;
import com.figsinc.app.analyze.model.TrendingAnalysts;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Sentiments} and makes a call to the
 * <p>
 * TODO: Replace the implementation with code for your data type.
 */
public class TrendingAnalystsRecyclerViewAdapter extends RecyclerView.Adapter<TrendingAnalystsRecyclerViewAdapter.ViewHolder> {

    private final List<TrendingAnalysts> mValues;
    private final Context context;


    public TrendingAnalystsRecyclerViewAdapter(List<TrendingAnalysts> items, final Context context) {
        mValues = items;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_trending_analysts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        //System.out.println(" ======>>> picture URL " +Constants.trendingAnalystsProfileImage +holder.mItem.getAnalystid()+".png");
        Glide.with(context)
                .load(Constants.trendingAnalystsProfileImage +holder.mItem.getAnalystid()+".png")
                .apply(RequestOptions.circleCropTransform())
                .into(holder.profileImage);

        holder.profileName.setText(holder.mItem.getAnalystname());
        holder.tvFigsAnalyticsScore.setText(String.format("%.2f",Double.parseDouble(holder.mItem.getFigs_analyst_score()))+"%");

        holder.tvAverageReturns.setText(String.format("%.2f",Double.parseDouble(holder.mItem.getAverage_return()))+"%");

        holder.tvAverageTimeForReturns.setText(String.format("%.2f",Double.parseDouble(holder.mItem.getAverage_time()) /30 )+" mnth");
        holder.profile_Country.setText(holder.mItem.getAnayst_compay_name());
        //+ "\n"
        if(!holder.mItem.getTopsector__name().equals("null")){
            holder.profile_Country.append("\n");
            holder.profile_Country.append(holder.mItem.getTopsector__name());

        }
        if(!holder.mItem.getSecondsector__name().equals("null")){
            holder.profile_Country.append("/");
           // holder.profile_Country.append("\n");
            holder.profile_Country.append(holder.mItem.getSecondsector__name());

        }


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
        @BindView(R.id.tvFigsAnalyticsScore)
        AppCompatTextView tvFigsAnalyticsScore;
        @BindView(R.id.tvAverageReturns)
        AppCompatTextView tvAverageReturns;
        @BindView(R.id.tvAverageTimeForReturns)
        AppCompatTextView tvAverageTimeForReturns;
        @BindView(R.id.card_viewRoot)
        CardView card_viewRoot;
        @BindView(R.id.profile_Country)
        TextView profile_Country;

        public TrendingAnalysts mItem;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;

        }
    }
}
