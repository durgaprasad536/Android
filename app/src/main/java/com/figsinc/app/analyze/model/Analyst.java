package com.figsinc.app.analyze.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dinash on 23-12-2017.
 */

public class Analyst implements Parcelable{

    @SerializedName("analystname")
    @Expose
    private String analystname;
    @SerializedName("anayst_compay_name_jpn")
    @Expose
    private String anaystCompayNameJpn;
    @SerializedName("analystname_jpn")
    @Expose
    private String analystnameJpn;
    @SerializedName("name_jpn")
    @Expose
    private String nameJpn;
    @SerializedName("anayst_compay_name")
    @Expose
    private String anaystCompayName;
    @SerializedName("analystid")
    @Expose
    private String analystid;
    @SerializedName("figs_analyst_score")
    @Expose
    private Double figsAnalystScore;

    protected Analyst(Parcel in) {
        analystname = in.readString();
        anaystCompayNameJpn = in.readString();
        analystnameJpn = in.readString();
        nameJpn = in.readString();
        anaystCompayName = in.readString();
        analystid = in.readString();
        if (in.readByte() == 0) {
            figsAnalystScore = null;
        } else {
            figsAnalystScore = in.readDouble();
        }
    }

    public static final Creator<Analyst> CREATOR = new Creator<Analyst>() {
        @Override
        public Analyst createFromParcel(Parcel in) {
            return new Analyst(in);
        }

        @Override
        public Analyst[] newArray(int size) {
            return new Analyst[size];
        }
    };

    public String getAnalystname() {
        return analystname;
    }

    public void setAnalystname(String analystname) {
        this.analystname = analystname;
    }

    public String getAnaystCompayNameJpn() {
        return anaystCompayNameJpn;
    }

    public void setAnaystCompayNameJpn(String anaystCompayNameJpn) {
        this.anaystCompayNameJpn = anaystCompayNameJpn;
    }

    public String getAnalystnameJpn() {
        return analystnameJpn;
    }

    public void setAnalystnameJpn(String analystnameJpn) {
        this.analystnameJpn = analystnameJpn;
    }

    public String getNameJpn() {
        return nameJpn;
    }

    public void setNameJpn(String nameJpn) {
        this.nameJpn = nameJpn;
    }

    public String getAnaystCompayName() {
        return anaystCompayName;
    }

    public void setAnaystCompayName(String anaystCompayName) {
        this.anaystCompayName = anaystCompayName;
    }

    public String getAnalystid() {
        return analystid;
    }

    public void setAnalystid(String analystid) {
        this.analystid = analystid;
    }

    public Double getFigsAnalystScore() {
        return figsAnalystScore;
    }

    public void setFigsAnalystScore(Double figsAnalystScore) {
        this.figsAnalystScore = figsAnalystScore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(analystname);
        dest.writeString(anaystCompayNameJpn);
        dest.writeString(analystnameJpn);
        dest.writeString(nameJpn);
        dest.writeString(anaystCompayName);
        dest.writeString(analystid);
        if (figsAnalystScore == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(figsAnalystScore);
        }
    }
}
