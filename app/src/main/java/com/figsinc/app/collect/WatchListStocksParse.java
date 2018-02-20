package com.figsinc.app.collect;

import com.figsinc.app.analyze.model.TrendingStocks;
import com.figsinc.app.utils.Header;
import com.figsinc.app.utils.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 461883 on 9/22/17.
 */

public class WatchListStocksParse {


    public static final String KEY_CF_NAME = "CF_NAME";
    public static final String KEY_Ticker_Exchange = "Ticker_Exchange";
    public static final String KEY_CF_TICK = "CF_TICK";
    public static final String KEY_PCTCHNG = "PCTCHNG";
    public static final String KEY_CF_LAST = "CF_LAST";
    public static final String KEY_CF_RIC = "CF_RIC";
    public static final String KEY_Region = "Region";
    public static final String KEY_CF_LOW = "CF_LOW";
    public static final String KEY_CF_HIGH = "CF_HIGH";
    public static final String KEY_CF_OPEN = "CF_OPEN";
    public static final String KEY_CF_ACTIVE = "CF_ACTIVE";
    public static final String KEY_CF_NETCHNG = "CF_NETCHNG";
    public static final String KEY_Ticker = "Ticker";
    public static final String KEY_Potential_Returns = "Potential_Returns";
    public static final String KEY_Sectorid_stock = "Sectorid_stock";
    public static final String KEY_CompanyName = "CompanyName";
    public static final String KEY_Exchange = "Exchange";
    public static final String KEY_ConcensusPrice = "ConcensusPrice";
    public static final String KEY_Net_Asset = "Net_Asset";


    private String json;


    public WatchListStocksParse(String json) {
        this.json = json;
    }

    public ArrayList<ListItem> parseJSON() {
        ArrayList<ListItem> sectorArrayList = new ArrayList<>();


        try {
            JSONArray sectorArray = new JSONArray(json);
            for (int i = 0; i < sectorArray.length(); i++) {
                Header header = new Header();
                JSONObject rootObject = sectorArray.getJSONObject(i);
                header.setRegionHeader(rootObject.getString("region"));
                JSONArray dataArray = rootObject.getJSONArray("data");
                sectorArrayList.add(header);
                for (int j = 0; j < dataArray.length(); j++) {
                    TrendingStocks trendingStocks = new TrendingStocks();
                    JSONObject jsonObject = dataArray.getJSONObject(j);
                    trendingStocks.setCF_NAME(getTag(jsonObject,KEY_CF_NAME));
                    trendingStocks.setTicker_Exchange(getTag(jsonObject,KEY_Ticker_Exchange));
                    trendingStocks.setCF_TICK(getTag(jsonObject,KEY_CF_TICK));
                    trendingStocks.setPCTCHNG(getTag(jsonObject,KEY_PCTCHNG));
                    trendingStocks.setCF_LAST(getTag(jsonObject,KEY_CF_LAST));
                    trendingStocks.setCF_RIC(getTag(jsonObject,KEY_CF_RIC));
                    trendingStocks.setPotential_Returns(getTag(jsonObject,KEY_Potential_Returns));
                    trendingStocks.setCF_NETCHNG(getTag(jsonObject,KEY_CF_NETCHNG));
                    trendingStocks.setTicker(getTag(jsonObject,KEY_Ticker));
                    trendingStocks.setRegion(getTag(jsonObject,KEY_Region));
                    trendingStocks.setCF_LOW(getTag(jsonObject,KEY_CF_LOW));
                    trendingStocks.setCF_HIGH(getTag(jsonObject,KEY_CF_HIGH));
                    trendingStocks.setCF_OPEN(getTag(jsonObject,KEY_CF_OPEN));
                    trendingStocks.setCF_ACTIVE(getTag(jsonObject,KEY_CF_ACTIVE));
                    trendingStocks.setSectorid_stock(getTag(jsonObject,KEY_Sectorid_stock));
                    trendingStocks.setCompanyName(getTag(jsonObject,KEY_CompanyName));
                    trendingStocks.setExchange(getTag(jsonObject,KEY_Exchange));
                    trendingStocks.setConcensusPrice(getTag(jsonObject,KEY_ConcensusPrice));
                    trendingStocks.setNet_Asset(getTag(jsonObject,KEY_Net_Asset));

                    sectorArrayList.add(trendingStocks);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sectorArrayList;
    }

    private String getTag(final JSONObject jsonObject, final String key) throws JSONException{
        if (jsonObject.has(key))
            return jsonObject.getString(key);
        else
            return "";
    }

}
