package com.gamesapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gamesapp.Db.SelectedAppsDb;
import com.gamesapp.R;
import com.gamesapp.beans.InfoObject;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ved on 6/4/15.
 */
public class AppSelectionList extends BaseAdapter {

    private ArrayList<InfoObject> infoObjects;
    Context context;
    public ArrayList<Integer> selectedInfoObjectsPosition;
    Cursor cursor_list_of_apps_in_db;
    private String TAG = "GamesApp AppSelectionList adapter";
    boolean is_checked;
    ViewHolder viewHolder;
    Typeface custom_font1;


    public AppSelectionList(Context context, ArrayList<InfoObject> arrayList, SelectedAppsDb selectedAppsDb) {
        infoObjects = arrayList;
        this.context = context;
        selectedInfoObjectsPosition = new ArrayList<Integer>();
        cursor_list_of_apps_in_db = selectedAppsDb.getAllApps();

        custom_font1= Typeface.createFromAsset(context.getAssets(), "Chunk.otf");

        for(int i=0; i<infoObjects.size(); i++){
            if(infoObjects.get(i).isCheck())
                selectedInfoObjectsPosition.add(i);
        }


    }

    @Override
    public int getCount() {
        return infoObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return infoObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        final InfoObject singleInfoObject = infoObjects.get(position);
        viewHolder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.select_app_list_helper, null);

            viewHolder = new ViewHolder();
            viewHolder.iv_app_icon = (ImageView) view.findViewById(R.id.iv_app_icon);
            viewHolder.tv_app_name = (TextView) view.findViewById(R.id.tv_app_name);
            viewHolder.cb_app_selection = (CheckBox) view.findViewById(R.id.cb_app_selection);
            viewHolder.ll_app_detail= (LinearLayout) view.findViewById(R.id.ll_app_list);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

//        Log.e(TAG, "=>\nApp Name : " + singleInfoObject.getAppname() + "\npackage : " + singleInfoObject.getPname() + "\nIsSet : " + singleInfoObject.isCheck());
//        Log.e(TAG, "Set : " + singleInfoObject.isCheck());


        viewHolder.iv_app_icon.setImageDrawable(singleInfoObject.getIcon());
        viewHolder.tv_app_name.setText(singleInfoObject.getAppname());
        viewHolder.tv_app_name.setTypeface(custom_font1);
        final CheckBox checkBox = viewHolder.cb_app_selection;
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(singleInfoObject.isCheck());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.e(TAG,"onCheckedChanged");

                if(isChecked && !selectedInfoObjectsPosition.contains(position)){
                    Log.e("POSITION", position+" check");
                    selectedInfoObjectsPosition.add(position);
                    infoObjects.get(position).setCheck(true);



                }else if(!isChecked && selectedInfoObjectsPosition.contains(position)){
                    Log.e("POSITION", position + " uncheck");
                    selectedInfoObjectsPosition.remove(selectedInfoObjectsPosition.indexOf(position));
                    infoObjects.get(position).setCheck(false);
                }

            }
        });





        viewHolder.ll_app_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkBox.setChecked(!checkBox.isChecked());
            }
        });
        return view;

    }

    private static class ViewHolder {
        ImageView iv_app_icon;
        TextView tv_app_name;
        CheckBox cb_app_selection;
        LinearLayout ll_app_detail;
    }
}
