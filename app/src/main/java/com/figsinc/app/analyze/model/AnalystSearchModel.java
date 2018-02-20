package com.figsinc.app.analyze.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dinash on 23-12-2017.
 */

public class AnalystSearchModel {

    @SerializedName("Funds")
    @Expose
    private List<Funds> funds = null;


    @SerializedName("Analysts")
    @Expose
    private List<Analyst> analysts = null;


    @SerializedName("Stock")
    @Expose
    private List<Stock> stock = null;

    public void setFunds(List<Funds> funds) {
        this.funds = funds;
    }

    public List<Funds> getFunds() {
        return funds;
    }

    public List<Analyst> getAnalysts() {
        return analysts;
    }

    public void setAnalysts(List<Analyst> analysts) {
        this.analysts = analysts;
    }

    public List<Stock> getStock() {
        return stock;
    }

    public void setStock(List<Stock> stock) {
        this.stock = stock;
    }
}
