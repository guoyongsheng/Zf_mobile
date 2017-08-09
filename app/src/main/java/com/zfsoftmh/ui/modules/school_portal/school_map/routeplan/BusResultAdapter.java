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
 * 公交路径规划所有结果列表适配器
 */
public class BusResultAdapter extends BaseAdapter {
    private ArrayList<BusRouteInfo> mDateList;
    private int flag=0;

    LayoutInflater mInflater;
    public BusResultAdapter(Context context, ArrayList<BusRouteInfo> list, int flag){
        mDateList=list;
        this.flag=flag;
        mInflater= LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        return mDateList.size();
    }

    @Override
    public BusRouteInfo getItem(int position) {
        return mDateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        BusRouteInfo item=mDateList.get(position);
        if(convertView==null){
            holder=new ViewHolder();
            convertView= mInflater.inflate(R.layout.busresult_item,null);
            holder.busName= (TextView) convertView.findViewById(R.id.bus_name);
            holder.info= (TextView) convertView.findViewById(R.id.bus_info);
            convertView.setTag(holder);
        }else{
           holder= (ViewHolder) convertView.getTag();
        }

        if(flag==0){
            holder.busName.setText(item.getBusname());
        String cost=item.getCost()+"元";
        String time=item.getTime()+"分钟";
        String walkdistance="步行"+item.getWalk_distance()+"米";
        holder.info.setText(" "+cost+" - "+walkdistance);}else{
        holder.busName.setText(item.getBusname());}
        return convertView;
    }




    class ViewHolder {
        TextView busName;
        TextView info;
    }

}
