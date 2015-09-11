package com.gamesapp.adapter;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.gamesapp.Db.SelectedAppsDb;
import com.gamesapp.beans.AppInfo;
import com.gamesapp.fragment.AppsPageFragment;

import java.util.ArrayList;

/**
 * Created by ved on 4/4/15.
 */
public class AppsViewPagerAdapter extends FragmentStatePagerAdapter {
Cursor cursor;
    int no_of_fragments=0;
    ArrayList<AppInfo> appInfos_arryList;
    private String TAG="GamesApp";
    public AppsViewPagerAdapter(FragmentManager fm, Cursor cursor) {
        super(fm);
        this.cursor=cursor;
    }

    @Override
    public Fragment getItem(int i) {
        Log.d(TAG," getItem frgment no : "+ i);

        int start_index=i * 3;
        int stop_index=start_index + 3;

        appInfos_arryList=new ArrayList<AppInfo>();
        for (int app_no=start_index ; app_no < stop_index ; app_no++){
            if( app_no < cursor.getCount() ){  /* Will not allow AppInfo object creation for the app_no which is not available in the cursor */
                cursor.moveToPosition(app_no);
                AppInfo appInfo=new AppInfo();
                appInfo.setApp_name(cursor.getString(cursor.getColumnIndex(SelectedAppsDb.APP_NAME)));
                appInfo.setApp_p_name(cursor.getString(cursor.getColumnIndex(SelectedAppsDb.APP_P_NAME)));
                Log.d(TAG,"app package no in AppsViewPagerAdapter "+cursor.getString(cursor.getColumnIndex(SelectedAppsDb.APP_P_NAME)));
                appInfo.setApp_v_name(cursor.getString(cursor.getColumnIndex(SelectedAppsDb.APP_V_NAME)));
                appInfo.setApp_v_code(cursor.getString(cursor.getColumnIndex(SelectedAppsDb.APP_V_CODE)));
                appInfos_arryList.add(appInfo);

            }
        }

        return AppsPageFragment.newInstance(Integer.toString(i),"",appInfos_arryList);
    }

    @Override
    public int getCount()
    {
        int no_of_apps=cursor.getCount();
        if((no_of_apps % 3) > 0){
            no_of_fragments=(no_of_apps/3)+1;
            return no_of_fragments;
        }else
        no_of_fragments=(no_of_apps/3);
        return no_of_fragments;
    }
}
