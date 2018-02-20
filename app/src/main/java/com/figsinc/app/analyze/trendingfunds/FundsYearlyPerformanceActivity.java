package com.figsinc.app.analyze.trendingfunds;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.figsinc.app.analyze.BarChart.FundsPeformanceBarChartData;
import com.figsinc.app.analyze.sentiments.MyMarkerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.figsinc.app.Constants.PREF_NAME;

public class FundsYearlyPerformanceActivity extends AppCompatActivity {

    BarChart  chart;

    @BindView(R.id.linear_chart)
    LinearLayout linear_chart;
    @BindView(R.id.emptychart)
    LinearLayout emptychart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funds_yearly_performance);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Constants.setStatusBar(FundsYearlyPerformanceActivity.this,R.color.cobalt);

        chart = (BarChart) findViewById(R.id.chart);

        showJSON(getFundInfoFundsPerfrormance());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * generates a  ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateData(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < fundsPeformanceBarChartDatas.size(); i++) {
            //System.out.println( fundsPeformanceBarChartDatas.get(i).getValue() +" @@@@@@@@@@@@ " + fundsPeformanceBarChartDatas.size());
            entries.add(new BarEntry(i, Float.parseFloat(fundsPeformanceBarChartDatas.get(i).getValue())));
        }
        BarDataSet d = new BarDataSet(entries, /*fundsPeformanceBarChartDatas.get(cnt).getDataDate()*/"");
        d.setColor(ContextCompat.getColor(this, R.color.cobalt));
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.5f);

        return cd;
    }
   private ArrayList<FundsPeformanceBarChartData> fundsPeformanceBarChartDatas = new ArrayList<>();
    private void showJSON(String json) {

        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                FundsPeformanceBarChartData fundsPeformanceBarChartData = new FundsPeformanceBarChartData();
                JSONObject  jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.has("DataDate")){
                    fundsPeformanceBarChartData.setDataDate(jsonObject.getString("DataDate"));
                    fundsPeformanceBarChartData.setValue(jsonObject.getString("Value"));
                } else{
                    fundsPeformanceBarChartData.setDataDate(jsonObject.getString("EndDate"));
                    fundsPeformanceBarChartData.setValue(jsonObject.getString("Value"));
                }
                fundsPeformanceBarChartDatas.add(fundsPeformanceBarChartData);
            }
        }catch (JSONException e){
            
        }

        BarData data = generateData(0);

        // apply styling
        data.setValueTextColor(Color.BLACK);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(FundsYearlyPerformanceActivity.this, R.layout.custom_marker_view);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(fundsPeformanceBarChartDatas.size());
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                System.out.println(value);

                if (((int) value) <= fundsPeformanceBarChartDatas.size()) {
                    return (changeDateFormat(fundsPeformanceBarChartDatas.get((int) value).getDataDate()));
                } else {
                    return "";
                }
            }
        });

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        if (data.getEntryCount() == 0) {
            chart.setNoDataText(getResources().getString(R.string.error_no_data));
            linear_chart.setVisibility(View.GONE);
            emptychart.setVisibility(View.VISIBLE);

        } else {
            // set data
            chart.setData(data);
            chart.setFitBars(true);
            chart.setPinchZoom(false);
            chart.getLegend().setEnabled(false);
            chart.setDoubleTapToZoomEnabled(false);
            chart.getBarData().setDrawValues(false);
            // do not forget to refresh the chart
            // holder.chart.invalidate();
            chart.animateY(1000);
        }
    }

    private String changeDateFormat(String date){
        try {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = spf.parse(date);
            spf = new SimpleDateFormat("yyyy");
            date = spf.format(newDate);

            String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            if(date.equals(year)){
                date = "*"+ date;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }


    private String getFundInfoFundsPerfrormance(){
        SharedPreferences shared = FundsYearlyPerformanceActivity.this.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String fundInfoFundsPerfrormance = shared.getString("fundInfoFundsPerfrormance","");
        //System.out.println("(((((((( " + fundInfoFundsPerfrormance);
        return fundInfoFundsPerfrormance;
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
