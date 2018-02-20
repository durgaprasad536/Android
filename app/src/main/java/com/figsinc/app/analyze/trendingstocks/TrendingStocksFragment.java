package com.figsinc.app.analyze.trendingstocks;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.figsinc.app.Constants;
import com.figsinc.app.FigsApplication;
import com.figsinc.app.R;
import com.figsinc.app.analyze.MessageEvent;
import com.figsinc.app.analyze.model.TrendingStocks;
import com.figsinc.app.utils.GlobalBus;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.figsinc.app.learn.sector.SectorDetailsSubActivity.KEY_SECTOR_ID;

//import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

public class TrendingStocksFragment extends Fragment{

    String  url = Constants.trendingStocks;
    private ArrayList<TrendingStocks> sentimentsArrayList;

    @BindView(R.id.listTrendingStocks)
    RecyclerView recyclerView;

    @BindView(R.id.emptyView)
    TextView emptyView;

    public TrendingStocksFragment() {
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
        GlobalBus.getBus().register(this);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analyze_trending_stocks, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Subscribe
    public void getMessage(MessageEvent messageEvent) {
        //Write code to perform action after event is received.
        Bundle bundle = messageEvent.getMessage();
        try {
            showJSON(bundle.getString("Stocks"));
        }catch (NullPointerException e){
            e.printStackTrace();
            if(bundle.getString("All").equals("All")){
                network(url);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unregister the registered event.
        GlobalBus.getBus().unregister(this);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String Id = bundle.getString(KEY_SECTOR_ID, "");
            url = Id;
        }
        network(url);
    }

    public void doNetworkCall(String uri){
        network(Constants.trendingStocksFilter+uri);
    }

    private void listBind(final ArrayList<TrendingStocks> sentimentsArrayList) {
      //  System.out.println(" =======>> Stocks " + sentimentsArrayList.size());

        TrendingStocksRecyclerViewAdapter mAdapter = new TrendingStocksRecyclerViewAdapter(sentimentsArrayList, getActivity());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void network(final String url) {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            showJSON(response);
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // Toast.makeText(getActivity(), "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", FigsApplication.getAuthToken());
                    return headers;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showJSON(String json) {
        TrendingStocksParse pj = new TrendingStocksParse(json);
        sentimentsArrayList = pj.parseJSON();
        listBind(sentimentsArrayList);
    }
   // showJSON(intent.getStringExtra("Stocks"));

}


