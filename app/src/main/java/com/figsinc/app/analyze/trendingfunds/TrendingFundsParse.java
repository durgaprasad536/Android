package com.figsinc.app.analyze.trendingfunds;

import com.figsinc.app.analyze.model.TrendingFunds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 461883 on 9/12/17.
 */

public class TrendingFundsParse {

    public static final String KEY_CF_NAME = "CF_NAME";
    public static final String KEY_CF_CURRENCY = "CF_CURRENCY";
    public static final String KEY_PCTCHNG = "PCTCHNG";
    public static final String KEY_CF_TICK = "CF_TICK";
    public static final String KEY_FundsName = "FundsName";
    public static final String KEY_CF_LAST = "CF_LAST";
    public static final String KEY_Potential_Returns = "Potential_Returns";
    public static final String KEY_CF_NETCHNG = "CF_NETCHNG";
    public static final String KEY_lippernos = "lippernos";

    private String json;

    public TrendingFundsParse(String json) {
        this.json = json;
    }

    public ArrayList<TrendingFunds> parseJSON() {
        ArrayList<TrendingFunds>sectorArrayList = new ArrayList<>();
        try {
            JSONArray sectorArray = new JSONArray(json);
            for (int i = 0; i < sectorArray.length(); i++) {
                TrendingFunds trendingFunds = new TrendingFunds();
                JSONObject jsonObject = sectorArray.getJSONObject(i);
                trendingFunds.setCF_NAME(jsonObject.getString(KEY_CF_NAME));
                trendingFunds.setCF_TICK(jsonObject.getString(KEY_CF_TICK));
                trendingFunds.setPCTCHNG(jsonObject.getString(KEY_PCTCHNG));
                trendingFunds.setCF_LAST(jsonObject.getString(KEY_CF_LAST));
                trendingFunds.setPotential_Returns(jsonObject.getString(KEY_Potential_Returns));
                trendingFunds.setCF_NETCHNG(jsonObject.getString(KEY_CF_NETCHNG));
                if(jsonObject.has(KEY_CF_CURRENCY))
                trendingFunds.setCF_CURRENCY(jsonObject.getString(KEY_CF_CURRENCY));
                trendingFunds.setFundsName(jsonObject.getString(KEY_FundsName));
                trendingFunds.setLippernos(jsonObject.getString(KEY_lippernos));

                sectorArrayList.add(trendingFunds);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sectorArrayList;
    }

}
