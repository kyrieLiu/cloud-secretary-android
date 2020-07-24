package com.luck.cloud.function.office.clock;

public class ClockBean {
    /**
     * clockId : 1
     * userId : 300
     * clockDate : 2020-07-24
     * clockTime : 2020-07-24T14:50:22
     * clockMonth : 202007
     */

    private int clockId;
    private int userId;
    private String clockDate;
    private String clockTime;
    private String clockMonth;

    public int getClockId() {
        return clockId;
    }

    public void setClockId(int clockId) {
        this.clockId = clockId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getClockDate() {
        return clockDate;
    }

    public void setClockDate(String clockDate) {
        this.clockDate = clockDate;
    }

    public String getClockTime() {
        return clockTime;
    }

    public void setClockTime(String clockTime) {
        this.clockTime = clockTime;
    }

    public String getClockMonth() {
        return clockMonth;
    }

    public void setClockMonth(String clockMonth) {
        this.clockMonth = clockMonth;
    }
}
