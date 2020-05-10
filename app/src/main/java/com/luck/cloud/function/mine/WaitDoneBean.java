package com.luck.cloud.function.mine;

import java.util.List;

/**
 * Created by liuyin on 2019/2/27 15:19
 * Description: 待办事项实体
 */
public class WaitDoneBean {


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

    public static class ItemsBean{

       /**
        * gmtCreate : 2019-04-12 11:35
        * id : 320
        * taskName : 测试附件问题
        * taskStatus : 10
        * taskStatusName : 未处理
        * taskType : 1010
        * typeName : 督查
        * typeStatus : 待反馈
        */

       private String gmtCreate;
       private int id;
       private int idBusiness;
       private String taskName;
       private int taskStatus;
       private String taskStatusName;
       private int taskType;
       private String typeName;
       private String typeStatus;

        public int getIdBusiness() {
            return idBusiness;
        }

        public void setIdBusiness(int idBusiness) {
            this.idBusiness = idBusiness;
        }

        public String getGmtCreate() {
           return gmtCreate;
       }

       public void setGmtCreate(String gmtCreate) {
           this.gmtCreate = gmtCreate;
       }

       public int getId() {
           return id;
       }

       public void setId(int id) {
           this.id = id;
       }

       public String getTaskName() {
           return taskName;
       }

       public void setTaskName(String taskName) {
           this.taskName = taskName;
       }

       public int getTaskStatus() {
           return taskStatus;
       }

       public void setTaskStatus(int taskStatus) {
           this.taskStatus = taskStatus;
       }

       public String getTaskStatusName() {
           return taskStatusName;
       }

       public void setTaskStatusName(String taskStatusName) {
           this.taskStatusName = taskStatusName;
       }

       public int getTaskType() {
           return taskType;
       }

       public void setTaskType(int taskType) {
           this.taskType = taskType;
       }

       public String getTypeName() {
           return typeName;
       }

       public void setTypeName(String typeName) {
           this.typeName = typeName;
       }

       public String getTypeStatus() {
           return typeStatus;
       }

       public void setTypeStatus(String typeStatus) {
           this.typeStatus = typeStatus;
       }
   }
}
