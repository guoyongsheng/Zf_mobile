package com.zfsoftmh.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wangshimei
 * @date: 17/5/15
 * @Description: 云笔记列表
 */

public class CloudNoteListInfo implements Parcelable {
    public String memoCatalogId; // 标签类别id
    public String catalogColor; // 标签类别颜色
    public String memoCatalogName; // 便签类别名
    public String memoTitle; // 备忘录标题
    public String memoFileName; // 备忘录文件名称
    public String createTime; // 创建时间
    public String memoPath; // 备忘录路径(下载)
    public String userName; // 用户名
    public String contentFlag; // 图文标志位 ，1为文，2为图，3为图文
    public int toPage; // 页数
    public int perPageSize; // 数据条数
    public int totalItem; // 页数
    public int startRow; // 页数
    public int endRow; // 页数
    public String memoContent; // 备忘录内容
    public boolean isSelect = false; // 选中状态

    public CloudNoteListInfo() {
    }

    public CloudNoteListInfo(String memoCatalogId, String catalogColor,
                             String memoCatalogName, String memoTitle,
                             String memoFileName, String createTime,
                             String memoPath, String userName,
                             String contentFlag, int toPage,
                             int perPageSize, int totalItem,
                             int startRow, int endRow,
                             String memoContent, boolean isSelect) {
        this.memoCatalogId = memoCatalogId;
        this.catalogColor = catalogColor;
        this.memoCatalogName = memoCatalogName;
        this.memoTitle = memoTitle;
        this.memoFileName = memoFileName;
        this.createTime = createTime;
        this.memoPath = memoPath;
        this.userName = userName;
        this.contentFlag = contentFlag;
        this.toPage = toPage;
        this.perPageSize = perPageSize;
        this.totalItem = totalItem;
        this.startRow = startRow;
        this.endRow = endRow;
        this.memoContent = memoContent;
        this.isSelect = isSelect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.memoCatalogId);
        dest.writeString(this.catalogColor);
        dest.writeString(this.memoCatalogName);
        dest.writeString(this.memoTitle);
        dest.writeString(this.memoFileName);
        dest.writeString(this.createTime);
        dest.writeString(this.memoPath);
        dest.writeString(this.userName);
        dest.writeString(this.contentFlag);
        dest.writeInt(this.toPage);
        dest.writeInt(this.perPageSize);
        dest.writeInt(this.totalItem);
        dest.writeInt(this.startRow);
        dest.writeInt(this.endRow);
        dest.writeString(this.memoContent);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    protected CloudNoteListInfo(Parcel in) {
        this.memoCatalogId = in.readString();
        this.catalogColor = in.readString();
        this.memoCatalogName = in.readString();
        this.memoTitle = in.readString();
        this.memoFileName = in.readString();
        this.createTime = in.readString();
        this.memoPath = in.readString();
        this.userName = in.readString();
        this.contentFlag = in.readString();
        this.toPage = in.readInt();
        this.perPageSize = in.readInt();
        this.totalItem = in.readInt();
        this.startRow = in.readInt();
        this.endRow = in.readInt();
        this.memoContent = in.readString();
        this.isSelect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CloudNoteListInfo> CREATOR = new Parcelable.Creator<CloudNoteListInfo>() {
        @Override
        public CloudNoteListInfo createFromParcel(Parcel source) {
            return new CloudNoteListInfo(source);
        }

        @Override
        public CloudNoteListInfo[] newArray(int size) {
            return new CloudNoteListInfo[size];
        }
    };
}
