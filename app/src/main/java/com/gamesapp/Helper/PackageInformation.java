package com.gamesapp.Helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;

import com.gamesapp.beans.InfoObject;

import java.util.ArrayList;
import java.util.List;


public class PackageInformation{

    private Context mContext;

    public  PackageInformation(Context context){
        mContext=context;
    }






    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    public ArrayList<InfoObject> getInstalledApps(boolean getSysPackages) {
        ArrayList<InfoObject> res = new ArrayList<InfoObject>();
        List<PackageInfo> packs = mContext.getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue ;
            }
            InfoObject newInfo = new InfoObject();
            newInfo.setAppname(p.applicationInfo.loadLabel(mContext.getPackageManager()).toString());
            newInfo.setPname(p.packageName);
            newInfo.setVersionName(p.versionName);
            newInfo.setVersionCode(p.versionCode);
            newInfo.setIcon(p.applicationInfo.loadIcon(mContext.getPackageManager()));
            newInfo.setIntent(mContext.getPackageManager().getLaunchIntentForPackage(p.packageName));
            res.add(newInfo);
        }
        return res;
    }


}


/*final PackageManager pm = getPackageManager();
        //get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        Log.i(TAG, "List size : " + packages.size());

        for (ApplicationInfo packageInfo : packages) {
            Log.v(TAG, "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            Log.d(TAG, "Installed package :" + packageInfo.packageName);
            Log.d(TAG, "Source dir : " + packageInfo.sourceDir);
            int i=packageInfo.flags;
            int j=packageInfo.FLAG_IS_GAME;
            Log.d(TAG,"FLAG_IS_GAME value : "+packageInfo.FLAG_IS_GAME);

            if ((packageInfo.flags & packageInfo.FLAG_IS_GAME) != 0) {
                Log.d(TAG,"");
                System.out.println(">>>>>>..................************* :  : "+packageInfo.packageName);
            }

           *//* Intent intent=pm.getLaunchIntentForPackage(packageInfo.packageName);
            if(intent != null){
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                startActivity(intent);
            }
            Log.d(TAG, "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));*//*
        }
*/

