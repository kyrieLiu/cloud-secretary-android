package com.luck.cloud.function.mine.work;

import java.util.List;

/**
 * Created by liuyin on 2018/7/25 14:17
 * 维修指派日历Model
 */
public class WorkerRepairCalendarBean {

    /**
     * id : 2
     * uniqueNumber : null
     * repairTime : 2018-10-20T06:32:59.000+0000
     * repairSource : 1
     * belongProject : 1
     * belongProjectStr : 博客雅苑
     * repairProject : 1
     * repairProjectStr : 博客雅苑
     * createBy : 1
     * repairBy : 1
     * repairByStr : 地址
     * repairByPhone : 15622222233
     * repairAddress : 报修地址博客雅苑2号楼2层2011
     * repairDescribe : 我家房屋漏水
     * isEffective : 0
     * repairType : 1
     * voice : null
     * voiceTime : null
     * img : [我是照2骗]
     * workOrderType : 1
     * isRead : 1
     * status : 4
     * completionTime : null
     * evaluationContent : null
     * overallEvaluationLevel : null
     * isRecord : 0
     * appointmentTime : 2018-10-20T06:12:49.000+0000
     * userList : [{"id":7,"username":"物业工程师","password":"40881a303c959d810320f692792c5c83","telephone":"15211111111","loginName":null,"name":"布里茨","source":null,"pushDate":null,"credit":null,"type":2,"status":null,"isDel":0,"pwdFlag":0,"userType":6,"detailedAddress":null,"updataStatus":null,"employeeNo":null,"headImage":null,"sendMessageStatus":null},{"id":8,"username":"新元项目工程师","password":"b9a20a681189643f06f68d0aaa7e600c","telephone":"15211111111","loginName":null,"name":"李青","source":null,"pushDate":null,"credit":null,"type":4,"status":null,"isDel":0,"pwdFlag":0,"userType":4,"detailedAddress":null,"updataStatus":null,"employeeNo":null,"headImage":null,"sendMessageStatus":null},{"id":9,"username":"新元工程师","password":"43c0b552f9ea97eeeaa7ed1a874437de","telephone":"15211111111","loginName":null,"name":"艾希","source":null,"pushDate":null,"credit":null,"type":4,"status":null,"isDel":0,"pwdFlag":0,"userType":5,"detailedAddress":null,"updataStatus":null,"employeeNo":null,"headImage":null,"sendMessageStatus":null},{"id":10,"username":"三方负责人","password":"2bee8d5f0f592cf10e3292629301a1cc","telephone":"15211111111","loginName":null,"name":"卢锡安","source":null,"pushDate":null,"credit":null,"type":3,"status":null,"isDel":0,"pwdFlag":0,"userType":1,"detailedAddress":null,"updataStatus":null,"employeeNo":null,"headImage":null,"sendMessageStatus":null},{"id":11,"username":"三方工程师","password":"c9b0fc9fc53ab9dc7fc21bd8e0d93d9d","telephone":"15211111111","loginName":null,"name":"易","source":null,"pushDate":null,"credit":null,"type":3,"status":null,"isDel":0,"pwdFlag":0,"userType":2,"detailedAddress":null,"updataStatus":null,"employeeNo":null,"headImage":null,"sendMessageStatus":null}]
     */

    private int id;
    private Object uniqueNumber;
    private String repairTime;
    private int repairSource;
    private int belongProject;
    private String belongProjectStr;
    private int repairProject;
    private String repairProjectStr;
    private int createBy;
    private int repairBy;
    private String repairByStr;
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
    private boolean isSign;
    private String day;
    private boolean select;
    private String date;
    private List<UserListBean> userList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSign() {
        return isSign;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public Object getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(Object uniqueNumber) {
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

    public String getBelongProjectStr() {
        return belongProjectStr;
    }

    public void setBelongProjectStr(String belongProjectStr) {
        this.belongProjectStr = belongProjectStr;
    }

    public int getRepairProject() {
        return repairProject;
    }

    public void setRepairProject(int repairProject) {
        this.repairProject = repairProject;
    }

    public String getRepairProjectStr() {
        return repairProjectStr;
    }

    public void setRepairProjectStr(String repairProjectStr) {
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

    public String getRepairByStr() {
        return repairByStr;
    }

    public void setRepairByStr(String repairByStr) {
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

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class UserListBean {
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
    }
}
