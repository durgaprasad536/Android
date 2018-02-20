package com.figsinc.app.settings.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country implements Parcelable{

@SerializedName("iso")
@Expose
private String iso;
@SerializedName("name")
@Expose
private String name;

    protected Country(Parcel in) {
        iso = in.readString();
        name = in.readString();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public String getIso() {
return iso;
}

public void setIso(String iso) {
this.iso = iso;
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(iso);
        parcel.writeString(name);
    }
}