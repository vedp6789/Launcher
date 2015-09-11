package com.gamesapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamesapp.R;
import com.gamesapp.beans.InfoObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ved on 8/4/15.
 */
public class SearchSuggestionListAdapter extends BaseAdapter {
 ArrayList<InfoObject> search_suggestion;
    Context context;
    Typeface custom_font1;
    public SearchSuggestionListAdapter(Context context,ArrayList<InfoObject> search_suggestion){
        this.search_suggestion=search_suggestion;
        this.context=context;
        custom_font1= Typeface.createFromAsset(context.getAssets(), "Chunk.otf");

    }
    @Override
    public int getCount() {
        return search_suggestion.size();
    }

    @Override
    public Object getItem(int position) {
        return  search_suggestion.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.search_app_suggestion_list_helper, null);

            ViewHolder viewHolder=new ViewHolder();
            viewHolder.iv_app_icon= (ImageView) convertView.findViewById(R.id.iv_app_icon);
            viewHolder.tv_app_name= (TextView) convertView.findViewById(R.id.tv_app_name);
            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.iv_app_icon.setImageDrawable(search_suggestion.get(position).getIcon());
        holder.tv_app_name.setText(search_suggestion.get(position).getAppname());
        holder.tv_app_name.setTypeface(custom_font1);

        return convertView;
    }


    private static class ViewHolder {
        ImageView iv_app_icon;
        TextView tv_app_name;

    }
}
