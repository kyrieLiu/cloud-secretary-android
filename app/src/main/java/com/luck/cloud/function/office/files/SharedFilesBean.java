package com.luck.cloud.function.office.files;

import java.util.List;

/**
 * created by YQ on 2019-05-24 16:13
 */
public class SharedFilesBean {

    /**
     * items : [{"editor":"CS终审","fileName":"1111.jpg","fileAddr":"http://pocketbook.document.jingcaiwang.cn/group1/M00/00/35/rBMBLFzQ_e-ALqn5AACu-JzvUxQ634.jpg","creator":"CS终审","gmtModified":"2019-05-07 11:39:38","id":10,"gmtCreate":"2019-05-07 11:39:38","fileSharingTypeTitle":"集团会议纪要"}]
     * total : 1
     */

    private int total;
    private List<ItemsBean> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * editor : CS终审
         * fileName : 1111.jpg
         * fileAddr : http://pocketbook.document.jingcaiwang.cn/group1/M00/00/35/rBMBLFzQ_e-ALqn5AACu-JzvUxQ634.jpg
         * creator : CS终审
         * gmtModified : 2019-05-07 11:39:38
         * id : 10
         * gmtCreate : 2019-05-07 11:39:38
         * fileSharingTypeTitle : 集团会议纪要
         */

        private String editor;
        private String fileName;
        private String fileAddr;
        private String creator;
        private String gmtModified;
        private int id;
        private String gmtCreate;
        private String fileSharingTypeTitle;

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileAddr() {
            return fileAddr;
        }

        public void setFileAddr(String fileAddr) {
            this.fileAddr = fileAddr;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(String gmtModified) {
            this.gmtModified = gmtModified;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getFileSharingTypeTitle() {
            return fileSharingTypeTitle;
        }

        public void setFileSharingTypeTitle(String fileSharingTypeTitle) {
            this.fileSharingTypeTitle = fileSharingTypeTitle;
        }
    }
}
