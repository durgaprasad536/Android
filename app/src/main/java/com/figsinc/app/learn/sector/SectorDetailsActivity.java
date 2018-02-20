package com.figsinc.app.learn.sector;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
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
import com.figsinc.app.calculator.CalculatorActivity;
import com.figsinc.app.collect.CollectActivity;
import com.figsinc.app.learn.Model.Profile;
import com.figsinc.app.learn.news.NewsActivity;
import com.figsinc.app.learn.news.NewsDetailActivity;
import com.figsinc.app.settings.SettingsActivity;
import com.figsinc.app.utils.FigsPopupActivity;
import com.figsinc.app.utils.RoundedCornersTransformation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.figsinc.app.Constants.SHOW_DETAILS_MAX_LINES;
import static com.figsinc.app.Constants.readyToTradeUrlSbiInsurance;
import static com.figsinc.app.Constants.readyToTradeUrlSbiSecurities;
import static com.figsinc.app.Constants.readyToTradeUrlSumishinNetBank;
import static com.figsinc.app.Constants.saveSector;
import static com.figsinc.app.learn.news.NewsActivity.KEY_NEWS_KEYWORD;
import static com.figsinc.app.learn.sector.SectorDetailsSubActivity.KEY_COLORCODE;

public class SectorDetailsActivity extends AppCompatActivity {

    private List<String> filterListOne = new ArrayList<>();
    private List<String> filterListTwo = new ArrayList<>();
    private String Id;
    private String imageUrl = "";
    private String apiUrl = "";
    private int colorCode = 0;

    @BindView(R.id.textView_entity_header)
    TextView textView_entity_header;
    @BindView(R.id.textView_entity_subheader)
    TextView textView_entity_subheader;
    //@BindView(R.id.entities_overview) TextView entities_overview;
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

