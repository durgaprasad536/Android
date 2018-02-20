package com.figsinc.app.analyze.model;

/**
 * Created by 461883 on 9/29/17.
 */

public class FundsTopTenHolding {

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getPotentialreturns() {
        return potentialreturns;
    }

    public void setPotentialreturns(String potentialreturns) {
        this.potentialreturns = potentialreturns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice_close() {
        return price_close;
    }

    public void setPrice_close(String price_close) {
        this.price_close = price_close;
    }

    String percentage;
    String potentialreturns;
    String name;
    String price_close;
}
