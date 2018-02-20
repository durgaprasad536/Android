package com.figsinc.app.analyze.trendingfunds;

import com.figsinc.app.analyze.model.FundsBasicInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 461883 on 9/24/17.
 */

public class FundsBasicInfoParse {

    private final String KEY_AssetManager = "AssetManager";
    private final String KEY_TechnicalIndicator = "TechnicalIndicator";
    private final String KEY_Initial = "Initial";
    private final String KEY_Domicile = "Domicile";
    private final String KEY_LegalStructure = "LegalStructure";
    private final String KEY_TNACurrency = "TNACurrency";
    private final String KEY_Administrator = "Administrator";
    private final String KEY_Custodian = "Custodian";
    private final String KEY_Advisor = "Advisor";
    private final String KEY_ISIN = "ISIN";
    private final String KEY_MinimumInvestmentIRRegular = "MinimumInvestmentIRRegular";
    private final String KEY_Redemption = "Redemption";
    private final String KEY_Annual = "Annual";
    private final String KEY_FundManagementCompany = "FundManagementCompany";
    private final String KEY_TNADate = "TNADate";
    private final String KEY_GeographicalFocus = "GeographicalFocus";
    private final String KEY_RiskFreeIndex = "RiskFreeIndex";
    private final String KEY_Overview = "Overview";
    private final String KEY_MinimumInvestmentInitial = "MinimumInvestmentInitial";
    private final String KEY_MinimumInvestmentRegular = "MinimumInvestmentRegular";
    private final String KEY_PotentialReturn = "PotentialReturn";
    private final String KEY_TNA = "TNA";

    private String json;

    public FundsBasicInfoParse(String json) {
         this.json = json;
    }

    public ArrayList<FundsBasicInfo> parseJSON() {
        ArrayList<FundsBasicInfo>fundsBasicInfoArrayList = new ArrayList<>();
        try {
            JSONArray sectorArray = new JSONArray(json);
            for (int i = 0; i < sectorArray.length(); i++) {
                FundsBasicInfo basicInfo = new FundsBasicInfo();
                JSONObject jsonObject = sectorArray.getJSONObject(i);
                basicInfo.setAssetManager(jsonObject.getString(KEY_AssetManager));
                basicInfo.setTechnicalIndicator(jsonObject.getString(KEY_TechnicalIndicator));
                basicInfo.setInitial(jsonObject.getString(KEY_Initial));
                basicInfo.setDomicile(jsonObject.getString(KEY_Domicile));
                basicInfo.setLegalStructure(jsonObject.getString(KEY_LegalStructure));
                basicInfo.setTNACurrency(jsonObject.getString(KEY_TNACurrency));
                basicInfo.setAdministrator(jsonObject.getString(KEY_Administrator));
                basicInfo.setCustodian(jsonObject.getString(KEY_Custodian));
                basicInfo.setAdvisor(jsonObject.getString(KEY_Advisor));
                basicInfo.setISIN(jsonObject.getString(KEY_ISIN));
                basicInfo.setMinimumInvestmentIRRegular(jsonObject.getString(KEY_MinimumInvestmentIRRegular));
                basicInfo.setRedemption(jsonObject.getString(KEY_Redemption));
                basicInfo.setAnnual(jsonObject.getString(KEY_Annual));
                basicInfo.setFundManagementCompany(jsonObject.getString(KEY_FundManagementCompany));
                basicInfo.setTNADate(jsonObject.getString(KEY_TNADate));
                basicInfo.setGeographicalFocus(jsonObject.getString(KEY_GeographicalFocus));
                basicInfo.setRiskFreeIndex(jsonObject.getString(KEY_RiskFreeIndex));
                basicInfo.setOverview(jsonObject.getString(KEY_Overview));
                basicInfo.setMinimumInvestmentInitial(jsonObject.getString(KEY_MinimumInvestmentInitial));
                basicInfo.setPotentialReturn(jsonObject.getString(KEY_PotentialReturn));
                basicInfo.setTNA(jsonObject.getString(KEY_TNA));

                fundsBasicInfoArrayList.add(basicInfo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return fundsBasicInfoArrayList;
    }

}
