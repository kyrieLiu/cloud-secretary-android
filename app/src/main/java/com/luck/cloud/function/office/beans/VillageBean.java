package com.luck.cloud.function.office.beans;

public class VillageBean {
    /**
     * villageId : 1
     * villageDetails : 推荐
     * userId : 300
     * createTime : 2020-07-24T15:54:06
     * createUser : liuyin
     */

    private int villageId;
    private String villageDetails;
    private int userId;
    private String createTime;
    private String createUser;

    public int getVillageId() {
        return villageId;
    }

    public void setVillageId(int villageId) {
        this.villageId = villageId;
    }

    public String getVillageDetails() {
        return villageDetails;
    }

    public void setVillageDetails(String villageDetails) {
        this.villageDetails = villageDetails;
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
