package com.gamesapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.gamesapp.R;

import java.util.ArrayList;

/**
 * Created by ved on 22/4/15.
 */
public class WebViewActivity extends Activity implements Button.OnClickListener{
    WebView webView;
    private boolean isLoadComplete;
    ProgressDialog progressDialog;
    Button b_back;
    ArrayList<String> list_of_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String url = getIntent().getStringExtra("url");
        init();
        list_of_url=new ArrayList<String>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        isLoadComplete = false;

        if (!isNetworkConnected(WebViewActivity.this)) {
            Toast.makeText(WebViewActivity.this, getResources().getString(R.string.internet_connectivity_problem), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollbarFadingEnabled(true);
        webView.loadUrl(url);
        list_of_url.add(url);


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                isLoadComplete = false;
                Log.d("Games App ","url : "+url);
                list_of_url.add(url);
               // view.loadUrl(url);
                return false;
            }

            //Show loader on url load
            @Override
            public void onLoadResource(WebView view, String url) {
                try{
                    if (!isLoadComplete)
                        progressDialog.show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }


            public void onPageFinished(WebView view, String url) {
                try {
                    isLoadComplete = true;
                    progressDialog.dismiss();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }


        });


    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.web_view);
        b_back= (Button) findViewById(R.id.b_back);
        b_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.b_back:
             try{
                 if(list_of_url.size() > 0){
                     list_of_url.remove(list_of_url.size()-1);
                     if(list_of_url.size() > 0){
                         isLoadComplete = false;
                         // progressDialog.show();
                         webView.loadUrl(list_of_url.get(list_of_url.size()-1));
                     }else{
                         finish();
                     }


                 }else{
                     finish();
                 }
             }catch (Exception e){
                 e.printStackTrace();
             }

             break;
     }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Games App","WebViewActivity onPause ");

    }

    @Override
    protected void onStop() {


        super.onStop();
        Log.d("Games App","WebViewActivity onStop ");

    }
}
