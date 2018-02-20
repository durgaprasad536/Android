package com.figsinc.app.analyze.model;

import com.figsinc.app.utils.ListItem;

/**
 * Created by 461883 on 9/29/17.
 */

public class StockCoverage extends ListItem {

    private String regionHeader;

    @Override
    public String getRegionHeader() {
        return regionHeader;
    }

    @Override
    public void setRegionHeader(String regionHeader) {
        this.regionHeader = regionHeader;
    }

    String CF_NAME;
    String Ticker_Exchange;
    String Average_returns;
    String PCTCHNG;
    String CF_TICK;
    String CompanyName;
    String CF_LAST;

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    String Region;
    String Average_Time;
    String CF_ACTIVE;
    String Potential_Returns;
    String Position;
    String Accurace_rate;
    String MarketCap;
    String CF_NETCHNG;
    String Sectorid_stock;
    String Ticker;
    String CF_RIC;

    public String getCF_NAME() {
        return CF_NAME;
    }

    public void setCF_NAME(String CF_NAME) {
        this.CF_NAME = CF_NAME;
    }

    public String getTicker_Exchange() {
        return Ticker_Exchange;
    }

    public void setTicker_Exchange(String ticker_Exchange) {
        Ticker_Exchange = ticker_Exchange;
    }

    public String getAverage_returns() {
        return Average_returns;
    }

    public void setAverage_returns(String average_returns) {
        Average_returns = average_returns;
    }

    public String getPCTCHNG() {
        return PCTCHNG;
    }

    public void setPCTCHNG(String PCTCHNG) {
        this.PCTCHNG = PCTCHNG;
    }

    public String getCF_TICK() {
        return CF_TICK;
    }

    public void setCF_TICK(String CF_TICK) {
        this.CF_TICK = CF_TICK;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCF_LAST() {
        return CF_LAST;
    }

    public void setCF_LAST(String CF_LAST) {
        this.CF_LAST = CF_LAST;
    }

    public String getAverage_Time() {
        return Average_Time;
    }

    public void setAverage_Time(String average_Time) {
        Average_Time = average_Time;
    }

    public String getCF_ACTIVE() {
        return CF_ACTIVE;
    }

    public void setCF_ACTIVE(String CF_ACTIVE) {
        this.CF_ACTIVE = CF_ACTIVE;
    }

    public String getPotential_Returns() {
        return Potential_Returns;
    }

    public void setPotential_Returns(String potential_Returns) {
        Potential_Returns = potential_Returns;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getAccurace_rate() {
        return Accurace_rate;
    }

    public void setAccurace_rate(String accurace_rate) {
        Accurace_rate = accurace_rate;
    }

    public String getMarketCap() {
        return MarketCap;
    }

    public void setMarketCap(String marketCap) {
        MarketCap = marketCap;
    }

    public String getCF_NETCHNG() {
        return CF_NETCHNG;
    }

    public void setCF_NETCHNG(String CF_NETCHNG) {
        this.CF_NETCHNG = CF_NETCHNG;
    }

    public String getSectorid_stock() {
        return Sectorid_stock;
    }

    public void setSectorid_stock(String sectorid_stock) {
        Sectorid_stock = sectorid_stock;
    }

    public String getTicker() {
        return Ticker;
    }

    public void setTicker(String ticker) {
        Ticker = ticker;
    }

    public String getCF_RIC() {
        return CF_RIC;
    }

    public void setCF_RIC(String CF_RIC) {
        this.CF_RIC = CF_RIC;
    }


}
