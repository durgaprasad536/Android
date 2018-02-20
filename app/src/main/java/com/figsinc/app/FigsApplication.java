package com.figsinc.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.figsinc.app.utils.language.LanguageHelper;
import com.figsinc.app.utils.language.LanguageUtil;
import com.figsinc.app.utils.language.SPUtils;
import com.netdania.NDChartAPI;
import com.netdania.ui.chart.xml.ApplicationChartDatabase;

import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.figsinc.app.Constants.PREF_NAME;

/**
 * Created by K. Rajesh on 8/19/17.
 */

public class FigsApplication extends Application {

    private static Context context;
    private Typeface normalFont;
    private Typeface boldFont;
    private Typeface semiBoldFont;

    @Override
    public void onCreate() {
        super.onCreate();
       /* CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/opensans_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );*/
        FacebookSdk.sdkInitialize(getApplicationContext());

       context = this;

        String localLanguageType = SPUtils.getLocalLanguageType(context);
        if ("日本語".equals(localLanguageType)) {
            LanguageUtil.changeLanguageType(context, Locale.JAPANESE);
        } else {
            LanguageUtil.changeLanguageType(context, Locale.ENGLISH);
        }
    }

    public static String getAuthToken(){
        SharedPreferences shared = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String auth_token = shared.getString("auth_token","");
        return "Bearer " +auth_token;
    }




    public void setNormalFont(TextView textView) {
        if(normalFont == null) {
            normalFont = Typeface.createFromAsset(getAssets(),"fonts/opensans_regular.ttf");
        }
        textView.setTypeface(normalFont);
    }

    public void setSemiBoldFont(TextView textView) {
        if(semiBoldFont == null) {
            semiBoldFont = Typeface.createFromAsset(getAssets(),"fonts/opensans_semibold.ttf");
        }
        textView.setTypeface(semiBoldFont);
    }

    public void setBoldFont(TextView textView) {
        if(boldFont == null) {
            boldFont = Typeface.createFromAsset(getAssets(),"fonts/opensans_bold.ttf");
        }
        textView.setTypeface(boldFont);
    }

    private final ExecutorService threadPoolService;
    private NDChartAPI frameworkConfiguration;

    public FigsApplication() {
        this.threadPoolService = Executors.newCachedThreadPool();
    }

    public Executor getThreadPool() {
        return threadPoolService;
    }

    public void setFrameworkConfiguration(NDChartAPI frameworkConfiguration) {
        this.frameworkConfiguration = frameworkConfiguration;
    }

    public ApplicationChartDatabase getApplicationChartDatabase() {
        return frameworkConfiguration.getApplicationChartDatabase();
    }

    //This is new remote branch for ReSkin Android.


    // override the base context of application to update default locale for the application
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base, LanguageHelper.getLanguage(base)));
    }

    public static Context getContext() {
        return context;
    }
}
