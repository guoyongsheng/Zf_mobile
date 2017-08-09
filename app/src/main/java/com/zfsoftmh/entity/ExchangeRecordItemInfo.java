package com.zfsoftmh.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author wangshimei
 * @date: 17/7/10
 * @Description: 兑换记录列表
 */

public class ExchangeRecordItemInfo implements Parcelable {

    public String id; // 订单编号
    public String createtime; // 记录创建时间
    public String createtimeStr; // 展示时间
    public String userid; // 用户ID
    public String goodspicPath; // 商品显示图片
    public String goodsid; // 商品ID
    public String goodsname; // 商品名称
    public int amount; // 兑换商品数量
    public String flag; // 领取标志（0.未领取，2.已领取）
    public String exhangetime; // 商品领取时间（flag为0时不显示）
    public String exhangetimeStr; // 展示的领取时间

    public ExchangeRecordItemInfo() {
    }

    public ExchangeRecordItemInfo(String id, String createtime,
                                  String createtimeStr, String userid,
                                  String goodspicPath, String goodsid,
                                  String goodsname, int amount, String flag,
                                  String exhangetime, String exhangetimeStr) {
        this.id = id;
        this.createtime = createtime;
        this.createtimeStr = createtimeStr;
        this.userid = userid;
        this.goodspicPath = goodspicPath;
        this.goodsid = goodsid;
        this.goodsname = goodsname;
        this.amount = amount;
        this.flag = flag;
        this.exhangetime = exhangetime;
        this.exhangetimeStr = exhangetimeStr;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.createtime);
        dest.writeString(this.createtimeStr);
        dest.writeString(this.userid);
        dest.writeString(this.goodspicPath);
        dest.writeString(this.goodsid);
        dest.writeString(this.goodsname);
        dest.writeInt(this.amount);
        dest.writeString(this.flag);
        dest.writeString(this.exhangetime);
        dest.writeString(this.exhangetimeStr);
    }

    protected ExchangeRecordItemInfo(Parcel in) {
        this.id = in.readString();
        this.createtime = in.readString();
        this.createtimeStr = in.readString();
        this.userid = in.readString();
        this.goodspicPath = in.readString();
        this.goodsid = in.readString();
        this.goodsname = in.readString();
        this.amount = in.readInt();
        this.flag = in.readString();
        this.exhangetime = in.readString();
        this.exhangetimeStr = in.readString();
    }

    public static final Creator<ExchangeRecordItemInfo> CREATOR = new Creator<ExchangeRecordItemInfo>() {
        @Override
        public ExchangeRecordItemInfo createFromParcel(Parcel source) {
            return new ExchangeRecordItemInfo(source);
        }

        @Override
        public ExchangeRecordItemInfo[] newArray(int size) {
            return new ExchangeRecordItemInfo[size];
        }
    };
}
