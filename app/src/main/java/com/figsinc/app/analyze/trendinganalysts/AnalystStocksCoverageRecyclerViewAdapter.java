package com.figsinc.app.analyze.trendinganalysts;

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
import android.widget.TextView;

import com.figsinc.app.R;
import com.figsinc.app.analyze.model.Sentiments;
import com.figsinc.app.analyze.model.StockCoverage;
import com.figsinc.app.analyze.trendingstocks.StockInfoActivity;
import com.figsinc.app.utils.Header;
import com.figsinc.app.utils.ListItem;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.figsinc.app.analyze.trendingstocks.StockInfoActivity.KEY_EXTRA_TRENDING_STOCKS_PARSE;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Sentiments} and makes a call to the
 * <p>
 * TODO: Replace the implementation with code for your data type.
 */
public class AnalystStocksCoverageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //Header header;
    private List<ListItem> mValues;

    public AnalystStocksCoverageRecyclerViewAdapter(List<ListItem> items, final Context context) {
        this.mValues = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_header, parent, false);
            return new VHHeader(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_analyse_trending_stocks, parent, false);
            return new ViewHolderItem(v);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof VHHeader) {
            Header currentItem = (Header) mValues.get(position);
            VHHeader VHheader = (VHHeader) holder;
            VHheader.txtHeader.setText(currentItem.getRegionHeader());

        } else if (holder instanceof ViewHolderItem) {
            final StockCoverage currentItem = (StockCoverage) mValues.get(position);
            final ViewHolderItem VHitem = (ViewHolderItem) holder;

            VHitem.tvIncName.setText(currentItem.getCompanyName());
            VHitem.tvIncStock.setText(currentItem.getCF_LAST());

            VHitem.tvCompanyName.setText(currentItem.getTicker());

            double pctchng = Double.parseDouble(currentItem.getPCTCHNG());

            double netchng = Double.parseDouble(currentItem.getCF_NETCHNG());

            VHitem.tvCompanyStock.setText(String.format("%.2f", new BigDecimal(netchng)) + "(" + String.format("%.2f", new BigDecimal(pctchng)) + "%)");

            if (currentItem.getPCTCHNG().startsWith("-")) {
                VHitem.tvCompanyStock.setTextColor(ContextCompat.getColor(this.context, R.color.paleRed));
                VHitem.tvCompanyStock.setText(String.format("%.2f", new BigDecimal(netchng)) + "(" + String.format("%.2f", new BigDecimal(pctchng)) + "%)");

            } else {
                VHitem.tvCompanyStock.setText("+ " + String.format("%.2f", new BigDecimal(netchng)) + "(" + String.format("%.2f", new BigDecimal(pctchng)) + "%)");
                VHitem.tvCompanyStock.setTextColor(ContextCompat.getColor(this.context, R.color.greenBlueTwo));
            }


            VHitem.root_card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, StockInfoActivity.class);
                    intent.putExtra("id", currentItem.getSectorid_stock());
                    intent.putExtra("CF_TICK", currentItem.getCF_TICK());
                   // System.out.println(" **********  " + currentItem.getCF_TICK());
                   // intent.putExtra(KEY_EXTRA_TRENDING_STOCKS_PARSE,currentItem);

                   // context.startActivity(intent);
                }
            });
        }


    }

    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position)
    {

        return mValues.get(position) instanceof Header;

    }

    @Override
    public int getItemCount() {
        return mValues.size();

    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {
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

        public ViewHolderItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;

        }
    }

    class VHHeader extends RecyclerView.ViewHolder {

        public final View mView;

        @BindView(R.id.txtHeader)
        TextView txtHeader;

        public VHHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mView = itemView;

        }
    }
}
