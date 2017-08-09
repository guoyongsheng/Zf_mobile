package com.zfsoftmh.entity;

import io.realm.RealmObject;

/**
 * @author wesley
 * @date: 2017-6-6
 * @Description: string类型的对象
 */

public class RealmString extends RealmObject {

    private String value;

    public RealmString(){

    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
