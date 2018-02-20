package com.figsinc.app.settings.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSettings implements Parcelable {

    @SerializedName("Gender")
    @Expose
    private List<String> gender = null;
    @SerializedName("Country")
    @Expose
    private ArrayList<Country> country = null;
    @SerializedName("Titles")
    @Expose
    private List<String> titles = null;
    @SerializedName("Investment_Year_CHOICES")
    @Expose
    private List<String> investmentYearCHOICES = null;
    @SerializedName("Settings")
    @Expose
    private Settings settings;

    protected UserSettings(Parcel in) {
        gender = in.createStringArrayList();
        country = in.createTypedArrayList(Country.CREATOR);
        titles = in.createStringArrayList();
        investmentYearCHOICES = in.createStringArrayList();
    }

    public static final Creator<UserSettings> CREATOR = new Creator<UserSettings>() {
        @Override
        public UserSettings createFromParcel(Parcel in) {
            return new UserSettings(in);
        }

        @Override
        public UserSettings[] newArray(int size) {
            return new UserSettings[size];
        }
    };

    public List<String> getGender() {
        return gender;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    public ArrayList<Country> getCountry() {
        return country;
    }

    public void setCountry(ArrayList<Country> country) {
        this.country = country;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<String> getInvestmentYearCHOICES() {
        return investmentYearCHOICES;
    }

    public void setInvestmentYearCHOICES(List<String> investmentYearCHOICES) {
        this.investmentYearCHOICES = investmentYearCHOICES;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(gender);
        parcel.writeTypedList(country);
        parcel.writeStringList(titles);
        parcel.writeStringList(investmentYearCHOICES);
    }
}