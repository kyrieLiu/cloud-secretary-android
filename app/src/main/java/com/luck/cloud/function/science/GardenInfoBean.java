package com.luck.cloud.function.science;

import java.util.List;

/**
 * Created by liuyin on 2019/4/12 11:34
 * Description:园区列表实体
 */
public class GardenInfoBean {

    /**
     * items : [{"editor":"李明","creator":"admin,系统管理员","gmtModified":"2019-04-07 13:42:58","notes":"","nameRbacDepartment":"1111","operatingArea":"@@","name":"博大万源公寓","id":1,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"李明","creator":"admin,系统管理员","gmtModified":"2019-04-10 10:23:10","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"27932.97","name":"陆道培","id":2,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"李明","creator":"admin,系统管理员","gmtModified":"2019-04-04 16:30:26","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"110123.03","name":"永康公寓","id":3,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"22463.74","name":"万源商务中心","id":4,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"42168.92","name":"隆盛大厦","id":5,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"31224.61","name":"施耐德一期","id":6,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"53194.55","name":"博大大厦","id":7,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"25088.02","name":"同济科技园","id":8,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":null,"name":"生物医药园","id":9,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"管理员","creator":"admin,系统管理员","gmtModified":"2019-04-28 19:38:12","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"100001.24","name":"经海产业园","id":10,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"53452.51","name":"亦城科技中心","id":11,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"138589.2","name":"康宁二期","id":12,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"管理员","creator":"admin,系统管理员","gmtModified":"2019-04-28 19:38:03","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"142630.94","name":"亦城财富中心","id":13,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"37866.0","name":"创新大厦","id":14,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":null,"name":"科技保税库","id":15,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":null,"name":"保华国际学校","id":16,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":null,"name":"万源饭店","id":17,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":null,"name":"白楼","id":18,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":null,"name":"红楼","id":19,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"60566.88","name":"亦城国际","id":20,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"2570.63","name":"BDA（18号楼）","id":21,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":null,"name":"BDA（61幢）","id":22,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":null,"name":"BDA（69幢地库）","id":23,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":null,"name":"博大俱乐部","id":24,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"李明","creator":"李明","gmtModified":"2019-04-11 09:59:47","notes":"","nameRbacDepartment":"经开软件","operatingArea":"中国好","name":"万源路财富中心","id":67,"sort":1554876372311,"gmtCreate":"2019-04-10 14:06:12"},{"editor":"李明","creator":"李明","gmtModified":"2019-04-17 20:30:06","notes":"","nameRbacDepartment":"经开软件","operatingArea":"北京","name":"亦庄人城","id":71,"sort":1554898733060,"gmtCreate":"2019-04-10 20:18:53"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"142630.94","name":"亦城财富中心","id":506,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"admin,系统管理员","creator":"admin,系统管理员","gmtModified":"2019-01-12 14:55:49","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"142630.94","name":"技术部测试项目","id":827,"sort":null,"gmtCreate":"2019-01-12 14:55:49"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:06:07","notes":"","nameRbacDepartment":"","operatingArea":"37866.0","name":"创新大厦1","id":828,"sort":1555589167191,"gmtCreate":"2019-04-18 20:06:07"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:09:50","notes":"","nameRbacDepartment":"","operatingArea":"37866.0","name":"创新大厦1","id":829,"sort":1555589390999,"gmtCreate":"2019-04-18 20:09:50"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:09:56","notes":"","nameRbacDepartment":"","operatingArea":"25088.02","name":"BDA（61幢）1","id":830,"sort":1555589396264,"gmtCreate":"2019-04-18 20:09:56"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:10:02","notes":"","nameRbacDepartment":"","operatingArea":"2570.63","name":"BDA（18号楼）1","id":831,"sort":1555589402109,"gmtCreate":"2019-04-18 20:10:02"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:10:09","notes":"","nameRbacDepartment":"","operatingArea":"60566.88","name":"亦城国际中心1","id":832,"sort":1555589409368,"gmtCreate":"2019-04-18 20:10:09"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:10:16","notes":"","nameRbacDepartment":"","operatingArea":"@@","name":"博大万源公寓1","id":833,"sort":1555589416977,"gmtCreate":"2019-04-18 20:10:16"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:10:24","notes":"","nameRbacDepartment":"","operatingArea":"142630.94","name":"亦城财富中心1","id":834,"sort":1555589424750,"gmtCreate":"2019-04-18 20:10:24"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:10:31","notes":"","nameRbacDepartment":"","operatingArea":"138589.2","name":"康宁二期1","id":835,"sort":1555589431717,"gmtCreate":"2019-04-18 20:10:31"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:10:38","notes":"","nameRbacDepartment":"","operatingArea":"53452.51","name":"亦城科技中心1","id":836,"sort":1555589438696,"gmtCreate":"2019-04-18 20:10:38"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:10:40","notes":"","nameRbacDepartment":"","operatingArea":"100001.24","name":"经海产业园1","id":837,"sort":1555589440802,"gmtCreate":"2019-04-18 20:10:40"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:10:41","notes":"","nameRbacDepartment":"","operatingArea":"25088.02","name":"生物医药园1","id":838,"sort":1555589441050,"gmtCreate":"2019-04-18 20:10:41"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:10:41","notes":"","nameRbacDepartment":"","operatingArea":"25088.02","name":"同济科技园1","id":839,"sort":1555589441382,"gmtCreate":"2019-04-18 20:10:41"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:10:41","notes":"","nameRbacDepartment":"","operatingArea":"53194.55","name":"博大大厦1","id":840,"sort":1555589441701,"gmtCreate":"2019-04-18 20:10:41"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:10:41","notes":"","nameRbacDepartment":"","operatingArea":"31224.61","name":"施耐德一期1","id":841,"sort":1555589441947,"gmtCreate":"2019-04-18 20:10:41"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:10:42","notes":"","nameRbacDepartment":"","operatingArea":"42168.92","name":"隆盛大厦1","id":842,"sort":1555589442184,"gmtCreate":"2019-04-18 20:10:42"},{"editor":"张云浩","creator":"张云浩","gmtModified":"2019-04-18 20:10:42","notes":"","nameRbacDepartment":"","operatingArea":"22463.74","name":"万源商务中心1","id":843,"sort":1555589442421,"gmtCreate":"2019-04-18 20:10:42"},{"editor":"李明","creator":"张云浩","gmtModified":"2019-05-10 12:31:10","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"110123.03","name":"永康公寓1","id":844,"sort":1555589442759,"gmtCreate":"2019-04-18 20:10:42"},{"editor":"李明","creator":"张云浩","gmtModified":"2019-04-20 15:47:27","notes":"","nameRbacDepartment":"润兴物业","operatingArea":"27932.97","name":"陆道培1","id":845,"sort":1555589443091,"gmtCreate":"2019-04-18 20:10:43"},{"editor":"李明","creator":"刘尚-集团","gmtModified":"2019-04-30 14:58:52","notes":"","nameRbacDepartment":"经开经济","operatingArea":"300","name":"如家连锁","id":848,"sort":1556415309260,"gmtCreate":"2019-04-28 09:35:09"},{"editor":"李明","creator":"李明","gmtModified":"2019-05-15 17:23:24","notes":"","nameRbacDepartment":"亦庄置业","operatingArea":"27932.97","name":"陆道培1阿斯顿发大水阿斯蒂芬","id":880,"sort":1557912204322,"gmtCreate":"2019-05-15 17:23:24"}]
     * total : 48
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
         * editor : 李明
         * creator : admin,系统管理员
         * gmtModified : 2019-04-07 13:42:58
         * notes :
         * nameRbacDepartment : 1111
         * operatingArea : @@
         * name : 博大万源公寓
         * id : 1
         * sort : null
         * gmtCreate : 2019-01-12 14:55:49
         */

        private String editor;
        private String creator;
        private String gmtModified;
        private String notes;
        private String nameRbacDepartment;
        private String operatingArea;
        private String name;
        private int id;
        private Object sort;
        private String gmtCreate;
        private int isChecked;

        public int getIsChecked() {
            return isChecked;
        }

        public void setIsChecked(int isChecked) {
            this.isChecked = isChecked;
        }

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
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

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getNameRbacDepartment() {
            return nameRbacDepartment;
        }

        public void setNameRbacDepartment(String nameRbacDepartment) {
            this.nameRbacDepartment = nameRbacDepartment;
        }

        public String getOperatingArea() {
            return operatingArea;
        }

        public void setOperatingArea(String operatingArea) {
            this.operatingArea = operatingArea;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }
    }
}
