package com.figsinc.app.analyze.trendinganalysts;

import com.figsinc.app.analyze.model.StockCoverage;
import com.figsinc.app.utils.Header;
import com.figsinc.app.utils.ListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 461883 on 9/12/17.
 */

public class AnalystStocksCoverageParse {

    public static final String KEY_CF_NAME = "CF_NAME";
    public static final String KEY_Ticker_Exchange = "Ticker_Exchange";
    public static final String KEY_CF_TICK = "CF_TICK";
    public static final String KEY_PCTCHNG = "PCTCHNG";
    public static final String KEY_CompanyName = "CompanyName";
    public static final String KEY_CF_LAST = "CF_LAST";
    public static final String KEY_CF_RIC = "CF_RIC";
    public static final String KEY_Potential_Returns = "Potential_Returns";
    public static final String KEY_CF_NETCHNG = "CF_NETCHNG";
    public static final String KEY_Ticker = "Ticker";
    public static final String KEY_Sectorid_stock = "Sectorid_stock";
    public static final String KEY_Header_Region = "region";
    public static final String KEY_Data = "data";


    private String json;

    public AnalystStocksCoverageParse(String json) {
        this.json = json;
    }

    public ArrayList<ListItem> parseJSON() {
        ArrayList<ListItem> sectorArrayList = new ArrayList<>();
        try {
            JSONArray sectorArray = new JSONArray(json);
            JSONArray dummyArray = sectorArray.getJSONArray(0);
            for (int i = 0; i < dummyArray.length(); i++) {
                Header header=new Header();
                JSONObject jsonObject = dummyArray.getJSONObject(i);
                header.setRegionHeader(jsonObject.getString(KEY_Header_Region));
                sectorArrayList.add(header);
                System.out.println(" =========== " + header.getRegionHeader());
                JSONArray dataArray = jsonObject.getJSONArray(KEY_Data);
                for (int j = 0; j < dataArray.length(); j++) {
                    StockCoverage trendingStocks = new StockCoverage();
                    JSONObject dataJsonObject = dataArray.getJSONObject(j);
                    trendingStocks.setCF_NAME(dataJsonObject.getString(KEY_CF_NAME));
                    trendingStocks.setTicker_Exchange(dataJsonObject.getString(KEY_Ticker_Exchange));
                    trendingStocks.setCF_TICK(dataJsonObject.getString(KEY_CF_TICK));
                    trendingStocks.setPCTCHNG(dataJsonObject.getString(KEY_PCTCHNG));
                    trendingStocks.setCompanyName(dataJsonObject.getString(KEY_CompanyName));
                    trendingStocks.setCF_LAST(dataJsonObject.getString(KEY_CF_LAST));
                    trendingStocks.setCF_RIC(dataJsonObject.getString(KEY_CF_RIC));
                    trendingStocks.setPotential_Returns(dataJsonObject.getString(KEY_Potential_Returns));
                    trendingStocks.setCF_NETCHNG(dataJsonObject.getString(KEY_CF_NETCHNG));
                    trendingStocks.setTicker(dataJsonObject.getString(KEY_Ticker));
                    trendingStocks.setSectorid_stock(dataJsonObject.getString(KEY_Sectorid_stock));
                    sectorArrayList.add(trendingStocks);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sectorArrayList;
    }

}
