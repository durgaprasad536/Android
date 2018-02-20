package com.figsinc.app.analyze.trendingfunds;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.figsinc.app.R;
import com.figsinc.app.analyze.model.Sentiments;
import com.figsinc.app.analyze.model.TrendingFunds;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.figsinc.app.analyze.trendingfunds.FundsInfoActivity.KEY_EXTRA_FUNDS_LIPPER_NO;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Sentiments} and makes a call to the
 * <p>
 * TODO: Replace the implementation with code for your data type.
 */
public class TrendingFundsRecyclerViewAdapter extends RecyclerView.Adapter<TrendingFundsRecyclerViewAdapter.ViewHolder> {

    private final List<TrendingFunds> mValues;
    private Context context;

    public TrendingFundsRecyclerViewAdapter(List<TrendingFunds> items, final Context context) {
        mValues = items;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_analyse_trending_stocks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvIncName.setText(holder.mItem.getFundsName());
        holder.tvIncStock.setText(holder.mItem.getCF_LAST());

        holder.tvCompanyName.setText(holder.mItem.getLippernos());

        double pctchng = Double.parseDouble(holder.mItem.getPCTCHNG());

        double netchng = Double.parseDouble(holder.mItem.getCF_NETCHNG());

        String pctchngFund= String.format("%.2f", new BigDecimal(netchng));
        String netchngFund =  "(" + String.format("%.2f", new BigDecimal(pctchng)) + "%)";

        if (holder.mItem.getPCTCHNG().startsWith("-")) {
            holder.tvCompanyStock.setTextColor(ContextCompat.getColor(this.context, R.color.paleRed));
            holder.tvCompanyStock.setText(pctchngFund+ netchngFund);
        } else {
            holder.tvCompanyStock.setTextColor(ContextCompat.getColor(this.context, R.color.greenBlueTwo));
            holder.tvCompanyStock.setText("+ " + pctchngFund + netchngFund);
        }

        holder.root_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FundsInfoActivity.class);
                intent.putExtra(KEY_EXTRA_FUNDS_LIPPER_NO,holder.mItem.getLippernos());
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

        @BindView(R.id.tvIncName)
        AppCompatTextView tvIncName;
        @BindView(R.id.tv_inc_stock)
        AppCompatTextView tvIncStock;
        @BindView(R.id.tv_company_name)
        AppCompatTextView tvCompanyName;
        @BindView(R.id.tv_company_stock)
        AppCompatTextView tvCompanyStock;
        @BindView(R.id.root_card_view)
        LinearLayout root_card_view;

        public TrendingFunds mItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;

        }
    }
}
