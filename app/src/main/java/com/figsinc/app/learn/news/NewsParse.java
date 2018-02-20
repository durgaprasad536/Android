package com.figsinc.app.learn.news;

/**
 * Created by 461883 on 8/30/17.
 */

import com.figsinc.app.learn.Model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsParse {

    public static final String JSON_ARRAY_VALUE = "value";
    public static final String JSON_ARRAY_CATEGORY_NAME = "category_name";


    public static final String KEY_NAME = "name";
    public static final String KEY_URL = "url";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_THUMBNAIL = "thumbnail";
    public static final String KEY_CONTENT_URL = "contentUrl";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DATE_PUBLISHED = "datePublished";


    private String json;

    public NewsParse(String json) {
        this.json = json;
    }

    public ArrayList<News> parseJSON() {
        ArrayList<News> sectorArrayList = new ArrayList<>();
        try {
            JSONObject rootObject = new JSONObject(json);
            JSONArray valueArray = rootObject.getJSONArray(JSON_ARRAY_VALUE);
            for (int i = 0; i < valueArray.length(); i++) {
                JSONObject valueObject = valueArray.getJSONObject(i);
                News sector = new News();
                sector.setName(valueObject.getString(KEY_NAME));
                sector.setUrl(valueObject.getString(KEY_URL));

                if(valueObject.has(KEY_IMAGE)) {
                    JSONObject imageObject = valueObject.getJSONObject(KEY_IMAGE);
                    JSONObject thumbNailObject = imageObject.getJSONObject(KEY_THUMBNAIL);
                    sector.setImage(thumbNailObject.getString(KEY_CONTENT_URL));
                    sector.setDatePublished(valueObject.getString(KEY_DATE_PUBLISHED));
                    sectorArrayList.add(sector);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sectorArrayList;
    }
}