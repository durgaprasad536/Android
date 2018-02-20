package com.figsinc.app.analyze.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dinash on 23-12-2017.
 */

public class Stock  {

    @SerializedName("ticker_exchange")
    @Expose
    private String tickerExchange;
    @SerializedName("company_japanese_name")
    @Expose
    private String companyJapaneseName;
    @SerializedName("sub_industry_code")
    @Expose
    private Integer subIndustryCode;
    @SerializedName("company_trad_chinese_name")
    @Expose
    private String companyTradChineseName;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("ticker")
    @Expose
    private String ticker;
    @SerializedName("ric")
    @Expose
    private String ric;

    public String getTickerExchange() {
        return tickerExchange;
    }

    public void setTickerExchange(String tickerExchange) {
        this.tickerExchange = tickerExchange;
    }

    public String getCompanyJapaneseName() {
        return companyJapaneseName;
    }

    public void setCompanyJapaneseName(String companyJapaneseName) {
        this.companyJapaneseName = companyJapaneseName;
    }

    public Integer getSubIndustryCode() {
        return subIndustryCode;
    }

    public void setSubIndustryCode(Integer subIndustryCode) {
        this.subIndustryCode = subIndustryCode;
    }

    public String getCompanyTradChineseName() {
        return companyTradChineseName;
    }

    public void setCompanyTradChineseName(String companyTradChineseName) {
        this.companyTradChineseName = companyTradChineseName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getRic() {
        return ric;
    }

    public void setRic(String ric) {
        this.ric = ric;
    }
}
