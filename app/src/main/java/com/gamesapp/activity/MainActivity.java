package com.gamesapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gamesapp.Db.SelectedAppsDb;
import com.gamesapp.Helper.PackageInformation;
import com.gamesapp.R;
import com.gamesapp.adapter.AppsViewPagerAdapter;
import com.gamesapp.beans.InfoObject;
import com.viewpagerindicator.CirclePageIndicator;

import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ved on 6/4/15.
 */
public class MainActivity extends FragmentActivity implements Button.OnClickListener {

    TextView tv_search_apps;
    TextView tv_time, tv_date;
    Button tv_label;
    LinearLayout ll_websit_launchers;
    RelativeLayout ll_search_app;
    Button b_eye_candy, b_orthodontic;
    ViewPager pager;
    Handler handler;
    private String TAG = "GamesApp";
    SelectedAppsDb selectedAppsDb;
    AppsViewPagerAdapter appsViewPagerAdapter;
    public static ArrayList<InfoObject> selectable_app = null;

    public static ArrayList<InfoObject> selectable_app2 = null;
    ArrayList<InfoObject> appsData;
    public final static int REQUEST = 199, RESULT = 200, SEARCH_RESULT = 765;
    CirclePageIndicator circlePageIndicator;
    Typeface custom_font1;
    Context context;
    boolean populateViewPager = false;
    ProgressDialog progressDialog;
    PackageInformation androidPackagesInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectable_app = null;
        selectable_app2 = null;
        Log.d(TAG, "Main Activity onCreate");
        setContentView(R.layout.activity_main);
        initialize();

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.initiating_launcher));
        custom_font1 = Typeface.createFromAsset(getResources().getAssets(), "Chunk.otf");
        tv_search_apps.setTypeface(custom_font1);
        tv_date.setTypeface(custom_font1);
        tv_time.setTypeface(custom_font1);
        handler = new Handler();
        context = this;


        new AsyncTask<Object, Void, Cursor>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected Cursor doInBackground(Object... params) {

                androidPackagesInfo = new PackageInformation((MainActivity) params[0]);
                appsData = androidPackagesInfo.getInstalledApps(true);
                Log.d(TAG, "appsData size :" + appsData.size());

                selectedAppsDb = new SelectedAppsDb((MainActivity) params[0], SelectedAppsDb.DB_NAME, null, SelectedAppsDb.DB_VERSION);

                Cursor cursor = selectedAppsDb.getAllApps();
                if (cursor.getCount() > 0) {
                    populateViewPager = true;

                } else {
                    populateViewPager = false;
                    selectable_app = new ArrayList<InfoObject>();
                    for (InfoObject infoObject : appsData) {
                        if (infoObject.getIntent() != null) {
                            if (!infoObject.getPname().equals("com.gamesapp")) {
                                Log.d(TAG, "selectable app going to be populate in db : " + infoObject.getPname());
                                selectable_app.add(infoObject);
                            }

                        }
                    }
                }
                return cursor;
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                super.onPostExecute(cursor);

                progressDialog.dismiss();
                if (populateViewPager) {
                    populateViewPager(cursor);   /*    Comes in this scope when app db have some rows */
                } else {
                    Intent intent = new Intent(MainActivity.this, SelectFromAvailableApps.class);
                    intent.putExtra("call", "first");
                    startActivityForResult(intent, REQUEST);

                    Log.d(TAG, "selectable_app arraylist size : " + selectable_app.size());

                    Log.d(TAG, "table is empty");

                }

            }
        }.execute(MainActivity.this);


        /*for (InfoObject infoObject : appsData) {
            Log.d(TAG, "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ new app data ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            Log.d(TAG, "app name : " + infoObject.getAppname());
            Log.d(TAG, "app package : " + infoObject.getPname());
            Log.d(TAG, "version name : " + infoObject.getVersionName());
            Log.d(TAG, "version code : " + infoObject.getVersionCode());

        }*/
    }

    private void populateViewPager(Cursor cursor) {
        appsViewPagerAdapter = new AppsViewPagerAdapter(getSupportFragmentManager(), cursor);
        pager.setAdapter(appsViewPagerAdapter);
        circlePageIndicator.setViewPager(pager);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST && resultCode == RESULT) {
            Cursor cursor = selectedAppsDb.getAllApps();
            if (cursor.getCount() > 0) {
                populateViewPager(cursor);

            } else {
                Toast.makeText(MainActivity.this, "Have you not selected any app ?", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUEST && resultCode == SEARCH_RESULT) {

        }


    }

    Runnable timeUpdate = new Runnable() {
        @Override
        public void run() {
            int update_time_after = updateTime(); //.
            handler.postDelayed(timeUpdate, update_time_after * 1000);
        }
    };

    int updateTime() {
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        final String date_time = sdf.format(new Date());
        tv_time.setText(date_time.substring(9, 11) + ":" + date_time.substring(11, 13));
        int seconds = Integer.parseInt(date_time.substring(13, 15));
        Log.d(TAG, "seconds : " + date_time.substring(13, 15));
        int month = calendar1.get(Calendar.MONTH);
        int year = calendar1.get(Calendar.YEAR);

//        tv_date.setText(getResources().getStringArray(R.array.month)[month]+"\t"+String.valueOf(year));
        tv_date.setText(date_time.substring(6, 8) + "\t" + getResources().getStringArray(R.array.month)[month]);

        return (60 - seconds);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Main Activity onResume");

        Log.d(TAG, "start digital clock on Resume");
        timeUpdate.run();

    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(timeUpdate);
        Log.d(TAG, "Main Activity onPause");

        Log.d(TAG, "stop digital clock on onPause");

        super.onPause();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }


    private void initialize() {
        ll_search_app = (RelativeLayout) findViewById(R.id.ll_search_apps);
        ll_websit_launchers = (LinearLayout) findViewById(R.id.ll_website_launchers);
        tv_label = (Button) findViewById(R.id.tv_label);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_date = (TextView) findViewById(R.id.tv_date);
        pager = (ViewPager) findViewById(R.id.apps_pager);
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circle_pager_indicator);
        tv_search_apps = (TextView) findViewById(R.id.tv_search_apps);
        b_eye_candy = (Button) findViewById(R.id.b_eye_candy);
        b_orthodontic = (Button) findViewById(R.id.b_orthodontic);
        b_eye_candy.setOnClickListener(this);
        b_orthodontic.setOnClickListener(this);


        ll_search_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intent, REQUEST);


            }
        });


        tv_label.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SelectedAppsDb selectedAppsDb1 = new SelectedAppsDb(MainActivity.this, SelectedAppsDb.DB_NAME, null, SelectedAppsDb.DB_VERSION);
                Cursor cursor = selectedAppsDb1.getAllApps();


                PackageInformation androidPackagesInfo = new PackageInformation(context);
                appsData = androidPackagesInfo.getInstalledApps(true);
                Log.d(TAG, "appsData size :" + appsData.size());

                selectable_app2 = new ArrayList<InfoObject>();
                for (InfoObject infoObject : appsData) {
                    if (infoObject.getIntent() != null) {
                        if (!infoObject.getPname().equals("com.gamesapp")) {
                            Log.d(TAG, "selectable app going to be populated in db : " + infoObject.getPname());
                            selectable_app2.add(infoObject);

                        }

                    }
                }

                Intent intent = new Intent(MainActivity.this, SelectFromAvailableApps.class);
                intent.putExtra("call", "second");
                startActivityForResult(intent, REQUEST);


                return true;

            }
        });


    }


    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_eye_candy:

                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", "http://www.eyecandyopticians.com");
                startActivity(intent);

                break;
            case R.id.b_orthodontic:

                /* This is having problem in opening url in default browser in some devices like Yurek/cynogen*/
                /*Intent internetIntent1 = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.ennisbraces.com"));
                internetIntent1.setComponent(new ComponentName("com.android.browser","com.android.browser.BrowserActivity"));
                internetIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(internetIntent1);*/

                Intent intent1 = new Intent(MainActivity.this, WebViewActivity.class);
                intent1.putExtra("url", "http://www.ennisbraces.com");
                startActivity(intent1);


                /*
                * This is working fine if user wants to open url in default browser*/
/*                Intent intent1 = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.ennisbraces.com"));
                startActivity(intent1);*/
                break;
        }
    }
}
