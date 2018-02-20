package com.figsinc.app.analyze.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dinash on 23-12-2017.
 */

public class Funds {

    @SerializedName("name_jpn")
    @Expose
    private String nameJpn;
    @SerializedName("potential_returns")
    @Expose
    private Double potentialReturns;
    @SerializedName("lippernumber")
    @Expose
    private String lippernumber;
    @SerializedName("name")
    @Expose
    private String name;

    public String getNameJpn() {
        return nameJpn;
    }

    public void setNameJpn(String nameJpn) {
        this.nameJpn = nameJpn;
    }

    public Double getPotentialReturns() {
        return potentialReturns;
    }

    public void setPotentialReturns(Double potentialReturns) {
        this.potentialReturns = potentialReturns;
    }

    public String getLippernumber() {
        return lippernumber;
    }

    public void setLippernumber(String lippernumber) {
        this.lippernumber = lippernumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
