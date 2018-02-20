package com.figsinc.app.analyze.trendinganalysts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
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
import com.figsinc.app.analyze.MessageEvent;
import com.figsinc.app.analyze.model.TrendingAnalysts;
import com.figsinc.app.analyze.trendingfunds.TrendingFundsFragment;
import com.figsinc.app.analyze.trendingfunds.TrendingFundsRecyclerViewAdapter;
import com.figsinc.app.utils.GlobalBus;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrendingAnalystsFragment extends Fragment {

    String url;
    private ArrayList<TrendingAnalysts> sentimentsArrayList;

    @BindView(R.id.emptyView)
    TextView emptyView;

    @BindView(R.id.listAnalysts)
    RecyclerView recyclerView;
    private TrendingAnalystsRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public TrendingAnalystsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_analyze_analysts, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            String url = bundle.getString("SavedAnalystsURL", "");
            this.url = url;
        } else {
            url = Constants.trendingAnalysts;
        }

        network();
    }
    public void doNetworkCall(String uri){
        url=Constants.SERVER_URL+uri;
        network();
    }
    private void listBind(ArrayList<TrendingAnalysts> sentimentsArrayList){
       // System.out.println(" =======>> Analysts " + sentimentsArrayList.size());
        mAdapter = new TrendingAnalystsRecyclerViewAdapter(sentimentsArrayList,getActivity());
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
       // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);;
    }

    private void network() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());


            //System.out.println(" **********  " + FigsApplication.getAuthToken());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                             //Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                            //System.out.println(" TrendingAnalysts  " + response);
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                            showJSON(response);

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
                    headers.put("Authorization",FigsApplication.getAuthToken());
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

    private void showJSON(String json){
        TrendingAnalystsParse pj = new TrendingAnalystsParse(json);
        sentimentsArrayList = pj.parseJSON();
        listBind(sentimentsArrayList);

    }

   // showJSON(intent.getStringExtra("Analyst"));
   @Override
   public void onDestroyView() {
       super.onDestroyView();
       // Unregister the registered event.
       GlobalBus.getBus().unregister(this);
   }

    @Subscribe
    public void getMessage(MessageEvent messageEvent) {
        //Write code to perform action after event is received.
        Bundle bundle = messageEvent.getMessage();
        try {
            showJSON(bundle.getString("Analyst"));
        }catch (NullPointerException e){
            e.printStackTrace();
            if(bundle.getString("All").equals("All")){
                network();
            }
        }

    }

}


