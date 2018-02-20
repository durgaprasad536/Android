package com.figsinc.app.learn.theme;

/**
 * Created by 461883 on 8/30/17.
 */

import com.figsinc.app.learn.Model.Theme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThemeParse {

    public static final String JSON_ARRAY_DETAILS = "details";
    public static final String JSON_ARRAY_CATEGORY_NAME = "category_name";

    public static final String KEY_ID = "id";
    public static final String KEY_THEMATICS_CATEGORY_ID = "thematics_category_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_POTENTIAL_RETURNS = "potential_return";

    public static final String KEY_THEMATICS_REPORT_PDF = "thematics_report_pdf";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_INFOGRAPHIC_PDF = "infographics_pdf";
    public static final String KEY_THEMATICS_IOS = "thematics_ios";
    public static final String KEY_THEMATICS_VIDEO = "thematics_video";

    private String json;

    public ThemeParse(String json) {
        this.json = json;
    }

    public ArrayList<Theme> parseJSON() {
        ArrayList<Theme>themeArrayList = new ArrayList<>();
        try {
            JSONArray sectorArray = new JSONArray(json);
            for (int i = 0; i < sectorArray.length(); i++) {
                Theme sector = new Theme();
                JSONObject jsonObject = sectorArray.getJSONObject(i);
                sector.setId(jsonObject.getString(KEY_ID));
                sector.setThematics_category_id(jsonObject.getString(KEY_THEMATICS_CATEGORY_ID));
                sector.setName(jsonObject.getString(KEY_NAME));
                sector.setPotential_returns(jsonObject.getString(KEY_POTENTIAL_RETURNS));

                JSONArray details = jsonObject.getJSONArray(JSON_ARRAY_DETAILS);
                JSONObject detailsJsonObject = details.getJSONObject(0);
                sector.setThematics_report_pdf(detailsJsonObject.getString(KEY_THEMATICS_REPORT_PDF));
                sector.setDescription(detailsJsonObject.getString(KEY_DESCRIPTION));
                sector.setInfographics_pdf(detailsJsonObject.getString(KEY_INFOGRAPHIC_PDF));
                sector.setThematics_ios(detailsJsonObject.getString(KEY_THEMATICS_IOS));
                sector.setThematics_video(detailsJsonObject.getString(KEY_THEMATICS_VIDEO));

                JSONArray categoryArray = jsonObject.getJSONArray(JSON_ARRAY_CATEGORY_NAME);
                JSONObject categoryJsonObject = categoryArray.getJSONObject(0);
                sector.setCategory_name(categoryJsonObject.getString(KEY_NAME));
                sector.setCategory_description(categoryJsonObject.getString(KEY_DESCRIPTION));
                themeArrayList.add(sector);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return themeArrayList;
    }
}