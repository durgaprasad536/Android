package com.figsinc.app.netdhania;

import com.figsinc.app.analyze.model.TrendingStocks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 461883 on 10/16/17.
 */

public class PriceBarParse {

    String KEY_VOLUME = "VOLUME";
    String KEY_TIMESTAMP = "TIMESTAMP";
    String KEY_HIGH = "HIGH";
    String KEY_LOW = "LOW";
    String KEY_CLOSE = "CLOSE";
    String KEY_OPEN = "OPEN";


    private String json;

    public PriceBarParse(String json) {
        this.json = json;
    }

    public ArrayList<PriceBar> parseJSON() {
        ArrayList<PriceBar>priceBarArrayList = new ArrayList<>();
        try {
            JSONArray sectorArray = new JSONArray(json);
            for (int i = 0; i < sectorArray.length(); i++) {
                PriceBar priceBar = new PriceBar();
                JSONObject jsonObject = sectorArray.getJSONObject(i);
                priceBar.setVolume(jsonObject.getDouble(KEY_VOLUME));
               // priceBar.setVolume(jsonObject.getDouble(KEY_TIMESTAMP));
                priceBar.setHigh(jsonObject.getDouble(KEY_HIGH));
                priceBar.setLow(jsonObject.getDouble(KEY_LOW));
                priceBar.setClose(jsonObject.getDouble(KEY_CLOSE));
                if(jsonObject.has(KEY_OPEN))
                priceBar.setOpen(jsonObject.getDouble(KEY_OPEN));
                priceBarArrayList.add(priceBar);

               // System.out.println(" 888888888888888 " + priceBarArrayList.size());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return priceBarArrayList;
    }


}
