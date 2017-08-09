package com.zfsoftmh.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author wangshimei
 * @date: 17/7/5
 * @Description: 积分商城商品列表实体类
 */

public class IntegralMallGoodsInfo implements Parcelable {
    public String goodsid; // 商品ID
    public String createtime; // 创建时间
    public String createtimeStr; // 转换的时间格式
    public String goodsname ; // 商品名
    public List<String> picPathList; // 商品图片集合
    public int numbericvalue; // 商品所需兑换积分
    public int storage; // 商品库存
    public String description; // 商品描述
    public String isactive; // 商品能否兑换状态（1.启用,0.停用）

    public IntegralMallGoodsInfo() {
    }

    public IntegralMallGoodsInfo(String goodsid, String createtime, String createtimeStr, String goodsname, List<String> picPathList, int numbericvalue, int storage, String description, String isactive) {
        this.goodsid = goodsid;
        this.createtime = createtime;
        this.createtimeStr = createtimeStr;
        this.goodsname = goodsname;
        this.picPathList = picPathList;
        this.numbericvalue = numbericvalue;
        this.storage = storage;
        this.description = description;
        this.isactive = isactive;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.goodsid);
        dest.writeString(this.createtime);
        dest.writeString(this.createtimeStr);
        dest.writeString(this.goodsname);
        dest.writeStringList(this.picPathList);
        dest.writeInt(this.numbericvalue);
        dest.writeInt(this.storage);
        dest.writeString(this.description);
        dest.writeString(this.isactive);
    }

    protected IntegralMallGoodsInfo(Parcel in) {
        this.goodsid = in.readString();
        this.createtime = in.readString();
        this.createtimeStr = in.readString();
        this.goodsname = in.readString();
        this.picPathList = in.createStringArrayList();
        this.numbericvalue = in.readInt();
        this.storage = in.readInt();
        this.description = in.readString();
        this.isactive = in.readString();
    }

    public static final Creator<IntegralMallGoodsInfo> CREATOR = new Creator<IntegralMallGoodsInfo>() {
        @Override
        public IntegralMallGoodsInfo createFromParcel(Parcel source) {
            return new IntegralMallGoodsInfo(source);
        }

        @Override
        public IntegralMallGoodsInfo[] newArray(int size) {
            return new IntegralMallGoodsInfo[size];
        }
    };
}
