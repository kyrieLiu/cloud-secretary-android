package com.luck.cloud.function.study.model;

import java.io.Serializable;

public class StudyTabModel implements Serializable {
    /**
     * atId : 1364
     * cuName : 思想道德修养与法律基础
     * atCode : 2c6b1e8d8dd87c2460abe5d192657c58
     * atDetail : 思想道德修养与法律基础
     * atPid : 0
     * atType : 1
     * atFrom : 1
     * parkId : 1
     * atClass : 1
     */

    private int atId;
    private String cuName;
    private String atCode;
    private String atDetail;
    private int atPid;
    private int atType;
    private int atFrom;
    private int parkId;
    private int atClass;

    public int getAtId() {
        return atId;
    }

    public void setAtId(int atId) {
        this.atId = atId;
    }

    public String getCuName() {
        return cuName;
    }

    public void setCuName(String cuName) {
        this.cuName = cuName;
    }

    public String getAtCode() {
        return atCode;
    }

    public void setAtCode(String atCode) {
        this.atCode = atCode;
    }

    public String getAtDetail() {
        return atDetail;
    }

    public void setAtDetail(String atDetail) {
        this.atDetail = atDetail;
    }

    public int getAtPid() {
        return atPid;
    }

    public void setAtPid(int atPid) {
        this.atPid = atPid;
    }

    public int getAtType() {
        return atType;
    }

    public void setAtType(int atType) {
        this.atType = atType;
    }

    public int getAtFrom() {
        return atFrom;
    }

    public void setAtFrom(int atFrom) {
        this.atFrom = atFrom;
    }

    public int getParkId() {
        return parkId;
    }

    public void setParkId(int parkId) {
        this.parkId = parkId;
    }

    public int getAtClass() {
        return atClass;
    }

    public void setAtClass(int atClass) {
        this.atClass = atClass;
    }
}
