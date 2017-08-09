package com.zfsoftmh.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wangshimei
 * @date: 17/6/14
 * @Description: 我的收藏
 */

public class FavouritesListInfo implements Parcelable {


    public String favourid; // 收藏ID
    public String favouritesort; // 收藏种类（1.文本，2.图片，3.视频，4.网址，5.附件）
    public String favouriteavatar; // 收藏头像
    public String favouritecontent; // 收藏内容
    public String userid; // 用户名
    public String favouritecustom; // 被收藏人
    public String favouriteimage; // 附件收藏缩略图
    public String favouritetitle; // 收藏标题
    public String favouriteattachmentsort; // 附件种类
    public String favouriteattachmentsize; // 附件大小
    public String attachmentPath; // 文件下载路径（5.附件下载路径，4.网址中缩略图路径）
    public String favouritedateStr; // 收藏时间
    public int toPage;
    public int perPageSize;
    public int totalItem;
    public int startRow;
    public int endRow;

    public FavouritesListInfo() {
    }

    public FavouritesListInfo(String favourid, String favouritesort,
                              String favouriteavatar, String favouritedateStr,
                              String favouritecontent, String userid,
                              String favouritecustom, String favouriteimage,
                              String favouritetitle, String favouriteattachmentsort,
                              String favouriteattachmentsize, String attachmentPath, int toPage,
                              int perPageSize, int totalItem, int startRow, int endRow) {
        this.favourid = favourid;
        this.favouritesort = favouritesort;
        this.favouriteavatar = favouriteavatar;
        this.favouritedateStr = favouritedateStr;
        this.favouritecontent = favouritecontent;
        this.userid = userid;
        this.favouritecustom = favouritecustom;
        this.favouriteimage = favouriteimage;
        this.favouritetitle = favouritetitle;
        this.favouriteattachmentsort = favouriteattachmentsort;
        this.favouriteattachmentsize = favouriteattachmentsize;
        this.attachmentPath = attachmentPath;
        this.toPage = toPage;
        this.perPageSize = perPageSize;
        this.totalItem = totalItem;
        this.startRow = startRow;
        this.endRow = endRow;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.favourid);
        dest.writeString(this.favouritesort);
        dest.writeString(this.favouriteavatar);
        dest.writeString(this.favouritecontent);
        dest.writeString(this.userid);
        dest.writeString(this.favouritecustom);
        dest.writeString(this.favouriteimage);
        dest.writeString(this.favouritetitle);
        dest.writeString(this.favouriteattachmentsort);
        dest.writeString(this.favouriteattachmentsize);
        dest.writeString(this.attachmentPath);
        dest.writeString(this.favouritedateStr);
        dest.writeInt(this.toPage);
        dest.writeInt(this.perPageSize);
        dest.writeInt(this.totalItem);
        dest.writeInt(this.startRow);
        dest.writeInt(this.endRow);
    }

    protected FavouritesListInfo(Parcel in) {
        this.favourid = in.readString();
        this.favouritesort = in.readString();
        this.favouriteavatar = in.readString();
        this.favouritecontent = in.readString();
        this.userid = in.readString();
        this.favouritecustom = in.readString();
        this.favouriteimage = in.readString();
        this.favouritetitle = in.readString();
        this.favouriteattachmentsort = in.readString();
        this.favouriteattachmentsize = in.readString();
        this.attachmentPath = in.readString();
        this.favouritedateStr = in.readString();
        this.toPage = in.readInt();
        this.perPageSize = in.readInt();
        this.totalItem = in.readInt();
        this.startRow = in.readInt();
        this.endRow = in.readInt();
    }

    public static final Parcelable.Creator<FavouritesListInfo> CREATOR = new Parcelable.Creator<FavouritesListInfo>() {
        @Override
        public FavouritesListInfo createFromParcel(Parcel source) {
            return new FavouritesListInfo(source);
        }

        @Override
        public FavouritesListInfo[] newArray(int size) {
            return new FavouritesListInfo[size];
        }
    };
}
