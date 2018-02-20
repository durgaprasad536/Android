package com.figsinc.app.analyze.trendingfunds;

import com.figsinc.app.analyze.BarChart.BarChartData;
import com.figsinc.app.analyze.model.Sentiments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 461883 on 10/12/17.
 */

public class FundsInfoChartParse {

    private final String KEY_Nav = "Nav";
    private final String KEY_DataDate = "DataDate";

    private String json;


    public FundsInfoChartParse(String json) {
        this.json = json;
    }

    protected ArrayList<BarChartData> parseJSON() {

        ArrayList<BarChartData> fundsInfoChartList = new ArrayList<>();
        try {
            JSONArray lineChart = new JSONArray(json);

            for (int j = 0; j < lineChart.length(); j++) {
                JSONObject lineJsonObject = lineChart.getJSONObject(j);
                BarChartData barChartData = new BarChartData();
                barChartData.setValue(lineJsonObject.getString(KEY_Nav));
                barChartData.setLabel(lineJsonObject.getString(KEY_DataDate));
                fundsInfoChartList.add(barChartData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fundsInfoChartList;
    }
}