    @BindView(R.id.imageViewAnalyse)
    ImageView imageViewAnalyse;
    @BindView(R.id.imageViewLearn)
    ImageView imageViewLearn;
    @BindView(R.id.imageViewCollect)
    ImageView imageViewCollect;
    @BindView(R.id.entities_showDetails)
    TextView entities_showDetails;
    @BindView(R.id.textLearn)
    TextView textLearn;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.linearShowDetails)
    LinearLayout linearShowDetails;

    BottomSheetDialog mBottomSheetDialog;
    View sheetView;

    @OnClick(R.id.button_calculator)
    public void calculator(View view) {
        Intent intent = new Intent(SectorDetailsActivity.this, CalculatorActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_save)
    public void save(View view) {
     Constants.savedToFigs(SectorDetailsActivity.this, saveSector + sectorArrayList.get(0).getSub_industry_code(), Request.Method.POST,coordinatorLayout);
    }

    @OnClick(R.id.button_forward)
    public void forward(View view) {
        Intent intent = new Intent(SectorDetailsActivity.this, CalculatorActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_dollar)
    public void dollar(View view) {
        mBottomSheetDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_new);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Id = getIntent().getStringExtra("id");
        imageUrl = getIntent().getStringExtra("imageUrl");
        apiUrl = getIntent().getStringExtra("apiUrl");
        colorCode = getIntent().getIntExtra("colorCode", 0);

        System.out.println(" sectorId ==>> " + Id);

        filterListOne.add("Stocks");
        filterListOne.add("Funds");

        filterListTwo.add("Related Stocks");
        filterListTwo.add("Stock Holdings");
        filterListTwo.add("Other Ideas");
        filterListTwo.add("More News");
        network();
        Constants.setStatusBar(this, colorCode);
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

                Intent intent = new Intent(SectorDetailsActivity.this, NewsDetailActivity.class);
                intent.putExtra("DetailsUrl", readyToTradeUrlSbiInsurance);
                startActivity(intent);

            }
        });

        liearWeb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectorDetailsActivity.this, NewsDetailActivity.class);
                intent.putExtra("DetailsUrl", readyToTradeUrlSbiSecurities);
                startActivity(intent);
            }
        });

        liearWeb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SectorDetailsActivity.this, NewsDetailActivity.class);
                intent.putExtra("DetailsUrl", readyToTradeUrlSumishinNetBank);
                startActivity(intent);
            }
        });


    }

    private void setFirstFilter() {
        FilterAdapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;

        RecyclerView recyclerViewFilterListOne = (RecyclerView) findViewById(R.id.filter1_recyclerView);
        mAdapter = new FilterAdapter(filterListOne, this);
        recyclerViewFilterListOne.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewFilterListOne.setLayoutManager(mLayoutManager);
        recyclerViewFilterListOne.setNestedScrollingEnabled(false);

        recyclerViewFilterListOne.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewFilterListOne.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFilterListOne.setAdapter(mAdapter);
    }

    private void setSecondFilter() {
        SectorStaticListAdapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;

        RecyclerView recyclerViewFilterListTwo = (RecyclerView) findViewById(R.id.filter2_recyclerView);
        mAdapter = new SectorStaticListAdapter(filterListTwo, this, sectorArrayList.get(0).getSub_industry_code(), sectorArrayList.get(0).getName());
        recyclerViewFilterListTwo.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewFilterListTwo.setLayoutManager(mLayoutManager);
        recyclerViewFilterListTwo.setNestedScrollingEnabled(false);

        recyclerViewFilterListTwo.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewFilterListTwo.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFilterListTwo.setAdapter(mAdapter);
    }

    private void network() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(SectorDetailsActivity.this);
            String url = apiUrl + Id;

            //System.out.println(" **********  " + FigsApplication.getAuthToken());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                           // Toast.makeText(SectorDetailsActivity.this, response, Toast.LENGTH_SHORT).show();
                            showJSON(response);
                            //System.out.println(" **********  " + response);
                           // Log.d("Description", response);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SectorDetailsActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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
        SectorDetailsParse sectorDetailsParse = new SectorDetailsParse(json);
        sectorArrayList = sectorDetailsParse.parseJSON();
        setValues();
    }

    ArrayList<Profile> sectorArrayList = new ArrayList<>();

    private void setValues() {
        String url = imageUrl + sectorArrayList.get(0).getImage_ios() + ".jpeg";
       /*// Glide.with(SectorDetailsActivity.this).load(url).into(fab);
        Glide.with(SectorDetailsActivity.this)
                .load(url)
                //.apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.signatureOf(new BorderTransformation(SectorDetailsActivity.this)))
                .into(fab);*/
        // Rounded corners with border
        Glide.with(this).load(url)
                .apply(RequestOptions.bitmapTransform(
                        new RoundedCornersTransformation(this, sCorner, sMargin, sColor, sBorder)))
                .into(fab);

        toolbarTitle.setText(sectorArrayList.get(0).getName());
        //System.out.println(" **********  " + sectorArrayList.get(0).getDescription());
        entities_update_company_description.setText(Html.fromHtml(sectorArrayList.get(0).getDescription()));

       if( entities_update_company_description.getLineCount()>5){
           linearShowDetails.setVisibility(View.VISIBLE);
       }else{
           linearShowDetails.setVisibility(View.GONE);
       }
        textView_entity_header.setText(sectorArrayList.get(0).getName());

        double potentialReturns = Double.parseDouble(sectorArrayList.get(0).getPotential_returns());
        textView_entity_subheader.setText(String.format("%.2f", new BigDecimal(potentialReturns)) + "% Potential Returns");

        // entities_overview.setText(sectorArrayList.get(0).getPotential_returns());

        setFirstFilter();
        setSecondFilter();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.learn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_report) {
            Intent intent = new Intent(this, PdfViewActivity.class);
            intent.putExtra("PDF_URL", sectorArrayList.get(0).getIndustry_report());
            startActivity(intent);
            return true;
        } else if (id == R.id.action_infographic) {
            Intent intent = new Intent(this, PdfViewActivity.class);
            intent.putExtra("PDF_URL", sectorArrayList.get(0).getInfographics_pdf());
            startActivity(intent);
            return true;
        } else if (id == R.id.action_video) {
            Intent intent = new Intent(this, VideoViewActivity.class);
            intent.putExtra("VIDEO_URL", sectorArrayList.get(0).getSector_video());
            intent.putExtra(KEY_COLORCODE, R.color.tealish);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_news) {
            Intent intent = new Intent(this, NewsActivity.class);
            intent.putExtra("VIDEO_URL", sectorArrayList.get(0).getSector_video());
            intent.putExtra(KEY_NEWS_KEYWORD, sectorArrayList.get(0).getName());
            intent.putExtra(KEY_COLORCODE, R.color.tealish);
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static int sCorner = 2;
    public static int sMargin = 1;
    public static int sBorder = 3;
    public static String sColor = "#ffffff";

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
        startActivity(intent);
    }

    @OnClick(R.id.imageViewFigs)
    public void imageViewFigs(View view) {
        Intent intent = new Intent(this, FigsPopupActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.entities_showDetails)
    public void collapse() {

        // (entities_update_company_description.getMaxLines() > SHOW_DETAILS_MAX_LINES)
        {
            if (entities_showDetails.getText().toString().trim().equals(getResources().getString(R.string.title_Show_Details))) {
                entities_showDetails.setText(R.string.title_Show_Less);
                entities_update_company_description.setMaxLines(Integer.MAX_VALUE);
            } else {
                entities_showDetails.setText(getResources().getString(R.string.title_Show_Details));
                entities_update_company_description.setMaxLines(SHOW_DETAILS_MAX_LINES);
            }
        }
    }

    private void setFooterIcons() {
        imageViewAnalyse.setBackground(getResources().getDrawable(R.mipmap.analyse_grey));
        imageViewLearn.setBackground(getResources().getDrawable(R.mipmap.learn_active));
        textLearn.setTextColor(ContextCompat.getColor(SectorDetailsActivity.this, R.color.cobalt));
        imageViewCollect.setBackground(getResources().getDrawable(R.mipmap.bookmark_grey));
    }


}
