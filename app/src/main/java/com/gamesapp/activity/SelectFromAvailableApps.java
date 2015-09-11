package com.gamesapp.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.gamesapp.Db.SelectedAppsDb;

import com.gamesapp.R;
import com.gamesapp.adapter.AppSelectionList;
import com.gamesapp.beans.InfoObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ved on 6/4/15.
 */
public class SelectFromAvailableApps extends Activity implements Button.OnClickListener {
    ListView lv_app_slection;
    ArrayList<InfoObject> list_of_apps;
    AppSelectionList appSelectionList;   /* BaseAdapter class */
    Button b_commit, b_cancel;
    SelectedAppsDb selectedAppsDb;
    private String which_call;
    private String TAG = "GamesApp :  SelectFromAvailableApp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_available_apps);
        initialize();
        selectedAppsDb = new SelectedAppsDb(this, SelectedAppsDb.DB_NAME, null, SelectedAppsDb.DB_VERSION);

        which_call = getIntent().getExtras().getString("call");
        list_of_apps = new ArrayList<InfoObject>();
        if (which_call.equals("first"))
            list_of_apps = MainActivity.selectable_app;
        if (which_call.equals("second"))
            list_of_apps = MainActivity.selectable_app2;


        Collections.sort(list_of_apps, InfoObject.infoObjectComparator);


        Cursor cursor = selectedAppsDb.getAllApps();
        if (cursor.getCount() > 0) {
            for (int app_from_db = 0; app_from_db < cursor.getCount(); app_from_db++) {
                cursor.moveToPosition(app_from_db);
                String app_package = cursor.getString(cursor.getColumnIndex(SelectedAppsDb.APP_P_NAME));
                for (int list_of_apps_index = 0; list_of_apps_index < list_of_apps.size(); list_of_apps_index++) {
                    InfoObject infoObject = list_of_apps.get(list_of_apps_index);
                    if (app_package.equals(infoObject.getPname())) {
                        infoObject.setCheck(true);

                        break;
                    }
                }
            }
        }


       //new ListAsyncTask().execute(this);


        Log.i(TAG, " AppSelectionList adapter constructor call ");
        appSelectionList = new AppSelectionList(this, list_of_apps, selectedAppsDb);
        lv_app_slection.setAdapter(appSelectionList);

    }

    private void initialize() {
        lv_app_slection = (ListView) findViewById(R.id.lv_select_app);
        lv_app_slection.setDivider(null);
        lv_app_slection.setDividerHeight(0);
        b_commit = (Button) findViewById(R.id.b_commit);
        b_commit.setOnClickListener(this);
        b_cancel = (Button) findViewById(R.id.b_cancel);
        b_cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_commit:
                b_commit.setBackgroundDrawable(getResources().getDrawable(R.drawable.import_btnonclick1));


                ArrayList<Integer> arrayList_of_Index_values = appSelectionList.selectedInfoObjectsPosition;  /* This arraylist will have different index values,  of list_of_apps arrayList . These are developed when user select checkbox from listview */
                if (arrayList_of_Index_values.size() > 0) {
                    if (selectedAppsDb.getAllApps().getCount() > 0)
                        selectedAppsDb.delete_whole_table();

                    for (int no_of_selected_apps = 0; no_of_selected_apps < arrayList_of_Index_values.size(); no_of_selected_apps++) {

                        ContentValues contentValues = new ContentValues();
                        String app_name = list_of_apps.get(arrayList_of_Index_values.get(no_of_selected_apps)).getAppname();
                        contentValues.put(SelectedAppsDb.APP_NAME, app_name);
                        contentValues.put(SelectedAppsDb.APP_P_NAME, list_of_apps.get(arrayList_of_Index_values.get(no_of_selected_apps)).getPname());
                        contentValues.put(SelectedAppsDb.APP_V_NAME, list_of_apps.get(arrayList_of_Index_values.get(no_of_selected_apps)).getVersionName());
                        contentValues.put(SelectedAppsDb.APP_V_CODE, list_of_apps.get(arrayList_of_Index_values.get(no_of_selected_apps)).getVersionCode());
                        selectedAppsDb.insertAppDetails(contentValues);

                    }
                    setResult(MainActivity.RESULT);
                    finish();
                } else {
                    Toast.makeText(SelectFromAvailableApps.this, "Select apps please !", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.b_cancel:
                b_cancel.setBackgroundDrawable(getResources().getDrawable(R.drawable.cancel_btn_onclick1));
                setResult(MainActivity.RESULT);
                finish();
                break;

        }
    }

   /* public class ListAsyncTask extends AsyncTask<Object,Void,Object> {
        @Override
        protected Object doInBackground(Object... params) {

            Log.i(TAG, " AppSelectionList adapter constructor call ");
            appSelectionList = new AppSelectionList((SelectFromAvailableApps)params[0], list_of_apps, selectedAppsDb);
            //lv_app_slection.setAdapter(appSelectionList);



            return appSelectionList;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            lv_app_slection.setAdapter((AppSelectionList)o);
        }
    }*/

}
