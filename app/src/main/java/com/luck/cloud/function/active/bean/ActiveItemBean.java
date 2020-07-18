package com.luck.cloud.function.active.bean;

import com.luck.cloud.function.witness.model.CommentModel;

import java.util.List;

public class ActiveItemBean {
    /**
     * records : [{"activityId":1,"activityName":"jj","activityContent":"ii","startDate":"2020-07-18","endDate":"2020-07-20","createUser":"liuyinpark","createTime":"2020-07-18T14:28:32","userName":"刘隐","userId":12},{"activityId":2,"activityName":"jj","activityContent":"ii","startDate":"2020-07-18","endDate":"2020-07-20","createUser":"liuyinpark","createTime":"2020-07-18T14:29:01","userName":"刘隐","userId":12,"activityAddress":"dd","activityUnit":"bb","userPhone":""},{"activityId":3,"activityName":"jj","activityContent":"ii","startDate":"2020-07-18","endDate":"2020-07-20","createUser":"liuyinpark","createTime":"2020-07-18T14:29:10","userName":"刘隐","userId":12,"peopleCount":1234455555,"activityAddress":"dd","activityUnit":"bb","userPhone":"1234455555"},{"activityId":4,"activityName":"jj","activityContent":"ii","startDate":"2020-07-18","endDate":"2020-07-20","createUser":"liuyinpark","createTime":"2020-07-18T14:29:26","userName":"刘隐","userId":12,"peopleCount":1234455555,"activityPicture":"http://121.36.144.200/group1/M00/00/13/wKgAnF8Sj8SAKdrqAFik_zpKaug999.jpg","activityAddress":"dd","activityUnit":"bb","userPhone":"1234455555"},{"activityId":5,"activityName":"jj","activityContent":"ii","startDate":"2020-07-18","endDate":"2020-07-20","createUser":"liuyinpark","createTime":"2020-07-18T14:29:34","userName":"刘隐","userId":12,"peopleCount":1234455555,"activityPicture":"http://121.36.144.200/group1/M00/00/13/wKgAnF8Sj8SAKdrqAFik_zpKaug999.jpg","activityAddress":"dd","activityUnit":"bb","userPhone":"1234455555"}]
     * total : 5
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
         * activityId : 1
         * activityName : jj
         * activityContent : ii
         * startDate : 2020-07-18
         * endDate : 2020-07-20
         * createUser : liuyinpark
         * createTime : 2020-07-18T14:28:32
         * userName : 刘隐
         * userId : 12
         * activityAddress : dd
         * activityUnit : bb
         * userPhone :
         * peopleCount : 1234455555
         * activityPicture : http://121.36.144.200/group1/M00/00/13/wKgAnF8Sj8SAKdrqAFik_zpKaug999.jpg
         */

        private int activityId;
        private String activityName;
        private String activityContent;
        private String startDate;
        private String endDate;
        private String createUser;
        private String createTime;
        private String userName;
        private int userId;
        private String activityAddress;
        private String activityUnit;
        private String userPhone;
        private int peopleCount;
        private String activityPicture;
        private List<CommentModel> messages;

        public List<CommentModel> getMessages() {
            return messages;
        }

        public void setMessages(List<CommentModel> messages) {
            this.messages = messages;
        }

        public int getActivityId() {
            return activityId;
        }

        public void setActivityId(int activityId) {
            this.activityId = activityId;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getActivityContent() {
            return activityContent;
        }

        public void setActivityContent(String activityContent) {
            this.activityContent = activityContent;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getActivityAddress() {
            return activityAddress;
        }

        public void setActivityAddress(String activityAddress) {
            this.activityAddress = activityAddress;
        }

        public String getActivityUnit() {
            return activityUnit;
        }

        public void setActivityUnit(String activityUnit) {
            this.activityUnit = activityUnit;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public int getPeopleCount() {
            return peopleCount;
        }

        public void setPeopleCount(int peopleCount) {
            this.peopleCount = peopleCount;
        }

        public String getActivityPicture() {
            return activityPicture;
        }

        public void setActivityPicture(String activityPicture) {
            this.activityPicture = activityPicture;
        }
    }
}
