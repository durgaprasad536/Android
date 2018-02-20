package com.figsinc.app.analyze.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.figsinc.app.utils.ListItem;

/**
 * Created by 461883 on 9/12/17.
 */

public class TrendingStocks extends ListItem implements Parcelable {

    String CF_NAME;
    String Ticker_Exchange;
    String CF_TICK;
    String PCTCHNG;
    String CompanyName;
    String CF_LAST;
    String CF_RIC;
    String Exchange;
    String ConcensusPrice;
    String Potential_Returns;
    String CF_NETCHNG;
    String CF_CF_CURRENCY;
    String Ticker;
    String Sectorid_stock;
    String Overview;
    String sub_industry_code;
    String Region;
    String CF_LOW;
    String CF_HIGH;
    String CF_ACTIVE;
    String CF_OPEN;
    String Net_Asset;

    public String getSub_industry_code() {
        return sub_industry_code;
    }

    public void setSub_industry_code(String sub_industry_code) {
        this.sub_industry_code = sub_industry_code;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }


    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getCF_LOW() {
        return CF_LOW;
    }

    public void setCF_LOW(String CF_LOW) {
        this.CF_LOW = CF_LOW;
    }

    public String getCF_HIGH() {
        return CF_HIGH;
    }

    public void setCF_HIGH(String CF_HIGH) {
        this.CF_HIGH = CF_HIGH;
    }

    public String getCF_ACTIVE() {
        return CF_ACTIVE;
    }

    public void setCF_ACTIVE(String CF_ACTIVE) {
        this.CF_ACTIVE = CF_ACTIVE;
    }

    public String getCF_OPEN() {
        return CF_OPEN;
    }

    public void setCF_OPEN(String CF_OPEN) {
        this.CF_OPEN = CF_OPEN;
    }

    public static Creator<TrendingStocks> getCREATOR() {
        return CREATOR;
    }


    public String getNet_Asset() {
        return Net_Asset;
    }

    public void setNet_Asset(String net_Asset) {
        Net_Asset = net_Asset;
    }

    public TrendingStocks() {

    }

    protected TrendingStocks(Parcel in) {
        CF_NAME = in.readString();
        Ticker_Exchange = in.readString();
        CF_TICK = in.readString();
        PCTCHNG = in.readString();
        CompanyName = in.readString();
        CF_LAST = in.readString();
        CF_RIC = in.readString();
        Exchange = in.readString();
        ConcensusPrice = in.readString();
        Potential_Returns = in.readString();
        CF_NETCHNG = in.readString();
        CF_CF_CURRENCY = in.readString();
        Ticker = in.readString();
        Sectorid_stock = in.readString();
        Overview = in.readString();
        sub_industry_code = in.readString();
    }

    public static final Creator<TrendingStocks> CREATOR = new Creator<TrendingStocks>() {
        @Override
        public TrendingStocks createFromParcel(Parcel in) {
            return new TrendingStocks(in);
        }

        @Override
        public TrendingStocks[] newArray(int size) {
            return new TrendingStocks[size];
        }
    };

    public String getSectorid_stock() {
        return Sectorid_stock;
    }

    public void setSectorid_stock(String sectorid_stock) {
        Sectorid_stock = sectorid_stock;
    }

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

    public String getCF_TICK() {
        return CF_TICK;
    }

    public void setCF_TICK(String CF_TICK) {
        this.CF_TICK = CF_TICK;
    }

    public String getPCTCHNG() {
        return PCTCHNG;
    }

    public void setPCTCHNG(String PCTCHNG) {
        this.PCTCHNG = PCTCHNG;
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

    public String getCF_RIC() {
        return CF_RIC;
    }

    public void setCF_RIC(String CF_RIC) {
        this.CF_RIC = CF_RIC;
    }

    public String getExchange() {
        return Exchange;
    }

    public void setExchange(String exchange) {
        Exchange = exchange;
    }

    public String getConcensusPrice() {
        return ConcensusPrice;
    }

    public void setConcensusPrice(String concensusPrice) {
        ConcensusPrice = concensusPrice;
    }

    public String getPotential_Returns() {
        return Potential_Returns;
    }

    public void setPotential_Returns(String potential_Returns) {
        Potential_Returns = potential_Returns;
    }

    public String getCF_NETCHNG() {
        return CF_NETCHNG;
    }

    public void setCF_NETCHNG(String CF_NETCHNG) {
        this.CF_NETCHNG = CF_NETCHNG;
    }

    public String getCF_CF_CURRENCY() {
        return CF_CF_CURRENCY;
    }

    public void setCF_CF_CURRENCY(String CF_CF_CURRENCY) {
        this.CF_CF_CURRENCY = CF_CF_CURRENCY;
    }

    public String getTicker() {
        return Ticker;
    }

    public void setTicker(String ticker) {
        Ticker = ticker;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(getCF_NAME());
        dest.writeString(getTicker_Exchange());
        dest.writeString(getCF_TICK());
        dest.writeString(getPCTCHNG());
        dest.writeString(getCompanyName());
        dest.writeString(getCF_LAST());
        dest.writeString(getCF_RIC());
        dest.writeString(getExchange());
        dest.writeString(getConcensusPrice());
        dest.writeString(getPotential_Returns());
        dest.writeString(getCF_NETCHNG());
        dest.writeString(getCF_CF_CURRENCY());
        dest.writeString(getTicker());
        dest.writeString(getSectorid_stock());
        dest.writeString(getOverview());
        dest.writeString(getSub_industry_code());
    }


    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public String getRegionHeader() {
        return super.getRegionHeader();
    }

}
