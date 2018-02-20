package com.figsinc.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.figsinc.app.analyze.trendinganalysts.AnalystInfoActivity;
import com.figsinc.app.analyze.trendingstocks.StockInfoActivity;
import com.figsinc.app.learn.news.NewsDetailActivity;
import com.figsinc.app.netdhania.LoadInitialConfigurationDataTask;
import com.figsinc.app.netdhania.PriceBarParse;
import com.netdania.NDChartAPI;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Created by K. Rajesh on 8/30/17.
 */

public class Constants {

    // public static String SERVER_URL = "http://13.228.92.201:8000/";
    //public static String SERVER_URL = "https://alpha.figsinc.com:8000/";
    public static String SERVER_URL = "https://devapi.figsinc.com/";

    public static String PREF_NAME = "FIGS_PREF";

    public static String login = SERVER_URL + "auth/login/";
    public static String socialLogin = SERVER_URL + "auth/social/";
    public static String sectorList = SERVER_URL + "api/v1/sector/";
    public static String sectorDetails = SERVER_URL + "api/v1/sector/";

    public static String thematicsList = SERVER_URL + "api/v1/thematics/";
    public static String thematicsDetails = SERVER_URL + "api/v1/thematics/";

    public static String sentimentsIndex = SERVER_URL + "SentimentIndex/";
    public static String trendingStocks = SERVER_URL + "CompanyRueters/?TopStocks=54";
    public static String trendingStocksFilter = SERVER_URL + "CompanyRueters/";
    public static String trendingStocksBySectorID = SERVER_URL + "CompanyRueters/?SectorID=";

    public static String trendingStocksByThemeID = SERVER_URL + "CompanyRueters/?ThematicID=";

    public static String sectorDetailsTopFiveSector = SERVER_URL + "api/v1/sectortop5/";//Other Ideas
    public static String thmeDetailsTopFiveThemes = SERVER_URL + "api/v1/thematicstop5/";//Other Ideas

    public static String trendingFunds = SERVER_URL + "fundsTopSix/?TopFundsNos=54";
    public static String trendingFundsFilter = SERVER_URL + "fundsTopSix/";
    public static String trendingAnalysts = SERVER_URL + "AnalystTopSix/?TopAnalystNos=54";

    public static String stockInfoTopStocksDetails = SERVER_URL + "CompanyRueters/?SectorID=";
    public static String sockInfoStockDetailsDescription = SERVER_URL + "Companyoverview/?tag=";
    public static String sockInfoAnalystCompanyTopSix = SERVER_URL + "AnalystCompanyTopSix/?CompanyID=";


    public static String fundInfoDescription = SERVER_URL + "FundsOverview/?FundsID=";
    public static String FundsTopTendHoldings = SERVER_URL + "FundsTopTendHoldings/?FundsID=";
    public static String fundInfoDetails = SERVER_URL + "FundsDetails/?FundsID=";
    public static String fundInfoFundsPerfrormance = SERVER_URL + "FundsPerformance/?FundsID=";

    public static String calculator = SERVER_URL + "api/v1/calculatorsearch/?";
    public static String finalCalculatorValues = SERVER_URL + "api/v1/calculator/?tag=";
    public static String trendingAnalystDetails = SERVER_URL + "AnalystDetails/?AnalystID=";

    public static String saveSector = SERVER_URL + "getsetCollectIdeas/?SavedSector=";
    public static String saveTheme = SERVER_URL + "getsetCollectIdeas/?SavedThemes=";
    public static String saveFunds = SERVER_URL + "getsetCollectIdeas/?SavedSector=";
    public static String saveStocks = SERVER_URL + "getsetCollectStocks/?SavedStocks=";
    public static String saveAnalysts = SERVER_URL + "getsetCollectAnalyst/?SavedAnalyst=";

    public static String collectSavedStocks = SERVER_URL + "getsetCollectStocks/";
    public static String collectSavedFunds = SERVER_URL + "getsetCollectFunds/";

    public static String stocksFundamentalInformationValuation = SERVER_URL + "CompanyValuationYearly/?Ticker_Exchange=";
    public static String stocksFundamentalInformationProfitability = SERVER_URL + "CompanyProfitabilityYearly/?Ticker_Exchange=";
    public static String stocksFundamentalInformationEfficiency = SERVER_URL + "CompanyEfficiencyYearly/?Ticker_Exchange=";
    public static String stocksFundamentalInformationCapitalStructure = SERVER_URL + "CompanyCapitalStructureYearly/?Ticker_Exchange=";
    public static String stocksCandleStickChart = SERVER_URL + "CompanyHistorical/?tag=";
    public static String register = SERVER_URL + "auth/register";
    public static String newsAPI = SERVER_URL + "api/v1/news/?query=";

    public static boolean isDebug = false;
    public static String lauguage = "&lang=english";

    public static String readyToTradeUrlSbiInsurance = "https://www.sbisec.co.jp/ETGate";
    public static String readyToTradeUrlSbiSecurities = "https://www.netbk.co.jp/wpl/NBGate";
    public static String readyToTradeUrlSumishinNetBank = "http://www.sbisonpo.co.jp/";

