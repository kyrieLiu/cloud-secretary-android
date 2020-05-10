package com.luck.cloud.common.entity;

import java.util.List;

/**
 * Created by liuyin on 2019/5/15 14:21
 * Description: 接口分页固定格式
 */
public class RequestBean {

    private CondBean cond;
    private PageableBean pageable;
    private EntityBean entity;

    public CondBean getCond() {
        return cond;
    }

    public void setCond(CondBean cond) {
        this.cond = cond;
    }

    public PageableBean getPageable() {
        return pageable;
    }

    public void setPageable(PageableBean pageable) {
        this.pageable = pageable;
    }

    public EntityBean getEntity() {
        return entity;
    }

    public void setEntity(EntityBean entity) {
        this.entity = entity;
    }

    public static class CondBean {
        private List<?> rules;
        private List<?> groups;
        private Object groupOp;

        public Object getGroupOp() {
            return groupOp;
        }

        public void setGroupOp(Object groupOp) {
            this.groupOp = groupOp;
        }

        public List getRules() {
            return rules;
        }

        public void setRules(List rules) {
            this.rules = rules;
        }

        public List getGroups() {
            return groups;
        }

        public void setGroups(List groups) {
            this.groups = groups;
        }
    }

    public static class PageableBean {
        private int size = 10;
        private int current = 1;


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
    }

    public static class EntityBean {
        /**
         * title : 阿斯
         */

        private String title;
        private String acParkId;
        private String customQuery;

        public String getCustomQuery() {
            return customQuery;
        }

        public void setCustomQuery(String customQuery) {
            this.customQuery = customQuery;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAcParkId() {
            return acParkId;
        }

        public void setAcParkId(String acParkId) {
            this.acParkId = acParkId;
        }
    }
}
