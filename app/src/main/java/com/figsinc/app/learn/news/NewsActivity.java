package com.figsinc.app.learn.news;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

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
import com.figsinc.app.learn.Model.News;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.figsinc.app.learn.sector.SectorDetailsSubActivity.KEY_COLORCODE;

public class NewsActivity extends AppCompatActivity {

    private NewsAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private String keyWord="";
    public static final String KEY_NEWS_KEYWORD="KEY_NEWS_KEYWORD";
    private int colorCode=R.color.cobalt;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(colorCode));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            keyWord = bundle.getString(KEY_NEWS_KEYWORD);
            colorCode = bundle.getInt(KEY_COLORCODE,0);
            toolbar.setBackgroundColor(ContextCompat.getColor(NewsActivity.this,colorCode));
            //toolbar.setTitle(keyWord);
            toolbarTitle.setText(keyWord);
        }
        Constants.setStatusBar(NewsActivity.this,colorCode);
        network();

    }


    private void listBind(ArrayList<News> sectorArrayList){
        recyclerView = (RecyclerView) findViewById(R.id.news_recyclerView);
        mAdapter = new NewsAdapter(sectorArrayList, NewsActivity.this);
        //recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(NewsActivity.this.getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(NewsActivity.this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void network() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(NewsActivity.this);
            String url = Constants.newsAPI+keyWord;

           // System.out.println(" **********  " + FigsApplication.getAuthToken());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                            //System.out.println(" **********  " + response);
                            showJSON(response);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(NewsActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showJSON(String json){
        NewsParse pj = new NewsParse(json);
        listBind(pj.parseJSON());
        // CustomList cl = new CustomList(this, ParseJSON.ids,ParseJSON.names,ParseJSON.emails);
        //listView.setAdapter(cl);
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
