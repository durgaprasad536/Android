package com.figsinc.app.analyze.model;

/**
 * Created by 461883 on 12/25/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FundsOverView {

    @SerializedName("CF_NAME")
    @Expose
    private String cFNAME;
    @SerializedName("CF_TICK")
    @Expose
    private Object cFTICK;
    @SerializedName("AssetManager")
    @Expose
    private String assetManager;
    @SerializedName("TechnicalIndicator")
    @Expose
    private String technicalIndicator;
    @SerializedName("Initial")
    @Expose
    private Double initial;
    @SerializedName("Domicile")
    @Expose
    private String domicile;
    @SerializedName("LegalStructure")
    @Expose
    private String legalStructure;
    @SerializedName("TNACurrency")
    @Expose
    private String tNACurrency;
    @SerializedName("Administrator")
    @Expose
    private String administrator;
    @SerializedName("PCTCHNG")
    @Expose
    private String pCTCHNG;
    @SerializedName("Custodian")
    @Expose
    private String custodian;
    @SerializedName("Advisor")
    @Expose
    private String advisor;
    @SerializedName("ISIN")
    @Expose
    private String iSIN;
    @SerializedName("MinimumInvestmentIRRegular")
    @Expose
    private Object minimumInvestmentIRRegular;
    @SerializedName("Redemption")
    @Expose
    private Double redemption;
    @SerializedName("FundsName_JPN")
    @Expose
    private String fundsNameJPN;
    @SerializedName("Annual")
    @Expose
    private Object annual;
    @SerializedName("FundManagementCompany")
    @Expose
    private String fundManagementCompany;
    @SerializedName("CF_DATE")
    @Expose
    private String cFDATE;
    @SerializedName("TNADate")
    @Expose
    private String tNADate;
    @SerializedName("GeographicalFocus")
    @Expose
    private String geographicalFocus;
    @SerializedName("CF_NETCHNG")
    @Expose
    private Double cFNETCHNG;
    @SerializedName("FundsName")
    @Expose
    private String fundsName;
    @SerializedName("CF_LAST")
    @Expose
    private Double cFLAST;
    @SerializedName("FundsName_CHN")
    @Expose
    private String fundsNameCHN;
    @SerializedName("CF_TIME")
    @Expose
    private String cFTIME;
    @SerializedName("CF_CURRENCY")
    @Expose
    private String cFCURRENCY;
    @SerializedName("RiskFreeIndex")
    @Expose
    private String riskFreeIndex;
    @SerializedName("Overview")
    @Expose
    private String overview;
    @SerializedName("MinimumInvestmentInitial")
    @Expose
    private Double minimumInvestmentInitial;
    @SerializedName("MinimumInvestmentRegular")
    @Expose
    private Object minimumInvestmentRegular;
    @SerializedName("PotentialReturn")
    @Expose
    private Double potentialReturn;
    @SerializedName("TNA")
    @Expose
    private Double tNA;

    public String getCFNAME() {
        return cFNAME;
    }

    public void setCFNAME(String cFNAME) {
        this.cFNAME = cFNAME;
    }

    public Object getCFTICK() {
        return cFTICK;
    }

    public void setCFTICK(Object cFTICK) {
        this.cFTICK = cFTICK;
    }

    public String getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(String assetManager) {
        this.assetManager = assetManager;
    }

    public String getTechnicalIndicator() {
        return technicalIndicator;
    }

    public void setTechnicalIndicator(String technicalIndicator) {
        this.technicalIndicator = technicalIndicator;
    }

    public Double getInitial() {
        return initial;
    }

    public void setInitial(Double initial) {
        this.initial = initial;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getLegalStructure() {
        return legalStructure;
    }

    public void setLegalStructure(String legalStructure) {
        this.legalStructure = legalStructure;
    }

    public String getTNACurrency() {
        return tNACurrency;
    }

    public void setTNACurrency(String tNACurrency) {
        this.tNACurrency = tNACurrency;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }

    public String getPCTCHNG() {
        return pCTCHNG;
    }

    public void setPCTCHNG(String pCTCHNG) {
        this.pCTCHNG = pCTCHNG;
    }

    public String getCustodian() {
        return custodian;
    }

    public void setCustodian(String custodian) {
        this.custodian = custodian;
    }

    public String getAdvisor() {
        return advisor;
    }

    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }

    public String getISIN() {
        return iSIN;
    }

    public void setISIN(String iSIN) {
        this.iSIN = iSIN;
    }

    public Object getMinimumInvestmentIRRegular() {
        return minimumInvestmentIRRegular;
    }

    public void setMinimumInvestmentIRRegular(Object minimumInvestmentIRRegular) {
        this.minimumInvestmentIRRegular = minimumInvestmentIRRegular;
    }

    public Double getRedemption() {
        return redemption;
    }

    public void setRedemption(Double redemption) {
        this.redemption = redemption;
    }

    public String getFundsNameJPN() {
        return fundsNameJPN;
    }

    public void setFundsNameJPN(String fundsNameJPN) {
        this.fundsNameJPN = fundsNameJPN;
    }

    public Object getAnnual() {
        return annual;
    }

    public void setAnnual(Object annual) {
        this.annual = annual;
    }

    public String getFundManagementCompany() {
        return fundManagementCompany;
    }

    public void setFundManagementCompany(String fundManagementCompany) {
        this.fundManagementCompany = fundManagementCompany;
    }

    public String getCFDATE() {
        return cFDATE;
    }

    public void setCFDATE(String cFDATE) {
        this.cFDATE = cFDATE;
    }

    public String getTNADate() {
        return tNADate;
    }

    public void setTNADate(String tNADate) {
        this.tNADate = tNADate;
    }

    public String getGeographicalFocus() {
        return geographicalFocus;
    }

    public void setGeographicalFocus(String geographicalFocus) {
        this.geographicalFocus = geographicalFocus;
    }

    public Double getCFNETCHNG() {
        return cFNETCHNG;
    }

    public void setCFNETCHNG(Double cFNETCHNG) {
        this.cFNETCHNG = cFNETCHNG;
    }

    public String getFundsName() {
        return fundsName;
    }

    public void setFundsName(String fundsName) {
        this.fundsName = fundsName;
    }

    public Double getCFLAST() {
        return cFLAST;
    }

    public void setCFLAST(Double cFLAST) {
        this.cFLAST = cFLAST;
    }

    public String getFundsNameCHN() {
        return fundsNameCHN;
    }

    public void setFundsNameCHN(String fundsNameCHN) {
        this.fundsNameCHN = fundsNameCHN;
    }

    public String getCFTIME() {
        return cFTIME;
    }

    public void setCFTIME(String cFTIME) {
        this.cFTIME = cFTIME;
    }

    public String getCFCURRENCY() {
        return cFCURRENCY;
    }

    public void setCFCURRENCY(String cFCURRENCY) {
        this.cFCURRENCY = cFCURRENCY;
    }

    public String getRiskFreeIndex() {
        return riskFreeIndex;
    }

    public void setRiskFreeIndex(String riskFreeIndex) {
        this.riskFreeIndex = riskFreeIndex;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getMinimumInvestmentInitial() {
        return minimumInvestmentInitial;
    }

    public void setMinimumInvestmentInitial(Double minimumInvestmentInitial) {
        this.minimumInvestmentInitial = minimumInvestmentInitial;
    }

    public Object getMinimumInvestmentRegular() {
        return minimumInvestmentRegular;
    }

    public void setMinimumInvestmentRegular(Object minimumInvestmentRegular) {
        this.minimumInvestmentRegular = minimumInvestmentRegular;
    }

    public Double getPotentialReturn() {
        return potentialReturn;
    }

    public void setPotentialReturn(Double potentialReturn) {
        this.potentialReturn = potentialReturn;
    }

    public Double getTNA() {
        return tNA;
    }

    public void setTNA(Double tNA) {
        this.tNA = tNA;
    }

}