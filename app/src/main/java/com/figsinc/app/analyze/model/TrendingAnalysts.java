package com.figsinc.app.analyze.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 461883 on 9/12/17.
 */

public class TrendingAnalysts  implements Parcelable{

    String average_return;
    String topsector__name;
    String average_time;
    String anayst_compay_name;
    String analystid;
    String analystname;
    String figs_analyst_score;
    String secondsector__name;

    public TrendingAnalysts(){

    }
    protected TrendingAnalysts(Parcel in) {
        average_return = in.readString();
        topsector__name = in.readString();
        average_time = in.readString();
        anayst_compay_name = in.readString();
        analystid = in.readString();
        analystname = in.readString();
        figs_analyst_score = in.readString();
        secondsector__name = in.readString();
    }

    public static final Creator<TrendingAnalysts> CREATOR = new Creator<TrendingAnalysts>() {
        @Override
        public TrendingAnalysts createFromParcel(Parcel in) {
            return new TrendingAnalysts(in);
        }

        @Override
        public TrendingAnalysts[] newArray(int size) {
            return new TrendingAnalysts[size];
        }
    };

    public String getAverage_return() {
        return average_return;
    }

    public void setAverage_return(String average_return) {
        this.average_return = average_return;
    }

    public String getTopsector__name() {
        return topsector__name;
    }

    public void setTopsector__name(String topsector__name) {
        this.topsector__name = topsector__name;
    }

    public String getAverage_time() {
        return average_time;
    }

    public void setAverage_time(String average_time) {
        this.average_time = average_time;
    }

    public String getAnayst_compay_name() {
        return anayst_compay_name;
    }

    public void setAnayst_compay_name(String anayst_compay_name) {
        this.anayst_compay_name = anayst_compay_name;
    }

    public String getAnalystid() {
        return analystid;
    }

    public void setAnalystid(String analystid) {
        this.analystid = analystid;
    }

    public String getAnalystname() {
        return analystname;
    }

    public void setAnalystname(String analystname) {
        this.analystname = analystname;
    }

    public String getFigs_analyst_score() {
        return figs_analyst_score;
    }

    public void setFigs_analyst_score(String figs_analyst_score) {
        this.figs_analyst_score = figs_analyst_score;
    }

    public String getSecondsector__name() {
        return secondsector__name;
    }

    public void setSecondsector__name(String secondsector__name) {
        this.secondsector__name = secondsector__name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(average_return);
        dest.writeString(topsector__name);
        dest.writeString(average_time);
        dest.writeString(anayst_compay_name);
        dest.writeString(analystid);
        dest.writeString(analystname);
        dest.writeString(figs_analyst_score);
        dest.writeString(secondsector__name);
    }
}
