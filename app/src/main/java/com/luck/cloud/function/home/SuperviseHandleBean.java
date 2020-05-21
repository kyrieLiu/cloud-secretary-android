package com.luck.cloud.function.home;

import java.util.List;

/**
 * Created by liuyin on 2019/4/16 9:42
 * Description:督查督办信息
 */
public class SuperviseHandleBean {
    private List<ItemsBean> items;
    private int total;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class ItemsBean {

        /**
         * chargeInfo : 孙哲
         * id : 51
         * isRead : 0
         * missionName : 督查督办单
         * nameAcPark : 博大万源公寓
         * nameRbacDepartment : 亦庄置业
         * supervisoryCode : DC2019050800003
         * supervisoryStatus : 40
         * supervisoryStatusTitle : 已验收
         */

        private String chargeInfo;
        private int id;
        private int isRead;
        private String missionName;
        private String nameAcPark;
        private String nameRbacDepartment;
        private String supervisoryCode;
        private int supervisoryStatus;
        private String supervisoryStatusTitle;

        public String getChargeInfo() {
            return chargeInfo;
        }

        public void setChargeInfo(String chargeInfo) {
            this.chargeInfo = chargeInfo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsRead() {
            return isRead;
        }

        public void setIsRead(int isRead) {
            this.isRead = isRead;
        }

        public String getMissionName() {
            return missionName;
        }

        public void setMissionName(String missionName) {
            this.missionName = missionName;
        }

        public String getNameAcPark() {
            return nameAcPark;
        }

        public void setNameAcPark(String nameAcPark) {
            this.nameAcPark = nameAcPark;
        }

        public String getNameRbacDepartment() {
            return nameRbacDepartment;
        }

        public void setNameRbacDepartment(String nameRbacDepartment) {
            this.nameRbacDepartment = nameRbacDepartment;
        }

        public String getSupervisoryCode() {
            return supervisoryCode;
        }

        public void setSupervisoryCode(String supervisoryCode) {
            this.supervisoryCode = supervisoryCode;
        }

        public int getSupervisoryStatus() {
            return supervisoryStatus;
        }

        public void setSupervisoryStatus(int supervisoryStatus) {
            this.supervisoryStatus = supervisoryStatus;
        }

        public String getSupervisoryStatusTitle() {
            return supervisoryStatusTitle;
        }

        public void setSupervisoryStatusTitle(String supervisoryStatusTitle) {
            this.supervisoryStatusTitle = supervisoryStatusTitle;
        }
    }
}
