package com.figsinc.app.analyze.search;

import com.figsinc.app.analyze.model.TrendingAnalysts;
import com.figsinc.app.analyze.model.TrendingFunds;
import com.figsinc.app.analyze.model.TrendingStocks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 461883 on 9/22/17.
 */

public class SearchParse {

    public static final String KEY_CF_NAME = "CF_NAME";
    public static final String KEY_CF_CURRENCY = "CF_CURRENCY";
    public static final String KEY_name = "name";
    public static final String KEY_Overview = "Overview";
    public static final String KEY_lippernumber = "lippernumber";
    public static final String KEY_CF_TICK = "CF_TICK";
    public static final String KEY_PCTCHNG = "PCTCHNG";
    public static final String KEY_potential_returns = "potential_returns";
    public static final String KEY_CF_LAST = "CF_LAST";
    public static final String KEY_CF_NETCHNG = "CF_NETCHNG";
    public static final String KEY_ticker_exchange = "ticker_exchange";
    public static final String KEY_lang = "lang";
    public static final String KEY_SourceFilingDate = "SourceFilingDate";
    public static final String KEY_SourceFilingType = "SourceFilingType";
    public static final String KEY_Value = "Value";
    public static final String KEY_LastUpdated = "LastUpdated";
    public static final String KEY_sub_industry_code = "sub_industry_code";
    public static final String KEY_company_name = "company_name";
    public static final String KEY_Ticker = "ticker";
    public static final String KEY_ric = "ric";

    public static final String KEY_analystid = "analystid";
    public static final String KEY_anayst_compay_name = "anayst_compay_name";
    public static final String KEY_figs_analyst_score = "figs_analyst_score";
    public static final String KEY_analystname = "analystname";

    private String json;

    public SearchParse(String json) {
        this.json = json;
    }

    private  static final ArrayList<TrendingAnalysts> listAnalysts = new ArrayList<>();
    private  static final ArrayList<TrendingFunds> listFunds = new ArrayList<>();
    private  static final ArrayList<TrendingStocks> listStocks = new ArrayList<>();


    public ArrayList<TrendingAnalysts> getListAnalysts(){
        return  listAnalysts;
    }

    public ArrayList<TrendingFunds> getListFunds(){
        return  listFunds;
    }

    public ArrayList<TrendingStocks> getListStocks(){
        return  listStocks;
    }

    public ArrayList<SearchItem> parseJSON() {
        ArrayList<SearchItem> sectorArrayList = new ArrayList<>();

        try {
            JSONArray rootJsonArray = new JSONArray(json);
            JSONObject rootObject = rootJsonArray.getJSONObject(0);
            {
                Header header = new Header();
                if (rootObject.has("Companysearch")) {
                    header.setTypeHeader(Type.STOCKS.title());
                    JSONArray CompanysearchJSONArray = rootObject.optJSONArray("Companysearch");
                    listStocks.clear();
                    for (int j = 0; j < CompanysearchJSONArray.length(); j++) {
                        TrendingStocks trendingStocks = new TrendingStocks();
                        JSONObject jsonObject = CompanysearchJSONArray.getJSONObject(j);
                        trendingStocks.setTicker_Exchange(getTag(jsonObject, KEY_ticker_exchange));
                        trendingStocks.setCF_CF_CURRENCY(getTag(jsonObject, KEY_CF_CURRENCY));
                        trendingStocks.setPCTCHNG(getTag(jsonObject, KEY_PCTCHNG));
                       // trendingStocks.setLang(getTag(jsonObject, KEY_lang));
                       // trendingStocks.setSourceFilingDate(getTag(jsonObject, KEY_SourceFilingDate));
                       // trendingStocks.setValue(getTag(jsonObject, KEY_Value));
                      //  trendingStocks.setLastUpdated(getTag(jsonObject, KEY_LastUpdated));
                        trendingStocks.setSub_industry_code(getTag(jsonObject, KEY_sub_industry_code));
                        trendingStocks.setCompanyName(getTag(jsonObject, KEY_company_name));
                        trendingStocks.setOverview(getTag(jsonObject, KEY_Overview));
                        trendingStocks.setCF_LAST(getTag(jsonObject, KEY_CF_LAST));
                        trendingStocks.setCF_NETCHNG(getTag(jsonObject, KEY_CF_NETCHNG));
                        trendingStocks.setTicker(getTag(jsonObject, KEY_Ticker));
                        trendingStocks.setCF_RIC(getTag(jsonObject, KEY_ric));
                        listStocks.add(trendingStocks);
                    }
                }

                if (rootObject.has("FundsSearch")) {
                    header.setTypeHeader(Type.FUNDS.title());

                    JSONArray FundsSearchJSONArray = rootObject.optJSONArray("FundsSearch");
                    listFunds.clear();
                    for (int j = 0; j < FundsSearchJSONArray.length(); j++) {
                        TrendingFunds trendingStocks = new TrendingFunds();
                        JSONObject jsonObject = FundsSearchJSONArray.getJSONObject(j);
                        trendingStocks.setCF_NAME(getTag(jsonObject, KEY_CF_NAME));
                        trendingStocks.setCF_CURRENCY(getTag(jsonObject, KEY_CF_CURRENCY));
                        trendingStocks.setFundsName(getTag(jsonObject, KEY_name));
                        trendingStocks.setPCTCHNG(getTag(jsonObject, KEY_PCTCHNG));
                        trendingStocks.setOverview(getTag(jsonObject, KEY_Overview));
                        trendingStocks.setLippernos(getTag(jsonObject, KEY_lippernumber));
                        trendingStocks.setCF_TICK(getTag(jsonObject, KEY_CF_TICK));
                        trendingStocks.setPotential_Returns(getTag(jsonObject, KEY_potential_returns));
                        trendingStocks.setCF_LAST(getTag(jsonObject, KEY_CF_LAST));
                        trendingStocks.setCF_NETCHNG(getTag(jsonObject, KEY_CF_NETCHNG));
                        listFunds.add(trendingStocks);
                    }
                }

                if (rootObject.has("AnalystSearchResult")) {
                    header.setTypeHeader(Type.ANAlYSTS.title());

                    JSONArray FundsSearchJSONArray = rootObject.optJSONArray("AnalystSearchResult");
                    listAnalysts.clear();
                    for (int j = 0; j < FundsSearchJSONArray.length(); j++) {
                        TrendingAnalysts trendingStocks = new TrendingAnalysts();
                        JSONObject jsonObject = FundsSearchJSONArray.getJSONObject(j);
                        trendingStocks.setAnalystid(getTag(jsonObject, KEY_analystid));
                        trendingStocks.setAnayst_compay_name(getTag(jsonObject, KEY_anayst_compay_name));
                        trendingStocks.setFigs_analyst_score(getTag(jsonObject, KEY_figs_analyst_score));
                        trendingStocks.setAnalystname(getTag(jsonObject, KEY_analystname));

                        listAnalysts.add(trendingStocks);
                    }
                }

                sectorArrayList.add(header);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sectorArrayList;
    }

    private String getTag(final JSONObject jsonObject, final String key) throws JSONException {
        if (jsonObject.has(key))
            return jsonObject.getString(key);
        else
            return "";
    }

    public enum Type {
        STOCKS("Stocks"),
        FUNDS("Funds"),
        ANAlYSTS("Analysts");

        private String title;

        Type(String title) {
            this.title = title;
        }

        public String title() {
            return title;
        }
    }



}


