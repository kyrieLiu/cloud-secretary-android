package com.luck.cloud.common.entity;

import java.io.Serializable;
import java.util.List;

public class DictBean {
    private List<InformationTypeBean> information_type;

    public List<InformationTypeBean> getInformation_type() {
        return information_type;
    }

    public void setInformation_type(List<InformationTypeBean> information_type) {
        this.information_type = information_type;
    }

    public static class InformationTypeBean implements Serializable {
        /**
         * dictId : 1121
         * dicttypeId : 31
         * dicttypeCode : information_type
         * parentId : 0
         * dictCode : current_information
         * dictValue : 专题资讯
         * isEnable : 1
         */

        private int dictId;
        private int dicttypeId;
        private String dicttypeCode;
        private int parentId;
        private String dictCode;
        private String dictValue;
        private String isEnable;

        public int getDictId() {
            return dictId;
        }

        public void setDictId(int dictId) {
            this.dictId = dictId;
        }

        public int getDicttypeId() {
            return dicttypeId;
        }

        public void setDicttypeId(int dicttypeId) {
            this.dicttypeId = dicttypeId;
        }

        public String getDicttypeCode() {
            return dicttypeCode;
        }

        public void setDicttypeCode(String dicttypeCode) {
            this.dicttypeCode = dicttypeCode;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getDictCode() {
            return dictCode;
        }

        public void setDictCode(String dictCode) {
            this.dictCode = dictCode;
        }

        public String getDictValue() {
            return dictValue;
        }

        public void setDictValue(String dictValue) {
            this.dictValue = dictValue;
        }

        public String getIsEnable() {
            return isEnable;
        }

        public void setIsEnable(String isEnable) {
            this.isEnable = isEnable;
        }
    }
}
