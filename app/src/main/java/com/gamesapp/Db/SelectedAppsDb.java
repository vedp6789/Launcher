package com.gamesapp.Db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ved on 6/4/15.
 */
public class SelectedAppsDb extends SQLiteOpenHelper{

    public static final String DB_NAME="selected_apps_db";
    public static final int DB_VERSION=29;
    public static final String TB_NAME="selected_apps_relation";
    public static final String ID="_id";
    public static final String APP_NAME="app_name";
    public static final String APP_P_NAME="package_name";
    public static final String APP_V_NAME="version_name";
    public static final String APP_V_CODE="version_code";

    private final String createString="CREATE TABLE "+TB_NAME+ " ( "+ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+APP_NAME+" VARCHAR(250), "+APP_P_NAME+" VARCHAR, "+APP_V_NAME+" TEXT, "+APP_V_CODE+" TEXT )";


    public SelectedAppsDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(createString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TB_NAME);
        onCreate(db);
    }

    public Cursor getAllApps(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String rq="Select * from "+TB_NAME+ " ORDER BY "+SelectedAppsDb.APP_NAME+" COLLATE NOCASE;";  /*  This will return alphabetically arranged cursor */
       /* String rq="Select * from "+TB_NAME;*/
        Cursor cursor=sqLiteDatabase.rawQuery(rq,null);
        return cursor;
    }

    public  void insertAppDetails(ContentValues contentValues){

        try{
            SQLiteDatabase sqLiteDatabase=getWritableDatabase();
            String app_name=contentValues.getAsString(APP_NAME);
            app_name.replace("'","\\'");

            String rq="INSERT INTO "+TB_NAME+" ( "+APP_NAME+" , "+APP_P_NAME+" , "+APP_V_NAME+" , "+APP_V_CODE+" ) "+" values "+" ( "+ "'"+app_name+"'"+" , "+"'"+contentValues.getAsString(APP_P_NAME)+"'"+" , "+"'"+contentValues.getAsString(APP_V_NAME)+"'"+" , "+"'"+contentValues.getAsString(APP_V_CODE)+"' )";
            sqLiteDatabase.execSQL(rq);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public  void delete_whole_table(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String rq="DELETE FROM "+ TB_NAME;
        sqLiteDatabase.execSQL(rq);
    }




}
