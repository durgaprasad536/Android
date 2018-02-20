package com.figsinc.app.analyze.model;

/**
 * Created by Dinash on 25-12-2017.
 */

public class RecentSearchModel {
    public Analyst analyst;

    public Funds funds;

    public Stock stock;

    public Analyst getAnalyst() {
        return analyst;
    }

    public Funds getFunds() {
        return funds;
    }

    public Stock getStock() {
        return stock;
    }

    public void setFunds(Funds funds) {
        this.funds = funds;
    }

    public void setAnalyst(Analyst analyst) {
        this.analyst = analyst;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
