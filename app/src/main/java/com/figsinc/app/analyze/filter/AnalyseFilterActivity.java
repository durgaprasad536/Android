package com.figsinc.app.analyze.filter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
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
import com.figsinc.app.analyze.model.SectorFilterModel;
import com.figsinc.app.learn.Model.RegionFilterModel;
import com.figsinc.app.learn.filter.FilterActivity;
import com.figsinc.app.learn.filter.SingleStaticRecyclerViewAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dinash on 17-12-2017.
 */

public class AnalyseFilterActivity extends AppCompatActivity {//implements  SingleStaticRecyclerViewAdapter.SingleStaticClickListener{

    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerViewFilterListOne;

    @BindView(R.id.my_recycler_view1)
    RecyclerView recyclerViewFilterListTwo;

    @BindView(R.id.my_recycler_view2)
    RecyclerView recyclerViewFilterListThree;

    @BindView(R.id.filter1)
    CardView filter1;

    @BindView(R.id.filter2)
    CardView filter2;

    @BindView(R.id.filter3)
    CardView filter3;

    @BindView(R.id.title1)
    TextView title1;

    @BindView(R.id.title2)
    TextView title2;

    @BindView(R.id.title3)
    TextView title3;
    private SingleStaticRecyclerViewAdapter mAdapter1;
    private SingleStaticRecyclerViewAdapter mAdapter2;
    private SingleStaticRecyclerViewAdapter mAdapter3;

    private ArrayList<String> regionList = new ArrayList<>();
    public static final String CURRENT_INDEX = "CURRENT_INDEX";

    int currentIndex = 0;

