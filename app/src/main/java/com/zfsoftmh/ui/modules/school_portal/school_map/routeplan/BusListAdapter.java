package com.zfsoftmh.ui.modules.school_portal.school_map.routeplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zfsoftmh.R;

import java.util.ArrayList;


/**
 * Created by ljq on 2017/7/6.
 * 公交路线规划详细适配器
 */
public class BusListAdapter extends BaseAdapter {
    private ArrayList<String> arrayList;
    private LayoutInflater mInflater;

    public BusListAdapter(Context context, ArrayList<String> list){
        mInflater= LayoutInflater.from(context);
        arrayList=list;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public String getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String item=arrayList.get(position);
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.pathlist_item,parent,false);
            holder.textView= (TextView) convertView.findViewById(R.id.item_path);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(item);
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }



}
