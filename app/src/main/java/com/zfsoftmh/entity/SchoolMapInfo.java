package com.zfsoftmh.entity;

import com.amap.api.services.core.LatLonPoint;

import java.util.ArrayList;

/**
 * Created by ljq on 2017/6/28.
 */

public class SchoolMapInfo {


    String name;
    ArrayList<LongAndLat> pointList;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<LongAndLat> getPointList() {
        return pointList;
    }

    public void setPointList(ArrayList<LongAndLat> pointList) {
        this.pointList = pointList;
    }
}
