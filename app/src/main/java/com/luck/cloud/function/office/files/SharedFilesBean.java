package com.luck.cloud.function.office.files;

/**
 * created by YQ on 2019-05-24 16:13
 */
public class SharedFilesBean {


    /**
     * fileId : 1
     * fileName : sdf
     * filePath : http://121.36.144.200/group1/M00/00/17/wKgAnF8ac9iAH14zAAAhUja_n_g901.jpg
     * userId : 300
     * createTime : 2020-07-24T13:57:15
     * createUser : liuyin
     */

    private int fileId;
    private String fileName;
    private String filePath;
    private int userId;
    private String createTime;
    private String createUser;

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
