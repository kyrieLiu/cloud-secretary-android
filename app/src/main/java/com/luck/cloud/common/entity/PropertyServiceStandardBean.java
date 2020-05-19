package com.luck.cloud.common.entity;

import java.util.List;

/**
 * Created by liuyin on 2019/4/17 9:42
 * Description:物业服务标准实体
 */
public class PropertyServiceStandardBean {

    /**
     * items : [{"acParkName":"永康公寓","docUrl":"D:/test_dir/develTemp/ueditor_251.html","title":"阿斯蒂芬","gmtCreate":"2019-04-10 09:55:53","acParkId":3},{"acParkName":"万源商务中心","docUrl":"D:/test_dir/develTemp/ueditor_259.html","title":"阿斯蒂芬","gmtCreate":"2019-04-19 14:29:24","acParkId":4},{"acParkName":"陆道培","docUrl":"http://pocketbook.document.jingcaiwang.cn/group1/M00/00/43/rBMBOFzep2qAYJJGAAAAFVWdQng66.html","title":"水电费阿斯蒂芬阿斯蒂芬阿斯蒂芬电放费是大法官是大法官电饭锅发生的","gmtCreate":"2019-05-17 20:22:01","acParkId":2},{"acParkName":"博大万源公寓","docUrl":"http://pocketbook.document.jingcaiwang.cn/group1/M00/00/43/rBMBLFzeqV2AAI2lAAAB7ULVSUI51.html","title":"阿斯蒂芬","gmtCreate":"2019-05-17 20:30:16","acParkId":1},{"acParkName":"博大万源公寓","docUrl":"http://pocketbook.document.jingcaiwang.cn/group1/M00/00/43/rBMBOFzeqV2AAdkMAAAB7ULVSUI57.html","title":"阿斯蒂芬","gmtCreate":"2019-05-17 20:30:21","acParkId":1}]
     * total : 5
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
         * acParkName : 永康公寓
         * docUrl : D:/test_dir/develTemp/ueditor_251.html
         * title : 阿斯蒂芬
         * gmtCreate : 2019-04-10 09:55:53
         * acParkId : 3
         */

        private String acParkName;
        private String docUrl;
        private String title;
        private String gmtCreate;
        private int acParkId;
        private int docId; //文章id

        public int getDocId() {
            return docId;
        }

        public void setDocId(int docId) {
            this.docId = docId;
        }


        public String getAcParkName() {
            return acParkName;
        }

        public void setAcParkName(String acParkName) {
            this.acParkName = acParkName;
        }

        public String getDocUrl() {
            return docUrl;
        }

        public void setDocUrl(String docUrl) {
            this.docUrl = docUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public int getAcParkId() {
            return acParkId;
        }

        public void setAcParkId(int acParkId) {
            this.acParkId = acParkId;
        }
    }
}
