package com.figsinc.app.utils;

import com.figsinc.app.learn.Model.CandleStick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 461883 on 9/25/17.
 */

public class CandleStickParse {

    public static final String KEY_HIGH = "HIGH";
    public static final String KEY_LOW = "LOW";
    public static final String KEY_CLOSE = "CLOSE";
    public static final String KEY_OPEN = "OPEN";

    private String json;

    public CandleStickParse(String json) {
        this.json = json;
    }

    public ArrayList<CandleStick> parseJSON() {
        ArrayList<CandleStick> sectorArrayList = new ArrayList<>();

        try {
            JSONArray sentimentsRootArray = new JSONArray(json);
            for (int i = 0; i < sentimentsRootArray.length(); i++) {
                CandleStick candleStick = new CandleStick();
                JSONObject rootJsonObject = sentimentsRootArray.getJSONObject(i);
                candleStick.setHIGH(rootJsonObject.getString(KEY_HIGH));
                candleStick.setLOW(rootJsonObject.getString(KEY_LOW));
                candleStick.setCLOSE(rootJsonObject.getString(KEY_CLOSE));
                candleStick.setOPEN(rootJsonObject.getString(KEY_OPEN));
                sectorArrayList.add(candleStick);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sectorArrayList;
    }


}
