package com.luck.cloud.function.login;

import java.util.List;
import java.util.Set;

/**
 * Created by liuyin on 2019/5/16 11:01
 * Description:登录返回数据
 */
public class LoginBean {
    /**
     * apiResource : []
     * departmentIds : [3,4,5,36,41,42,43,44,49,54,59,65,23]
     * identityIds : [6]
     * moduleResource : []
     * roleIds : [7]
     * token : customerae399f03e6fb4f3090a079cd78e8551e
     * user : {"email":"","gmtCreate":"2019-03-05 16:34:03","headPic":"","id":21,"jobNumber":"","loginName":"18410259803","name":"李明","nickName":"","notes":"","phone":"18410259803"}
     */

    private String token;
    private UserBean user;
    private String nameDepart;
    private List<Integer> departmentIds;
    private List<Integer> identityIds;
    private List<?> moduleResource;
    private List<Integer> roleIds;
    private Set<String> apiResource;

    public String getNameDepart() {
        return nameDepart;
    }

    public void setNameDepart(String nameDepart) {
        this.nameDepart = nameDepart;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<String> getApiResource() {
        return apiResource;
    }

    public void setApiResource(Set<String> apiResource) {
        this.apiResource = apiResource;
    }

    public List<Integer> getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(List<Integer> departmentIds) {
        this.departmentIds = departmentIds;
    }

    public List<Integer> getIdentityIds() {
        return identityIds;
    }

    public void setIdentityIds(List<Integer> identityIds) {
        this.identityIds = identityIds;
    }

    public List<?> getModuleResource() {
        return moduleResource;
    }

    public void setModuleResource(List<?> moduleResource) {
        this.moduleResource = moduleResource;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public static class UserBean {

        /**
         * email :
         * gmtCreate : 2019-03-05 16:34:03
         * headPic :
         * id : 21
         * jobNumber :
         * loginName : 18410259803
         * name : 李明
         * nickName :
         * notes :
         * phone : 18410259803
         */

        private String email;
        private String gmtCreate;
        private String headPic;
        private int id;
        private String jobNumber;
        private String loginName;
        private String name;
        private String nickName;
        private String notes;
        private String phone;
        private String userPosition;

        public String getUserPosition() {
            return userPosition;
        }

        public void setUserPosition(String userPosition) {
            this.userPosition = userPosition;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJobNumber() {
            return jobNumber;
        }

        public void setJobNumber(String jobNumber) {
            this.jobNumber = jobNumber;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
