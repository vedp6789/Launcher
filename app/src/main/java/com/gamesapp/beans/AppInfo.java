package com.gamesapp.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by ved on 7/4/15.
 */
public class AppInfo implements Parcelable {
    private String app_name;
    private String app_p_name;
    private String app_v_name;
    private String app_v_code;

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_p_name() {
        return app_p_name;
    }

    public void setApp_p_name(String app_p_name) {
        this.app_p_name = app_p_name;
    }

    public String getApp_v_name() {
        return app_v_name;
    }

    public void setApp_v_name(String app_v_name) {
        this.app_v_name = app_v_name;
    }

    public String getApp_v_code() {
        return app_v_code;
    }

    public void setApp_v_code(String app_v_code) {
        this.app_v_code = app_v_code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.app_name);
        dest.writeString(this.app_p_name);
        dest.writeString(this.app_v_name);
        dest.writeString(this.app_v_code);
    }


    public AppInfo(Parcel parcel) {
        this();
        this.app_name = parcel.readString();
        this.app_p_name = parcel.readString();
        this.app_v_name = parcel.readString();
        this.app_v_code = parcel.readString();
    }

    public AppInfo(){

    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public AppInfo createFromParcel(Parcel in) {
            return new AppInfo(in);
        }

        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };


    public static Comparator<AppInfo> appInfoComparator=new Comparator<AppInfo>() {
        @Override
        public int compare(AppInfo lhs, AppInfo rhs) {
            String app_name1=lhs.getApp_name().toUpperCase();
            String app_name2=rhs.getApp_name().toUpperCase();

            return app_name1.compareTo(app_name2);
        }
    };


}
