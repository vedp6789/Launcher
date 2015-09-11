package com.gamesapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gamesapp.R;
import com.gamesapp.beans.AppInfo;
import android.graphics.Typeface;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class AppsPageFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<AppInfo> appInfos;
    TextView tv_app_name1,tv_app_name2,tv_app_name3;
    ImageView iv_app_icon1,iv_app_icon2,iv_app_icon3;
    LinearLayout ll_app_two,ll_app_three;
    Button b_play1,b_play2,b_play3;
    private String TAG="GamesApp";
    private String mParam1;
    private String mParam2;
    Intent intent_app_1=null,intent_app_2=null,intent_app_3=null;
    Typeface custom_font,custom_font1;

    private OnFragmentInteractionListener mListener;

    public static AppsPageFragment newInstance(String param1, String param2,ArrayList<AppInfo> appInfos) {
        AppsPageFragment fragment = new AppsPageFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putParcelableArrayList("APPS_A_LIST",appInfos);

        fragment.setArguments(args);
        return fragment;
    }

    public AppsPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appInfos =new ArrayList<AppInfo>();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            appInfos=getArguments().getParcelableArrayList("APPS_A_LIST");
            Log.d(TAG," Fragment class getting arraylist size : "+appInfos.size());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_apps_page,container,false);
        initialize(view);
        custom_font=Typeface.createFromAsset(getResources().getAssets(),"Chunk.ttf");
        custom_font1=Typeface.createFromAsset(getResources().getAssets(),"Chunk.otf");
        if(appInfos.size() == 1){

            ll_app_two.setVisibility(View.GONE);
            ll_app_three.setVisibility(View.GONE);
            populateAppOne();
        }
        if(appInfos.size() == 2){
            ll_app_three.setVisibility(View.INVISIBLE);
            populateAppOne();
            populateAppTwo();
        }
        if(appInfos.size() == 3){
            populateAppOne();
            populateAppTwo();
            populateAppThree();
        }


        return view;
    }

    private void populateAppOne() {
        AppInfo appInfo=appInfos.get(0);
        String package_name=appInfo.getApp_p_name();
        Log.d(TAG,"app package name getting while fragment population 1: "+package_name);
        String app_name=appInfo.getApp_name();
        Log.d(TAG,"app name getting while fragment population 1: "+app_name);

        PackageManager pm = getActivity().getPackageManager();
        intent_app_1=pm.getLaunchIntentForPackage(package_name);
        try {
            Drawable drawable=pm.getApplicationIcon(package_name);
            iv_app_icon1.setImageDrawable(drawable);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tv_app_name1.setText(app_name);
        tv_app_name1.setTypeface(custom_font);
        b_play1.setOnClickListener(this);
        b_play1.setTypeface(custom_font1);
    }
    private void populateAppTwo() {
        AppInfo appInfo=appInfos.get(1);
        String package_name=appInfo.getApp_p_name();
        Log.d(TAG,"app package name getting while fragment population 2: "+package_name);

        String app_name=appInfo.getApp_name();
        PackageManager pm = getActivity().getPackageManager();
        intent_app_2=pm.getLaunchIntentForPackage(package_name);
        try {
            Drawable drawable=pm.getApplicationIcon(package_name);
            iv_app_icon2.setImageDrawable(drawable);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tv_app_name2.setText(app_name);
        tv_app_name2.setTypeface(custom_font);
        b_play2.setTypeface(custom_font1);
        b_play2.setOnClickListener(this);
    }
    private void populateAppThree() {
        AppInfo appInfo=appInfos.get(2);
        String package_name=appInfo.getApp_p_name();
        Log.d(TAG,"app package name getting while fragment population 3: "+package_name);

        String app_name=appInfo.getApp_name();
        PackageManager pm = getActivity().getPackageManager();
        intent_app_3=pm.getLaunchIntentForPackage(package_name);
        try {
            Drawable drawable=pm.getApplicationIcon(package_name);
            iv_app_icon3.setImageDrawable(drawable);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tv_app_name3.setText(app_name);
        tv_app_name3.setTypeface(custom_font);
        b_play3.setTypeface(custom_font1);
        b_play3.setOnClickListener(this);
    }

    private void initialize(View view) {
        tv_app_name1= (TextView) view.findViewById(R.id.tv_app_name1);
        tv_app_name2= (TextView) view.findViewById(R.id.tv_app_name2);
        tv_app_name3= (TextView) view.findViewById(R.id.tv_app_name3);
        iv_app_icon1= (ImageView) view.findViewById(R.id.iv_app_icon1);
        iv_app_icon2= (ImageView) view.findViewById(R.id.iv_app_icon2);
        iv_app_icon3= (ImageView) view.findViewById(R.id.iv_app_icon3);
        b_play1= (Button) view.findViewById(R.id.b_play1);
        b_play2= (Button) view.findViewById(R.id.b_play2);
        b_play3= (Button) view.findViewById(R.id.b_play3);
        ll_app_two= (LinearLayout) view.findViewById(R.id.ll_app_two);
        ll_app_three= (LinearLayout) view.findViewById(R.id.ll_app_three);



    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b_play1:
                startActivity(intent_app_1);
                break;
            case R.id.b_play2:
                startActivity(intent_app_2);
                break;
            case R.id.b_play3:
                startActivity(intent_app_3);
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
