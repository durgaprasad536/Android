package com.figsinc.app.analyze.sentiments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.figsinc.app.R;
import com.figsinc.app.analyze.model.Sentiments;
import com.figsinc.app.utils.Techniques;
import com.figsinc.app.utils.YoYo;
import com.github.mikephil.charting.charts.LineChart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SentimentsDetailsActivity extends AppCompatActivity {

    public static final String KEY_SENTIMENTS_DETAILS = "KEY_SENTIMENTS_DETAILS";
    public static final String KEY_FLAG_POSITION = "KEY_FLAG_POSITION";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_three_month_preturn)
    AppCompatTextView text3MPreturn;
    @BindView(R.id.text_six_month_preturn)
    AppCompatTextView text6MPreturn;
    @BindView(R.id.text_nine_month_preturn)
    AppCompatTextView text9MPreturn;
    @BindView(R.id.text_twelve_month_preturn)
    AppCompatTextView text12MPreturn;

    @BindView(R.id.text_three_month_ploss)
    AppCompatTextView text3Mploss;
    @BindView(R.id.text_six_month_ploss)
    AppCompatTextView text6Mploss;
    @BindView(R.id.text_nine_month_ploss)
    AppCompatTextView text9Mploss;
    @BindView(R.id.text_twelve_month_ploss)
    AppCompatTextView text12Mploss;

    @BindView(R.id.text_three_month_rlr)
    AppCompatTextView text3Mrlr;
    @BindView(R.id.text_six_month_rlr)
    AppCompatTextView text6Mrlr;
    @BindView(R.id.text_nine_month_rlr)
    AppCompatTextView text9Mrlr;
    @BindView(R.id.text_twelve_month_rlr)
    AppCompatTextView text12Mrlr;

    @BindView(R.id.entities_Country)
    TextView entitiesCountry;
    @BindView(R.id.button_percent)
    AppCompatButton buttonPercent;

    @BindView(R.id.lineChart)
    LineChart lineChart;

    @BindView(R.id.linear_new_map)
    LinearLayout linear_new_map;

    @BindView(R.id.imageFlag)
    ImageView imageFlag;

    @BindView(R.id.linear_item)
    LinearLayout linear_item;

    @BindView(R.id.imageFlagHK)
    ImageView imageFlagHK;
    @BindView(R.id.imageFlagJapan)
    ImageView imageFlagJapan;
    @BindView(R.id.imageFlagSing)
    ImageView imageFlagSing;
    @BindView(R.id.imageFlagTw)
    ImageView imageFlagTw;
    @BindView(R.id.imageFlagUs)
    ImageView imageFlagUs;
    private ArrayList<Sentiments> models;
    private int position;

    @BindView(R.id.buttonNASDAQ)
    TextView buttonNASDAQ;
    @BindView(R.id.buttonNYSE)
    TextView buttonNYSE;
    @BindView(R.id.linearUnitedSwitch)
    LinearLayout linearUnitedSwitch;

    @BindView(R.id.marker_nasdaq)
    ImageView marker_nasdaq;
    @BindView(R.id.marker_nyse)
    ImageView marker_nyse;
    @BindView(R.id.marker_japan)
    ImageView marker_japan;
    @BindView(R.id.marker_taiwan)
    ImageView marker_taiwan;
    @BindView(R.id.marker_HongKong)
    ImageView marker_HongKong;
    @BindView(R.id.marker_singapore)
    ImageView marker_singapore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentiments_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setAlpha();
        hideMarkers();
        if (getIntent().getExtras() != null) {
            models = getIntent().getExtras().getParcelableArrayList(KEY_SENTIMENTS_DETAILS);
            position = getIntent().getExtras().getInt(KEY_FLAG_POSITION);
            setValues(models, position);
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


    private void hideMarkers() {
        marker_nasdaq.setVisibility(View.GONE);
        marker_nyse.setVisibility(View.GONE);
        marker_japan.setVisibility(View.GONE);
        marker_taiwan.setVisibility(View.GONE);
        marker_HongKong.setVisibility(View.GONE);
        marker_singapore.setVisibility(View.GONE);
    }

    @OnClick(R.id.imageFlagHK)
    public void HK() {
        setAlpha();
        hideMarkers();
        setValues(models, 0);
        linearUnitedSwitch.setVisibility(View.GONE);
        marker_HongKong.setVisibility(View.VISIBLE);
        showAnimation(marker_HongKong);
    }

    @OnClick(R.id.imageFlagJapan)
    public void Japan() {
        setAlpha();
        hideMarkers();
        setValues(models, 1);
        linearUnitedSwitch.setVisibility(View.GONE);
        marker_japan.setVisibility(View.VISIBLE);
        showAnimation(marker_japan);
    }

    @OnClick(R.id.imageFlagUs)
    public void US() {
        setAlpha();
        hideMarkers();
        setValues(models, 2);
        linearUnitedSwitch.setVisibility(View.VISIBLE);
        linearUnitedSwitch.setBackgroundResource(R.drawable.selector_sentiments_unitedstates);
        marker_nasdaq.setVisibility(View.VISIBLE);
        showAnimation(marker_nasdaq);
    }

    @OnClick(R.id.imageFlagSing)
    public void Sing() {
        setAlpha();
        hideMarkers();
        setValues(models, 4);
        linearUnitedSwitch.setVisibility(View.GONE);
        marker_singapore.setVisibility(View.VISIBLE);
        showAnimation(marker_singapore);
    }

    @OnClick(R.id.imageFlagTw)
    public void TW() {
        setAlpha();
        hideMarkers();
        setValues(models, 5);
        linearUnitedSwitch.setVisibility(View.GONE);
        marker_taiwan.setVisibility(View.VISIBLE);
        showAnimation(marker_taiwan);
    }

    private void setAlpha() {
        imageFlagHK.setAlpha(.5f);
        imageFlagJapan.setAlpha(.5f);
        imageFlagUs.setAlpha(.5f);
        imageFlagSing.setAlpha(.5f);
        imageFlagTw.setAlpha(.5f);
    }

    private void showAnimation(final ImageView imageView) {

        YoYo.with(Techniques.BounceInDown)
                .duration(700)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .playOn(imageView);

    }

    @OnClick(R.id.buttonNASDAQ)
    public void nasdaq() {
        hideMarkers();
        setValues(models, 2);
        selectNasdaq();
    }

    private void selectNasdaq(){
        buttonNASDAQ.setBackgroundColor(ContextCompat.getColor(this, R.color.darkish_blue));
        buttonNASDAQ.setTextColor(Color.WHITE);
        buttonNYSE.setTextColor(ContextCompat.getColor(this, R.color.darkish_blue));
        buttonNYSE.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        marker_nasdaq.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.buttonNYSE)
    public void nyse() {
        hideMarkers();
        setValues(models, 3);
        selectNYS();
    }

    private void selectNYS(){
        buttonNASDAQ.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        buttonNASDAQ.setTextColor(ContextCompat.getColor(this, R.color.darkish_blue));
        buttonNYSE.setBackgroundColor(ContextCompat.getColor(this, R.color.darkish_blue));
        buttonNYSE.setTextColor(ContextCompat.getColor(this, R.color.white));
        marker_nyse.setVisibility(View.VISIBLE);
    }

    private void setValues(final List<Sentiments> mValues, final int position) {
        String name = mValues.get(position).getName();

        switch (name) {

            case "NAS":
                name = "United States (NASDAQ)";
                imageFlag.setBackgroundResource(R.mipmap.us);
                imageFlagUs.setAlpha(1f);
                linearUnitedSwitch.setVisibility(View.VISIBLE);
                linearUnitedSwitch.setBackgroundResource(R.drawable.selector_sentiments_unitedstates);
                showAnimation(marker_nasdaq);
                selectNasdaq();
                break;
            case "JAPAN":
                name = "Japan (TSE)";
                imageFlag.setBackgroundResource(R.mipmap.japan);
                imageFlagJapan.setAlpha(1f);
                marker_japan.setVisibility(View.VISIBLE);
                showAnimation(marker_japan);
                break;
            case "NYS":
                name = "United States (NYS)";
                imageFlag.setBackgroundResource(R.mipmap.us);
                imageFlagUs.setAlpha(1f);
                linearUnitedSwitch.setVisibility(View.VISIBLE);
                linearUnitedSwitch.setBackgroundResource(R.drawable.selector_sentiments_unitedstates);
                showAnimation(marker_nyse);
                selectNYS();
                break;
            case "HK":
                name = "Hong Kong";
                imageFlag.setBackgroundResource(R.mipmap.hongkong);
                imageFlagHK.setAlpha(1f);
                marker_HongKong.setVisibility(View.VISIBLE);
                showAnimation(marker_HongKong);
                break;
            case "SGX":
                name = "Singapore (SGX)";
                imageFlag.setBackgroundResource(R.mipmap.singapore);
                imageFlagSing.setAlpha(1f);
                marker_singapore.setVisibility(View.VISIBLE);
                showAnimation(marker_singapore);
                break;
            case "TAIWAN":
                name = "Taiwan (TWSE)";
                imageFlag.setBackgroundResource(R.mipmap.taiwan);
                imageFlagTw.setAlpha(1f);
                marker_taiwan.setVisibility(View.VISIBLE);
                showAnimation(marker_taiwan);
                break;
        }

        entitiesCountry.setText(name);

        String percentage = String.format("%.1f", new BigDecimal(mValues.get(position).getSentiment_index()));
        buttonPercent.setText(percentage + "%");

        //ArrayList<BarChartData> lineChart = listOLists.get(position);
        if (isGreen(mValues.get(position).getSentiment_index())) {
            buttonPercent.setBackgroundColor(ContextCompat.getColor(SentimentsDetailsActivity.this, R.color.greenBlueTwo));
            //LineData data = getData(lineChart, R.color.greenBlueTwo, name);
            //setupChart(lineChart, data);
            setRightColor(R.mipmap.green_pin);
        } else {
            buttonPercent.setBackgroundColor(ContextCompat.getColor(SentimentsDetailsActivity.this, R.color.rouge));
            // LineData data = getData(lineChart, R.color.rouge, name);
            //setupChart(lineChart, data);
            setRightColor(R.mipmap.red_pin);
        }

        text3MPreturn.setText(mValues.get(position).getPotential_return3m() + "%");
        text6MPreturn.setText(mValues.get(position).getPotential_return6m() + "%");
        text9MPreturn.setText(mValues.get(position).getPotential_return9m() + "%");
        text12MPreturn.setText(mValues.get(position).getPotential_return12m() + "%");

        text3Mploss.setText(mValues.get(position).getPotential_loss3m() + "%");
        text6Mploss.setText(mValues.get(position).getPotential_loss6m() + "%");
        text9Mploss.setText(mValues.get(position).getPotential_loss9m() + "%");
        text12Mploss.setText(mValues.get(position).getPotential_loss12m() + "%");

        text3Mrlr.setText(mValues.get(position).getReturnriskratio3m() /*+ "%"*/);
        text6Mrlr.setText(mValues.get(position).getReturnriskratio6m() /*+ "%"*/);
        text9Mrlr.setText(mValues.get(position).getReturnriskratio9m() /*+ "%"*/);
        text12Mrlr.setText(mValues.get(position).getReturnriskratio12m() /*+ "%"*/);
    }

    private boolean isGreen(String sentimentsIndex) {
        return Float.parseFloat(sentimentsIndex) > 50f;
    }

    private void setRightColor(int resource) {
        marker_nasdaq.setBackgroundResource(resource);
        marker_nyse.setBackgroundResource(resource);
        marker_japan.setBackgroundResource(resource);
        marker_taiwan.setBackgroundResource(resource);
        marker_HongKong.setBackgroundResource(resource);
        marker_singapore.setBackgroundResource(resource);
    }


}
