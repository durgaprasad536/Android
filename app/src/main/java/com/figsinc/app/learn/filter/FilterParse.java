package com.figsinc.app.learn.filter;

/**
 * Created by 461883 on 8/30/17.
 */

import com.figsinc.app.learn.Model.Filter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FilterParse {

    public static final String KEY_ID = "id";
    public static final String KEY_SECTOR_ID = "sector_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";

    private String json;

    public FilterParse(String json) {
        this.json = json;

    }

    protected ArrayList<Filter> parseJSON() {
        ArrayList<Filter>filterArrayList = new ArrayList<>();

        Filter filterAll = new Filter();
        filterAll.setId("0");
        filterAll.setName("All");
        filterAll.setSector_id("0");
        filterAll.setDescription("All");

        filterArrayList.add(filterAll);

        try {
            JSONArray sectorArray = new JSONArray(json);
            for (int i = 0; i < sectorArray.length(); i++) {
                Filter filter = new Filter();
                JSONObject jsonObject = sectorArray.getJSONObject(i);
                filter.setId(jsonObject.getString(KEY_ID));
                filter.setName(jsonObject.getString(KEY_NAME));

                if(jsonObject.has(KEY_SECTOR_ID)) {
                    filter.setSector_id(jsonObject.getString(KEY_SECTOR_ID));
                }
                filter.setDescription(jsonObject.getString(KEY_DESCRIPTION));

                filterArrayList.add(filter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return filterArrayList;
    }
}