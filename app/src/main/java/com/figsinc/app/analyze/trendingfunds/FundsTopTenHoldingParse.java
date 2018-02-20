package com.figsinc.app.analyze.trendingfunds;

import com.figsinc.app.analyze.model.FundsTopTenHolding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 461883 on 9/29/17.
 */

public class FundsTopTenHoldingParse {

    private static final String KEY_percentage="percentage";
    private static final String KEY_potentialreturns="potentialreturns";
    private static final String KEY_name="name";
    private static final String KEY_price_close="price_close";

    private String json;

    public FundsTopTenHoldingParse(String json) {
        this.json = json;
    }

    public ArrayList<FundsTopTenHolding> parseJSON() {
        ArrayList<FundsTopTenHolding>sectorArrayList = new ArrayList<>();
        try {
            JSONArray sectorArray = new JSONArray(json);
            for (int i = 0; i < sectorArray.length(); i++) {
                FundsTopTenHolding trendingFunds = new FundsTopTenHolding();
                JSONObject jsonObject = sectorArray.getJSONObject(i);
                trendingFunds.setPercentage(jsonObject.getString(KEY_percentage));
                trendingFunds.setPotentialreturns(jsonObject.getString(KEY_potentialreturns));
                trendingFunds.setName(jsonObject.getString(KEY_name));
                trendingFunds.setPrice_close(jsonObject.getString(KEY_price_close));

                sectorArrayList.add(trendingFunds);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sectorArrayList;
    }

}
