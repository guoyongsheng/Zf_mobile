package com.zfsoftmh.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author wesley
 * @date: 2017/4/7
 * @Description:
 */

public class AgencyMattersInfo implements Parcelable{

    private List<AgencyMattersItemInfo> todoTaskList;
    private int sum;//总数

    public List<AgencyMattersItemInfo> getTodoTaskList() {
        return todoTaskList;
    }

    public void setTodoTaskList(List<AgencyMattersItemInfo> todoTaskList) {
        this.todoTaskList = todoTaskList;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.todoTaskList);
        dest.writeInt(this.sum);
    }

    public AgencyMattersInfo() {
    }

    protected AgencyMattersInfo(Parcel in) {
        this.todoTaskList = in.createTypedArrayList(AgencyMattersItemInfo.CREATOR);
        this.sum = in.readInt();
    }

    public static final Creator<AgencyMattersInfo> CREATOR = new Creator<AgencyMattersInfo>() {
        @Override
        public AgencyMattersInfo createFromParcel(Parcel source) {
            return new AgencyMattersInfo(source);
        }

        @Override
        public AgencyMattersInfo[] newArray(int size) {
            return new AgencyMattersInfo[size];
        }
    };
}
