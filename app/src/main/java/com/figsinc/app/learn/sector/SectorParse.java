package com.figsinc.app.learn.sector;

/**
 * Created by 461883 on 8/30/17.
 */

import com.figsinc.app.learn.Model.Sector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SectorParse {

    public static final String JSON_ARRAY_DETAILS = "details";
    public static final String JSON_ARRAY_CATEGORY_NAME = "category_name";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SUB_INDUSTRY_CODE = "sub_industry_code";
    public static final String KEY_POTENTIAL_RETURNS = "potential_returns";
    public static final String KEY_IS_ACTIVE = "is_active";
    public static final String KEY_SECTOR_CATEGORY = "sector_category";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_INDUSTRY_REPORT = "industry_report";
    public static final String KEY_IMAGE_IOS = "image_ios";
    public static final String KEY_INFOGRAPHIC_PDF = "infographics_pdf";
    public static final String KEY_SECTOR_VIDEO = "sector_video";
    public static final String KEY_IMAGE_WEB = "image_web";

    private String json;

    public SectorParse(String json) {
        System.out.println(" 9999999999999  " + json);
        this.json = json;
    }

    public ArrayList<Sector> parseJSON() {
        ArrayList<Sector>sectorArrayList = new ArrayList<>();
        try {
            JSONArray sectorArray = new JSONArray(json);
            for (int i = 0; i < sectorArray.length(); i++) {
                Sector sector = new Sector();
                JSONObject jsonObject = sectorArray.getJSONObject(i);
                sector.setId(jsonObject.getString(KEY_ID));
                sector.setName(jsonObject.getString(KEY_NAME));
                sector.setSub_industry_code(jsonObject.getString(KEY_SUB_INDUSTRY_CODE));
                sector.setPotential_returns(jsonObject.getString(KEY_POTENTIAL_RETURNS));
                sector.setIs_active(jsonObject.getString(KEY_IS_ACTIVE));
                sector.setSector_category(jsonObject.getString(KEY_SECTOR_CATEGORY));

                JSONArray details = jsonObject.getJSONArray(JSON_ARRAY_DETAILS);
                JSONObject detailsJsonObject = details.getJSONObject(0);
                sector.setDescription(detailsJsonObject.getString(KEY_DESCRIPTION));
                sector.setIndustry_report(detailsJsonObject.getString(KEY_INDUSTRY_REPORT));
                sector.setImage_ios(detailsJsonObject.getString(KEY_IMAGE_IOS));
                sector.setInfographics_pdf(detailsJsonObject.getString(KEY_INFOGRAPHIC_PDF));
                sector.setSector_video(detailsJsonObject.getString(KEY_SECTOR_VIDEO));
                sector.setImage_web(detailsJsonObject.getString(KEY_IMAGE_WEB));

                JSONArray categoryArray = jsonObject.getJSONArray(JSON_ARRAY_CATEGORY_NAME);
                JSONObject categoryJsonObject = categoryArray.getJSONObject(0);
                if(categoryJsonObject.has(KEY_NAME)) {
                    sector.setCategory_name(categoryJsonObject.getString(KEY_NAME));
                }else if(categoryJsonObject.has("Name")) {
                    sector.setCategory_name(categoryJsonObject.getString("Name"));
                }
                if(categoryJsonObject.has(KEY_DESCRIPTION)) {
                    sector.setCategory_description(categoryJsonObject.getString(KEY_DESCRIPTION));
                }else  if(categoryJsonObject.has("Description")) {
                    sector.setCategory_description(categoryJsonObject.getString("Description"));
                }
                sectorArrayList.add(sector);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sectorArrayList;
    }
}