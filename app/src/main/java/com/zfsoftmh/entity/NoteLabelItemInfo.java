package com.zfsoftmh.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wangshimei
 * @date: 17/5/11
 * @Description: 云笔记标签
 */

public class NoteLabelItemInfo implements Parcelable {

    /**
     * code : 1
     * msg : 成功
     * data : [{"memoCatalogId":"48944E4B1694545BE0538513470AC4A1","memoCatalogName":"未标签","catalogColor":"959595","sortNumber":1},{"memoCatalogId":"48944E4B1690545BE0538513470AC4A1","memoCatalogName":"旅游","catalogColor":"FDD5A6","sortNumber":2},{"memoCatalogId":"48944E4B1691545BE0538513470AC4A1","memoCatalogName":"个人","catalogColor":"A6EAF6","sortNumber":3},{"memoCatalogId":"48944E4B1692545BE0538513470AC4A1","memoCatalogName":"生活","catalogColor":"A6A6F8","sortNumber":4},{"memoCatalogId":"48944E4B1693545BE0538513470AC4A1","memoCatalogName":"工作","catalogColor":"FDB8B8","sortNumber":5}]
     */

    public String memoCatalogId; // 标签ID
    public String memoCatalogName; // 标签名
    public String catalogColor; // 类别颜色值，对应rgb值
    public Integer sortNumber; // 排序

    public NoteLabelItemInfo() {
    }

    public NoteLabelItemInfo(String memoCatalogId, String memoCatalogName, String catalogColor, Integer sortNumber) {
        this.memoCatalogId = memoCatalogId;
        this.memoCatalogName = memoCatalogName;
        this.catalogColor = catalogColor;
        this.sortNumber = sortNumber;
    }

    public String getMemoCatalogId() {
        return memoCatalogId;
    }

    public void setMemoCatalogId(String memoCatalogId) {
        this.memoCatalogId = memoCatalogId;
    }

    public String getMemoCatalogName() {
        return memoCatalogName;
    }

    public void setMemoCatalogName(String memoCatalogName) {
        this.memoCatalogName = memoCatalogName;
    }

    public String getCatalogColor() {
        return catalogColor;
    }

    public void setCatalogColor(String catalogColor) {
        this.catalogColor = catalogColor;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.memoCatalogId);
        dest.writeString(this.memoCatalogName);
        dest.writeString(this.catalogColor);
        dest.writeValue(this.sortNumber);
    }

    protected NoteLabelItemInfo(Parcel in) {
        this.memoCatalogId = in.readString();
        this.memoCatalogName = in.readString();
        this.catalogColor = in.readString();
        this.sortNumber = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<NoteLabelItemInfo> CREATOR = new Parcelable.Creator<NoteLabelItemInfo>() {
        @Override
        public NoteLabelItemInfo createFromParcel(Parcel source) {
            return new NoteLabelItemInfo(source);
        }

        @Override
        public NoteLabelItemInfo[] newArray(int size) {
            return new NoteLabelItemInfo[size];
        }
    };
}
