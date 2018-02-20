package com.figsinc.app.analyze.trendingfunds;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.figsinc.app.Constants;
import com.figsinc.app.R;
import com.figsinc.app.analyze.model.FundsBasicInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.figsinc.app.Constants.PREF_NAME;

public class FundsManagementFeesActivity extends AppCompatActivity {

    private String json = "json";

    @BindView(R.id.tvAdministrator)
    AppCompatTextView tvAdministrator;

    @BindView(R.id.tvCustodian)
    AppCompatTextView tvCustodian;

    @BindView(R.id.tvInvestmentAdvisor)
    AppCompatTextView tvInvestmentAdvisor;

    @BindView(R.id.tvFundManagementCompany)
    AppCompatTextView tvFundManagementCompany;

    @BindView(R.id.tvchargersInitial)
    AppCompatTextView tvchargersInitial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funds_management_fees);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        Constants.setStatusBar(FundsManagementFeesActivity.this,R.color.cobalt);
        try {
            SharedPreferences shared = this.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            json = shared.getString("fundInfoDescription", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        showJSON(json);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void showJSON(String json) {
        FundsBasicInfoParse fundsBasicInfoParse = new FundsBasicInfoParse(json);
        ArrayList<FundsBasicInfo> trendingStocksArrayList = fundsBasicInfoParse.parseJSON();
        FundsBasicInfo fundsBasicInfo = trendingStocksArrayList.get(0);
        // tvFundType.setText(trendingStocksArrayList.get(0).get);
        tvAdministrator.setText(fundsBasicInfo.getAdministrator());
        tvCustodian.setText(fundsBasicInfo.getCustodian());
        tvInvestmentAdvisor.setText(fundsBasicInfo.getAdvisor());
        tvFundManagementCompany.setText(fundsBasicInfo.getFundManagementCompany());
        tvchargersInitial.setText(fundsBasicInfo.getInitial());

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
