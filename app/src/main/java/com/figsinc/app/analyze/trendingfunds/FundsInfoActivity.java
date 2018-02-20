package com.figsinc.app.analyze.trendingfunds;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.figsinc.app.analyze.AnalyzeActivity;
import com.figsinc.app.analyze.BarChart.BarChartData;
import com.figsinc.app.analyze.model.CompanyStockDetails;
import com.figsinc.app.analyze.model.FundsOverView;
import com.figsinc.app.analyze.model.FundsTopTenHolding;
import com.figsinc.app.analyze.model.TrendingFunds;
import com.figsinc.app.analyze.sentiments.MyMarkerView;
import com.figsinc.app.calculator.CalculatorActivity;
import com.figsinc.app.collect.CollectActivity;
import com.figsinc.app.learn.LearnActivity;
import com.figsinc.app.learn.news.NewsDetailActivity;
import com.figsinc.app.settings.SettingsActivity;
import com.figsinc.app.utils.FigsPopupActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.figsinc.app.Constants.readyToTradeUrlSbiInsurance;
import static com.figsinc.app.Constants.readyToTradeUrlSbiSecurities;
import static com.figsinc.app.Constants.readyToTradeUrlSumishinNetBank;
import static com.figsinc.app.Constants.saveStocks;

public class FundsInfoActivity extends AppCompatActivity {


    public static final String KEY_EXTRA_FUNDS_LIPPER_NO = "KEY_EXTRA_FUNDS_LIPPER_NO";
    public static final String KEY_EXTRA_CFTICK = "KEY_EXTRA_CFTICK";

