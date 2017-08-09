package com.zfsoftmh.ui.modules.school_portal.school_map.routeplan;

import java.util.ArrayList;

/**
 * Created by ljq on 2017/7/6.
 * 公交路线规划需要存储的信息
 */
public class BusRouteInfo {
    private String busname;
    private float time;
    private float cost;
    private float walk_distance;
    private ArrayList<BusStationInfo> busStationInfos;

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getWalk_distance() {
        return walk_distance;
    }

    public void setWalk_distance(float walk_distance) {
        this.walk_distance = walk_distance;
    }

    public String getBusname() {
        return busname;
    }

    public void setBusname(String busname) {
        this.busname = busname;
    }

    public ArrayList<BusStationInfo> getBusStationInfos() {
        return busStationInfos;
    }

    public void setBusStationInfos(ArrayList<BusStationInfo> busStationInfos) {
        this.busStationInfos = busStationInfos;
    }
}
