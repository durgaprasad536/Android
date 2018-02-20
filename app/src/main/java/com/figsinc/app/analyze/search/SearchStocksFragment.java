package com.figsinc.app.analyze.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.figsinc.app.R;
import com.figsinc.app.analyze.model.Sentiments;
import com.figsinc.app.analyze.model.TrendingStocks;
import com.figsinc.app.analyze.trendingstocks.StockInfoActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.figsinc.app.analyze.trendingstocks.StockInfoActivity.KEY_EXTRA_TRENDING_STOCKS_PARSE;

//import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

public class SearchStocksFragment extends Fragment {


    private ArrayList<TrendingStocks> sentimentsArrayList;

    @BindView(R.id.listTrendingStocks)
    RecyclerView recyclerView;
    @BindView(R.id.entities_overview)
    TextView entities_overview;
    private SearchStocksRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public SearchStocksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analyze_trending_stocks, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unregister the registered event.
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            sentimentsArrayList = bundle.getParcelableArrayList("Stocks");
            listBind(sentimentsArrayList);
        }
        entities_overview.setVisibility(View.GONE);
    }

    private void listBind(final ArrayList<TrendingStocks> sentimentsArrayList) {
        //System.out.println(" =======>> Stocks " + sentimentsArrayList.size());
        mAdapter = new SearchStocksRecyclerViewAdapter(sentimentsArrayList, getActivity());
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }



}


