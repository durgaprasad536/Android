package com.figsinc.app.analyze.trendingstocks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.figsinc.app.Constants;
import com.figsinc.app.FigsApplication;
import com.figsinc.app.R;
import com.figsinc.app.analyze.AnalyzeActivity;
import com.figsinc.app.analyze.SmallListAdapter;
import com.figsinc.app.analyze.model.CompanyStockDetails;
import com.figsinc.app.analyze.model.RelatedStocks;
import com.figsinc.app.analyze.model.Stock;
import com.figsinc.app.analyze.model.TrendingStocks;
import com.figsinc.app.analyze.trendinganalysts.TrendingAnalystsParse;
import com.figsinc.app.calculator.CalculatorActivity;
import com.figsinc.app.collect.CollectActivity;
import com.figsinc.app.learn.LearnActivity;
import com.figsinc.app.learn.news.NewsActivity;
import com.figsinc.app.learn.news.NewsAdapter;
import com.figsinc.app.learn.news.NewsParse;
import com.figsinc.app.netdhania.ChangeThemeTask;
import com.figsinc.app.netdhania.ChartImageActivity;
import com.figsinc.app.netdhania.ChartPreferences;
import com.figsinc.app.netdhania.LoadInitialConfigurationDataTask;
import com.figsinc.app.netdhania.MainProPatternsChartActivity;
import com.figsinc.app.netdhania.PriceBar;
import com.figsinc.app.netdhania.PriceBarParse;
import com.figsinc.app.netdhania.ProPatternsChartCommands;
import com.figsinc.app.netdhania.Utils;
import com.figsinc.app.settings.PersonalInfoAcitivity;
import com.figsinc.app.settings.SettingsActivity;
import com.figsinc.app.utils.FigsPopupActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netdania.NDChartAPI;
import com.netdania.common.NDChartAnalysisObject;
import com.netdania.common.NDChartAnalysisTimeFrame;
import com.netdania.common.NDChartController;
import com.netdania.common.NDChartFibonacciObject;
import com.netdania.common.NDChartField;
import com.netdania.common.NDChartInstrument;
import com.netdania.common.NDChartInstrumentChangeTypeListener;
import com.netdania.common.NDChartInstrumentTimeScaleChangedListener;
import com.netdania.common.NDChartInstrumentType;
import com.netdania.common.NDChartListener;
import com.netdania.common.NDChartObject;
import com.netdania.common.NDChartOverlayObject;
import com.netdania.common.NDChartPatternsListener;
import com.netdania.common.NDChartSettings;
import com.netdania.common.NDChartStudyObject;
import com.netdania.common.NDChartTitleVisibility;
import com.netdania.common.NDChartToolbarLocation;
import com.netdania.common.NDChartTrendLineObject;
import com.netdania.common.NDChartType;
import com.netdania.common.NDChartView;
import com.netdania.ui.views.ListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.figsinc.app.Constants.saveStocks;
import static com.figsinc.app.Constants.searchStockInfo;
import static com.figsinc.app.Constants.sockInfoAnalystCompanyTopSix;
import static com.figsinc.app.Constants.stockInfoTopStocksDetails;

public class StockInfoActivity extends AppCompatActivity implements ProPatternsChartCommands {

    public static final String KEY_EXTRA_TRENDING_STOCKS_PARSE = "KEY_EXTRA_TRENDING_STOCKS_PARSE";
    public static final String KEY_EXTRA_CF_RIC = "KEY_EXTRA_CF_RIC";
    public static final String KEY_EXTRA_TICKER_EXCHANGE = "KEY_EXTRA_TICKER_EXCHANGE";
    public static final String KEY_EXTRA_SUB_INDUSTRY_CODE = "KEY_EXTRA_SUB_INDUSTRY_CODE";


    private String imageUrl = "";
    private int colorCode = 0;
    // private String CF_TICK;

