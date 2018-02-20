package com.figsinc.app.analyze.trendinganalysts;

import com.figsinc.app.analyze.model.TrendingAnalysts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 461883 on 9/12/17.
 */

public class TrendingAnalystsParse {

    public static final String KEY_average_return = "average_return";
    public static final String KEY_topsector__name = "topsector__name";
    public static final String KEY_average_time = "average_time";
    public static final String KEY_anayst_compay_name= "anayst_compay_name";
    public static final String KEY_analystid= "analystid";
    public static final String KEY_analystname = "analystname";
    public static final String KEY_figs_analyst_score = "figs_analyst_score";
    public static final String KEY_secondsector__name = "secondsector__name";

    private String json;

    public TrendingAnalystsParse(String json) {
        this.json = json;
    }

    public ArrayList<TrendingAnalysts> parseJSON() {
        ArrayList<TrendingAnalysts>sectorArrayList = new ArrayList<>();
        try {
            JSONArray sectorArray = new JSONArray(json);
            for (int i = 0; i < sectorArray.length(); i++) {
                TrendingAnalysts sentiments = new TrendingAnalysts();
                JSONObject jsonObject = sectorArray.getJSONObject(i);
                sentiments.setAverage_return(jsonObject.getString(KEY_average_return));
                sentiments.setTopsector__name(jsonObject.getString(KEY_topsector__name));
                sentiments.setAverage_time(jsonObject.getString(KEY_average_time));
                sentiments.setAnayst_compay_name(jsonObject.getString(KEY_anayst_compay_name));
                sentiments.setAnalystid(jsonObject.getString(KEY_analystid));
                sentiments.setAnalystname(jsonObject.getString(KEY_analystname));
                sentiments.setFigs_analyst_score(jsonObject.getString(KEY_figs_analyst_score));
                sentiments.setSecondsector__name(jsonObject.getString(KEY_secondsector__name));
                sectorArrayList.add(sentiments);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sectorArrayList;
    }

}
