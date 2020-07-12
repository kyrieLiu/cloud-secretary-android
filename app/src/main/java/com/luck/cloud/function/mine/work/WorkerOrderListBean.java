package com.luck.cloud.function.mine.work;

import java.util.List;

/**
 * Created by liuyin on 2018/10/31 10:18
 */
public class WorkerOrderListBean {
    /**
     * id : 7
     * username : 物业工程师
     * password : 40881a303c959d810320f692792c5c83
     * telephone : 15211111111
     * loginName : null
     * name : 布里茨
     * source : null
     * pushDate : null
     * credit : null
     * type : 2
     * status : null
     * isDel : 0
     * pwdFlag : 0
     * userType : 6
     * detailedAddress : null
     * updataStatus : null
     * employeeNo : null
     * headImage : null
     * sendMessageStatus : null
     * dateWorkOrderListDTOList : [{"id":3,"uniqueNumber":"DG20181030000003","repairTime":null,"repairSource":2,"belongProject":1,"belongProjectStr":null,"repairProject":3,"repairProjectStr":null,"createBy":1,"repairBy":1,"repairByStr":null,"repairByPhone":"15737898765","repairAddress":"北京市","repairDescribe":"张三","isEffective":1,"repairType":1,"voice":null,"voiceTime":null,"img":"[\"\\\\static\\\\img\\\\2018-10-30\\\\d3ad8973d4494e33b1f14fe36dcb68c4u=1141023710,4129385760&fm=26&gp=0.jpg\",\"\\\\static\\\\img\\\\2018-10-30\\\\7cebc4a8670c4ceaa0a2cd8467492d7fu=114535759,2106559056&fm=26&gp=0.jpg\"]","workOrderType":0,"isRead":0,"status":4,"completionTime":null,"evaluationContent":null,"overallEvaluationLevel":null,"isRecord":0,"userList":[{"id":1,"username":"admin","password":"c0e024d9200b5705bc4804722636378a","telephone":"15322222222","loginName":null,"name":"地址","source":null,"pushDate":null,"credit":null,"type":5,"status":null,"isDel":0,"pwdFlag":0,"userType":6,"detailedAddress":null,"updataStatus":0,"employeeNo":null,"headImage":null,"sendMessageStatus":null}]},{"id":5,"uniqueNumber":"DG20181030000005","repairTime":"2018-10-30T02:53:22.000+0000","repairSource":1,"belongProject":1,"belongProjectStr":null,"repairProject":null,"repairProjectStr":null,"createBy":53400,"repairBy":53400,"repairByStr":null,"repairByPhone":"15600897865","repairAddress":"地下室","repairDescribe":"漏水","isEffective":0,"repairType":2,"voice":"","voiceTime":0,"img":"","workOrderType":0,"isRead":1,"status":1,"completionTime":null,"evaluationContent":null,"overallEvaluationLevel":null,"isRecord":1,"userList":[]}]
     */

    private int id;
    private String username;
    private String password;
    private String telephone;
    private Object loginName;
    private String name;
    private Object source;
    private Object pushDate;
    private Object credit;
    private int type;
    private Object status;
    private int isDel;
    private int pwdFlag;
    private int userType;
    private Object detailedAddress;
    private Object updataStatus;
    private Object employeeNo;
    private Object headImage;
    private Object sendMessageStatus;
    private boolean showDetail=true;//是否展示详情
    private List<DateWorkOrderListDTOListBean> dateWorkOrderListDTOList;

    public boolean isShowDetail() {
        return showDetail;
    }

