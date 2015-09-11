package com.gamesapp.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gamesapp.Helper.PackageInformation;
import com.gamesapp.R;
import com.gamesapp.adapter.SearchSuggestionListAdapter;
import com.gamesapp.beans.InfoObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ved on 13/4/15.
 */
public class SearchActivity extends Activity {
    ImageView iv_back;
    EditText et_search;
    ListView lv_app_search_list;
    TextView tv_search_status;
    public ArrayList<InfoObject> selectable_app1 = null;
    ArrayList<InfoObject> appsData;
    SearchSuggestionListAdapter searchSuggestionListAdapter;
    ArrayList<InfoObject> apps_by_suggestion;
    private String TAG = "GamesApp Search Activity";
    private int RESULT = 765;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();


        et_search.setFocusable(true);
    }

    private void initialize() {
        et_search = (EditText) findViewById(R.id.et_search);
        lv_app_search_list = (ListView) findViewById(R.id.lv_app_search_list);
        lv_app_search_list.setDivider(null);
        lv_app_search_list.setDividerHeight(0);
        tv_search_status= (TextView) findViewById(R.id.tv_search_status);
        tv_search_status.setVisibility(View.GONE);
        iv_back= (ImageView) findViewById(R.id.iv_back);





        applyListenears();
    }

    private void applyListenears() {

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = et_search.getText().toString();
                if (input.length() >= 1) {
                    getAutoSuggestions(input);
                } else {
                  tv_search_status.setVisibility(View.GONE);
                    lv_app_search_list.setVisibility(View.GONE);
                }

            }
        });


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                setResult(RESULT);
            }
        });






    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        setResult(RESULT);
    }

    void getAutoSuggestions(String input) {
        PackageInformation androidPackagesInfo = new PackageInformation(this);
        appsData = androidPackagesInfo.getInstalledApps(true);
        apps_by_suggestion = new ArrayList<InfoObject>();
        selectable_app1 = new ArrayList<InfoObject>();

        for (InfoObject infoObject : appsData) {
            if (infoObject.getIntent() != null) {
                Log.d(TAG, "App name which are launchable : " + infoObject.getAppname());
                if (!infoObject.getPname().equals("com.gamesapp")) {
                    selectable_app1.add(infoObject);
                }


            }
        }
        if (selectable_app1 != null && selectable_app1.size() > 0) {

            Collections.sort(selectable_app1, InfoObject.infoObjectComparator);    /* Used to arrange list in ascending order */

            for (int match_app_possibility = 0; match_app_possibility < selectable_app1.size(); match_app_possibility++) {
                InfoObject infoObject = selectable_app1.get(match_app_possibility);
                String app_name = infoObject.getAppname();
                if (app_name.toLowerCase().contains(input.toLowerCase())) {
                    apps_by_suggestion.add(infoObject);
                }


            }
            showSuggestion(apps_by_suggestion);
        } else {

            Log.d(TAG, "MainActivity :  No apps found which can be launced in the database.");
        }

        if(apps_by_suggestion != null && apps_by_suggestion.size() >0){
            tv_search_status.setVisibility(View.GONE);
            lv_app_search_list.setVisibility(View.VISIBLE);
        }else{
            lv_app_search_list.setVisibility(View.GONE);
            tv_search_status.setVisibility(View.VISIBLE);
        }


    }

    private void showSuggestion(final ArrayList<InfoObject> apps_by_suggestion) {
        final ArrayList<String> app_suggestion = new ArrayList<String>();
        for (InfoObject infoObject : apps_by_suggestion) {
            app_suggestion.add(infoObject.getAppname());
        }
        searchSuggestionListAdapter = new SearchSuggestionListAdapter(SearchActivity.this, apps_by_suggestion);
        /*arrayAdapter_search_suggestion=new ArrayAdapter<String>(MainActivity.this,R.layout.search_app_suggestion_list_helper,app_suggestion);
        arrayAdapter_search_suggestion=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,app_suggestion);*/
        lv_app_search_list.setAdapter(searchSuggestionListAdapter);
        lv_app_search_list.setVisibility(View.VISIBLE);
        //makeOthersViewGone();

        lv_app_search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                InfoObject infoObject = apps_by_suggestion.get(position);
                try {

                    startActivity(infoObject.getIntent());
                    finish();
                    setResult(RESULT);

                } catch (Exception e) {
                    Log.e(TAG, "Problem while intent launch while search for app ");
                    Toast.makeText(SearchActivity.this, "Sorry, \t" + infoObject.getAppname() + " has encountered some problem.", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