    public static String collectSavedideas = SERVER_URL + "getsetCollectIdeas/";
    public static String collectSavedAnalysts = SERVER_URL + "getsetCollectAnalyst/";
    public static String collectGetUserDetails = SERVER_URL + "getCollectUserProfile/";

    //public static String sectorImageUrl="http://52.221.212.166/dist/assets/siteImages/sector/";
    public static String sectorImageUrl = "http://54.254.165.187/dist/assets/siteImages/sector/";
    //public static String thematicsImageUrl="http://52.221.212.166/dist/assets/siteImages/thematics/";
    public static String thematicsImageUrl = "http://54.254.165.187/dist/assets/siteImages/thematics/";
    // public static String trendingAnalystsProfileImage = "http://54.169.52.96/figs_data/pdf/analyst/";
    public static String trendingAnalystsProfileImage = "http://d1z4o3o2zsjyg.cloudfront.net/";

    public static String trendingAnalystsProfileImageHolder = "http://54.169.52.96/figs_data/pdf/analyst/Male_07.png";

    public static String thematicsFilterCategory = SERVER_URL + "api/v1/thematicscategory/";
    public static String sectorFilterCategory = SERVER_URL + "api/v1/sectorcategory/";

    public static String FilterRegion = SERVER_URL + "RegionList/";

    public static String SectorFilter = SERVER_URL + "api/v1/sector/";
    // Secotr - Top Funds -
    public static String sectorStockHoldings = SERVER_URL + "CompanyTopTen/?SectorID=";
    public static String calculatorCompanyListAutoFill = SERVER_URL + "CompanyCalList/?searchDet=";

    public static String searchAutoCompleteAnalyse = SERVER_URL + "SearchDropdown/?newstag=";

    public static String searchStockInfo = SERVER_URL + "CompanyStockDetails/?";  //http://13.228.92.201:8000/CompanyStockDetails/?tag=AAPL.OQ&tickerexchange=AAPL-NAS
    public static String userSettings = SERVER_URL + "usersettings/";

    // SERVER_URL+"CompanyTopTen/?SectorID=50102010";

    public static void setStatusBar(final Activity context, int colorCode) {
        Window window = context.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(context, colorCode));
    }

    public static void readyToTrade(final Context context, final View sheetView) {
        LinearLayout liearWeb1 = (LinearLayout) sheetView.findViewById(R.id.liearWeb1);
        LinearLayout liearWeb2 = (LinearLayout) sheetView.findViewById(R.id.liearWeb2);
        LinearLayout liearWeb3 = (LinearLayout) sheetView.findViewById(R.id.liearWeb3);
        liearWeb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("DetailsUrl", readyToTradeUrlSbiInsurance);
                context.startActivity(intent);

            }
        });

        liearWeb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("DetailsUrl", readyToTradeUrlSbiSecurities);
                context.startActivity(intent);
            }
        });

        liearWeb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("DetailsUrl", readyToTradeUrlSumishinNetBank);
                context.startActivity(intent);
            }
        });
    }

    public static final int SHOW_DETAILS_MAX_LINES = 5;

    public static final String fundsHistory = SERVER_URL + "FundsHistory/?FundsID=";
    public static final String FilterDetails = SERVER_URL + "FilterDetails/?RegionID=";
    public static final String SearchDetails = SERVER_URL + "SearchDetails?newstag=";

    // http://13.228.92.201:8000/CompanyHistorical/?tag=MSFT.O


    private static int statusCode = 0;

    public static void savedToFigs(final Context context, final String url, final int method, final CoordinatorLayout coordinatorLayout) {

        try {
            // Instantiate the RequestQueue.
            final RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(method, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (method == Request.Method.POST) {
                                Snackbar snackbar = Snackbar
                                        .make(coordinatorLayout, "Saved to Figs", Snackbar.LENGTH_LONG)
                                        .setAction("UNDO", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                savedToFigs(context, url, Request.Method.DELETE, coordinatorLayout);
                                            }
                                        });

                                snackbar.show();
                            } else if (method == Request.Method.DELETE) {
                                Snackbar.make(coordinatorLayout, "Removed from Ideas!", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.statusCode == 500 || networkResponse.statusCode == 404/*HttpStatu.SC_UNAUTHORIZED*/) {
                        Snackbar.make(coordinatorLayout, "Already Saved", Snackbar.LENGTH_LONG).show();
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Token " + FigsApplication.getAuthToken());
                    return headers;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getCountryCode(final String countryName) {
        System.out.println(" 77777777777777777777 " + countryName);
        String countryCode = "";
        String[] locales = Locale.getISOCountries();

        for (String country : locales) {

            Locale obj = new Locale("", country);

            if (obj.getDisplayCountry().equals(countryName)) {
                countryCode = obj.getCountry();
            }
        }
        System.out.println(" 77777777777777777777 " + countryCode);
        return countryCode;
    }


}
