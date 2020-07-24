package com.luck.cloud.function.office.beans;

public class ArrangeBean {
    /**
     * planId : 1
     * planName : gfff
     * planDetails : 一般
     * planDay : 星期五
     * planDate : 2020-07-24
     * planTime : 15:04:00
     * userId : 300
     * createTime : 2020-07-24T15:04:48
     * createUser : liuyin
     */

    private int planId;
    private String planName;
    private String planDetails;
    private String planDay;
    private String planDate;
    private String planTime;
    private int userId;
    private String createTime;
    private String createUser;

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDetails() {
        return planDetails;
    }

    public void setPlanDetails(String planDetails) {
        this.planDetails = planDetails;
    }

    public String getPlanDay() {
        return planDay;
    }

    public void setPlanDay(String planDay) {
        this.planDay = planDay;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
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
