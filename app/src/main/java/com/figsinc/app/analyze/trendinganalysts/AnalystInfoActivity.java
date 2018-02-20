package com.figsinc.app.analyze.trendinganalysts;

import android.content.Intent;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.figsinc.app.Constants;
import com.figsinc.app.FigsApplication;
import com.figsinc.app.R;
import com.figsinc.app.analyze.AnalyzeActivity;
import com.figsinc.app.analyze.model.TrendingAnalysts;
import com.figsinc.app.analyze.trendingfunds.FundsInfoActivity;
import com.figsinc.app.calculator.CalculatorActivity;
import com.figsinc.app.collect.CollectActivity;
import com.figsinc.app.learn.LearnActivity;
import com.figsinc.app.learn.news.NewsDetailActivity;
import com.figsinc.app.learn.theme.ThemeProfileActivity;
import com.figsinc.app.settings.SettingsActivity;
import com.figsinc.app.settings.SubscriptionActivity;
import com.figsinc.app.utils.FigsPopupActivity;
import com.figsinc.app.utils.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.figsinc.app.Constants.readyToTradeUrlSbiInsurance;
import static com.figsinc.app.Constants.readyToTradeUrlSbiSecurities;
import static com.figsinc.app.Constants.readyToTradeUrlSumishinNetBank;
import static com.figsinc.app.Constants.saveAnalysts;
import static com.figsinc.app.Constants.saveStocks;
import static com.figsinc.app.analyze.trendinganalysts.TrendingAnalystsParse.KEY_analystid;
import static com.figsinc.app.analyze.trendinganalysts.TrendingAnalystsParse.KEY_analystname;
import static com.figsinc.app.analyze.trendinganalysts.TrendingAnalystsParse.KEY_anayst_compay_name;
import static com.figsinc.app.analyze.trendinganalysts.TrendingAnalystsParse.KEY_average_return;
import static com.figsinc.app.analyze.trendinganalysts.TrendingAnalystsParse.KEY_average_time;
import static com.figsinc.app.analyze.trendinganalysts.TrendingAnalystsParse.KEY_figs_analyst_score;
import static com.figsinc.app.analyze.trendinganalysts.TrendingAnalystsParse.KEY_secondsector__name;
import static com.figsinc.app.analyze.trendinganalysts.TrendingAnalystsParse.KEY_topsector__name;

public class AnalystInfoActivity extends AppCompatActivity {


    private String Id;
    private String imageUrl = "";
    private int colorCode = 0;
    private String CF_TICK;

    @BindView(R.id.textView_entity_header)
    TextView textView_entity_header;
    @BindView(R.id.textView_entity_subheader)
    TextView textView_entity_subheader;

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

    //  @BindView(R.id.tvAverage_Price_target)AppCompatTextView tvAverage_Price_target;
    // @BindView(R.id.tvtitle_Potential_Gain)AppCompatTextView tvtitle_Potential_Gain;

    @BindView(R.id.imageViewAnalyse)
    ImageView imageViewAnalyse;
    @BindView(R.id.textAnalyze)
    TextView textAnalyze;
    @BindView(R.id.imageViewLearn)
    ImageView imageViewLearn;
    @BindView(R.id.imageViewCollect)
    ImageView imageViewCollect;
    BottomSheetDialog mBottomSheetDialog;
    View sheetView;

