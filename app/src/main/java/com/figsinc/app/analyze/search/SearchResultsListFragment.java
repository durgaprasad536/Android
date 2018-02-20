package com.figsinc.app.analyze.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.figsinc.app.R;
import com.figsinc.app.analyze.model.TrendingAnalysts;
import com.figsinc.app.analyze.model.TrendingFunds;
import com.figsinc.app.analyze.model.TrendingStocks;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsListFragment extends Fragment {

    @BindView(R.id.recylerView_Stocks)
    RecyclerView recylerView_Stocks;

    @BindView(R.id.recylerView_Funds)
    RecyclerView recylerView_Funds;

    @BindView(R.id.recylerView_Analysts)
    RecyclerView recylerView_Analysts;

    private ArrayList<TrendingAnalysts> listAnalysts = new ArrayList<>();
    private ArrayList<TrendingFunds> listFunds = new ArrayList<>();
    private ArrayList<TrendingStocks> listStocks = new ArrayList<>();

    public SearchResultsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_item_search_all, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listFunds = bundle.getParcelableArrayList("Funds");
            listBindFunds(listFunds);
            listAnalysts = bundle.getParcelableArrayList("Analyst");
            listBindAnalytics(listAnalysts);
            listStocks = bundle.getParcelableArrayList("Stocks");
            listStocksBind(listStocks);

        }

    }

    private void listStocksBind(final ArrayList<TrendingStocks> sentimentsArrayList) {
       // System.out.println(" =======>> Stocks " + sentimentsArrayList.size());
        SearchStocksRecyclerViewAdapter mAdapter = new SearchStocksRecyclerViewAdapter(sentimentsArrayList, getActivity());
        recylerView_Stocks.setHasFixedSize(true);
        RecyclerView.LayoutManager  mLayoutManager = new LinearLayoutManager(getActivity());
        recylerView_Stocks.setLayoutManager(mLayoutManager);
        recylerView_Stocks.setItemAnimator(new DefaultItemAnimator());
        recylerView_Stocks.setAdapter(mAdapter);
    }

    private void listBindAnalytics(ArrayList<TrendingAnalysts> sentimentsArrayList) {
       // System.out.println(" =======>> Analysts " + sentimentsArrayList.size());
        SearchAnalystsRecyclerViewAdapter mAdapter = new SearchAnalystsRecyclerViewAdapter(sentimentsArrayList, getActivity());
        recylerView_Analysts.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recylerView_Analysts.setLayoutManager(mLayoutManager);
        // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recylerView_Analysts.setItemAnimator(new DefaultItemAnimator());
        recylerView_Analysts.setAdapter(mAdapter);
    }

    private void listBindFunds(ArrayList<TrendingFunds> sentimentsArrayList) {
       // System.out.println(" =======>> funds " + sentimentsArrayList.size());
        SearchFundsRecyclerViewAdapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;
        mAdapter = new SearchFundsRecyclerViewAdapter(sentimentsArrayList, getActivity());
        recylerView_Funds.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recylerView_Funds.setLayoutManager(mLayoutManager);
        recylerView_Funds.setItemAnimator(new DefaultItemAnimator());
        recylerView_Funds.setAdapter(mAdapter);
    }

}




