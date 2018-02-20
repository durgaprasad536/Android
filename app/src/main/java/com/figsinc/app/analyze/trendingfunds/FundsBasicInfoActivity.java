package com.figsinc.app.analyze.trendingfunds;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.figsinc.app.analyze.model.FundsBasicInfo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.figsinc.app.Constants.PREF_NAME;

public class FundsBasicInfoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.tvFundType)TextView tvFundType;
    @BindView(R.id.tvGeographicalFocus)TextView tvGeographicalFocus;
    @BindView(R.id.tvLaunchDate)TextView tvLaunchDate;
    @BindView(R.id.tvDomicile)TextView tvDomicile;
    @BindView(R.id.tvCurrency)TextView tvCurrency;
    @BindView(R.id.tvLegalStructure)TextView tvLegalStructure;
    @BindView(R.id.tvNAVasof)TextView tvNAVasof;
    @BindView(R.id.tvAuMasof)TextView tvAuMasof;
    @BindView(R.id.tvISIN)TextView tvISIN;
    @BindView(R.id.tvTechnical_Indicator)TextView tvTechnical_Indicator;
    @BindView(R.id.tvRisk_Free)TextView tvRisk_Free;
    @BindView(R.id.tvFundManager)TextView tvFundManager;
    @BindView(R.id.textAumTitle)TextView textAumTitle;

    private String json ="json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funds_detail_informations);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Constants.setStatusBar(FundsBasicInfoActivity.this,R.color.cobalt);
        try {
            SharedPreferences shared = this.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            json = shared.getString("fundInfoDescription","");
        }catch (Exception e){
            e.printStackTrace();
        }
        showJSON(json);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showJSON(String json){
        FundsBasicInfoParse fundsBasicInfoParse = new FundsBasicInfoParse(json);
        ArrayList<FundsBasicInfo> trendingStocksArrayList =fundsBasicInfoParse.parseJSON();
        FundsBasicInfo fundsBasicInfo = trendingStocksArrayList.get(0);
        tvFundType.setText("Equity");
        tvGeographicalFocus.setText(fundsBasicInfo.getGeographicalFocus());
        tvLaunchDate.setText(changeDateFormat(fundsBasicInfo.getTNADate()));
        tvDomicile.setText(fundsBasicInfo.getDomicile());
        tvCurrency.setText(fundsBasicInfo.getTNACurrency());
        tvLegalStructure.setText(fundsBasicInfo.getLegalStructure());
        tvNAVasof.setText(changeDateFormat(fundsBasicInfo.getTNADate()));
        textAumTitle.append(" 31-05-"+Calendar.getInstance().get(Calendar.YEAR));
        tvAuMasof.setText(String.format("%.0f", new BigDecimal(fundsBasicInfo.getTNA())));
        tvISIN.setText(fundsBasicInfo.getISIN());
        tvTechnical_Indicator.setText(fundsBasicInfo.getTechnicalIndicator());
        tvRisk_Free.setText(fundsBasicInfo.getRiskFreeIndex());
        tvFundManager.setText(fundsBasicInfo.getFundManagementCompany());

    }

    private String changeDateFormat(String date){
         try {
             SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
             Date newDate = spf.parse(date);
             spf = new SimpleDateFormat("dd-MM-yyyy");
             date = spf.format(newDate);
         }catch (Exception e){
             e.printStackTrace();
         }
         return date;
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
