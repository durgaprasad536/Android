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

import com.figsinc.app.R;
import com.figsinc.app.analyze.model.TrendingAnalysts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchAnalystsFragment extends Fragment {

    String url;
    private ArrayList<TrendingAnalysts> listAnalyst;

    @BindView(R.id.listAnalysts)
    RecyclerView recyclerView;
    private SearchAnalystsRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public SearchAnalystsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_analyze_analysts, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listAnalyst = bundle.getParcelableArrayList("Analyst");
           // System.out.println(" **********&&&&&&&&&&&& %%%%%%%%%%" + listAnalyst.size());
            listBind(listAnalyst);
        }
    }

    private void listBind(ArrayList<TrendingAnalysts> sentimentsArrayList){
       // System.out.println(" =======>> Analysts " + sentimentsArrayList.size());
        mAdapter = new SearchAnalystsRecyclerViewAdapter(sentimentsArrayList,getActivity());
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
       // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

   // showJSON(intent.getStringExtra("Analyst"));
   @Override
   public void onDestroyView() {
       super.onDestroyView();
       // Unregister the registered event.

   }




}


