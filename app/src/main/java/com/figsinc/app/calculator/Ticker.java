package com.figsinc.app.calculator;

/**
 * Created by 461883 on 12/13/17.
 */

public class Ticker {

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTicker_Exchange() {
        return Ticker_Exchange;
    }

    public void setTicker_Exchange(String ticker_Exchange) {
        Ticker_Exchange = ticker_Exchange;
    }

    String companyName;
    String Ticker_Exchange;

}
