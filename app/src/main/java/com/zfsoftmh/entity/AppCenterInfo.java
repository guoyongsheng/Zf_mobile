package com.zfsoftmh.entity;

import java.util.List;

/**
 * @author wesley
 * @date: 2017/3/28
 * @Description: 应用中心的实体类
 */

public class AppCenterInfo {

    private String id;
    private String systemCode;
    private String systemName;
    private String procode;
    private String otherFlag;
    private List<AppCenterItemInfo> serviceEntityList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getProcode() {
        return procode;
    }

    public void setProcode(String procode) {
        this.procode = procode;
    }

    public String getOtherFlag() {
        return otherFlag;
    }

    public void setOtherFlag(String otherFlag) {
        this.otherFlag = otherFlag;
    }

    public List<AppCenterItemInfo> getServiceEntityList() {
        return serviceEntityList;
    }

    public void setServiceEntityList(List<AppCenterItemInfo> serviceEntityList) {
        this.serviceEntityList = serviceEntityList;
    }
}
