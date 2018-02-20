package com.figsinc.app;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.figsinc.app.analyze.search.SearchAnalystsFragment;
import com.figsinc.app.analyze.search.SearchEvent;
import com.figsinc.app.analyze.search.SearchFundsFragment;
import com.figsinc.app.analyze.search.SearchParse;
import com.figsinc.app.analyze.search.SearchResultsListFragment;
import com.figsinc.app.analyze.search.SearchStocksFragment;
import com.figsinc.app.utils.GlobalBus;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private TabsPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_search_results);
        ButterKnife.bind(this);
         handleIntent(getIntent());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Constants.setStatusBar(this,R.color.darkish_blue);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            network(query);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        SearchView search = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchResultsActivity.class)));
        search.setQueryHint(getResources().getString(R.string.search_hint));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }/**/


    private void network(final String query) {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(SearchResultsActivity.this);
            String url = Constants.SearchDetails+query;

           /// System.out.println(" **********  " + FigsApplication.getAuthToken());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                            System.out.println(" Search Results  " + response);
                           // System.out.println(" **********  " + response);
                           showStocksJSON(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SearchResultsActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Token " + FigsApplication.getAuthToken());
                    return headers;
                }
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };
            requestQueue.add(stringRequest);

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    25000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showStocksJSON(String json){

        SearchParse searchParse = new SearchParse(json);
        searchParse.parseJSON();


        adapter = new TabsPagerAdapter(getSupportFragmentManager()); //TabsPagerAdapter = whatever you named the PagerAdapter


        if(searchParse.getListAnalysts().size()>0){
            Bundle bundle = new Bundle();
            SearchResultsListFragment searchResultsListFragment = new SearchResultsListFragment();
            bundle.putParcelableArrayList("Analyst", searchParse.getListAnalysts());
            bundle.putParcelableArrayList("Funds", searchParse.getListFunds());
            bundle.putParcelableArrayList("Stocks", searchParse.getListStocks());
            searchResultsListFragment.setArguments(bundle);
            adapter.addFragment(searchResultsListFragment, "All Results");

        }

        if(searchParse.getListAnalysts().size()>0){
            Bundle bundle = new Bundle();
            SearchAnalystsFragment searchAnalystsFragment = new SearchAnalystsFragment();
            bundle.putParcelableArrayList("Analyst", searchParse.getListAnalysts());
            searchAnalystsFragment.setArguments(bundle);
            adapter.addFragment(searchAnalystsFragment, "Analyst");
        }
        if(searchParse.getListFunds().size()>0){
            Bundle bundle = new Bundle();
            SearchFundsFragment searchFundsFragment = new SearchFundsFragment();
            bundle.putParcelableArrayList("Funds", searchParse.getListFunds());
            searchFundsFragment.setArguments(bundle);
            adapter.addFragment(searchFundsFragment, "Funds");
        }
        if(searchParse.getListStocks().size()>0){
            Bundle bundle = new Bundle();
            SearchStocksFragment searchStocksFragment = new SearchStocksFragment();
            bundle.putParcelableArrayList("Stocks", searchParse.getListStocks());
            searchStocksFragment.setArguments(bundle);
            adapter.addFragment(searchStocksFragment, "Stocks");
        }
        viewPager.setAdapter(adapter); //viewpager = ViewPager view instance
        tabLayout.setupWithViewPager(viewPager); //strip = PagerSlidingTabStrip view instance

       // SearchEvent messageEvent = new SearchEvent(bundle);
        //GlobalBus.getBus().post(messageEvent);
    }




    /**
     * TabsPagerAdapter.
     *
     * @author Niels
     * @version 1.0
     * @since 11-9-2015
     */
    public class TabsPagerAdapter extends FragmentStatePagerAdapter {

        /**
         * Contains all the fragments.
         */
        private List<Fragment> fragments = new ArrayList<>();

        /**
         * Contains all the tab titles.
         */
        private List<String> tabTitles = new ArrayList<>();

        /**
         * Creates a new PagerAdapter instance.
         *
         * @param fragmentManager The FragmentManager.
         */
        public TabsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }

        /**
         * Adds the fragment to the list, also adds the fragment's tab title.
         *
         * @param fragment New instance of the Fragment to be associated with this tab.
         * @param tabTitle A String containing the tab title for this Fragment.
         */
        public void addFragment(Fragment fragment, String tabTitle) {
            fragments.add(fragment);
            tabTitles.add(tabTitle);
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        // Register this fragment to listen to event.
        GlobalBus.getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GlobalBus.getBus().unregister(this);
    }


    @Subscribe
    public void getMessage(SearchEvent messageEvent) {

    }
}