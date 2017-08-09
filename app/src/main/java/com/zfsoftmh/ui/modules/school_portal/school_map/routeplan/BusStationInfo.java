package com.zfsoftmh.ui.modules.school_portal.school_map.routeplan;

/**
 * Created by ljq on 2017/7/3.
 * 公交车规划路线时所需要的站点信息
 */
public class BusStationInfo {

    private String arrivalStation;
    private String departStation;
    private String busName;

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public String getDepartStation() {
        return departStation;
    }

    public void setDepartStation(String departStation) {
        this.departStation = departStation;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }
}
