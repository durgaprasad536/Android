package com.figsinc.app.analyze.trendingstocks;

import com.figsinc.app.analyze.model.TrendingStocks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 461883 on 9/12/17.
 */

public class TrendingStocksParse {

    public static final String KEY_CF_NAME = "CF_NAME";
    public static final String KEY_Ticker_Exchange = "Ticker_Exchange";
    public static final String KEY_CF_TICK = "CF_TICK";
    public static final String KEY_PCTCHNG = "PCTCHNG";
    public static final String KEY_CompanyName = "CompanyName";
    public static final String KEY_CF_LAST = "CF_LAST";
    public static final String KEY_CF_RIC = "CF_RIC";
    public static final String KEY_Exchange = "Exchange";
    public static final String KEY_ConcensusPrice = "ConcensusPrice";
    public static final String KEY_Potential_Returns = "Potential_Returns";
    public static final String KEY_CF_NETCHNG = "CF_NETCHNG";
    public static final String KEY_CF_CF_CURRENCY = "CF_CF_CURRENCY";
    public static final String KEY_Ticker = "Ticker";
    public static final String KEY_Sectorid_stock="Sectorid_stock";

    private String json;

    public TrendingStocksParse(String json) {
        this.json = json;
    }

    public ArrayList<TrendingStocks> parseJSON() {
        ArrayList<TrendingStocks>sectorArrayList = new ArrayList<>();
        try {
            JSONArray sectorArray = new JSONArray(json);
            for (int i = 0; i < sectorArray.length(); i++) {
                TrendingStocks trendingStocks = new TrendingStocks();
                JSONObject jsonObject = sectorArray.getJSONObject(i);
                trendingStocks.setCF_NAME(jsonObject.getString(KEY_CF_NAME));
                if(jsonObject.has(KEY_Ticker_Exchange))
                trendingStocks.setTicker_Exchange(getTag(jsonObject,KEY_Ticker_Exchange));
                trendingStocks.setCF_TICK(getTag(jsonObject,KEY_CF_TICK));
                trendingStocks.setPCTCHNG(getTag(jsonObject,KEY_PCTCHNG));
                trendingStocks.setCompanyName(getTag(jsonObject,KEY_CompanyName));
                trendingStocks.setCF_LAST(getTag(jsonObject,KEY_CF_LAST));
                trendingStocks.setCF_RIC(getTag(jsonObject,KEY_CF_RIC));
                trendingStocks.setExchange(getTag(jsonObject,KEY_Exchange));
                trendingStocks.setConcensusPrice(getTag(jsonObject,KEY_ConcensusPrice));
                trendingStocks.setPotential_Returns(getTag(jsonObject,KEY_Potential_Returns));
                trendingStocks.setCF_NETCHNG(getTag(jsonObject,KEY_CF_NETCHNG));
                if(jsonObject.has(KEY_CF_CF_CURRENCY))
                trendingStocks.setCF_CF_CURRENCY(getTag(jsonObject,KEY_CF_CF_CURRENCY));
                trendingStocks.setTicker(getTag(jsonObject,KEY_Ticker));
                trendingStocks.setSectorid_stock(getTag(jsonObject,KEY_Sectorid_stock));
                sectorArrayList.add(trendingStocks);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sectorArrayList;
    }


    private String getTag(final JSONObject jsonObject, final String key) throws JSONException {
        if (jsonObject.has(key))
            return jsonObject.getString(key);
        else
            return "";
    }

}