    @BindView(R.id.tvFigsAnalyticsScore)
    AppCompatTextView tvFigsAnalyticsScore;
    @BindView(R.id.tvAverageReturnPerForecast)
    AppCompatTextView tvAverageReturnPerForecast;
    @BindView(R.id.tvAverageTimeforReturn)
    AppCompatTextView tvAverageTimeForReturns;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyst_info);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Constants.setStatusBar(this,R.color.cobalt);
        try {
            Id = getIntent().getStringExtra("AnalystID");
            imageUrl = getIntent().getStringExtra("imageUrl");
        } catch (Exception e) {
            e.printStackTrace();
        }

        network();
        // setCompanyFundamentalInformations();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setFooterIcons();

        mBottomSheetDialog = new BottomSheetDialog(this);
        sheetView = getLayoutInflater().inflate(R.layout.trade_popup, null);
        mBottomSheetDialog.setContentView(sheetView);
        LinearLayout liearWeb1 = (LinearLayout) sheetView.findViewById(R.id.liearWeb1);
        LinearLayout liearWeb2 = (LinearLayout) sheetView.findViewById(R.id.liearWeb2);
        LinearLayout liearWeb3 = (LinearLayout) sheetView.findViewById(R.id.liearWeb3);
        liearWeb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AnalystInfoActivity.this, NewsDetailActivity.class);
                intent.putExtra("DetailsUrl", readyToTradeUrlSbiInsurance);
                startActivity(intent);

            }
        });

        liearWeb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnalystInfoActivity.this, NewsDetailActivity.class);
                intent.putExtra("DetailsUrl", readyToTradeUrlSbiSecurities);
                startActivity(intent);
            }
        });

        liearWeb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnalystInfoActivity.this, NewsDetailActivity.class);
                intent.putExtra("DetailsUrl", readyToTradeUrlSumishinNetBank);
                startActivity(intent);
            }
        });

    }

    private void setStockCoverageList(final ArrayList<ListItem> trendingStocksArrayList) {

        AnalystStocksCoverageRecyclerViewAdapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;
        RecyclerView recyclerViewFilterListOne = (RecyclerView) findViewById(R.id.filter1_recyclerView);
        mAdapter = new AnalystStocksCoverageRecyclerViewAdapter(trendingStocksArrayList, this);
        recyclerViewFilterListOne.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewFilterListOne.setLayoutManager(mLayoutManager);
        recyclerViewFilterListOne.setNestedScrollingEnabled(false);

        recyclerViewFilterListOne.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewFilterListOne.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFilterListOne.setAdapter(mAdapter);
    }


    @OnClick(R.id.button_calculator)
    public void calculator(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra("CF_TICK", CF_TICK);
        startActivity(intent);
    }

    @OnClick(R.id.button_save)
    public void save(View view) {
        Constants.savedToFigs(AnalystInfoActivity.this, saveAnalysts + Id, Request.Method.POST,coordinatorLayout);
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



    private void network() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(AnalystInfoActivity.this);
            String url = Constants.trendingAnalystDetails + Id;

            // System.out.println(" **********  " + FigsApplication.getAuthToken());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                System.out.println(" trendingAnalystDetails  " + response);
                                showJSON(response);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AnalystInfoActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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
        AnalystStocksCoverageParse sectorDetailsParse = new AnalystStocksCoverageParse(json);
        setStockCoverageList(sectorDetailsParse.parseJSON());
        setValues(json);
    }


    private void setValues(String json) {
        try {

            TrendingAnalysts trendingAnalysts = parseJSON(json).get(0);
            Glide.with(this).load(imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(fab);

            textView_entity_header.setText(trendingAnalysts.getAnalystname());
            tvFigsAnalyticsScore.setText(trendingAnalysts.getFigs_analyst_score() + "%");
            tvAverageReturnPerForecast.setText(trendingAnalysts.getAverage_return() + "%");
            tvAverageTimeForReturns.setText(trendingAnalysts.getAverage_time() + "mnth");
            textView_entity_subheader.setText(trendingAnalysts.getAnayst_compay_name());
            //+ "\n"
            if(!trendingAnalysts.getTopsector__name().equals("null")){
                textView_entity_subheader.append("\n");
                textView_entity_subheader.append(trendingAnalysts.getTopsector__name());

            }
            if(!trendingAnalysts.getSecondsector__name().equals("null")){
                textView_entity_subheader.append("/");
                // holder.profile_Country.append("\n");
                textView_entity_subheader.append(trendingAnalysts.getSecondsector__name());

            }

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
    public void figs(View view){
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
        textAnalyze.setTextColor(ContextCompat.getColor(AnalystInfoActivity.this,R.color.cobalt));
        imageViewLearn.setBackground(getResources().getDrawable(R.mipmap.learn_grey));
        imageViewCollect.setBackground(getResources().getDrawable(R.mipmap.bookmark_grey));

    }

    protected ArrayList<TrendingAnalysts> parseJSON(String json) {
        ArrayList<TrendingAnalysts> sectorArrayList = new ArrayList<>();
        try {
            JSONArray sectorArray = new JSONArray(json);
            {
                TrendingAnalysts sentiments = new TrendingAnalysts();
                JSONObject jsonObject = sectorArray.getJSONObject(1);
                sentiments.setAverage_return(jsonObject.getString(KEY_average_return));
                sentiments.setTopsector__name(jsonObject.getString(KEY_topsector__name));
                sentiments.setAverage_time(jsonObject.getString(KEY_average_time));
                sentiments.setAnayst_compay_name(jsonObject.getString(KEY_anayst_compay_name));
                sentiments.setAnalystid(jsonObject.getString(KEY_analystid));
                sentiments.setAnalystname(jsonObject.getString(KEY_analystname));
                sentiments.setFigs_analyst_score(jsonObject.getString(KEY_figs_analyst_score));
                if(jsonObject.has(KEY_secondsector__name)) {
                    sentiments.setSecondsector__name(jsonObject.getString(KEY_secondsector__name));
                }
                sectorArrayList.add(sentiments);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sectorArrayList;
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



}