    String firstFilter = "", secondFilter = "", thirdFilter = "";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                returnResult();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnResult() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("first", firstFilter);
        returnIntent.putExtra("second", secondFilter);
        returnIntent.putExtra("third", thirdFilter);
        if (currentIndex == 3) {
            if (myTypes != null) {
                for (SectorFilterModel sector : myTypes) {
                    if (sector.getName().equalsIgnoreCase(secondFilter)) {
                        returnIntent.putExtra("sectorid", sector.getSub_industry_code());
                        break;
                    }
                }
            } else {
                returnIntent.putExtra("sectorid", "All");

            }
        }
        setResult(Activity.RESULT_OK, returnIntent);
    }

    @Override
    public void onBackPressed() {
        returnResult();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse_filter);
        ButterKnife.bind(this);

        regionList.add("All");
        regionList.add("Hong Kong");
        regionList.add("Indonesia");
        regionList.add("Japan");
        regionList.add("Singapore");
        regionList.add("Taiwan");
        regionList.add("Thailand");
        regionList.add("United States");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            currentIndex = bundle.getInt(CURRENT_INDEX);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (currentIndex == 0 || currentIndex == 1) {
            filter1.setVisibility(View.VISIBLE);
            filter2.setVisibility(View.VISIBLE);
            filter3.setVisibility(View.GONE);
            setmAdapter1();
            setmAdapter2();
            //setmAdapter3();
        } else if (currentIndex == 2) {
            filter1.setVisibility(View.VISIBLE);
            filter2.setVisibility(View.GONE);
            filter3.setVisibility(View.GONE);
            setmAdapter1();
            setmAdapter2();
            // setmAdapter3();
        } else {
            networkRegion();
            filter1.setVisibility(View.VISIBLE);
            filter2.setVisibility(View.VISIBLE);
            filter3.setVisibility(View.VISIBLE);
            setmAdapter1();
            setmAdapter3();
        }
    }

    private ArrayList<String> getFirstAdapterData() {
        final ArrayList<String> filterListTwo = new ArrayList<>();
        switch (currentIndex) {
            case 0: {
                title1.setText("Category");
                filterListTwo.add("All");
                filterListTwo.add("Stocks");
                filterListTwo.add("Funds");
                filterListTwo.add("Analysts");
                break;
            }
            case 1: {
                title1.setText("Region");
                filterListTwo.addAll(regionList);
                break;
            }
            case 2: {
                title1.setText("Region");
                filterListTwo.addAll(regionList);
                break;
            }
            case 3: {
                title1.setText("Region");
                filterListTwo.addAll(regionList);
                break;
            }
        }

        return filterListTwo;
    }

    private ArrayList<String> getSecondAdapterData() {
        final ArrayList<String> filterListTwo = new ArrayList<>();
        switch (currentIndex) {
            case 0: {
                title2.setText("Region");
                filterListTwo.addAll(regionList);
                break;
            }
            case 1: {
                title2.setText("Sort By");
                filterListTwo.add("Potential Returns");
                filterListTwo.add("Market Cap");
                break;
            }
            case 3: {
                break;
            }
        }
        return filterListTwo;
    }

    private ArrayList<String> getThirdAdapterData() {
        //title1.setText("Region");

        title3.setText("Sort By");
        final ArrayList<String> filterListTwo = new ArrayList<>();
        filterListTwo.add("Top Accuracy Score");
        filterListTwo.add("Top Avg. Return");
        filterListTwo.add("Top Avg. Time");

        return filterListTwo;
    }

    SectorFilterModel[] myTypes;// = gson.fromJson(response, SectorFilterModel[].class);

    private void setSectorFilter(String response) {
        Gson gson = new Gson();
        // Gson gson = new Gson();
        myTypes = gson.fromJson(response, SectorFilterModel[].class);
        //List<RegionFilterModel> myTypes = gson.fromJson(response, ArrayList<RegionFilterModel>.class);

        final ArrayList<String> filterListTwo = new ArrayList<>();
        filterListTwo.add("All");
        for (SectorFilterModel rgm : myTypes) {
            filterListTwo.add(rgm.getName());
        }

        title2.setText("Sector");
        RecyclerView.LayoutManager mLayoutManager;

        mAdapter2 = new SingleStaticRecyclerViewAdapter(filterListTwo);
        recyclerViewFilterListTwo.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewFilterListTwo.setLayoutManager(mLayoutManager);
        recyclerViewFilterListTwo.setNestedScrollingEnabled(false);

        recyclerViewFilterListTwo.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewFilterListTwo.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFilterListTwo.setAdapter(mAdapter2);
        mAdapter2.setOnItemClickListener(sectorAdapterItemClick());
        secondFilter = mAdapter2.setFirstItem();
    }

    @NonNull
    protected SingleStaticRecyclerViewAdapter.SingleStaticClickListener sectorAdapterItemClick() {
        return new SingleStaticRecyclerViewAdapter.SingleStaticClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                secondFilter = mAdapter2.selectedItem(position);
            }
        };
    }

    private void networkRegion() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(AnalyseFilterActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.SectorFilter,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(AnalyseFilterActivity.this, response, Toast.LENGTH_SHORT).show();
                           // System.out.println(" **********  " + response);
                            // showJSON(response);
                            //setRegionFilter(response);
                            setSectorFilter(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // Toast.makeText(AnalyseFilterActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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

    private void setmAdapter1() {


        RecyclerView.LayoutManager mLayoutManager;

        mAdapter1 = new SingleStaticRecyclerViewAdapter(getFirstAdapterData());
        recyclerViewFilterListOne.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewFilterListOne.setLayoutManager(mLayoutManager);
        recyclerViewFilterListOne.setNestedScrollingEnabled(false);

        recyclerViewFilterListOne.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewFilterListOne.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFilterListOne.setAdapter(mAdapter1);
        mAdapter1.setOnItemClickListener(firstAdapterItemClick());
        firstFilter = mAdapter1.setFirstItem();
    }

    @NonNull
    protected SingleStaticRecyclerViewAdapter.SingleStaticClickListener firstAdapterItemClick() {
        return new SingleStaticRecyclerViewAdapter.SingleStaticClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                firstFilter = mAdapter1.selectedItem(position);

                if(position !=0 && title2.getText().toString().trim().equalsIgnoreCase("Sector")){
                    filter2.setVisibility(View.GONE);
                } else {
                    filter2.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    private void setmAdapter2() {

        RecyclerView.LayoutManager mLayoutManager;

        mAdapter2 = new SingleStaticRecyclerViewAdapter(getSecondAdapterData());
        recyclerViewFilterListTwo.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewFilterListTwo.setLayoutManager(mLayoutManager);
        recyclerViewFilterListTwo.setNestedScrollingEnabled(false);

        recyclerViewFilterListTwo.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewFilterListTwo.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFilterListTwo.setAdapter(mAdapter2);
        mAdapter2.setOnItemClickListener(secondAdapterItemClick());
        secondFilter = mAdapter2.setFirstItem();
    }

    @NonNull
    protected SingleStaticRecyclerViewAdapter.SingleStaticClickListener secondAdapterItemClick() {
        return new SingleStaticRecyclerViewAdapter.SingleStaticClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                secondFilter = mAdapter2.selectedItem(position);
            }
        };
    }

    private void setmAdapter3() {

        RecyclerView.LayoutManager mLayoutManager;

        mAdapter3 = new SingleStaticRecyclerViewAdapter(getThirdAdapterData());
        recyclerViewFilterListThree.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewFilterListThree.setLayoutManager(mLayoutManager);
        recyclerViewFilterListThree.setNestedScrollingEnabled(false);

        recyclerViewFilterListThree.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewFilterListThree.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFilterListThree.setAdapter(mAdapter3);
        mAdapter3.setOnItemClickListener(thirdAdapterItemClick());
        thirdFilter = mAdapter3.setFirstItem();
    }

    @NonNull
    protected SingleStaticRecyclerViewAdapter.SingleStaticClickListener thirdAdapterItemClick() {
        return new SingleStaticRecyclerViewAdapter.SingleStaticClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                thirdFilter = mAdapter3.selectedItem(position);
            }
        };
    }

}
