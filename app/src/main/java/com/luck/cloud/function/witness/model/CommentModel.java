package com.luck.cloud.function.witness.model;

public class CommentModel {
    /**
     * messageId : 1
     * userId : 12
     * userName : 刘隐
     * messageContent : hgf
     * createTime : 2020-07-18T16:20:16
     * activityId : 6
     */

    private int messageId;
    private int userId;
    private String userName;
    private String messageContent;
    private String createTime;
    private int activityId;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }
    private String username;
    private String content;

    public CommentModel(String username, String content) {
        this.username = username;
        this.content = content;
    }
    public CommentModel(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
