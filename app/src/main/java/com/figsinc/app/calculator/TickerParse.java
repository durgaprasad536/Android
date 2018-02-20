package com.figsinc.app.calculator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by K. Rajesh on 9/12/17.
 */

public class TickerParse {

    public static final String KEY_Ticker_Exchange = "Ticker-Exchange";
    public static final String KEY_lang = "lang";
    public static final String KEY_en = "en";

    private String json;
    ArrayList<String> tickerCompanyNameList = new ArrayList<>();
    ArrayList<String> tickerExchangeList = new ArrayList<>();

    public TickerParse(String json) {
        this.json = json;

    }

    protected void parseJSON() {
        tickerCompanyNameList.clear();
        tickerExchangeList.clear();

        try {
            JSONArray sentimentsRootArray = new JSONArray(json);
            for (int i = 0; i < sentimentsRootArray.length(); i++) {
                JSONObject rootJsonObject = sentimentsRootArray.getJSONObject(i);
                JSONObject langJsonObject = rootJsonObject.getJSONObject(KEY_lang);
                tickerCompanyNameList.add(langJsonObject.getString(KEY_en));
                tickerExchangeList.add(rootJsonObject.getString(KEY_Ticker_Exchange));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    protected ArrayList<String>  getCompanyList(){
        return tickerCompanyNameList;
    }

    protected ArrayList<String>  getTickerExchangeList(){
        return tickerExchangeList;
    }

}
