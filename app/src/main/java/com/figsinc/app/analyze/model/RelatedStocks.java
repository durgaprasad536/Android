package com.figsinc.app.analyze.model;

/**
 * Created by 461883 on 12/25/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelatedStocks {

    @SerializedName("CF_NAME")
    @Expose
    private String cFNAME;
    @SerializedName("Ticker_Exchange")
    @Expose
    private String tickerExchange;
    @SerializedName("CF_TIME")
    @Expose
    private String cFTIME;
    @SerializedName("CompanyName_CHN")
    @Expose
    private String companyNameCHN;
    @SerializedName("CF_TICK")
    @Expose
    private Object cFTICK;
    @SerializedName("PCTCHNG")
    @Expose
    private String pCTCHNG;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("CF_LAST")
    @Expose
    private Integer cFLAST;
    @SerializedName("CF_RIC")
    @Expose
    private String cFRIC;
    @SerializedName("Exchange")
    @Expose
    private String exchange;
    @SerializedName("CompanyName_JPN")
    @Expose
    private String companyNameJPN;
    @SerializedName("Exchange_JPN")
    @Expose
    private String exchangeJPN;
    @SerializedName("CF_DATE")
    @Expose
    private String cFDATE;
    @SerializedName("ConcensusPrice")
    @Expose
    private Integer concensusPrice;
    @SerializedName("Potential_Returns")
    @Expose
    private String potentialReturns;
    @SerializedName("Sectorid_stock")
    @Expose
    private Integer sectoridStock;
    @SerializedName("CF_NETCHNG")
    @Expose
    private Integer cFNETCHNG;
    @SerializedName("CF_CF_CURRENCY")
    @Expose
    private String cFCFCURRENCY;
    @SerializedName("Ticker")
    @Expose
    private String ticker;
    @SerializedName("Company_MKT_Cap")
    @Expose
    private Integer companyMKTCap;

    public String getCFNAME() {
        return cFNAME;
    }

    public void setCFNAME(String cFNAME) {
        this.cFNAME = cFNAME;
    }

    public String getTickerExchange() {
        return tickerExchange;
    }

    public void setTickerExchange(String tickerExchange) {
        this.tickerExchange = tickerExchange;
    }

    public String getCFTIME() {
        return cFTIME;
    }

    public void setCFTIME(String cFTIME) {
        this.cFTIME = cFTIME;
    }

    public String getCompanyNameCHN() {
        return companyNameCHN;
    }

    public void setCompanyNameCHN(String companyNameCHN) {
        this.companyNameCHN = companyNameCHN;
    }

    public Object getCFTICK() {
        return cFTICK;
    }

    public void setCFTICK(Object cFTICK) {
        this.cFTICK = cFTICK;
    }

    public String getPCTCHNG() {
        return pCTCHNG;
    }

    public void setPCTCHNG(String pCTCHNG) {
        this.pCTCHNG = pCTCHNG;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCFLAST() {
        return cFLAST;
    }

    public void setCFLAST(Integer cFLAST) {
        this.cFLAST = cFLAST;
    }

    public String getCFRIC() {
        return cFRIC;
    }

    public void setCFRIC(String cFRIC) {
        this.cFRIC = cFRIC;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getCompanyNameJPN() {
        return companyNameJPN;
    }

    public void setCompanyNameJPN(String companyNameJPN) {
        this.companyNameJPN = companyNameJPN;
    }

    public String getExchangeJPN() {
        return exchangeJPN;
    }

    public void setExchangeJPN(String exchangeJPN) {
        this.exchangeJPN = exchangeJPN;
    }

    public String getCFDATE() {
        return cFDATE;
    }

    public void setCFDATE(String cFDATE) {
        this.cFDATE = cFDATE;
    }

    public Integer getConcensusPrice() {
        return concensusPrice;
    }

    public void setConcensusPrice(Integer concensusPrice) {
        this.concensusPrice = concensusPrice;
    }

    public String getPotentialReturns() {
        return potentialReturns;
    }

    public void setPotentialReturns(String potentialReturns) {
        this.potentialReturns = potentialReturns;
    }

    public Integer getSectoridStock() {
        return sectoridStock;
    }

    public void setSectoridStock(Integer sectoridStock) {
        this.sectoridStock = sectoridStock;
    }

    public Integer getCFNETCHNG() {
        return cFNETCHNG;
    }

    public void setCFNETCHNG(Integer cFNETCHNG) {
        this.cFNETCHNG = cFNETCHNG;
    }

    public String getCFCFCURRENCY() {
        return cFCFCURRENCY;
    }

    public void setCFCFCURRENCY(String cFCFCURRENCY) {
        this.cFCFCURRENCY = cFCFCURRENCY;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Integer getCompanyMKTCap() {
        return companyMKTCap;
    }

    public void setCompanyMKTCap(Integer companyMKTCap) {
        this.companyMKTCap = companyMKTCap;
    }

}
