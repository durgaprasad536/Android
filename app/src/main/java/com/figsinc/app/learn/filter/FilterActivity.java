package com.figsinc.app.learn.filter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
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
import com.figsinc.app.learn.Model.Filter;
import com.figsinc.app.learn.Model.RegionFilterModel;
import com.google.gson.Gson;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterActivity extends AppCompatActivity {//implements CountryRecyclerViewAdapter.SingleClickListener, SingleStaticRecyclerViewAdapter.SingleStaticClickListener {

    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerViewFilterListOne;
    @BindView(R.id.my_recycler_view1)
    RecyclerView recyclerViewFilterListTwo;

    @BindView(R.id.my_recycler_view2)
    RecyclerView recyclerViewFilterListThree;

    @BindView(R.id.regionFilterCV)
    CardView regionFilterCV;

    private SingleRecyclerViewAdapter mAdapter1;
    private SingleStaticRecyclerViewAdapter mAdapter2;
    private SingleStaticRecyclerViewAdapter mAdapter3;
    public static final String KEY_FILTER_CATAGORY_URL = "KEY_FILTER_CATAGORY_URL";
    public static  final String IS_FROM_THEMES="IS_FROM_THEMES";
    String url;

    boolean isFromThemes;

    String selectedCategory="", selectedRegion="";
    int selectedPotReturnsIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = bundle.getString(KEY_FILTER_CATAGORY_URL);
            isFromThemes = bundle.getBoolean(IS_FROM_THEMES);
        }

        network();
        if(isFromThemes){
            regionFilterCV.setVisibility(View.VISIBLE);
            networkRegion();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setFirstFilter(final ArrayList<Filter> filterListOne) {

        RecyclerView.LayoutManager mLayoutManager;
        if(filterListOne!=null && filterListOne.size()>1) {
            Collections.sort(filterListOne, new Comparator<Filter>() {
                public int compare(Filter v1, Filter v2) {
                    return v1.getName().compareTo(v2.getName());
                }
            });
        }

        mAdapter1 = new SingleRecyclerViewAdapter(filterListOne);
        recyclerViewFilterListOne.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewFilterListOne.setLayoutManager(mLayoutManager);
        recyclerViewFilterListOne.setNestedScrollingEnabled(false);

        recyclerViewFilterListOne.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewFilterListOne.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFilterListOne.setAdapter(mAdapter1);
        mAdapter1.setOnItemClickListener(new SingleRecyclerViewAdapter.SingleClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                name =   mAdapter1.selectedItem();
                selectedCategory=name;
            }
        });
        mAdapter1.setFirstItem();
    }

    private void setSecondFilter() {
        final ArrayList<String> filterListTwo = new ArrayList<>();
        filterListTwo.add("All");
        filterListTwo.add("<0%");
        filterListTwo.add("0% - 10%");
        filterListTwo.add("10% - 20%");
        filterListTwo.add(">20%");


        RecyclerView.LayoutManager mLayoutManager;

        mAdapter2 = new SingleStaticRecyclerViewAdapter(filterListTwo);
        recyclerViewFilterListTwo.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewFilterListTwo.setLayoutManager(mLayoutManager);
        recyclerViewFilterListTwo.setNestedScrollingEnabled(false);

        recyclerViewFilterListTwo.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewFilterListTwo.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFilterListTwo.setAdapter(mAdapter2);
        mAdapter2.setOnItemClickListener(new SingleStaticRecyclerViewAdapter.SingleStaticClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                mAdapter2.selectedItem(position);
                selectedPotReturnsIndex=position;
            }
        });
        mAdapter2.setFirstItem();
    }

    private void setRegionFilter(String response) {
        Gson gson = new Gson();
        // Gson gson = new Gson();
        RegionFilterModel[] myTypes = gson.fromJson(response, RegionFilterModel[].class);
        //List<RegionFilterModel> myTypes = gson.fromJson(response, ArrayList<RegionFilterModel>.class);

        final ArrayList<String> filterListTwo = new ArrayList<>();

        for (RegionFilterModel rgm: myTypes         ) {
            filterListTwo.add(rgm.getDescription());
        }


        Collections.sort(filterListTwo, new Comparator<String>() {
            public int compare(String v1, String v2) {
                return v1.compareTo(v2);
            }
        });
        RecyclerView.LayoutManager mLayoutManager;

        mAdapter3 = new SingleStaticRecyclerViewAdapter(filterListTwo);
        recyclerViewFilterListThree.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewFilterListThree.setLayoutManager(mLayoutManager);
        recyclerViewFilterListThree.setNestedScrollingEnabled(false);

        recyclerViewFilterListThree.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewFilterListThree.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFilterListThree.setAdapter(mAdapter3);
        mAdapter3.setOnItemClickListener(new SingleStaticRecyclerViewAdapter.SingleStaticClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
             selectedRegion=  mAdapter3.selectedItem(position);
            }
        });
        mAdapter3.setFirstItem();
    }

    private void network() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(FilterActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                          //  System.out.println(" **********  " + response);
                            showJSON(response);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(FilterActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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

    private void networkRegion() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(FilterActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.FilterRegion,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                             //Toast.makeText(FilterActivity.this, response, Toast.LENGTH_SHORT).show();
                          //  System.out.println(" **********  " + response);
                           // showJSON(response);
                           setRegionFilter(response);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(FilterActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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

    private void showJSON(String json) {
        FilterParse filterParse = new FilterParse(json);
        setFirstFilter(filterParse.parseJSON());

        setSecondFilter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", name);
                returnIntent.putExtra("CATEGORY", selectedCategory);
                returnIntent.putExtra("REGION", selectedRegion);
                returnIntent.putExtra("RETURNS", selectedPotReturnsIndex);
                setResult(Activity.RESULT_OK, returnIntent);

                SharedPreferences sharedPref = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("result", name);
                editor.commit();

                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", name);
        returnIntent.putExtra("CATEGORY", selectedCategory);
        returnIntent.putExtra("REGION", selectedRegion);
        returnIntent.putExtra("RETURNS", selectedPotReturnsIndex);
        setResult(Activity.RESULT_OK, returnIntent);
        super.onBackPressed();
    }

    //    @Override
//    public void onItemClickListener(int position, View view) {
//        name = mAdapter1.selectedItem();
//        mAdapter2.selectedItem(position);
//        mAdapter3.selectedItem(position);
//    }

    String name;
}
