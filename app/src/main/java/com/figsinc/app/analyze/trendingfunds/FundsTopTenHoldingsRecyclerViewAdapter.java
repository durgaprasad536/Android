package com.figsinc.app.analyze.trendingfunds;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.figsinc.app.R;
import com.figsinc.app.analyze.model.FundsTopTenHolding;
import com.figsinc.app.analyze.model.Sentiments;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Sentiments} and makes a call to the
 * <p>
 * TODO: Replace the implementation with code for your data type.
 */
public class FundsTopTenHoldingsRecyclerViewAdapter extends RecyclerView.Adapter<FundsTopTenHoldingsRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<FundsTopTenHolding> mValues;
    private Context context;

    public FundsTopTenHoldingsRecyclerViewAdapter(ArrayList<FundsTopTenHolding> items, final Context context) {
        mValues = items;
        this.context = context;

        System.out.println(" mValues " + mValues.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_top_ten_stock_holdings, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.text_NA.setText(String.format("%.2f", new BigDecimal(holder.mItem.getPercentage())));
        holder.text_CP.setText(String.format("%.2f", new BigDecimal(holder.mItem.getPrice_close())));
        holder.tvCompanyName.setText(holder.mItem.getName());

        double potentialReturns = Double.parseDouble(holder.mItem.getPotentialreturns());


        if (holder.mItem.getPotentialreturns().startsWith("-")) {
            holder.text_PR.setTextColor(ContextCompat.getColor(this.context, R.color.paleRed));
            holder.text_PR.setText(String.format("%.2f", new BigDecimal(potentialReturns)));

        } else {
            holder.text_PR.setText("+" + String.format("%.2f", new BigDecimal(potentialReturns)));
            holder.text_PR.setTextColor(ContextCompat.getColor(this.context, R.color.greenBlueTwo));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        @BindView(R.id.tvCompanyName)
        AppCompatTextView tvCompanyName;
        @BindView(R.id.text_NA)
        AppCompatTextView text_NA;
        @BindView(R.id.text_CP)
        AppCompatTextView text_CP;
        @BindView(R.id.text_PR)
        AppCompatTextView text_PR;

        public FundsTopTenHolding mItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;

        }
    }
}
