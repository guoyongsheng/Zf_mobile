package com.zfsoftmh.entity;

/**
 * Created by li on 2017/7/4.
 */

public class LongAndLat {
    String x;
    String y;

    public Double getX() {
        return Double.valueOf(x).doubleValue();
    }

    public void setX(String x) {
        this.x = x;
    }

    public Double getY() {
        return Double.valueOf(y).doubleValue();
    }

    public void setY(String y) {
        this.y = y;
    }
}
