package com.luck.cloud.function.mine.bean;

import java.util.List;

public class PersonInfoBean {
    /**
     * peopleId : 12
     * peopleLoginname : liuyinpark
     * peoplePassword : f7b449140ffbe627364f137a3b548b94
     * peopleName : 刘隐
     * peopleMobile : 15001121167
     * peopleMail : 652992429@qq.com
     * photoLogo : http://121.36.144.200/group1/M00/00/01/wKgAnF6c9rmAO5hTAAAgDOh_BRw290.png
     * peopleType : 1
     * parkId : 1
     * deptId : 59
     * roles : [2,50]
     */

    private int peopleId;
    private String peopleLoginname;
    private String peoplePassword;
    private String peopleName;
    private String peopleMobile;
    private String peopleMail;
    private String photoLogo;
    private String peopleType;
    private int parkId;
    private int deptId;
    private List<Integer> roles;

    public int getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(int peopleId) {
        this.peopleId = peopleId;
    }

    public String getPeopleLoginname() {
        return peopleLoginname;
    }

    public void setPeopleLoginname(String peopleLoginname) {
        this.peopleLoginname = peopleLoginname;
    }

    public String getPeoplePassword() {
        return peoplePassword;
    }

    public void setPeoplePassword(String peoplePassword) {
        this.peoplePassword = peoplePassword;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getPeopleMobile() {
        return peopleMobile;
    }

    public void setPeopleMobile(String peopleMobile) {
        this.peopleMobile = peopleMobile;
    }

    public String getPeopleMail() {
        return peopleMail;
    }

    public void setPeopleMail(String peopleMail) {
        this.peopleMail = peopleMail;
    }

    public String getPhotoLogo() {
        return photoLogo;
    }

    public void setPhotoLogo(String photoLogo) {
        this.photoLogo = photoLogo;
    }

    public String getPeopleType() {
        return peopleType;
    }

    public void setPeopleType(String peopleType) {
        this.peopleType = peopleType;
    }

    public int getParkId() {
        return parkId;
    }

    public void setParkId(int parkId) {
        this.parkId = parkId;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }
}
