package com.luck.cloud.function.office.beans;

public class LowIncomePerson {
    /**
     * familyId : 1
     * familyName : 我五一
     * userId : 300
     * createTime : 2020-07-24T16:13:50
     * createUser : liuyin
     */

    private int familyId;
    private String familyName;
    private int userId;
    private String createTime;
    private String createUser;

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
