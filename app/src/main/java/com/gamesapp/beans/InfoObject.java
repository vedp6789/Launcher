package com.gamesapp.beans;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.Comparator;

/**
 * Created by ved on 6/4/15.
 */
public class InfoObject {
    private String appname = "";
    private  String pname = "";
    private String versionName = "";
    private int versionCode = 0;
    private Drawable icon;
    private Intent intent;
    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public void InfoObjectAggregatePrint() {//not used yet
        Log.v(appname, appname + "\t" + pname + "\t" + versionName + "\t" + versionCode);
    }


    public static Comparator<InfoObject> infoObjectComparator=new Comparator<InfoObject>() {
        @Override
        public int compare(InfoObject lhs, InfoObject rhs) {

            String appname1=lhs.getAppname().toUpperCase();
            String appname2=rhs.getAppname().toUpperCase();
            return appname1.compareTo(appname2);
        }
    };


}
