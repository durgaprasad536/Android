package com.figsinc.app.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
import com.figsinc.app.utils.CircularProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.figsinc.app.calculator.CalculatorActivity.KEY_EXPECTING_RETURNS;
import static com.figsinc.app.calculator.CalculatorActivity.KEY_INVESTING_MONTHS;

public class ResultsCalculatorActivity extends AppCompatActivity {

    @BindView(R.id.circular_progress_bar)
    CircularProgressBar circular_progress_bar;
    @BindView(R.id.progress_count) TextView progress_count;
    @BindView(R.id.entities_calculator_result)TextView entities_calculator_result;

    private int progressStatus = 0;
    private Handler handler = new Handler();

    private String expectingReturns;
    private String investingMonths;
    private String CF_TICK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        Constants.setStatusBar(this,R.color.darkish_blue);
        toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.darkish_blue));

        CF_TICK = getIntent().getStringExtra("CF_TICK");

        expectingReturns = getIntent().getStringExtra(KEY_EXPECTING_RETURNS);

        investingMonths = getIntent().getStringExtra(KEY_INVESTING_MONTHS);
        entities_calculator_result.setText(
                String.format(getResources().getString(R.string.result_calculator),
                        0+"%",expectingReturns+"%",Integer.parseInt(investingMonths)));
        getDescription();

    }

    @OnClick(R.id.buttonReset)
    public void reset() {
        finish();
    }

    private void getDescription() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(ResultsCalculatorActivity.this);
            String url = Constants.calculator+"Region="+1+"&Stock="+CF_TICK;


            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                CF_TICK = jsonObject.getString("ticker_exchange");
                                getCalculations();

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ResultsCalculatorActivity.this, "No data is available", Toast.LENGTH_SHORT).show();
                    entities_calculator_result.setText(" No data is available ");
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

    private void getCalculations() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(ResultsCalculatorActivity.this);
            String url = Constants.finalCalculatorValues+CF_TICK+"."+investingMonths.trim()+"M"+"."+expectingReturns;

           // System.out.println(" **********  " + url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                          //  System.out.println(" **********  " + response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                setValues(Float.parseFloat( jsonObject.getString("probability")));

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ResultsCalculatorActivity.this, "No data is available", Toast.LENGTH_SHORT).show();
                    entities_calculator_result.setText(" No data is available ");
                   // System.out.println(" **********  " + error);
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

    private void setValues(final float progressResponseValue){

        final int animationDuration = 50; // 2500ms = 2,5s

        // Math.round(Float.parseFloat(SectorList.get(position).getPotential_returns()))

        // Set the progress status zero on each button click
        progressStatus = 0;
        // Start the lengthy operation in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < progressResponseValue){
                    // Update the progress status
                    progressStatus +=5;

                    // Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(animationDuration);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            circular_progress_bar.setProgressWithAnimation(progressStatus,animationDuration);
                            progress_count.setText(progressResponseValue+"%");// Show the progress on TextView
                            entities_calculator_result.setText(
                                    String.format(getResources().getString(R.string.result_calculator),
                                            progressResponseValue+"%",expectingReturns+"%",Integer.parseInt(investingMonths)));

                        }

                    });
                }
            }
        }).start(); // Sta

    }

}
