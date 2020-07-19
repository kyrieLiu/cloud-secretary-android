package com.luck.cloud.function.office.notice;

/**
 * Created by liuyin on 2019/2/27 15:19
 * Description: 通知公告
 */
public class NoticeBean {


    /**
     * noticeId : 3
     * titleName : 测试公告标题
     * startTime : 2020-07-12T16:00:00
     * endTime : 2020-07-21T16:00:00
     * createUser : sunjie
     * createTime : 2020-07-19T14:45:54
     * noticeStatus : 1
     * pushTime : 2020-07-19T14:45:54
     */

    private int noticeId;
    private String titleName;
    private String startTime;
    private String endTime;
    private String createUser;
    private String createTime;
    private int noticeStatus;
    private String pushTime;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(int noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }
}
