package com.figsinc.app.analyze.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyStockDetails {

@SerializedName("CF_NAME")
@Expose
private String cFNAME;
@SerializedName("CompanyName_JPN")
@Expose
private String companyNameJPN;
@SerializedName("CF_PERATIO")
@Expose
private String cFPERATIO;
@SerializedName("CF_YRLOW")
@Expose
private String cFYRLOW;
@SerializedName("CF_YRHIGH")
@Expose
private String cFYRHIGH;
@SerializedName("NosOfBuy")
@Expose
private String nosOfBuy;
@SerializedName("TRADE_DATE")
@Expose
private String tRADEDATE;
@SerializedName("CF_TICK")
@Expose
private String cFTICK;
@SerializedName("PCTCHNG")
@Expose
private String pCTCHNG;
@SerializedName("Exchange")
@Expose
private String exchange;
@SerializedName("Exchange_JPN")
@Expose
private String exchangeJPN;
@SerializedName("Potential_Returns")
@Expose
private String potentialReturns;
@SerializedName("CF_DIVIDEND")
@Expose
private String cFDIVIDEND;
@SerializedName("Ticker")
@Expose
private String ticker;
@SerializedName("CF_VOLUME")
@Expose
private String cFVOLUME;
@SerializedName("NosofSell")
@Expose
private String nosofSell;
@SerializedName("NosofHold")
@Expose
private String nosofHold;
@SerializedName("CF_NETCHNG")
@Expose
private String cFNETCHNG;
@SerializedName("CF_CURRENCY")
@Expose
private String cFCURRENCY;
@SerializedName("CF_LAST")
@Expose
private String cFLAST;
@SerializedName("CF_EXDIVDATE")
@Expose
private String cFEXDIVDATE;
@SerializedName("ConcensusPrice")
@Expose
private String concensusPrice;
@SerializedName("CompanyName_CHN")
@Expose
private String companyNameCHN;

public String getCFNAME() {
return cFNAME;
}

public void setCFNAME(String cFNAME) {
this.cFNAME = cFNAME;
}

public String getCompanyNameJPN() {
return companyNameJPN;
}

public void setCompanyNameJPN(String companyNameJPN) {
this.companyNameJPN = companyNameJPN;
}

public String getCFPERATIO() {
return cFPERATIO;
}

public void setCFPERATIO(String cFPERATIO) {
this.cFPERATIO = cFPERATIO;
}

public String getCFYRLOW() {
return cFYRLOW;
}

public void setCFYRLOW(String cFYRLOW) {
this.cFYRLOW = cFYRLOW;
}

public String getCFYRHIGH() {
return cFYRHIGH;
}

public void setCFYRHIGH(String cFYRHIGH) {
this.cFYRHIGH = cFYRHIGH;
}

public String getNosOfBuy() {
return nosOfBuy;
}

public void setNosOfBuy(String nosOfBuy) {
this.nosOfBuy = nosOfBuy;
}

public String getTRADEDATE() {
return tRADEDATE;
}

public void setTRADEDATE(String tRADEDATE) {
this.tRADEDATE = tRADEDATE;
}

public String getCFTICK() {
return cFTICK;
}

public void setCFTICK(String cFTICK) {
this.cFTICK = cFTICK;
}

public String getPCTCHNG() {
return pCTCHNG;
}

public void setPCTCHNG(String pCTCHNG) {
this.pCTCHNG = pCTCHNG;
}

public String getExchange() {
return exchange;
}

public void setExchange(String exchange) {
this.exchange = exchange;
}

public String getExchangeJPN() {
return exchangeJPN;
}

public void setExchangeJPN(String exchangeJPN) {
this.exchangeJPN = exchangeJPN;
}

public String getPotentialReturns() {
return potentialReturns;
}

public void setPotentialReturns(String potentialReturns) {
this.potentialReturns = potentialReturns;
}

public String getCFDIVIDEND() {
return cFDIVIDEND;
}

public void setCFDIVIDEND(String cFDIVIDEND) {
this.cFDIVIDEND = cFDIVIDEND;
}

public String getTicker() {
return ticker;
}

public void setTicker(String ticker) {
this.ticker = ticker;
}

public String getCFVOLUME() {
return cFVOLUME;
}

public void setCFVOLUME(String cFVOLUME) {
this.cFVOLUME = cFVOLUME;
}

public String getNosofSell() {
return nosofSell;
}

public void setNosofSell(String nosofSell) {
this.nosofSell = nosofSell;
}

public String getNosofHold() {
return nosofHold;
}

public void setNosofHold(String nosofHold) {
this.nosofHold = nosofHold;
}

public String getCFNETCHNG() {
return cFNETCHNG;
}

public void setCFNETCHNG(String cFNETCHNG) {
this.cFNETCHNG = cFNETCHNG;
}

public String getCFCURRENCY() {
return cFCURRENCY;
}

public void setCFCURRENCY(String cFCURRENCY) {
this.cFCURRENCY = cFCURRENCY;
}

public String getCFLAST() {
return cFLAST;
}

public void setCFLAST(String cFLAST) {
this.cFLAST = cFLAST;
}

public String getCFEXDIVDATE() {
return cFEXDIVDATE;
}

public void setCFEXDIVDATE(String cFEXDIVDATE) {
this.cFEXDIVDATE = cFEXDIVDATE;
}

public String getConcensusPrice() {
return concensusPrice;
}

public void setConcensusPrice(String concensusPrice) {
this.concensusPrice = concensusPrice;
}

public String getCompanyNameCHN() {
return companyNameCHN;
}

public void setCompanyNameCHN(String companyNameCHN) {
this.companyNameCHN = companyNameCHN;
}

}