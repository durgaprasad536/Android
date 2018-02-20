package com.figsinc.app.analyze.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 461883 on 9/12/17.
 */

public class TrendingFunds implements Parcelable {

    public  TrendingFunds(){

    }
    String CF_NAME;
    String CF_CURRENCY;
    String PCTCHNG;
    String CF_TICK;
    String FundsName;
    String lippernos;
    String CF_NETCHNG;
    String Potential_Returns;
    String CF_LAST;
    String Overview;
    
    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }



    protected TrendingFunds(Parcel in) {
        CF_NAME = in.readString();
        CF_CURRENCY = in.readString();
        PCTCHNG = in.readString();
        CF_TICK = in.readString();
        FundsName = in.readString();
        lippernos = in.readString();
        CF_NETCHNG = in.readString();
        Potential_Returns = in.readString();
        CF_LAST = in.readString();
        Overview =  in.readString();
    }

    public static final Creator<TrendingFunds> CREATOR = new Creator<TrendingFunds>() {
        @Override
        public TrendingFunds createFromParcel(Parcel in) {
            return new TrendingFunds(in);
        }

        @Override
        public TrendingFunds[] newArray(int size) {
            return new TrendingFunds[size];
        }
    };

    public String getCF_NAME() {
        return CF_NAME;
    }

    public void setCF_NAME(String CF_NAME) {
        this.CF_NAME = CF_NAME;
    }

    public String getCF_CURRENCY() {
        return CF_CURRENCY;
    }

    public void setCF_CURRENCY(String CF_CURRENCY) {
        this.CF_CURRENCY = CF_CURRENCY;
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

    public String getFundsName() {
        return FundsName;
    }

    public void setFundsName(String fundsName) {
        FundsName = fundsName;
    }

    public String getLippernos() {
        return lippernos;
    }

    public void setLippernos(String lippernos) {
        this.lippernos = lippernos;
    }

    public String getCF_NETCHNG() {
        return CF_NETCHNG;
    }

    public void setCF_NETCHNG(String CF_NETCHNG) {
        this.CF_NETCHNG = CF_NETCHNG;
    }

    public String getPotential_Returns() {
        return Potential_Returns;
    }

    public void setPotential_Returns(String potential_Returns) {
        Potential_Returns = potential_Returns;
    }

    public String getCF_LAST() {
        return CF_LAST;
    }

    public void setCF_LAST(String CF_LAST) {
        this.CF_LAST = CF_LAST;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CF_NAME);
        dest.writeString(CF_CURRENCY);
        dest.writeString(PCTCHNG);
        dest.writeString(CF_TICK);
        dest.writeString(FundsName);
        dest.writeString(lippernos);
        dest.writeString(CF_NETCHNG);
        dest.writeString(Potential_Returns);
        dest.writeString(CF_LAST);
        dest.writeString(Overview);

    }
}
