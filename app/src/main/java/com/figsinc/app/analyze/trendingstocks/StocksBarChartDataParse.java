package com.figsinc.app.analyze.trendingstocks;

import com.figsinc.app.analyze.BarChart.BarChartData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 461883 on 9/23/17.
 */

public class StocksBarChartDataParse {

    public static final String KEY_value= "value";
    public static final String KEY_label = "label";
    public static final int BARCHART_COUNT=4;

    private String json;

   private  ArrayList<String> headersList=new ArrayList<>();

    public StocksBarChartDataParse(String json,final ArrayList<String> headersList) {
        this.json = json;
        this.headersList = headersList;
    }

    public ArrayList<ArrayList<BarChartData>>  parseJSON() {
         ArrayList<ArrayList<BarChartData>> listOLists = new ArrayList<ArrayList<BarChartData>>();

        try {
            JSONObject rootJsonObject = new JSONObject(json);

            for (int i = 0; i < BARCHART_COUNT; i++) {
                ArrayList<BarChartData>barChartDataArrayList = new ArrayList<>();
                JSONArray sentimentsArray = rootJsonObject.getJSONArray(headersList.get(i));
                for (int j = 0; j < sentimentsArray.length(); j++) {
                    BarChartData barChartData = new BarChartData();
                    JSONObject jsonObject = sentimentsArray.getJSONObject(j);
                    barChartData.setValue(jsonObject.getString(KEY_value));
                    barChartData.setLabel(jsonObject.getString(KEY_label));
                    barChartDataArrayList.add(barChartData);
                }
                listOLists.add(barChartDataArrayList);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listOLists;
    }



}