    @BindView(R.id.textView_entity_header)
    TextView textView_entity_header;
    @BindView(R.id.textView_entity_subheader)
    TextView textView_entity_subheader;
    @BindView(R.id.textView_entity_subheader_share)
    TextView textView_entity_subheader_share;
    @BindView(R.id.entities_overview)
    TextView entities_overview;
    @BindView(R.id.entities_update_company_description)
    TextView entities_update_company_description;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.button_calculator)
    ImageView button_calculator;
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
    @BindView(R.id.recyclerViewTop10Stocks)
    RecyclerView recyclerViewTop10Stocks;

    @BindView(R.id.imageViewAnalyse)
    ImageView imageViewAnalyse;
    @BindView(R.id.imageViewLearn)
    ImageView imageViewLearn;
    @BindView(R.id.imageViewCollect)
    ImageView imageViewCollect;
    BottomSheetDialog mBottomSheetDialog;
    View sheetView;
    @BindView(R.id.lineChart)
    LineChart lineChart;
    @BindView(R.id.textAnalyze)
    TextView textAnalyze;
    @BindView(R.id.linearShowDetails)
    LinearLayout linearShowDetails;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    private FundsOverView fundsOverView;

    private String lipperNos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funds_info);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        Constants.setStatusBar(FundsInfoActivity.this, R.color.cobalt);
        try {
            lipperNos = getIntent().getStringExtra(KEY_EXTRA_FUNDS_LIPPER_NO);
            System.out.println(" Lipper Nos ==> " + lipperNos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setFooterIcons();
        getDescription();

    }

    private void loadFundsInfoData() {

        setValues();
        setCompanyFundamentalInformations();
        mBottomSheetDialog = new BottomSheetDialog(this);
        sheetView = getLayoutInflater().inflate(R.layout.trade_popup, null);
        mBottomSheetDialog.setContentView(sheetView);
        getPerformance();
        getLipperScorecardDetails();
        networkTopTenFundsHoldings();
        getLineChartDetails();

        LinearLayout liearWeb1 = (LinearLayout) sheetView.findViewById(R.id.liearWeb1);
        LinearLayout liearWeb2 = (LinearLayout) sheetView.findViewById(R.id.liearWeb2);
        LinearLayout liearWeb3 = (LinearLayout) sheetView.findViewById(R.id.liearWeb3);
        liearWeb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FundsInfoActivity.this, NewsDetailActivity.class);
                intent.putExtra("DetailsUrl", readyToTradeUrlSbiInsurance);
                startActivity(intent);

            }
        });

        liearWeb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FundsInfoActivity.this, NewsDetailActivity.class);
                intent.putExtra("DetailsUrl", readyToTradeUrlSbiSecurities);
                startActivity(intent);
            }
        });

        liearWeb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FundsInfoActivity.this, NewsDetailActivity.class);
                intent.putExtra("DetailsUrl", readyToTradeUrlSumishinNetBank);
                startActivity(intent);
            }
        });
    }

    class FundsInfo extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    private void setCompanyFundamentalInformations() {

        List<String> fundamentalInfoList = Arrays.asList(getResources().getStringArray(R.array.Funds_Information_));
        FundsInformationListAdapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;

        RecyclerView recyclerViewFilterListOne = (RecyclerView) findViewById(R.id.filter1_recyclerView);
        mAdapter = new FundsInformationListAdapter(fundamentalInfoList, this);
        recyclerViewFilterListOne.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewFilterListOne.setLayoutManager(mLayoutManager);
        recyclerViewFilterListOne.setNestedScrollingEnabled(false);

        recyclerViewFilterListOne.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewFilterListOne.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFilterListOne.setAdapter(mAdapter);
    }


    private void setFundsTopTenHoldingsAdapter(ArrayList<FundsTopTenHolding> mValues) {

        FundsTopTenHoldingsRecyclerViewAdapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;

        RecyclerView recyclerViewTop10Stocks = (RecyclerView) findViewById(R.id.recyclerViewTop10Stocks);
        mAdapter = new FundsTopTenHoldingsRecyclerViewAdapter(mValues, this);
        recyclerViewTop10Stocks.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewTop10Stocks.setLayoutManager(mLayoutManager);
        recyclerViewTop10Stocks.setNestedScrollingEnabled(false);
        recyclerViewTop10Stocks.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewTop10Stocks.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTop10Stocks.setAdapter(mAdapter);


    }


    @OnClick(R.id.button_calculator)
    public void calculator(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        //intent.putExtra("CF_TICK", trendingFunds.getCF_TICK());
        startActivity(intent);
    }

    @OnClick(R.id.button_save)
    public void save(View view) {
        Constants.savedToFigs(FundsInfoActivity.this, saveStocks + lipperNos, Request.Method.POST, coordinatorLayout);
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

    private void setValues() {

        toolbarTitle.setText(getResources().getString(R.string.title_Funds_Info));
        textView_entity_header.setText(fundsOverView.getFundsName());

        textView_entity_subheader.setText("" + fundsOverView.getCFLAST());
        textView_entity_subheader.setTextColor(ContextCompat.getColor(this, R.color.greyBlue));

        double pctchng = Double.parseDouble(fundsOverView.getPCTCHNG());
        double netchng = Double.parseDouble("" + fundsOverView.getCFNETCHNG());

        String formatted = String.format("%.2f", new BigDecimal(netchng)) + "(" + String.format("%.2f", new BigDecimal(pctchng)) + "%)";

        if (String.valueOf(fundsOverView.getCFNETCHNG()).startsWith("-")) {
            textView_entity_subheader_share.setTextColor(ContextCompat.getColor(this, R.color.paleRed));
            textView_entity_subheader_share.setText(formatted);
        } else {
            textView_entity_subheader_share.setTextColor(ContextCompat.getColor(this, R.color.greenBlue));
            textView_entity_subheader_share.append("+ ");
            textView_entity_subheader_share.setText(formatted);
        }

        entities_showDetails.setTextColor(ContextCompat.getColor(this, R.color.cobalt));

        // setFirstFilter();
        // setSecondFilter();


    }

    private Gson gson;

    private void getDescription() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(FundsInfoActivity.this);
            String url = Constants.fundInfoDescription + lipperNos;
            // System.out.println(" **********  " + FigsApplication.getAuthToken());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            List<FundsOverView> fundsOverViewList = Arrays.asList(gson.fromJson(response, FundsOverView[].class));
                            fundsOverView = fundsOverViewList.get(0);

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                entities_update_company_description.setText(jsonObject.getString("Overview"));
                                SharedPreferences sharedPref = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("fundInfoDescription", response);
                                editor.commit();

                                if (entities_update_company_description.getLineCount() > 5) {
                                    linearShowDetails.setVisibility(View.VISIBLE);
                                    entities_update_company_description.setMaxLines(5);
                                } else {
                                    linearShowDetails.setVisibility(View.GONE);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            loadFundsInfoData();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // Toast.makeText(FundsInfoActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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
        textAnalyze.setTextColor(ContextCompat.getColor(FundsInfoActivity.this, R.color.cobalt));
        imageViewLearn.setBackground(getResources().getDrawable(R.mipmap.learn_grey));
        imageViewCollect.setBackground(getResources().getDrawable(R.mipmap.bookmark_grey));

    }


    private void getLipperScorecardDetails() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(FundsInfoActivity.this);
            String url = Constants.fundInfoDetails + lipperNos;
            // System.out.println(" **********  " + url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                SharedPreferences sharedPref = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("getLipperScorecardDetails", response);
                                editor.commit();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // Toast.makeText(FundsInfoActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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


    private void getPerformance() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(FundsInfoActivity.this);
            String url = Constants.fundInfoFundsPerfrormance + lipperNos;
            // System.out.println(" **********  " + FigsApplication.getAuthToken());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                SharedPreferences sharedPref = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("fundInfoFundsPerfrormance", response);
                                editor.commit();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // Toast.makeText(FundsInfoActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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

    private void networkTopTenFundsHoldings() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(FundsInfoActivity.this);
            String url = Constants.FundsTopTendHoldings + lipperNos;
            // System.out.println(" **********  " + FigsApplication.getAuthToken());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                setTopTenFundsHoldings(response);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                  //  Toast.makeText(FundsInfoActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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

    private void setTopTenFundsHoldings(final String json) {
        FundsTopTenHoldingParse fundsTopTenHoldingParse = new FundsTopTenHoldingParse(json);
        final ArrayList<FundsTopTenHolding> mValues = fundsTopTenHoldingParse.parseJSON();
        setFundsTopTenHoldingsAdapter(mValues);
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


    private void getLineChartDetails() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(FundsInfoActivity.this);
            String url = Constants.fundsHistory + lipperNos;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            showLineChartJSON(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // Toast.makeText(FundsInfoActivity.this, "\"Error in Chart loading \"", Toast.LENGTH_SHORT).show();
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

    private void showLineChartJSON(String json) {
        FundsInfoChartParse pj = new FundsInfoChartParse(json);
        ArrayList<BarChartData> lineChartArrayList = pj.parseJSON();
        System.out.println("================ > " + lineChartArrayList.size());
        LineData data = getData(lineChartArrayList, R.color.com_facebook_blue);
        setupChart(lineChart, data);
    }

    private void setupChart(LineChart chart, LineData data) {

        chart.setDrawGridBackground(false);

        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);
        chart.setNoDataText("No chart data available");

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(FundsInfoActivity.this, R.layout.custom_marker_view);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setEnabled(true);
        chart.getXAxis().setEnabled(false);
        chart.getAxisLeft().setDrawLimitLinesBehindData(false);
        chart.getAxisLeft().enableGridDashedLine(5f, 5f, 0f);

        // add data
        chart.setData(data);
        // chart.getLegend().setEnabled(false);
        chart.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.DEFAULT);
    }

    private LineData getData(ArrayList<BarChartData> lineChart, int color) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < lineChart.size(); i++) {
            yVals.add(new Entry(i, Float.parseFloat(lineChart.get(i).getValue()),lineChart.get(i).getLabel()));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "NAV- Last 24 months");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        set1.setLineWidth(1.75f);
        set1.setCircleRadius(3f);
        set1.setCircleHoleRadius(1.5f);
        set1.setColor(ContextCompat.getColor(FundsInfoActivity.this, color));
        set1.setCircleColor(Color.GRAY);
        set1.setDrawCircleHole(false);
        set1.setDrawCircles(false);
        set1.setHighLightColor(ContextCompat.getColor(FundsInfoActivity.this, R.color.tealish));
        set1.setDrawValues(false);

        // create a data object with the datasets
        LineData data = new LineData(set1);

        return data;
    }

}
