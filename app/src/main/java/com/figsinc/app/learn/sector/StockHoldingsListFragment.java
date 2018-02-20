package com.figsinc.app.learn.sector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.figsinc.app.collect.SavedStocksRecyclerViewAdapter;
import com.figsinc.app.collect.WatchListStocksParse;
import com.figsinc.app.utils.ListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.figsinc.app.learn.sector.SectorDetailsSubActivity.KEY_SECTOR_ID;


public class StockHoldingsListFragment extends Fragment {

    private RecyclerView recyclerViewStocks;
   private String sectorId;
    public StockHoldingsListFragment() {
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
        return inflater.inflate(R.layout.fragment_collect_watchlist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewStocks = (RecyclerView) view.findViewById(R.id.recylerView_Stocks);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            sectorId = bundle.getString(KEY_SECTOR_ID, "");

        }
        network();
    }

    private void listStocksBind(ArrayList<ListItem> sentimentsArrayList){
        SavedStocksRecyclerViewAdapter mAdapter = new SavedStocksRecyclerViewAdapter(sentimentsArrayList,getActivity());
        recyclerViewStocks.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewStocks.setLayoutManager(mLayoutManager);
       // recyclerViewStocks.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerViewStocks.setItemAnimator(new DefaultItemAnimator());
        recyclerViewStocks.setAdapter(mAdapter);
    }

    private void network() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String url = Constants.sectorStockHoldings+sectorId;

           // System.out.println(" **********  " + FigsApplication.getAuthToken());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                           // System.out.println(" **********  " + response);
                            showStocksJSON(response);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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

    private void showStocksJSON(String json){
        WatchListStocksParse pj = new WatchListStocksParse(json);
        listStocksBind(pj.parseJSON());
    }

}


