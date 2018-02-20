package com.figsinc.app.analyze.trendingfunds;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.figsinc.app.Constants;
import com.figsinc.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.figsinc.app.Constants.PREF_NAME;

public class FundsLipperScoreCardActivity extends AppCompatActivity {

    @BindView(R.id.text_overAll_Treturn)
    AppCompatTextView text_overAll_Treturn;
    @BindView(R.id.text_overall_Creturn)
    AppCompatTextView text_overall_Creturn;
    @BindView(R.id.text_overall_preservation)
    AppCompatTextView text_overall_preservation;

    @BindView(R.id.text_three_year_preservation)
    AppCompatTextView text_three_year_preservation;
    @BindView(R.id.text_three_year_Creturn)
    AppCompatTextView text_three_year_Creturn;
    @BindView(R.id.text_three_year_Treturn)
    AppCompatTextView text_three_year_Treturn;

    @BindView(R.id.text_five_year_preservation)
    AppCompatTextView text_five_year_preservation;
    @BindView(R.id.text_five_year_Creturn)
    AppCompatTextView text_five_year_Creturn;
    @BindView(R.id.text_five_year_Treturn)
    AppCompatTextView text_five_year_Treturn;

    @BindView(R.id.text_ten_year_preservation)
    AppCompatTextView text_ten_year_preservation;
    @BindView(R.id.text_ten_year_Creturn)
    AppCompatTextView text_ten_year_Creturn;
    @BindView(R.id.text_ten_year_Treturn)
    AppCompatTextView text_ten_year_Treturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funds_lipper_score_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        showJSON(getFundInfoFundsPerfrormance());
        Constants.setStatusBar(FundsLipperScoreCardActivity.this,R.color.cobalt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private String formatValue(String scoreValue){
           return scoreValue.trim().equals("null")?"-":scoreValue;
    }

    private void showJSON(String json){

        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            text_overAll_Treturn.setText(formatValue(jsonObject.getString("TotalReturnOverallScore")));

            text_overall_Creturn.setText(formatValue(jsonObject.getString("ConsistentReturnOverallScore")));
            text_overall_preservation.setText(formatValue(jsonObject.getString("PreservationOverallScore")));

            text_three_year_preservation.setText(formatValue(jsonObject.getString("Preservation3yrScore")));
            text_three_year_Creturn.setText(formatValue(jsonObject.getString("ConsistentReturn3yrScore")));
            text_three_year_Treturn.setText(formatValue(jsonObject.getString("TotalReturn3yrScore")));

            text_five_year_preservation.setText(formatValue(jsonObject.getString("Preservation5yrScore")));
            text_five_year_Creturn.setText(formatValue(jsonObject.getString("ConsistentReturn5yrScore")));
            text_five_year_Treturn.setText(formatValue(jsonObject.getString("TotalReturn5yrScore")));

            text_ten_year_preservation.setText(formatValue(jsonObject.getString("Preservation10yrScore")));
            text_ten_year_Creturn.setText(formatValue(jsonObject.getString("ConsistentReturn10yrScore")));
            text_ten_year_Treturn.setText(formatValue(jsonObject.getString("TotalReturn10yrScore")));

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private String getFundInfoFundsPerfrormance(){
        SharedPreferences shared = FundsLipperScoreCardActivity.this.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String fundInfoFundsPerfrormance = shared.getString("getLipperScorecardDetails","");
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
