package com.figsinc.app.analyze.trendingstocks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.figsinc.app.analyze.model.TrendingStocks;
import com.figsinc.app.analyze.search.SearchActivity;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.figsinc.app.analyze.trendingstocks.StockInfoActivity.KEY_EXTRA_CF_RIC;
import static com.figsinc.app.analyze.trendingstocks.StockInfoActivity.KEY_EXTRA_SUB_INDUSTRY_CODE;
import static com.figsinc.app.analyze.trendingstocks.StockInfoActivity.KEY_EXTRA_TICKER_EXCHANGE;
import static com.figsinc.app.analyze.trendingstocks.StockInfoActivity.KEY_EXTRA_TRENDING_STOCKS_PARSE;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Sentiments} and makes a call to the
 * <p>
 * TODO: Replace the implementation with code for your data type.
 */
public class TrendingStocksRecyclerViewAdapter extends RecyclerView.Adapter<TrendingStocksRecyclerViewAdapter.ViewHolder> {

    private final List<TrendingStocks> mValues;
   private Context context;

    public TrendingStocksRecyclerViewAdapter(List<TrendingStocks> items,final Context context) {
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

        holder.tvIncName.setText(holder.mItem.getCompanyName());
        holder.tvIncStock.setText(holder.mItem.getCF_LAST());

        holder.tvCompanyName.setText(holder.mItem.getExchange() + ": " + holder.mItem.getTicker());

        double pctchng = Double.parseDouble(holder.mItem.getPCTCHNG());

        double netchng = Double.parseDouble(holder.mItem.getCF_NETCHNG());

        //System.out.println( pctchng +  " **********  " + netchng);

        try{
        String pctchngStock = String.format("%.2f", new BigDecimal(netchng));
        String netchngStock = "("+String.format("%.2f", new BigDecimal(pctchng))+"%)";

        if(holder.mItem.getPCTCHNG().startsWith("-")){
            holder.tvCompanyStock.setTextColor(ContextCompat.getColor(this.context,R.color.paleRed));
            holder.tvCompanyStock.setText(pctchngStock + netchngStock);
        } else{
            holder.tvCompanyStock.setTextColor(ContextCompat.getColor(this.context,R.color.greenBlueTwo));
             holder.tvCompanyStock.setText("+ "+ pctchngStock + netchngStock);
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.root_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putString(KEY_EXTRA_TICKER_EXCHANGE, holder.mItem.getTicker_Exchange());
                b.putString(KEY_EXTRA_SUB_INDUSTRY_CODE, holder.mItem.getSectorid_stock());
                b.putString(KEY_EXTRA_CF_RIC, holder.mItem.getCF_RIC());
                context.startActivity(new Intent( context, StockInfoActivity.class).putExtras(b));
            }
        });

    }

    @Override
    public int getItemCount() {
        return  mValues.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        @BindView(R.id.tvIncName) AppCompatTextView tvIncName;
        @BindView(R.id.tv_inc_stock) AppCompatTextView tvIncStock;
        @BindView(R.id.tv_company_name) AppCompatTextView tvCompanyName;
        @BindView(R.id.tv_company_stock) AppCompatTextView tvCompanyStock;
        @BindView(R.id.root_card_view)
        LinearLayout root_card_view;


       public TrendingStocks mItem;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;

        }
    }
}
