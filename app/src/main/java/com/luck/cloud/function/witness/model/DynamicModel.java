package com.luck.cloud.function.witness.model;

import java.util.List;

public class DynamicModel {
    /**
     * records : [{"dyId":5,"userId":299,"content":"内容","createTime":"2020-07-19T17:13:15","dyType":1,"dyFile":"http://121.36.144.200/group1/M00/00/13/wKgAnF8UAcWAMUsPAFtpBnFXEZE697.jpg-http://121.36.144.200/group1/M00/00/13/wKgAnF8SxoaAHpUQAE17DcWxjHk492.jpg","userPhoto":"http://121.36.144.200/group1/M00/00/13/wKgAnF8Sj8SAKdrqAFik_zpKaug999.jpg","isAttention":0,"isLike":0,"isCollect":0}]
     * total : 1
     * size : 10
     * current : 1
     * orders : []
     * searchCount : true
     * pages : 1
     */

    private int total;
    private int size;
    private int current;
    private boolean searchCount;
    private int pages;
    private List<RecordsBean> records;
    private List<?> orders;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public List<?> getOrders() {
        return orders;
    }

    public void setOrders(List<?> orders) {
        this.orders = orders;
    }

    public static class RecordsBean {
        /**
         * dyId : 5
         * userId : 299
         * content : 内容
         * createTime : 2020-07-19T17:13:15
         * dyType : 1
         * dyFile : http://121.36.144.200/group1/M00/00/13/wKgAnF8UAcWAMUsPAFtpBnFXEZE697.jpg-http://121.36.144.200/group1/M00/00/13/wKgAnF8SxoaAHpUQAE17DcWxjHk492.jpg
         * userPhoto : http://121.36.144.200/group1/M00/00/13/wKgAnF8Sj8SAKdrqAFik_zpKaug999.jpg
         * isAttention : 0
         * isLike : 0
         * isCollect : 0
         */

        private int dyId;
        private int userId;
        private String content;
        private String createTime;
        private int dyType;
        private int fileType;
        private String dyFile;
        private String userPhoto;
        private int isAttention;
        private int isLike;
        private int isCollect;
        private String surfacePlot;
        private String userName;
        private int likeCount;
        private List<CommentModel> messages;

        public int getFileType() {
            return fileType;
        }

        public void setFileType(int fileType) {
            this.fileType = fileType;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public List<CommentModel> getMessages() {
            return messages;
        }

        public void setMessages(List<CommentModel> messages) {
            this.messages = messages;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSurfacePlot() {
            return surfacePlot;
        }

        public void setSurfacePlot(String surfacePlot) {
            this.surfacePlot = surfacePlot;
        }

        public int getDyId() {
            return dyId;
        }

        public void setDyId(int dyId) {
            this.dyId = dyId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDyType() {
            return dyType;
        }

        public void setDyType(int dyType) {
            this.dyType = dyType;
        }

        public String getDyFile() {
            return dyFile;
        }

        public void setDyFile(String dyFile) {
            this.dyFile = dyFile;
        }

        public String getUserPhoto() {
            return userPhoto;
        }

        public void setUserPhoto(String userPhoto) {
            this.userPhoto = userPhoto;
        }

        public int getIsAttention() {
            return isAttention;
        }

        public void setIsAttention(int isAttention) {
            this.isAttention = isAttention;
        }

        public int getIsLike() {
            return isLike;
        }

        public void setIsLike(int isLike) {
            this.isLike = isLike;
        }

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }
    }
//    private int isLike;
//    private int isCollect;
//    private List<CommentModel> commentModel;
//    private List<String> likeUsers;
//
//    public int getIsCollect() {
//        return isCollect;
//    }
//
//    public void setIsCollect(int isCollect) {
//        this.isCollect = isCollect;
//    }
//
//    public List<String> getLikeUsers() {
//        return likeUsers;
//    }
//
//    public void setLikeUsers(List<String> likeUsers) {
//        this.likeUsers = likeUsers;
//    }
//
//    public List<CommentModel> getCommentModel() {
//        return commentModel;
//    }
//
//    public void setCommentModel(List<CommentModel> commentModel) {
//        this.commentModel = commentModel;
//    }
//
//    public int getIsLike() {
//        return isLike;
//    }
//
//    public void setIsLike(int isLike) {
//        this.isLike = isLike;
//    }
}
