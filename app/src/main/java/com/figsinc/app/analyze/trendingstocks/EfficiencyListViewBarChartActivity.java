package com.figsinc.app.analyze.trendingstocks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.figsinc.app.analyze.sentiments.MyMarkerView;
import com.figsinc.app.collect.CollectActivity;
import com.figsinc.app.learn.LearnActivity;
import com.figsinc.app.settings.SettingsActivity;
import com.figsinc.app.utils.FigsPopupActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.figsinc.app.analyze.trendingstocks.ValuationListViewBarChartActivity.KEY_TICKER_EXCHANGE;

/**
 * Demonstrates the use of charts inside a ListView. IMPORTANT: provide a
 * specific height attribute for the chart inside your listview-item
 *
 * @author Rajesh K
 */
public class EfficiencyListViewBarChartActivity extends AppCompatActivity {

    private ArrayList<ArrayList<BarChartData>> listOLists = new ArrayList<ArrayList<BarChartData>>();

    private ArrayList<ArrayList<String>> xAxisLabels = new ArrayList<ArrayList<String>>();
    private ArrayList<String> headersList=new ArrayList<>();
    private String tickerExchange;

    private final ArrayList<String> listHeadersList =new ArrayList<>();

    @BindView(R.id.imageViewAnalyse)
    ImageView imageViewAnalyse;
    @BindView(R.id.imageViewLearn)
    ImageView imageViewLearn;
    @BindView(R.id.imageViewCollect)
    ImageView imageViewCollect;
    @BindView(R.id.textAnalyze)
    TextView textAnalyze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_listview_chart);
        ButterKnife.bind(this);
        headersList.add("Current_Ratio");
        headersList.add("Asset_Turnover");
        headersList.add("Receivables_Turnover");
        headersList.add("Payables_Turnover");

        listHeadersList.add("Current Ratio");
        listHeadersList.add("Asset Turnover");
        listHeadersList.add("Inventory Turnover");
        listHeadersList.add("Quick Ratio");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tickerExchange = getIntent().getStringExtra(KEY_TICKER_EXCHANGE);

        network();
        setFooterIcons();
    }

    private class ChartDataAdapter extends ArrayAdapter<BarData> {
        ArrayList<String> lableItem = new ArrayList<>();

        public ChartDataAdapter(Context context, List<BarData> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            BarData data = getItem(position);
            lableItem = xAxisLabels.get(position);
            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();

                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.activity_barchart, null);
                holder.chart = (BarChart) convertView.findViewById(R.id.chart1);
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.emptychart = (LinearLayout) convertView.findViewById(R.id.emptychart);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textView.setText(listHeadersList.get(position));
            // apply styling

            data.setValueTextColor(Color.BLACK);
            holder.chart.getDescription().setEnabled(false);
            holder.chart.setDrawGridBackground(false);

            // create a custom MarkerView (extend MarkerView) and specify the layout
            // to use for it
            MyMarkerView mv = new MyMarkerView(EfficiencyListViewBarChartActivity.this, R.layout.custom_marker_view);
            mv.setChartView( holder.chart); // For bounds control
            holder.chart.setMarker(mv); // Set the marker to the chart

            XAxis xAxis = holder.chart.getXAxis();
            xAxis.setPosition(XAxisPosition.BOTTOM);
            xAxis.setLabelCount(5);
            xAxis.setDrawGridLines(false);
            xAxis.setValueFormatter(new IndexAxisValueFormatter() {

                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    System.out.println(value);

                    if (((int) value) <= lableItem.size()) {
                        return (lableItem.get((int) value));
                    } else {
                        return "";
                    }
                }
            });

            YAxis leftAxis = holder.chart.getAxisLeft();
            leftAxis.setLabelCount(5, false);
            leftAxis.setSpaceTop(15f);
            leftAxis.setDrawZeroLine(true);
            leftAxis.setStartAtZero(true);

            YAxis rightAxis = holder.chart.getAxisRight();
            rightAxis.setEnabled(false);

            if (data.getEntryCount() == 0) {
                holder.chart.setNoDataText(getResources().getString(R.string.error_no_data));
                holder.chart.setVisibility(View.GONE);
                holder.emptychart.setVisibility(View.VISIBLE);
            } else {
                // set data
                holder.chart.setData(data);
                holder.chart.setFitBars(true);
                try {
                    holder.chart.getBarData().setDrawValues(false);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                holder.chart.setPinchZoom(false);
                holder.chart.getLegend().setEnabled(false);
                holder.chart.setClickable(false);
                holder.chart.setDoubleTapToZoomEnabled(false);
                // do not forget to refresh the chart
                // holder.chart.invalidate();
                holder.chart.animateY(1000);

            }
            return convertView;
        }

        private class ViewHolder {

            BarChart chart;
            TextView textView;
            LinearLayout emptychart;
        }
    }

    /**
     * generates a  ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateData(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        ArrayList<String> xAxisListItemLables = new ArrayList<>();
        for (int i = 0; i < listOLists.get(cnt).size(); i++) {
            entries.add(new BarEntry(i, Float.parseFloat(listOLists.get(cnt).get(i).getValue())));
            xAxisListItemLables.add(i, listOLists.get(cnt).get(i).getLabel());
        }
        xAxisLabels.add(cnt, xAxisListItemLables);
        BarDataSet d = new BarDataSet(entries, /*headersList.get(cnt)*/"");
        d.setColor(ContextCompat.getColor(this, R.color.cobalt));
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.5f);
        return cd;
    }

    private void network() {
        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(EfficiencyListViewBarChartActivity.this);
            String url = Constants.stocksFundamentalInformationEfficiency + tickerExchange;

           // System.out.println(" **********  " + FigsApplication.getAuthToken());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(SectorDetailsActivity.this, response, Toast.LENGTH_SHORT).show();
                            showJSON(response);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EfficiencyListViewBarChartActivity.this, "\"That didn't work!\"", Toast.LENGTH_SHORT).show();
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
        StocksBarChartDataParse pj = new StocksBarChartDataParse(json,headersList);
        listOLists = pj.parseJSON();
        ListView lv = (ListView) findViewById(R.id.listView1);

        ArrayList<BarData> list = new ArrayList<BarData>();

        for (int i = 0; i < headersList.size(); i++) {
            list.add(generateData(i));
        }

        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
        lv.setAdapter(cda);
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
        textAnalyze.setTextColor(ContextCompat.getColor(EfficiencyListViewBarChartActivity.this,R.color.cobalt));
        imageViewLearn.setBackground(getResources().getDrawable(R.mipmap.learn_grey));
        imageViewCollect.setBackground(getResources().getDrawable(R.mipmap.bookmark_grey));
    }
}
