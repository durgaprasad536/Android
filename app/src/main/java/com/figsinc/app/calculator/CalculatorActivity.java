package com.figsinc.app.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.figsinc.app.Constants;
import com.figsinc.app.FigsApplication;
import com.figsinc.app.R;
import com.figsinc.app.analyze.AnalyzeActivity;
import com.figsinc.app.analyze.SmallListAdapter;
import com.figsinc.app.analyze.trendingstocks.CapitalStructureListViewBarChartActivity;
import com.figsinc.app.analyze.trendingstocks.EfficiencyListViewBarChartActivity;
import com.figsinc.app.analyze.trendingstocks.ProfitabilityListViewBarChartActivity;
import com.figsinc.app.analyze.trendingstocks.ValuationListViewBarChartActivity;
import com.figsinc.app.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.figsinc.app.analyze.trendingstocks.ValuationListViewBarChartActivity.KEY_TICKER_EXCHANGE;

public class CalculatorActivity extends AppCompatActivity {

    @BindView(R.id.tvReturnsNumber)
    TextView tvReturnsNumber;
    @BindView(R.id.seekBarReturns)
    SeekBar seekBarReturns;

    @BindView(R.id.tvMonthsNumber)
    TextView tvMonthsNumber;
    @BindView(R.id.seekBarMonths)
    SeekBar seekBarMonths;
    @BindView(R.id.editTextStockName)
    AutoCompleteTextView editTextStockName;
    @BindView(R.id.spinner_SelectRegion)
    Spinner spinner_SelectRegion;

    public static final String KEY_EXPECTING_RETURNS = "KEY_EXPECTING_RETURNS";
    public static final String KEY_INVESTING_MONTHS = "KEY_INVESTING_MONTHS";

    public static final String KEY_CF_TICK = "CF_TICK";

    private String CF_TICK = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        Constants.setStatusBar(this, R.color.cobalt);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.cobalt));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CF_TICK = getIntent().getStringExtra("CF_TICK");

        editTextStockName.setText(CF_TICK);
        editTextStockName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                network(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextStockName.setOnEditorActionListener(new DoneOnEditorActionListener());
        editTextStockName.setImeActionLabel("Done", KeyEvent.KEYCODE_ENTER);
        seekBarReturns.setMax(20);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBarReturns.setMin(1);
        }
        // seekBarMonths.setMax(12);
        // seekBarMonths.setProgress(3);

        seekBarReturns.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 1;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress==0){
                    progress=1;
                }
                progressChangedValue = progress;
                tvReturnsNumber.setText("" + progressChangedValue + " %");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // get progress value from the Seek bar

            }
        });

        final int step = 3;
        final int max = 12;
        final int min = 3;
        seekBarMonths.setMax((max - min) / step);
        tvMonthsNumber.setText(min + " Months");

        seekBarMonths.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 3;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               /* progressChangedValue = progress;
                if (progressChangedValue % 3 == 0) {
                    tvMonthsNumber.setText(""+progressChangedValue +"Months");
                }*/

                int value = min + (progress * step);
                tvMonthsNumber.setText("" + value + " Months");
                // System.out.println(" ####### " + value);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // get progress value from the Seek bar

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Arrays.asList(getResources().getStringArray(R.array.calculator_regions)));
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner_SelectRegion.setAdapter(adapter);

    }

    private void network( String input) {
        int regionID = 0;
        switch (spinner_SelectRegion.getSelectedItem().toString()) {
            case "All":
                regionID = 0;
                break;
            case "Singapore":
                regionID = 3;
                break;
            case "Japan":
                regionID = 2;
                break;
            case "Hong Kong":
                regionID = 4;
                break;
            case "U.S":
                regionID = 1;
                break;
            case "Indonesia":
                regionID = 7;
                break;
            case "Thailand":
                regionID = 9;
                break;
            case "Taiwan":
                regionID = 5;
                break;
        }
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(CalculatorActivity.this);
            String url = "";
            input= URLEncoder.encode(input);
            if (regionID == 0) {
                url = Constants.calculatorCompanyListAutoFill + input;
            } else {
                url = Constants.calculatorCompanyListAutoFill + input + "&region_id=" + regionID;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                            final TickerParse tickerParse = new TickerParse(response);
                            tickerParse.parseJSON();
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CalculatorActivity.this, android.R.layout.simple_list_item_1, tickerParse.getCompanyList());
                            editTextStockName.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            editTextStockName.setThreshold(1);
                            editTextStockName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    CF_TICK = tickerParse.getTickerExchangeList().get(position);
                                }
                            });

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CalculatorActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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


    @OnClick(R.id.buttonCalculate)
    public void calculate(View view) {
        // TODO Navigation...
        if (!editTextStockName.getText().toString().trim().isEmpty()) {
            String returns = tvReturnsNumber.getText().toString().trim().replace("%", "");
            Intent intent = new Intent(CalculatorActivity.this, ResultsCalculatorActivity.class);
            intent.putExtra(KEY_EXPECTING_RETURNS, returns.trim());
            String months = tvMonthsNumber.getText().toString().trim().replace("Months", "");
            intent.putExtra(KEY_INVESTING_MONTHS, months.trim());
            intent.putExtra(KEY_CF_TICK, CF_TICK);
            startActivity(intent);
        } else {
            editTextStockName.setError("This field is required.");
        }
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

    class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        }
    }


}