    @BindView(R.id.textView_entity_header)
    TextView textView_entity_header;
    @BindView(R.id.textView_entity_subheader)
    TextView textView_entity_subheader;
    @BindView(R.id.entities_overview)
    TextView entities_overview;
    @BindView(R.id.entities_update_company_description)
    TextView entities_update_company_description;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.entities_showDetails)
    TextView entities_showDetails;
    @BindView(R.id.tvAverage_Price_target)
    AppCompatTextView tvAverage_Price_target;
    @BindView(R.id.tvtitle_Potential_Gain)
    AppCompatTextView tvtitle_Potential_Gain;
    @BindView(R.id.textAnalyze)
    TextView textAnalyze;

    @BindView(R.id.imageViewAnalyse)
    ImageView imageViewAnalyse;
    @BindView(R.id.imageViewLearn)
    ImageView imageViewLearn;
    @BindView(R.id.imageViewCollect)
    ImageView imageViewCollect;
    BottomSheetDialog mBottomSheetDialog;
    View sheetView;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.linearShowDetails)
    LinearLayout linearShowDetails;
    private Gson gson;

    //Statistics
    @BindView(R.id.title_Currency_stock)
    AppCompatTextView title_Currency_stock;
    @BindView(R.id.title_Exchange)
    AppCompatTextView title_Exchange;
    @BindView(R.id.title_Trade_Date)
    AppCompatTextView title_Trade_Date;
    @BindView(R.id.title_Price_Earnings_Ratio)
    AppCompatTextView title_Price_Earnings_Ratio;
    @BindView(R.id.title_Years_High)
    AppCompatTextView title_Years_High;
    @BindView(R.id.title_Years_Low)
    AppCompatTextView title_Years_Low;
    @BindView(R.id.title_Dividend)
    AppCompatTextView title_Dividend;
    @BindView(R.id.title_Ex_Dividend_Date)
    AppCompatTextView title_Ex_Dividend_Date;
    @BindView(R.id.title_Volumn)
    AppCompatTextView title_Volumn;

    //Analysts price
    @BindView(R.id.tvCurrent_Price)
    AppCompatTextView tvCurrent_Price;

    //Recommendations
    @BindView(R.id.tv_buy)
    AppCompatTextView tv_buy;
    @BindView(R.id.tv_hold)
    AppCompatTextView tv_hold;
    @BindView(R.id.tv_sell)
    AppCompatTextView tv_sell;

    RequestQueue requestQueue;
    String sectorID;
    String tickerExchange;
    String CF_RIC;
    private CompanyStockDetails companyStockDetails;

    @BindView(R.id.recyclerView_RelatedStocks)
    RecyclerView recyclerView_RelatedStocks;

    @BindView(R.id.recyclerView_Recommended_by)
    RecyclerView recyclerView_Recommended_by;

    @BindView(R.id.recyclerView_RelatedNews)
    RecyclerView recyclerView_RelatedNews;

    @BindView(R.id.linear_relatednews)
    LinearLayout linear_relatednews;
    @BindView(R.id.linearRelatedStocks)
    LinearLayout linearRelatedStocks;
    @BindView(R.id.linear_recommendedBy)
    LinearLayout linear_recommendedBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_info);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        requestQueue = Volley.newRequestQueue(StockInfoActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        Constants.setStatusBar(this, R.color.darkish_blue);
        try {
            imageUrl = getIntent().getStringExtra("imageUrl");
            colorCode = getIntent().getIntExtra("colorCode", 0);
            sectorID = getIntent().getStringExtra(KEY_EXTRA_SUB_INDUSTRY_CODE);
            tickerExchange = getIntent().getStringExtra(KEY_EXTRA_TICKER_EXCHANGE);
            CF_RIC = getIntent().getStringExtra(KEY_EXTRA_CF_RIC);
            network(CF_RIC, tickerExchange);
            networkTopStocks(sectorID, tickerExchange);
            networkRecommendedAnalysts(tickerExchange);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setFooterIcons();

    }

    private void setCompanyFundamentalInformations() {

        List<String> fundamentalInfoList = Arrays.asList(getResources().getStringArray(R.array.company_fundamental_info));
        SmallListAdapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;

        RecyclerView recyclerViewFilterListOne = (RecyclerView) findViewById(R.id.filter1_recyclerView);
        mAdapter = new SmallListAdapter(fundamentalInfoList, this, tickerExchange);
        recyclerViewFilterListOne.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewFilterListOne.setLayoutManager(mLayoutManager);
        recyclerViewFilterListOne.setNestedScrollingEnabled(false);

        recyclerViewFilterListOne.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewFilterListOne.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFilterListOne.setAdapter(mAdapter);
    }


    private void network(final String tag, final String tickerexchange) {
        try {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, searchStockInfo + "tag=" + tag + "&tickerexchange=" + tickerexchange,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                            List<CompanyStockDetails> posts = Arrays.asList(gson.fromJson(response, CompanyStockDetails[].class));
                            companyStockDetails = posts.get(0);
                            setValues(companyStockDetails);
                            title_Currency_stock.setText("" + companyStockDetails.getCFCURRENCY());
                            title_Exchange.setText("" + companyStockDetails.getExchange());
                            title_Trade_Date.setText("" + companyStockDetails.getTRADEDATE());
                            title_Price_Earnings_Ratio.setText("" + companyStockDetails.getCFPERATIO());
                            title_Years_High.setText("" + companyStockDetails.getCFYRHIGH());
                            title_Years_Low.setText("" + companyStockDetails.getCFYRLOW());
                            title_Dividend.setText("" + companyStockDetails.getCFDIVIDEND());
                            title_Ex_Dividend_Date.setText("" + companyStockDetails.getCFEXDIVDATE());
                            title_Volumn.setText("" + companyStockDetails.getCFVOLUME());
                            loadData();
                        }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // Toast.makeText(StockInfoActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();

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

    private void loadData() {
        networkRelatedNews();
        setCompanyFundamentalInformations();
        mBottomSheetDialog = new BottomSheetDialog(this);
        sheetView = getLayoutInflater().inflate(R.layout.trade_popup, null);
        mBottomSheetDialog.setContentView(sheetView);
        Constants.readyToTrade(this, sheetView);
        loadNetdhania();
    }

    private void networkTopStocks(final String SectorID, final String tickerexchange) {
        try {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, stockInfoTopStocksDetails + SectorID + "&Ticker_Exchang=" + tickerexchange,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                           // linearRelatedStocks.setVisibility(View.VISIBLE);
                            TrendingStocksParse pj = new TrendingStocksParse(response);
                            TrendingStocksRecyclerViewAdapter mAdapter = new TrendingStocksRecyclerViewAdapter(pj.parseJSON(), StockInfoActivity.this);
                            recyclerView_RelatedStocks.setHasFixedSize(true);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(StockInfoActivity.this);
                            recyclerView_RelatedStocks.setLayoutManager(mLayoutManager);
                            recyclerView_RelatedStocks.setItemAnimator(new DefaultItemAnimator());
                            recyclerView_RelatedStocks.setAdapter(mAdapter);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Toast.makeText(getActivity(), "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
                    linearRelatedStocks.setVisibility(View.GONE);
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


    private void networkRecommendedAnalysts(final String Ticker_Exchange) {
        try {

          //  System.out.println(" " +sockInfoAnalystCompanyTopSix + Ticker_Exchange);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, sockInfoAnalystCompanyTopSix + Ticker_Exchange,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            linear_recommendedBy.setVisibility(View.VISIBLE);
                            TrendingAnalystsParse pj = new TrendingAnalystsParse(response);
                            StockInfoAnalystsRecyclerViewAdapter mAdapter = new StockInfoAnalystsRecyclerViewAdapter(pj.parseJSON(), StockInfoActivity.this);
                            recyclerView_Recommended_by.setHasFixedSize(true);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(StockInfoActivity.this);
                            recyclerView_Recommended_by.setLayoutManager(mLayoutManager);
                            recyclerView_Recommended_by.addItemDecoration(new DividerItemDecoration(StockInfoActivity.this, LinearLayoutManager.VERTICAL));
                            recyclerView_Recommended_by.setItemAnimator(new DefaultItemAnimator());
                            recyclerView_Recommended_by.setAdapter(mAdapter);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                 //    Toast.makeText(StockInfoActivity.this, "\" recommended analytics That didn't work!\"", Toast.LENGTH_SHORT).show();
                    // linear_recommendedBy.setVisibility(View.GONE);

                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        // Print Error!
                        Toast.makeText(StockInfoActivity.this, ""+jsonError, Toast.LENGTH_SHORT).show();
                    }
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

    private void networkRelatedNews() {
        try {
            // Instantiate the RequestQueue.
            String url = Constants.newsAPI + companyStockDetails.getCFNAME();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                          //  linear_relatednews.setVisibility(View.VISIBLE);
                            NewsParse pj = new NewsParse(response);
                            NewsAdapter mAdapter = new NewsAdapter(pj.parseJSON(), StockInfoActivity.this);
                            //recyclerView.setHasFixedSize(true);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(StockInfoActivity.this.getApplicationContext());
                            recyclerView_RelatedNews.setLayoutManager(mLayoutManager);
                            recyclerView_RelatedNews.addItemDecoration(new DividerItemDecoration(StockInfoActivity.this, LinearLayoutManager.VERTICAL));
                            recyclerView_RelatedNews.setItemAnimator(new DefaultItemAnimator());
                            recyclerView_RelatedNews.setAdapter(mAdapter);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // Toast.makeText(StockInfoActivity.this, "\" Related news That didn't work!\"", Toast.LENGTH_SHORT).show();
                    linear_relatednews.setVisibility(View.GONE);
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


    @OnClick(R.id.button_calculator)
    public void calculator(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra(CalculatorActivity.KEY_CF_TICK, companyStockDetails.getTicker());
        startActivity(intent);
    }

    @OnClick(R.id.button_save)
    public void save(View view) {
        Constants.savedToFigs(StockInfoActivity.this, saveStocks + tickerExchange, Request.Method.POST, coordinatorLayout);

    }

    @OnClick(R.id.button_forward)
    public void forward(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_dollar)
    public void dollar(View view) {
        mBottomSheetDialog.show();
    }

    @OnClick(R.id.entities_showDetails)
    public void collapse() {

        if (entities_showDetails.getText().toString().trim().equals(getResources().getString(R.string.title_Show_Details))) {
            entities_showDetails.setText(R.string.title_Show_Less);
            entities_update_company_description.setMaxLines(Integer.MAX_VALUE);
        } else {
            entities_showDetails.setText(getResources().getString(R.string.title_Show_Details));
            entities_update_company_description.setMaxLines(5);
        }

    }

    private void setValues(final CompanyStockDetails trendingStocks) {

        // final TrendingStocks trendingStocks = trendingStocksArrayList.get(0);
        toolbarTitle.setText(getResources().getString(R.string.title_Stock_Info));

        textView_entity_header.setText(trendingStocks.getCFNAME());

        double pctchng = Double.parseDouble(trendingStocks.getPCTCHNG());
        double netchng = Double.parseDouble("" + trendingStocks.getCFNETCHNG());
        String formatted = String.format("%.2f", new BigDecimal(netchng)) + "(" + String.format("%.2f", new BigDecimal(pctchng)) + "%)";

        Spannable word = new SpannableString(trendingStocks.getExchange() + " : " + trendingStocks.getTicker());
        word.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.greyBlue)), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_entity_subheader.setText(word);
        textView_entity_subheader.append(" ");
        Spannable word1 = new SpannableString("" + trendingStocks.getCFLAST());
        word1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.greyBlue)), 0, word1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_entity_subheader.append(word1);
        textView_entity_subheader.append(" \n ");

        if (String.valueOf(trendingStocks.getCFNETCHNG()).startsWith("-")) {
            Spannable word2 = new SpannableString(formatted);
            word2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.paleRed)), 0, word2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView_entity_subheader.append(word2);
        } else {
            Spannable word3 = new SpannableString("+ " + formatted);
            word3.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.greenBlue)), 0, word3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView_entity_subheader.append(word3);
        }
        entities_showDetails.setTextColor(ContextCompat.getColor(this, R.color.cobalt));

        try {
            double concensusPrice = Double.parseDouble("" + trendingStocks.getConcensusPrice());
            double potentialReturns = Double.parseDouble(trendingStocks.getPotentialReturns());
            String targetPrice = String.format("%.2f", new BigDecimal(concensusPrice));
            String potentialGain = String.format("%.2f", new BigDecimal(potentialReturns)) /*+ "%"*/;
            tvAverage_Price_target.setText(/*"$ "+*/targetPrice);
            tvtitle_Potential_Gain.setText(potentialGain(potentialGain));
        } catch (Exception e) {
            e.printStackTrace();
        }

        getDescription();
    }

    private String potentialGain(String gain) {
        if (gain.startsWith("-")) {
            gain = gain;
        } else {
            gain = "+ " + gain;
        }
        return gain;
    }


    private void getDescription() {
        try {
            String url = Constants.sockInfoStockDetailsDescription + CF_RIC + Constants.lauguage;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                              //  System.out.println(" **********  " + response);

                                JSONObject jsonObject = new JSONObject(response);
                                entities_update_company_description.setText(jsonObject.getString("Value"));

                                if (entities_update_company_description.getLineCount() > 5) {
                                    linearShowDetails.setVisibility(View.VISIBLE);
                                } else {
                                    linearShowDetails.setVisibility(View.GONE);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // Toast.makeText(StockInfoActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.linearAnalyze)
    public void analyze(View view) {
        Intent intent = new Intent(this, AnalyzeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.linearCollect)
    public void collect(View view) {
        Intent intent = new Intent(this, CollectActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.linearFeed)
    public void feed(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.imageViewFigs)
    public void figs(View view) {
        Intent intent = new Intent(this, FigsPopupActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.linearLearn)
    public void learn(View view) {
        Intent intent = new Intent(this, LearnActivity.class);
        startActivity(intent);
    }

    private void setFooterIcons() {
        imageViewAnalyse.setBackground(getResources().getDrawable(R.mipmap.analyse_active));
        textAnalyze.setTextColor(ContextCompat.getColor(StockInfoActivity.this, R.color.cobalt));
        imageViewLearn.setBackground(getResources().getDrawable(R.mipmap.learn_grey));
        imageViewCollect.setBackground(getResources().getDrawable(R.mipmap.bookmark_grey));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.button_viewChart)
    public void netdhania() {
         Intent intent = new Intent(this, MainProPatternsChartActivity.class);
        intent.putExtra(KEY_EXTRA_CF_RIC, CF_RIC);
        startActivity(intent);
    }
/////////////////////////////////// CHART //////////////////////////////////////////////////

    private void onFinishLoadingConfigurationData(Exception exception, NDChartAPI frameworkConfiguration) {
        if (exception == null) {
            FigsApplication application = getBaseApplication();
            application.setFrameworkConfiguration(frameworkConfiguration);

            chartCommandsListView = (ListView) findViewById(R.id.chart_command_list);
            showLeftMenu = getIntent().getBooleanExtra("show_left_menu", false);
            if (showLeftMenu) {
                initChartCommandList();
            } else {
                chartCommandsListView.setVisibility(View.GONE);
            }

            initContent();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

       try {
            String chartAsXML = this.chartViewCommands.writeChartAsXML();
            String instrumentSymbol = "EURUSD";
            String providerName = "netdania_fxa";
            int timeScale = 60;
            this.chartPreferences.saveChartXML(instrumentSymbol, providerName, timeScale, chartAsXML);
            //System.out.println("chartAsXML="+chartAsXML);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initChartCommandList() {
        if (chartCommandsListView.getAdapter() == null) {
            ListAdapter<String> listAdapter = new ListAdapter<String>(this) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    TextView tv;
                    if (convertView == null) {
                        convertView = View.inflate(getContext(), android.R.layout.simple_list_item_1, null);

                        tv = (TextView) convertView.findViewById(android.R.id.text1);
                        tv.setSingleLine();
                        tv.setTextSize(13);
                        tv.setEllipsize(TextUtils.TruncateAt.END);
                        convertView.setTag(tv);
                    } else {
                        tv = (TextView) convertView.getTag();
                    }
                    tv.setText(getItem(position));
                    return tv;
                }

                @Override
                protected Filter getFilter() {
                    return null;
                }
            };

            chartCommandsListView.setAdapter(listAdapter);
            setAdapterItems();

            chartCommandsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    handleOnMenuItemClick(position);
                }
            });
        }
    }

    private void initContent() {
        NDChartAPI frameworkConfiguration = NDChartAPI.getInstance();

        this.chartPreferences = new ChartPreferences(this);

        prefferedInstrument = Utils.getIntstrumentFromBundle(getIntent().getExtras());

        if (prefferedInstrument == null) {
            String instrumentSymbol = "EURUSD";
            String providerName = "netdania_fxa";
            String instrumentName = "EUR/USD";
            prefferedInstrument = new NDChartInstrument(instrumentName, instrumentSymbol, providerName, NDChartInstrumentType.FOREX, NDChartField.BID, 5, 5);
        }

        NDChartSettings chartSettings = null;
        String xml = this.chartPreferences.loadChartXML(prefferedInstrument.getSymbol(), prefferedInstrument.getProvider());
        if (xml == null) {
            chartSettings = new NDChartSettings(NDChartInstrumentType.FOREX);
        } else {
            chartSettings = new NDChartSettings(xml);
        }

        NDChartListener chartListener = new NDChartPatternsListener() {
            @Override
            public void onLastAnalysisUpdate(NDChartAnalysisObject newAnalysis) {
                // Toast.makeText(StockInfoActivity.this, "onLastAnalysisUpdate " + newAnalysis.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnalysesReceivedForTimeframe(NDChartAnalysisTimeFrame timeframe) {
                // Toast.makeText(StockInfoActivity.this, "onAnalysesReceivedForTimeframe " + timeframe, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccessfullyLoadingChart(NDChartView chartView, NDChartController chartViewCommands) {
                // Toast.makeText(getContext(), "Chart has been successfully loaded", Toast.LENGTH_LONG).show();
                StockInfoActivity.this.onSuccessfullyLoadingChart(chartViewCommands);
                changeTheme("White");
            }

            @Override
            public void onFailedLoadingChart(NDChartView chartView) {
            }

            @Override
            public boolean canChangeChartType(NDChartView chartView, NDChartType fromChartType, NDChartType toChartType) {
                return true;
            }

            @Override
            public boolean canChangeTimeScale(NDChartView chartView, long fromTimeScale, long toTimeScale) {
                return true;
            }

            @Override
            public void onFinishEditingObject(NDChartView chartView, NDChartObject chartObject) {
                //Toast.makeText(getContext(), "Done editing object: " + chartObject.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSelectingObject(NDChartView chartView, NDChartObject chartObject) {
                // Toast.makeText(getContext(), "Selected object: " + chartObject.getName(), Toast.LENGTH_SHORT).show();
                StockInfoActivity.this.selectedChartObject = chartObject;
            }

            @Override
            public boolean canSelectObject(NDChartView chartView, NDChartObject chartObject) {
                if (chartObject.getName() != null && chartObject.getName().equals("BB20;2")) {
                    // Toast.makeText(getContext(), chartObject.getName() + " cannot be selected", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }

            @Override
            public void onDoubleTap(NDChartView chartView) {
                // Toast.makeText(getContext(), "Chart was double tapped", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinishAddingObjectFromUI(NDChartObject chartObject) {
                StockInfoActivity.this.addedFromUIChartObject.add(chartObject);
            }

            @Override
            public void chartBackButtonClicked() {
                //Toast.makeText(getContext(), "Chart back button clicked!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMainInstrumentDataRebuild() {
            }
        };

        float density = getResources().getDisplayMetrics().density;
        Rect margins = new Rect((int) (density * 2), (int) (density * 2), (int) (density * 2), (int) (density * 2));
        Rect padding = new Rect((int) (density * 2), (int) (density * 2), (int) (density * 2), (int) (density * 2));

        chartView = frameworkConfiguration.buildNewChartView(this, prefferedInstrument, chartSettings, chartListener);
        if (showLeftMenu) {
            chartView.hideToolbar();
        }
        chartView.hideToolbar();
        chartView.setChartPadding(padding);
        chartView.setChartMargins(margins);
//        chartView.setChartTitleVisibility(NDChartTitleVisibility.MAIN_CHART_TITLE|NDChartTitleVisibility.SECONDARY_CHART_TITLE, true);
        chartView.setHorizontalScrollButtonVisibility(true);
        chartView.setVerticalFitButtonVisibility(true);
        FrameLayout chartHolder = (FrameLayout) findViewById(R.id.chartFrame);
        chartHolder.addView(chartView);
    }

    protected Context getContext() {
        return this;
    }

    private void onSuccessfullyLoadingChart(NDChartController chartViewCommands) {
        this.chartViewCommands = chartViewCommands;
    }

    private void setAdapterItems() {
        List<String> adapterItems = new LinkedList<String>();
        adapterItems.add(ADD_STUDY_UI);
        adapterItems.add(ADD_STUDY_OBJECT);
        adapterItems.add(EDIT_STUDY_OBJECT_UI);
        adapterItems.add(FINISH_EDITING_STUDY);
        adapterItems.add(CHANGE_TIMESCALE);
        adapterItems.add(CHANGE_CHART_TYPE);
        adapterItems.add(SHOW_CROSSHAIR);
        adapterItems.add(HIDE_CROSSHAIR);
        adapterItems.add(START_ADDING_LINE_UI);
        adapterItems.add(START_EDITING_LINE_UI);
        adapterItems.add(START_ADDING_FIBONACCI_LINE_UI);
        adapterItems.add(START_EDITING_FIBONACCI_LINE_UI);
        adapterItems.add(FINISH_EDITING_LINE);
        adapterItems.add(ADD_OVERLAY_USDJPY);
        adapterItems.add(REMOVE_OVERLAY_USDJPY);
        adapterItems.add(EDIT_OVERLAY_USDJPY);
        adapterItems.add(FINISH_EDITING_OVERLAY);
        adapterItems.add(DELETE_OBJECT);
        adapterItems.add(DELETE_ALL_OBJECTS);
        adapterItems.add(EXPORT_TO_IMAGE);
        adapterItems.add(SHOW_VOLUME);
        adapterItems.add(HIDE_VOLUME);
        adapterItems.add(SHOW_MAIN_CHART_TITLE);
        adapterItems.add(HIDE_MAIN_CHART_TITLE);
        adapterItems.add(" ");
        adapterItems.add(SHOW_DEFAULT_TOOLBAR);
        adapterItems.add(HIDE_DEFAULT_TOOLBAR);
        adapterItems.add(PLACE_TOOLBAR_UP);
        adapterItems.add(PLACE_TOOLBAR_DOWN);
        adapterItems.add(" ");
        adapterItems.add(BLACK_THEME);
        adapterItems.add(BLUE_THEME);
        adapterItems.add(WHITE_THEME);
        adapterItems.add(GRAY_THEME);
        adapterItems.add(" ");
        adapterItems.add(SELECT_STUDY);
        adapterItems.add(SELECT_LINE);
        adapterItems.add(SELECT_FIBONACCI);
        adapterItems.add(SELECT_OVERLAY);
        adapterItems.add(GET_CHART_TYPE);
        adapterItems.add(GET_TIMESCALE);

        adapterItems.add(" ");
        adapterItems.add(START_ADDING_ANALYSIS_UI);
        adapterItems.add(SELECT_ANALYSIS);
        adapterItems.add(SHOW_ANALYSIS_STORY);
        adapterItems.add(REMOVE_ANALYSIS);
        adapterItems.add(ADD_LAST_ANALYSIS);
        adapterItems.add(REMOVE_LAST_ANALYSIS);
        adapterItems.add(GET_DISPLAYED_ANALYSES);
        adapterItems.add(SELECT_ANALYSES_LANGUAGE);
        adapterItems.add(FIT_CHART_Y);
        adapterItems.add(GO_LAST_INDEX);

        ListAdapter<String> adapter = (ListAdapter<String>) chartCommandsListView.getAdapter();
        adapter.setItems(adapterItems);
        adapter.notifyDataSetChanged();

    }

    protected void handleOnMenuItemClick(int position) {
        if (chartViewCommands == null) {
            // Toast.makeText(this, "Chart not ready to receive commands yet!", Toast.LENGTH_LONG).show();
            return;
        }
        String item = (String) chartCommandsListView.getAdapter().getItem(position);
        if (ADD_STUDY_UI.equals(item)) {
            chartViewCommands.showDialogToAddStudy();
        } else if (ADD_STUDY_OBJECT.equals(item)) {
            HashMap<String, NDChartStudyObject> allStudies = NDChartAPI.getInstance().getAllStudies();
            NDChartStudyObject ndChartStudyObject = allStudies.get("EWO");
            chartViewCommands.addStudyObject(ndChartStudyObject);
        } else if (EDIT_STUDY_OBJECT_UI.equals(item)) {
            List<NDChartStudyObject> studiesOnChart = chartViewCommands.getStudiesOnChart();
            if (studiesOnChart != null) {
                NDChartStudyObject ndChartStudyObject = studiesOnChart.get(0);
                chartViewCommands.editStudyUI(ndChartStudyObject);
            }
        } else if (FINISH_EDITING_STUDY.equals(item)) {
            chartViewCommands.finishEditingStudy();
        } else if (START_ADDING_LINE_UI.equals(item)) {
            chartViewCommands.startAddingLineUI();

        } else if (START_EDITING_LINE_UI.equals(item)) {
            List<NDChartTrendLineObject> trendlinesOnChart = chartViewCommands.getTrendlinesOnChart();
            if (trendlinesOnChart != null && !trendlinesOnChart.isEmpty()) {
                chartViewCommands.startEditingLineUI(trendlinesOnChart.get(0));
            }
        } else if (START_ADDING_FIBONACCI_LINE_UI.equals(item)) {
            chartViewCommands.startAddingFibonacciLineUI();

        } else if (START_EDITING_FIBONACCI_LINE_UI.equals(item)) {
            List<NDChartFibonacciObject> fibonacciLinesOnChart = chartViewCommands.getFibonacciLinesOnChart();
            if (fibonacciLinesOnChart != null && !fibonacciLinesOnChart.isEmpty()) {
                chartViewCommands.startEditingFibonacciUI(fibonacciLinesOnChart.get(0));
            }
        } else if (FINISH_EDITING_LINE.equals(item)) {
            chartViewCommands.finishEditingLine();
        } else if (EXPORT_TO_IMAGE.equals(item)) {
            Bitmap bitmap = chartViewCommands.exportToImageWithSize(600, 400);
            handleExportToImageCommand(bitmap);
        } else if (DELETE_OBJECT.equals(item)) {
            List<NDChartStudyObject> studiesOnChart = chartViewCommands.getStudiesOnChart();
            if (studiesOnChart != null) {
                NDChartObject objectToDelete = studiesOnChart.get(0);
                chartViewCommands.deleteChartObject(objectToDelete);
            }
            List<NDChartTrendLineObject> trendlinesOnChart = chartViewCommands.getTrendlinesOnChart();
            if (trendlinesOnChart != null && !trendlinesOnChart.isEmpty() && trendlinesOnChart.get(0) != null) {
                NDChartObject objectToDelete = trendlinesOnChart.get(0);
                chartViewCommands.deleteChartObject(objectToDelete);
            }
        } else if (DELETE_ALL_OBJECTS.equals(item)) {
            chartViewCommands.deleteAllChartItems();
        } else if (CHANGE_TIMESCALE.equals(item)) {
            NDChartInstrumentTimeScaleChangedListener changeInstrumentChartTimeScaleCallback = new NDChartInstrumentTimeScaleChangedListener() {
                @Override
                public void beforeChangeTimeScale(NDChartView chartView, int fromTimeScale, int toTimeScale) {
                }

                @Override
                public void afterChangeTimeScale(NDChartView chartView, int fromTimeScale, int toTimeScale, Exception exception) {
                }
            };
            chartViewCommands.changeInstrumentTimeScaleAsync(1, changeInstrumentChartTimeScaleCallback);
        } else if (CHANGE_CHART_TYPE.equals(item)) {
            NDChartInstrumentChangeTypeListener changeInstrumentChartTypeCallback = new NDChartInstrumentChangeTypeListener() {
                @Override
                public void beforeChangeChartType(NDChartView chartView, NDChartType fromChartType, NDChartType toChartType) {
                }

                @Override
                public void afterChangeChartType(NDChartView chartView, NDChartType fromChartType, NDChartType toChartType, Exception exception) {
                }
            };
            chartViewCommands.changeInstrumentChartTypeAsync(NDChartType.LINE_CHART, changeInstrumentChartTypeCallback);
        } else if (SHOW_VOLUME.equals(item)) {
            chartViewCommands.showVolume();
        } else if (HIDE_VOLUME.equals(item)) {
            chartViewCommands.hideVolume();
        } else if (SHOW_DEFAULT_TOOLBAR.equals(item)) {
            chartView.showToolbar();
        } else if (HIDE_DEFAULT_TOOLBAR.equals(item)) {
            chartView.hideToolbar();
        } else if (SHOW_MAIN_CHART_TITLE.equals(item)) {
            chartView.setChartTitleVisibility(NDChartTitleVisibility.MAIN_CHART_TITLE, true);
        } else if (HIDE_MAIN_CHART_TITLE.equals(item)) {
            chartView.setChartTitleVisibility(NDChartTitleVisibility.MAIN_CHART_TITLE, false);
        } else if (ADD_OVERLAY_USDJPY.equals(item)) {
            String instrumentSymbol = "USDJPY";
            String providerName = "netdania_fxa";
            String instrumentName = "USD/JPY";
            chartViewCommands.addOverlayInstrumentAsync(new NDChartInstrument(instrumentName, instrumentSymbol, providerName, NDChartInstrumentType.FOREX, NDChartField.BID, 5, 5));
        } else if (REMOVE_OVERLAY_USDJPY.equals(item)) {
            String instrumentSymbol = "USDJPY";
            String providerName = "netdania_fxa";
            String instrumentName = "USD/JPY";
            chartViewCommands.removeOverlayInstrumentAsync(new NDChartInstrument(instrumentName, instrumentSymbol, providerName, NDChartInstrumentType.FOREX, NDChartField.BID, 5, 5));
        } else if (EDIT_OVERLAY_USDJPY.equals(item)) {
            String instrumentSymbol = "USDJPY";
            String providerName = "netdania_fxa";
            String instrumentName = "USD/JPY";
            chartViewCommands.startEditingOverlayInstrument(new NDChartInstrument(instrumentName, instrumentSymbol, providerName, NDChartInstrumentType.FOREX, NDChartField.BID, 5, 5));
        } else if (FINISH_EDITING_OVERLAY.equals(item)) {
            chartViewCommands.finishEditingOverlayObject();
        } else if (SHOW_CROSSHAIR.equals(item)) {
            chartViewCommands.showCrossHair();
        } else if (HIDE_CROSSHAIR.equals(item)) {
            chartViewCommands.hideCrossHair();
        } else if (BLACK_THEME.equals(item)) {
            changeTheme("Black");
        } else if (WHITE_THEME.equals(item)) {
            changeTheme("White");
        } else if (BLUE_THEME.equals(item)) {
            changeTheme("Blue");
        } else if (GRAY_THEME.equals(item)) {
            changeTheme("Gray");
        } else if (SELECT_STUDY.equals(item)) {
            List<NDChartStudyObject> studiesOnChart = chartViewCommands.getStudiesOnChart();
            if (studiesOnChart == null || studiesOnChart.size() == 0) {
                //Toast.makeText(getContext(), "There is no study on chart to select.", Toast.LENGTH_LONG).show();
            } else {
                NDChartStudyObject ndChartStudyObject = studiesOnChart.get(0);
                chartViewCommands.selectStudy(ndChartStudyObject);
            }
        } else if (SELECT_LINE.equals(item)) {
            List<NDChartTrendLineObject> trendlinesOnChart = chartViewCommands.getTrendlinesOnChart();
            if (trendlinesOnChart == null || trendlinesOnChart.size() == 0) {
                // Toast.makeText(getContext(), "There is no line on chart to select.", Toast.LENGTH_LONG).show();
            } else {
                chartViewCommands.selectLine(trendlinesOnChart.get(0));
            }
        } else if (SELECT_FIBONACCI.equals(item)) {
            List<NDChartFibonacciObject> fibonacciLinesOnChart = chartViewCommands.getFibonacciLinesOnChart();
            if (fibonacciLinesOnChart == null || fibonacciLinesOnChart.size() == 0) {
                // Toast.makeText(getContext(), "There is no fibonacci line on chart to select.", Toast.LENGTH_LONG).show();
            } else {
                chartViewCommands.selectFibonacciLine(fibonacciLinesOnChart.get(0));
            }
        } else if (SELECT_OVERLAY.equals(item)) {
            List<NDChartOverlayObject> overlayInstrumentsOnChart = chartViewCommands.getOverlayInstrumentsOnChart();
            if (overlayInstrumentsOnChart == null || overlayInstrumentsOnChart.size() == 0) {
                //Toast.makeText(getContext(), "There is no overlay instrument on chart to select.", Toast.LENGTH_LONG).show();
            } else {
                chartViewCommands.selectOverlayObject(overlayInstrumentsOnChart.get(0));
            }
        } else if (GET_TIMESCALE.equals(item)) {
            // Toast.makeText(getContext(), "Timescale: " + chartViewCommands.getTimeScale() + " seconds", Toast.LENGTH_LONG).show();
        } else if (GET_CHART_TYPE.equals(item)) {
            String chartType = "";
            switch (chartViewCommands.getChartType()) {
                case LINE_CHART:
                    chartType = "Line";
                    break;
                case OHLC:
                    chartType = "OHLC";
                case CANDLE_STICK:
                    chartType = "Normal candlestick";
                case LINE_DOT:
                    chartType = "Line dot";
                case HLC:
                    chartType = "HLC";
                case HEIKIN_ASHI:
                    chartType = "Heikin Ashi";

                default:
                    break;
            }
            // Toast.makeText(getContext(), "Chart type: " + chartType, Toast.LENGTH_LONG).show();
        } else if (START_ADDING_ANALYSIS_UI.equals(item)) {

            chartViewCommands.startAddingAnalysisUI();

        } else if (SELECT_ANALYSIS.equals(item)) {

            List<NDChartAnalysisObject> displayedAnalyses = chartViewCommands.getDisplayedAnalyses();
            if (!displayedAnalyses.isEmpty()) {
                chartViewCommands.selectAnalysis(displayedAnalyses.get(0));
            }

        } else if (SHOW_ANALYSIS_STORY.equals(item)) {

            if (selectedChartObject instanceof NDChartAnalysisObject) {
                chartViewCommands.showAnalysisStoryUI((NDChartAnalysisObject) selectedChartObject);
            } else {
                for (NDChartObject o : addedFromUIChartObject) {
                    if (o instanceof NDChartAnalysisObject) {
                        chartViewCommands.showAnalysisStoryUI((NDChartAnalysisObject) o);
                        break;
                    }
                }
            }

        } else if (REMOVE_ANALYSIS.equals(item)) {

            List<NDChartAnalysisObject> displayedAnalyses = chartViewCommands.getDisplayedAnalyses();
            if (!displayedAnalyses.isEmpty()) {
                chartViewCommands.deleteChartObject(displayedAnalyses.get(0));
            }

        } else if (PLACE_TOOLBAR_DOWN.equals(item)) {

            NDChartToolbarLocation ndChartToolbarLocation = new NDChartToolbarLocation();
            ndChartToolbarLocation.setToolbarLocation(NDChartToolbarLocation.SHOW_TOOLBAR_BELOW);
            this.chartView.setToolbarPosition(ndChartToolbarLocation);

        } else if (PLACE_TOOLBAR_UP.equals(item)) {

            NDChartToolbarLocation ndChartToolbarLocation = new NDChartToolbarLocation();
            ndChartToolbarLocation.setToolbarLocation(NDChartToolbarLocation.SHOW_TOOLBAR_ABOVE);
            this.chartView.setToolbarPosition(ndChartToolbarLocation);

        } else if (ADD_LAST_ANALYSIS.equals(item)) {

            chartViewCommands.addLastAnalysis(NDChartAnalysisTimeFrame.INTRADAY);

        } else if (REMOVE_LAST_ANALYSIS.equals(item)) {

            chartViewCommands.removeLastAnalysis(NDChartAnalysisTimeFrame.INTRADAY);

        } else if (GET_DISPLAYED_ANALYSES.equals(item)) {

            StringBuilder sb = new StringBuilder();
            List<NDChartAnalysisObject> displayedAnalyses = chartViewCommands.getDisplayedAnalyses();
            for (NDChartAnalysisObject displayedAnalysis : displayedAnalyses) {
                sb.append(displayedAnalysis.getName());
                sb.append("\n");
            }
            Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();

        } else if (SELECT_ANALYSES_LANGUAGE.equals(item)) {

            chartViewCommands.setAnalysesLanguage("FR");

        } else if (FIT_CHART_Y.equals(item)) {

            chartViewCommands.fitChartVertically();

        } else if (GO_LAST_INDEX.equals(item)) {

            chartViewCommands.goToLastBar();

        }

    }

    private void changeTheme(String themeName) {
        if (changeThemeTask != null) {
            changeThemeTask.stopRunning();
        }
        changeThemeTask = new ChangeThemeTask(NDChartAPI.getInstance(), themeName);
        changeThemeTask.executeAsync(getBaseApplication().getThreadPool());
    }

    private void handleExportToImageCommand(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent intent = new Intent(this, ChartImageActivity.class);
        intent.putExtra("image", byteArray);
        startActivity(intent);
    }

    private ChartPreferences chartPreferences;

    private ListView chartCommandsListView;
    private NDChartController chartViewCommands;
    private NDChartView chartView;
    boolean showLeftMenu;
    private NDChartInstrument prefferedInstrument = null;
    private ChangeThemeTask changeThemeTask;
    private NDChartObject selectedChartObject;
    private List<NDChartObject> addedFromUIChartObject = new ArrayList<>();
    private List<PriceBar> priceBars = new ArrayList<>();

    private void loadNetdhania() {
        try {

            String url = Constants.stocksCandleStickChart + CF_RIC;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            PriceBarParse pj = new PriceBarParse(response);
                            priceBars = pj.parseJSON();
                            Executor threadPool = getBaseApplication().getThreadPool();
                            LoadInitialConfigurationDataTask task = new LoadInitialConfigurationDataTask(StockInfoActivity.this, threadPool, priceBars) {
                                @Override
                                protected void onPostExecute(Exception exception, NDChartAPI frameworkConfiguration) {
                                    onFinishLoadingConfigurationData(exception, frameworkConfiguration);
                                }
                            };
                            task.executeAsync(threadPool); // start the task

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(StockInfoActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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

    protected FigsApplication getBaseApplication() {
        return (FigsApplication) getApplication();
    }
}
