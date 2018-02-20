package com.figsinc.app.analyze.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.figsinc.app.R;
import com.figsinc.app.analyze.model.TrendingFunds;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchFundsFragment extends Fragment {

    ArrayList<TrendingFunds> listFunds = new ArrayList<>();
    @BindView(R.id.listTrendingStocks)
    RecyclerView recyclerView;
    @BindView(R.id.entities_overview)
    TextView entities_overview;
    private SearchFundsRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public SearchFundsFragment() {
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listFunds = bundle.getParcelableArrayList("Funds");
            // System.out.println(" **********&&&&&&&&&&&& %%%%%%%%%%" + listFunds.size());
            listBind(listFunds);
        }

        entities_overview.setVisibility(View.GONE);
    }

    private void listBind(ArrayList<TrendingFunds> sentimentsArrayList) {
        // System.out.println(" =======>> funds " + sentimentsArrayList.size());
        mAdapter = new SearchFundsRecyclerViewAdapter(sentimentsArrayList, getActivity());
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
