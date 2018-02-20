package com.figsinc.app.analyze.sentiments;

import com.figsinc.app.analyze.BarChart.BarChartData;
import com.figsinc.app.analyze.model.Sentiments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by K. Rajesh on 9/12/17.
 *
 */

public class SentimentsParse {

    public static final String KEY_sentiment_index = "sentiment_index";
    public static final String KEY_potential_return9m = "potential_return9m";
    public static final String KEY_potential_loss3m = "potential_loss3m";
    public static final String KEY_potential_return6m = "potential_return6m";
    public static final String KEY_potential_loss12m = "potential_loss12m";
    public static final String KEY_potential_return12m = "potential_return12m";
    public static final String KEY_stock_exchange_id = "stock_exchange_id";
    public static final String KEY_potential_return3m = "potential_return3m";
    public static final String KEY_potential_loss9m = "potential_loss9m";
    public static final String KEY_potential_loss6m = "potential_loss6m";

    public static final String KEY_returnriskratio3m = "returnriskratio3m";
    public static final String KEY_returnriskratio6m = "returnriskratio6m";
    public static final String KEY_returnriskratio9m = "returnriskratio9m";
    public static final String KEY_returnriskratio12m = "returnriskratio12m";

    public static final String KEY_value = "value";
    public static final String KEY_label = "label";

    private String json;
    ArrayList<ArrayList<BarChartData>> listOLists = new ArrayList<ArrayList<BarChartData>>();
    public SentimentsParse(String json) {
        this.json = json;
    }

    protected ArrayList<Sentiments> parseJSON() {
        ArrayList<Sentiments>sectorArrayList = new ArrayList<>();

        try {
            JSONArray sentimentsRootArray = new JSONArray(json);
            
            for (int i = 0; i < sentimentsRootArray.length(); i++) {
                JSONObject rootJsonObject = sentimentsRootArray.getJSONObject(i);
                Iterator<String> keys = rootJsonObject.keys();
                String key="";
                while (keys.hasNext()) {
                    key = keys.next();
                    System.out.println("Key :" + key);
                    break;
                }

                JSONArray sentimentsArray = rootJsonObject.getJSONArray(key);
                JSONArray emptyArray = sentimentsArray.getJSONArray(0);
                JSONObject emptyJsonObject = emptyArray.getJSONObject(0);
                Sentiments sentiments = new Sentiments();
                sentiments.setName(key);
                sentiments.setSentiment_index(emptyJsonObject.getString(KEY_sentiment_index));
                sentiments.setPotential_return9m(emptyJsonObject.getString(KEY_potential_return9m));
                sentiments.setPotential_loss3m(emptyJsonObject.getString(KEY_potential_loss3m));
                sentiments.setPotential_return6m(emptyJsonObject.getString(KEY_potential_return6m));
                sentiments.setPotential_loss12m(emptyJsonObject.getString(KEY_potential_loss12m));
                sentiments.setPotential_return12m(emptyJsonObject.getString(KEY_potential_return12m));
                sentiments.setStock_exchange_id(emptyJsonObject.getString(KEY_stock_exchange_id));
                sentiments.setPotential_return3m(emptyJsonObject.getString(KEY_potential_return3m));
                sentiments.setPotential_loss9m(emptyJsonObject.getString(KEY_potential_loss9m));
                sentiments.setPotential_loss6m(emptyJsonObject.getString(KEY_potential_loss6m));

                sentiments.setReturnriskratio3m(emptyJsonObject.getString(KEY_returnriskratio3m));
                sentiments.setReturnriskratio6m(emptyJsonObject.getString(KEY_returnriskratio6m));
                sentiments.setReturnriskratio9m(emptyJsonObject.getString(KEY_returnriskratio9m));
                sentiments.setReturnriskratio12m(emptyJsonObject.getString(KEY_returnriskratio12m));

                sectorArrayList.add(sentiments);

                JSONArray lineChart = sentimentsArray.getJSONArray(1);
                ArrayList<BarChartData>barChartDataArrayList = new ArrayList<>();
                for (int j = 0; j < lineChart.length(); j++) {
                    JSONObject lineJsonObject = lineChart.getJSONObject(j);
                    BarChartData barChartData = new BarChartData();
                    barChartData.setValue(lineJsonObject.getString(KEY_value));
                    barChartData.setLabel(lineJsonObject.getString(KEY_label));
                    barChartDataArrayList.add(barChartData);
                }
                listOLists.add(barChartDataArrayList);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sectorArrayList;
    }

    protected   ArrayList<ArrayList<BarChartData>> getListofList(){
        return listOLists;
    }
}
