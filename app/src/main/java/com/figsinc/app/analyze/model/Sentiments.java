package com.figsinc.app.analyze.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 461883 on 9/12/17.
 */

public class Sentiments implements Parcelable{

    String sentiment_index;
    String potential_return9m;
    String potential_loss3m;
    String potential_return6m;
    String potential_loss12m;
    String potential_return12m;
    String stock_exchange_id;
    String potential_return3m;
    String potential_loss9m;
    String potential_loss6m;
    String name;

    public  Sentiments(){

    }
    protected Sentiments(Parcel in) {
        sentiment_index = in.readString();
        potential_return9m = in.readString();
        potential_loss3m = in.readString();
        potential_return6m = in.readString();
        potential_loss12m = in.readString();
        potential_return12m = in.readString();
        stock_exchange_id = in.readString();
        potential_return3m = in.readString();
        potential_loss9m = in.readString();
        potential_loss6m = in.readString();
        name = in.readString();
        returnriskratio3m = in.readString();
        returnriskratio6m = in.readString();
        returnriskratio9m = in.readString();
        returnriskratio12m = in.readString();
    }

    public static final Creator<Sentiments> CREATOR = new Creator<Sentiments>() {
        @Override
        public Sentiments createFromParcel(Parcel in) {
            return new Sentiments(in);
        }

        @Override
        public Sentiments[] newArray(int size) {
            return new Sentiments[size];
        }
    };

    public String getReturnriskratio3m() {
        return returnriskratio3m;
    }

    public void setReturnriskratio3m(String returnriskratio3m) {
        this.returnriskratio3m = returnriskratio3m;
    }

    public String getReturnriskratio6m() {
        return returnriskratio6m;
    }

    public void setReturnriskratio6m(String returnriskratio6m) {
        this.returnriskratio6m = returnriskratio6m;
    }

    public String getReturnriskratio9m() {
        return returnriskratio9m;
    }

    public void setReturnriskratio9m(String returnriskratio9m) {
        this.returnriskratio9m = returnriskratio9m;
    }

    public String getReturnriskratio12m() {
        return returnriskratio12m;
    }

    public void setReturnriskratio12m(String returnriskratio12m) {
        this.returnriskratio12m = returnriskratio12m;
    }

    String returnriskratio3m;
    String returnriskratio6m;
    String returnriskratio9m;
    String returnriskratio12m;


    public String getSentiment_index() {
        return sentiment_index;
    }

    public void setSentiment_index(String sentiment_index) {
        this.sentiment_index = sentiment_index;
    }

    public String getPotential_return9m() {
        return potential_return9m;
    }

    public void setPotential_return9m(String potential_return9m) {
        this.potential_return9m = potential_return9m;
    }

    public String getPotential_loss3m() {
        return potential_loss3m;
    }

    public void setPotential_loss3m(String potential_loss3m) {
        this.potential_loss3m = potential_loss3m;
    }

    public String getPotential_return6m() {
        return potential_return6m;
    }

    public void setPotential_return6m(String potential_return6m) {
        this.potential_return6m = potential_return6m;
    }

    public String getPotential_loss12m() {
        return potential_loss12m;
    }

    public void setPotential_loss12m(String potential_loss12m) {
        this.potential_loss12m = potential_loss12m;
    }

    public String getPotential_return12m() {
        return potential_return12m;
    }

    public void setPotential_return12m(String potential_return12m) {
        this.potential_return12m = potential_return12m;
    }

    public String getStock_exchange_id() {
        return stock_exchange_id;
    }

    public void setStock_exchange_id(String stock_exchange_id) {
        this.stock_exchange_id = stock_exchange_id;
    }

    public String getPotential_return3m() {
        return potential_return3m;
    }

    public void setPotential_return3m(String potential_return3m) {
        this.potential_return3m = potential_return3m;
    }

    public String getPotential_loss9m() {
        return potential_loss9m;
    }

    public void setPotential_loss9m(String potential_loss9m) {
        this.potential_loss9m = potential_loss9m;
    }

    public String getPotential_loss6m() {
        return potential_loss6m;
    }

    public void setPotential_loss6m(String potential_loss6m) {
        this.potential_loss6m = potential_loss6m;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sentiment_index);
        dest.writeString(potential_return9m);
        dest.writeString(potential_loss3m);
        dest.writeString(potential_return6m);
        dest.writeString(potential_loss12m);
        dest.writeString(potential_return12m);
        dest.writeString(stock_exchange_id);
        dest.writeString(potential_return3m);
        dest.writeString(potential_loss9m);
        dest.writeString(potential_loss6m);
        dest.writeString(name);
        dest.writeString(returnriskratio3m);
        dest.writeString(returnriskratio6m);
        dest.writeString(returnriskratio9m);
        dest.writeString(returnriskratio12m);
    }
}
