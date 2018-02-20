package com.figsinc.app.analyze;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.figsinc.app.SearchResultsActivity;
import com.figsinc.app.analyze.filter.AnalyseFilterActivity;
import com.figsinc.app.analyze.model.TrendingStocks;
import com.figsinc.app.analyze.search.SearchActivity;
import com.figsinc.app.analyze.sentiments.SentimentsFragment;
import com.figsinc.app.analyze.trendinganalysts.TrendingAnalystsFragment;
import com.figsinc.app.analyze.trendingfunds.TrendingFundsFragment;
import com.figsinc.app.analyze.trendingstocks.TrendingStocksFragment;
import com.figsinc.app.collect.CollectActivity;
import com.figsinc.app.learn.LearnActivity;
import com.figsinc.app.settings.SettingsActivity;
import com.figsinc.app.utils.FigsPopupActivity;
import com.figsinc.app.utils.GlobalBus;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.figsinc.app.Constants.FilterDetails;
import static com.figsinc.app.analyze.filter.AnalyseFilterActivity.CURRENT_INDEX;

public class AnalyzeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageViewAnalyse)
    ImageView imageViewAnalyse;
    @BindView(R.id.imageViewLearn)
    ImageView imageViewLearn;
    @BindView(R.id.imageViewCollect)
    ImageView imageViewCollect;
    @BindView(R.id.imageViewFigs)
    ImageView imageViewFigs;
    @BindView(R.id.textAnalyze)
    TextView textAnalyze;
    @BindView(R.id.popupSpinner)
    Spinner popupSpinner;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    Menu myMenu;

    TrendingStocksFragment trendingStocksFragment;
    TrendingFundsFragment trendingFundsFragment;
    TrendingAnalystsFragment trendingAnalystsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        init();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    System.out.println(" !!!!!!!!!!!!!!!!!!!! ");
                    myMenu.findItem(R.id.miFilter)
                            .setVisible(false);
                } else {
                    myMenu.findItem(R.id.miFilter)
                            .setVisible(true);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            Intent intent = new Intent(getApplicationContext(), SearchResultsActivity.class);
            startActivity(intent);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {

            return true;

        }
    };


    private void init() {

        tabLayout.setupWithViewPager(viewPager);

        // setupTabIcons();
        setFooterIcons();
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.darkish_blue));
        Constants.setStatusBar(AnalyzeActivity.this, R.color.darkish_blue);

        tabLayout.setTabTextColors(
                ContextCompat.getColor(this, R.color.analyze_sentiments_non_selected_text),
                ContextCompat.getColor(this, R.color.white)
        );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Arrays.asList(getResources().getStringArray(R.array.analyze_regions)));
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        popupSpinner.setAdapter(adapter);

    }

    /**
     * Adding fragments to ViewPager
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        trendingStocksFragment = new TrendingStocksFragment();
        trendingFundsFragment = new TrendingFundsFragment();
        trendingAnalystsFragment = new TrendingAnalystsFragment();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new SentimentsFragment(), "Sentiments");
        adapter.addFrag(trendingStocksFragment, "Stocks");
        adapter.addFrag(trendingFundsFragment, "Funds");
        adapter.addFrag(trendingAnalystsFragment, "Analysts");
        viewPager.setAdapter(adapter);
        /* the ViewPager requires a minimum of 1 as OffscreenPageLimit */
        int limit = (adapter.getCount() > 1 ? adapter.getCount() - 1 : 1);
        viewPager.setOffscreenPageLimit(limit);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }/**/

    class ViewPagerAdapter extends FragmentStatePagerAdapter /*FragmentPagerAdapter*/ {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @OnClick(R.id.linearAnalyze)
    public void analyze(View view) {
       /* Intent intent = new Intent(this, AnalyzeActivity.class);
        startActivity(intent);*/
    }

    @OnClick(R.id.linearCollect)
    public void collect(View view) {
        Intent intent = new Intent(this, CollectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.linearFeed)
    public void feed(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    @OnClick(R.id.linearLearn)
    public void learn(View view) {
        Intent intent = new Intent(this, LearnActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.imageViewFigs)
    public void imageViewFigs(View view) {
        Intent intent = new Intent(this, FigsPopupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void setFooterIcons() {
        imageViewAnalyse.setBackground(getResources().getDrawable(R.mipmap.analyse_active));
        textAnalyze.setTextColor(ContextCompat.getColor(AnalyzeActivity.this, R.color.cobalt));
        imageViewLearn.setBackground(getResources().getDrawable(R.mipmap.learn_grey));
        imageViewCollect.setBackground(getResources().getDrawable(R.mipmap.bookmark_grey));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        myMenu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_analyse, menu);

        //myMenu.findItem(R.id.miFilter).setVisible(false);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
            case R.id.action_search: {
                Intent intent = new Intent(AnalyzeActivity.this, SearchActivity.class);
                //intent.putExtra(CURRENT_INDEX, viewPager.getCurrentItem());
                startActivity(intent);
                return true;
            }
            case R.id.miFilter: {
                Intent intent = new Intent(AnalyzeActivity.this, AnalyseFilterActivity.class);
                intent.putExtra(CURRENT_INDEX, viewPager.getCurrentItem());
                startActivityForResult(intent, 10011);
            }
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<TrendingStocks> filter(ArrayList<TrendingStocks> models, String query) {

        final String lowerCaseQuery = query.toLowerCase();

        final ArrayList<TrendingStocks> filteredModelList = new ArrayList<>();
        for (TrendingStocks model : models) {
            final String text = model.getCF_CF_CURRENCY().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private void network(final int ID) {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(AnalyzeActivity.this);

           // System.out.println(" **********  " + FigsApplication.getAuthToken());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, FilterDetails + ID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            sendEvent(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AnalyzeActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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

    private void sendEvent(final String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray fundsArray = jsonObject.getJSONArray("Funds");
            JSONArray StocksArray = jsonObject.getJSONArray("Stocks");
            JSONArray AnalystArray = jsonObject.getJSONArray("Analyst");
            Bundle bundle = new Bundle();
            bundle.putString("Funds", fundsArray.toString());
            bundle.putString("Stocks", StocksArray.toString());
            bundle.putString("Analyst", AnalystArray.toString());
            MessageEvent messageEvent = new MessageEvent(bundle);
            GlobalBus.getBus().post(messageEvent);

        } catch (Exception e) {
            e.printStackTrace();
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
    public void getMessage(MessageEvent messageEvent) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        moveTaskToBack(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10011) {

            if (resultCode == Activity.RESULT_OK) {
                String firstFilter = "", secondFilter = "", thirdFilter = "";
                if (viewPager.getCurrentItem() == 1) {
                    firstFilter = data.getStringExtra("first");
                    secondFilter = data.getStringExtra("second");
                    int regionid = getRegionIDByName(firstFilter);
                    String reqUri = "?";
                    if (regionid == 0) {
                        reqUri = reqUri + getStocksSortCodeByName(secondFilter, regionid);
                    } else {
                        reqUri = reqUri + getStocksSortCodeByName(secondFilter, regionid);
                    }
                    trendingStocksFragment.doNetworkCall(reqUri);
                    return;
                } else if (viewPager.getCurrentItem() == 2) {
                    firstFilter = data.getStringExtra("first");
                    int regionid = getRegionIDByName(firstFilter);
                    String reqUri = "?";
                    if (regionid == 0) {
                        reqUri = reqUri + "TopFundsNos=54";
                    } else {
                        reqUri = reqUri + "TopRegionFundsNos=54&" + "Region_ID=" + regionid;
                    }
                    trendingFundsFragment.doNetworkCall(reqUri);
                    return;
                } else if (viewPager.getCurrentItem() == 3) {
                    firstFilter = data.getStringExtra("first");
                    secondFilter = data.getStringExtra("second");
                    thirdFilter = data.getStringExtra("third");
                    int regionid = getRegionIDByName(firstFilter);
                    String reqUri = "";
                    if (regionid == 0) {
                        reqUri = reqUri + "AnalystTopSix/";
                        if (secondFilter.equalsIgnoreCase("all") && thirdFilter.equalsIgnoreCase("Top Accuracy Score")) {
                            reqUri = reqUri + "?TopAnalystNos=54";
                        } else if (!secondFilter.equalsIgnoreCase("all") && thirdFilter.equalsIgnoreCase("Top Accuracy Score")) {
                            String sectorid = data.getStringExtra("sectorid");

                            reqUri = reqUri + "?Sector=" + sectorid + "&TopScore=54";
                        } else if (secondFilter.equalsIgnoreCase("all") && !thirdFilter.equalsIgnoreCase("Top Accuracy Score")) {
                            reqUri = reqUri + "?" + getSortByAnalystByName(thirdFilter) + "=54";
                        }
                    } else {
                        reqUri = reqUri + "AnalystRegionalTopSix/?";
                        if (secondFilter.equalsIgnoreCase("all") && thirdFilter.equalsIgnoreCase("Top Accuracy Score")) {
                            reqUri = reqUri + "TopAnalystNos=54&RegionID=" + regionid;
                        } else if (!secondFilter.equalsIgnoreCase("all") && !thirdFilter.equalsIgnoreCase("Top Accuracy Score")) {
                            String sectorid = data.getStringExtra("sectorid");

                            reqUri = reqUri + "Sector=" + sectorid + "&" + getSortByAnalystByName(thirdFilter) + "=54";
                        } else if (!thirdFilter.equalsIgnoreCase("Top Accuracy Score")) {
                            reqUri = reqUri + getSortByAnalystByName(thirdFilter) + "=54&RegionID=" + regionid;
                        }
                    }


                    trendingAnalystsFragment.doNetworkCall(reqUri);
                    return;
                }
                //String result = data.getStringExtra("result");
//                String category=data.getStringExtra("CATEGORY");
//                String region=data.getStringExtra("REGION");
//                int returns=data.getIntExtra("RETURNS",0);


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private String getSortByAnalystByName(String thirdFilter) {
        String code = "";
        switch (thirdFilter) {
            case "Top Accuracy Score": {
                code = "TopAnalystNos";
                break;
            }
            case "Top Avg. Return": {
                code = "TopAvgReturns";
                break;
            }
            case "Top Avg. Time": {
                code = "TopAvgTime";
                break;
            }
        }

        return code;
    }


    private int getRegionIDByName(String name) {
        int regionID = 0;
        switch (name) {
            case "Hong Kong":
                regionID = 4;
                break;
            case "Indonesia":
                regionID = 7;
                break;
            case "Japan":
                regionID = 2;
                break;
            case "Singapore":
                regionID = 3;
                break;
            case "Taiwan":
                regionID = 5;
                break;
            case "Thailand":
                regionID = 9;
                break;
            case "United States":
                regionID = 1;
                break;
            default:
                regionID = 0;
        }

        return regionID;
    }

    private String getStocksSortCodeByName(String name, int regionid) {
        String fixedCode = "=54";
        String marketcapRegionIdArgument = "&RegionID=";
        String potentialReternsRegionIdArgument = "&Region_ID=";
        String code = "";
        switch (name) {
            case "Potential Returns": {
                code = "NosofTopStocks" + fixedCode + potentialReternsRegionIdArgument + regionid;
                break;
            }
            case "Market Cap": {
                code = "byMKTCap" + fixedCode;
                break;
            }
        }
        if (regionid != 0 && name.equalsIgnoreCase("Market Cap")) {
            code = "byRegionMKTCap" + fixedCode + marketcapRegionIdArgument + regionid;
        } else if (regionid == 0 && name.equalsIgnoreCase("Potential Returns")) {
            code = "TopStocks" + fixedCode;
        }
        return code;
    }


}
