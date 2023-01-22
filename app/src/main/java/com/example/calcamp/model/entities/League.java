package com.example.calcamp.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class League implements Parcelable {

    private Integer id;
    private String name;
    private PunctuationType punctuationType;

    public League() {

    }

    public League(Integer id, String name, PunctuationType punctuationType) {
        this.id = id;
        this.name = name;
        this.punctuationType = punctuationType;
    }

    protected League(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
    }

    public static final Creator<League> CREATOR = new Creator<League>() {
        @Override
        public League createFromParcel(Parcel in) {
            return new League(in);
        }

        @Override
        public League[] newArray(int size) {
            return new League[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PunctuationType getPunctuationType() {
        return punctuationType;
    }

    public void setPunctuationType(PunctuationType punctuationType) {
        this.punctuationType = punctuationType;
    }


    @Override
    public String toString() {
        return "League [id=" + id + ", name=" + name + ", PunctuationType=" + punctuationType + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
    }
}