    public void setShowDetail(boolean showDetail) {
        this.showDetail = showDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Object getLoginName() {
        return loginName;
    }

    public void setLoginName(Object loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Object getPushDate() {
        return pushDate;
    }

    public void setPushDate(Object pushDate) {
        this.pushDate = pushDate;
    }

    public Object getCredit() {
        return credit;
    }

    public void setCredit(Object credit) {
        this.credit = credit;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public int getPwdFlag() {
        return pwdFlag;
    }

    public void setPwdFlag(int pwdFlag) {
        this.pwdFlag = pwdFlag;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Object getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(Object detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public Object getUpdataStatus() {
        return updataStatus;
    }

    public void setUpdataStatus(Object updataStatus) {
        this.updataStatus = updataStatus;
    }

    public Object getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(Object employeeNo) {
        this.employeeNo = employeeNo;
    }

    public Object getHeadImage() {
        return headImage;
    }

    public void setHeadImage(Object headImage) {
        this.headImage = headImage;
    }

    public Object getSendMessageStatus() {
        return sendMessageStatus;
    }

    public void setSendMessageStatus(Object sendMessageStatus) {
        this.sendMessageStatus = sendMessageStatus;
    }

    public List<DateWorkOrderListDTOListBean> getDateWorkOrderListDTOList() {
        return dateWorkOrderListDTOList;
    }

    public void setDateWorkOrderListDTOList(List<DateWorkOrderListDTOListBean> dateWorkOrderListDTOList) {
        this.dateWorkOrderListDTOList = dateWorkOrderListDTOList;
    }

    public static class DateWorkOrderListDTOListBean {
        /**
         * id : 3
         * uniqueNumber : DG20181030000003
         * repairTime : null
         * repairSource : 2
         * belongProject : 1
         * belongProjectStr : null
         * repairProject : 3
         * repairProjectStr : null
         * createBy : 1
         * repairBy : 1
         * repairByStr : null
         * repairByPhone : 15737898765
         * repairAddress : 北京市
         * repairDescribe : 张三
         * isEffective : 1
         * repairType : 1
         * voice : null
         * voiceTime : null
         * img : ["\\static\\img\\2018-10-30\\d3ad8973d4494e33b1f14fe36dcb68c4u=1141023710,4129385760&fm=26&gp=0.jpg","\\static\\img\\2018-10-30\\7cebc4a8670c4ceaa0a2cd8467492d7fu=114535759,2106559056&fm=26&gp=0.jpg"]
         * workOrderType : 0
         * isRead : 0
         * status : 4
         * completionTime : null
         * evaluationContent : null
         * overallEvaluationLevel : null
         * isRecord : 0
         * userList : [{"id":1,"username":"admin","password":"c0e024d9200b5705bc4804722636378a","telephone":"15322222222","loginName":null,"name":"地址","source":null,"pushDate":null,"credit":null,"type":5,"status":null,"isDel":0,"pwdFlag":0,"userType":6,"detailedAddress":null,"updataStatus":0,"employeeNo":null,"headImage":null,"sendMessageStatus":null}]
         */

        private int id;
        private String uniqueNumber;
        private String repairTime;
        private int repairSource;
        private int belongProject;
        private Object belongProjectStr;
        private int repairProject;
        private Object repairProjectStr;
        private int createBy;
        private int repairBy;
        private Object repairByStr;
        private String repairByPhone;
        private String repairAddress;
        private String repairDescribe;
        private int isEffective;
        private int repairType;
        private Object voice;
        private Object voiceTime;
        private String img;
        private int workOrderType;
        private int isRead;
        private int status;
        private Object completionTime;
        private Object evaluationContent;
        private Object overallEvaluationLevel;
        private int isRecord;
        private String appointmentTime;
        private List<UserListBean> userList;

        public String getAppointmentTime() {
            return appointmentTime;
        }

        public void setAppointmentTime(String appointmentTime) {
            this.appointmentTime = appointmentTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUniqueNumber() {
            return uniqueNumber;
        }

        public void setUniqueNumber(String uniqueNumber) {
            this.uniqueNumber = uniqueNumber;
        }

        public String getRepairTime() {
            return repairTime;
        }

        public void setRepairTime(String repairTime) {
            this.repairTime = repairTime;
        }

        public int getRepairSource() {
            return repairSource;
        }

        public void setRepairSource(int repairSource) {
            this.repairSource = repairSource;
        }

        public int getBelongProject() {
            return belongProject;
        }

        public void setBelongProject(int belongProject) {
            this.belongProject = belongProject;
        }

        public Object getBelongProjectStr() {
            return belongProjectStr;
        }

        public void setBelongProjectStr(Object belongProjectStr) {
            this.belongProjectStr = belongProjectStr;
        }

        public int getRepairProject() {
            return repairProject;
        }

        public void setRepairProject(int repairProject) {
            this.repairProject = repairProject;
        }

        public Object getRepairProjectStr() {
            return repairProjectStr;
        }

        public void setRepairProjectStr(Object repairProjectStr) {
            this.repairProjectStr = repairProjectStr;
        }

        public int getCreateBy() {
            return createBy;
        }

        public void setCreateBy(int createBy) {
            this.createBy = createBy;
        }

        public int getRepairBy() {
            return repairBy;
        }

        public void setRepairBy(int repairBy) {
            this.repairBy = repairBy;
        }

        public Object getRepairByStr() {
            return repairByStr;
        }

        public void setRepairByStr(Object repairByStr) {
            this.repairByStr = repairByStr;
        }

        public String getRepairByPhone() {
            return repairByPhone;
        }

        public void setRepairByPhone(String repairByPhone) {
            this.repairByPhone = repairByPhone;
        }

        public String getRepairAddress() {
            return repairAddress;
        }

        public void setRepairAddress(String repairAddress) {
            this.repairAddress = repairAddress;
        }

        public String getRepairDescribe() {
            return repairDescribe;
        }

        public void setRepairDescribe(String repairDescribe) {
            this.repairDescribe = repairDescribe;
        }

        public int getIsEffective() {
            return isEffective;
        }

        public void setIsEffective(int isEffective) {
            this.isEffective = isEffective;
        }

        public int getRepairType() {
            return repairType;
        }

        public void setRepairType(int repairType) {
            this.repairType = repairType;
        }

        public Object getVoice() {
            return voice;
        }

        public void setVoice(Object voice) {
            this.voice = voice;
        }

        public Object getVoiceTime() {
            return voiceTime;
        }

        public void setVoiceTime(Object voiceTime) {
            this.voiceTime = voiceTime;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getWorkOrderType() {
            return workOrderType;
        }

        public void setWorkOrderType(int workOrderType) {
            this.workOrderType = workOrderType;
        }

        public int getIsRead() {
            return isRead;
        }

        public void setIsRead(int isRead) {
            this.isRead = isRead;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getCompletionTime() {
            return completionTime;
        }

        public void setCompletionTime(Object completionTime) {
            this.completionTime = completionTime;
        }

        public Object getEvaluationContent() {
            return evaluationContent;
        }

        public void setEvaluationContent(Object evaluationContent) {
            this.evaluationContent = evaluationContent;
        }

        public Object getOverallEvaluationLevel() {
            return overallEvaluationLevel;
        }

        public void setOverallEvaluationLevel(Object overallEvaluationLevel) {
            this.overallEvaluationLevel = overallEvaluationLevel;
        }

        public int getIsRecord() {
            return isRecord;
        }

        public void setIsRecord(int isRecord) {
            this.isRecord = isRecord;
        }

        public List<UserListBean> getUserList() {
            return userList;
        }

        public void setUserList(List<UserListBean> userList) {
            this.userList = userList;
        }

        public static class UserListBean {
            /**
             * id : 1
             * username : admin
             * password : c0e024d9200b5705bc4804722636378a
             * telephone : 15322222222
             * loginName : null
             * name : 地址
             * source : null
             * pushDate : null
             * credit : null
             * type : 5
             * status : null
             * isDel : 0
             * pwdFlag : 0
             * userType : 6
             * detailedAddress : null
             * updataStatus : 0
             * employeeNo : null
             * headImage : null
             * sendMessageStatus : null
             */

            private int id;
            private String username;
            private String password;
            private String telephone;
            private Object loginName;
            private String name;
            private Object source;
            private Object pushDate;
            private Object credit;
            private int type;
            private Object status;
            private int isDel;
            private int pwdFlag;
            private int userType;
            private Object detailedAddress;
            private int updataStatus;
            private Object employeeNo;
            private Object headImage;
            private Object sendMessageStatus;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public Object getLoginName() {
                return loginName;
            }

            public void setLoginName(Object loginName) {
                this.loginName = loginName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getSource() {
                return source;
            }

            public void setSource(Object source) {
                this.source = source;
            }

            public Object getPushDate() {
                return pushDate;
            }

            public void setPushDate(Object pushDate) {
                this.pushDate = pushDate;
            }

            public Object getCredit() {
                return credit;
            }

            public void setCredit(Object credit) {
                this.credit = credit;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public int getIsDel() {
                return isDel;
            }

            public void setIsDel(int isDel) {
                this.isDel = isDel;
            }

            public int getPwdFlag() {
                return pwdFlag;
            }

            public void setPwdFlag(int pwdFlag) {
                this.pwdFlag = pwdFlag;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public Object getDetailedAddress() {
                return detailedAddress;
            }

            public void setDetailedAddress(Object detailedAddress) {
                this.detailedAddress = detailedAddress;
            }

            public int getUpdataStatus() {
                return updataStatus;
            }

            public void setUpdataStatus(int updataStatus) {
                this.updataStatus = updataStatus;
            }

            public Object getEmployeeNo() {
                return employeeNo;
            }

            public void setEmployeeNo(Object employeeNo) {
                this.employeeNo = employeeNo;
            }

            public Object getHeadImage() {
                return headImage;
            }

            public void setHeadImage(Object headImage) {
                this.headImage = headImage;
            }

            public Object getSendMessageStatus() {
                return sendMessageStatus;
            }

            public void setSendMessageStatus(Object sendMessageStatus) {
                this.sendMessageStatus = sendMessageStatus;
            }
        }
    }
}
