package com.figsinc.app.learn.theme;

/**
 * Created by 461883 on 8/30/17.
 */


import com.figsinc.app.learn.Model.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThematicsDetailsParse {

    public static final String JSON_ARRAY_DETAILS = "details";

    public static final String KEY_ID = "thematics_category_id";
    public static final String KEY_NAME = "name";
   // public static final String KEY_SUB_INDUSTRY_CODE = "sub_industry_code";
    public static final String KEY_POTENTIAL_RETURNS = "potential_return";
   // public static final String KEY_IS_ACTIVE = "is_active";
  //  public static final String KEY_SECTOR_CATEGORY = "sector_category";

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_INDUSTRY_REPORT = "thematics_report_pdf";
    public static final String KEY_IMAGE_IOS = "thematics_ios";
    public static final String KEY_INFOGRAPHIC_PDF = "infographics_pdf";
    public static final String KEY_SECTOR_VIDEO = "thematics_video";
   // public static final String KEY_IMAGE_WEB = "image_web";

    private String json;

    public ThematicsDetailsParse(String json) {
        this.json = json;
    }

    public ArrayList<Profile> parseJSON() {
        ArrayList<Profile>sectorArrayList = new ArrayList<>();
        try {

                Profile profile = new Profile();
                JSONObject jsonObject = new JSONObject(json);
                profile.setName(jsonObject.getString(KEY_NAME));
               //profile.setSub_industry_code(jsonObject.getString(KEY_SUB_INDUSTRY_CODE));
                profile.setPotential_returns(jsonObject.getString(KEY_POTENTIAL_RETURNS));
               //profile.setIs_active(jsonObject.getString(KEY_IS_ACTIVE));
               // profile.setSector_category(jsonObject.getString(KEY_SECTOR_CATEGORY));

                JSONArray details = jsonObject.getJSONArray(JSON_ARRAY_DETAILS);
                JSONObject detailsJsonObject = details.getJSONObject(0);
                profile.setDescription(detailsJsonObject.getString(KEY_DESCRIPTION));
                profile.setIndustry_report(detailsJsonObject.getString(KEY_INDUSTRY_REPORT));
                profile.setImage_ios(detailsJsonObject.getString(KEY_IMAGE_IOS));
                profile.setInfographics_pdf(detailsJsonObject.getString(KEY_INFOGRAPHIC_PDF));
                profile.setSector_video(detailsJsonObject.getString(KEY_SECTOR_VIDEO));

                sectorArrayList.add(profile);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sectorArrayList;
    }
}