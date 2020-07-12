package com.luck.cloud.function.login;

import java.util.List;

/**
 * Created by liuyin on 2019/5/16 11:01
 * Description:登录返回数据
 */
public class LoginBean {

    /**
     * msg : 登录成功
     * code : SUCCESS
     * details : 1
     * user : {"accountNonExpired":true,"accountNonLocked":true,"authorities":[{"authority":"information:info:listExamine"}],"buttons":[],"credentialsNonExpired":true,"enabled":true,"menus":[{"opSequence":0,"opSort":2,"opType":1,"operationId":15,"operationName":"首页","parentId":0,"url":"dashboard"},{"opSequence":1,"opSort":1,"opType":1,"operationId":1,"operationName":"资讯管理","parentId":0,"url":"information"},{"opSequence":2,"opSort":1,"opType":1,"operationId":3,"operationName":"企业管理","parentId":0,"url":"parkmanage"},{"opSequence":6,"opSort":1,"opType":1,"operationId":13,"operationName":"系统管理","parentId":0,"url":"msm-manage"},{"opSequence":7,"opSort":1,"opType":1,"operationId":42,"operationName":"资源分配","parentId":0,"url":"permission"},{"opSequence":1,"opSort":2,"opType":1,"operationId":2,"operationName":"资讯发布","parentId":1,"url":"information-manage"},{"opPower":"information:info:listExamine","opSequence":2,"opSort":2,"opType":1,"operationId":41,"operationName":"发布审核","parentId":1,"url":"information-exverify"},{"opSequence":1,"opSort":2,"opType":1,"operationId":4,"operationName":"认证审核","parentId":3,"url":"enterprise-cert"},{"opSequence":2,"opSort":2,"opType":1,"operationId":5,"operationName":"入驻管理","parentId":3,"url":"enterprise-settled"},{"opSequence":3,"opSort":2,"opType":1,"operationId":65,"operationName":"账户管理","parentId":3,"url":"enterprise-account"},{"opSequence":1,"opSort":2,"opType":1,"operationId":47,"operationName":"用户管理","parentId":13,"url":"user-manage"},{"opSequence":1,"opSort":2,"opType":1,"operationId":75,"operationName":"领域管理","parentId":13,"url":"question-type"},{"opSequence":2,"opSort":2,"opType":1,"operationId":48,"operationName":"部门管理","parentId":13,"url":"dept-manage"},{"opSequence":3,"opSort":2,"opType":1,"operationId":49,"operationName":"角色管理","parentId":13,"url":"role-managet"},{"opSequence":4,"opSort":2,"opType":1,"operationId":14,"operationName":"园区信息管理","parentId":13,"url":"park-information"}],"parkId":1,"peopleId":12,"peopleLoginname":"liuyinpark","peopleName":"刘隐","peopleTag":"0","peopleType":"1","photoLogo":"http://121.36.144.200/group1/M00/00/01/wKgAnF6c9rmAO5hTAAAgDOh_BRw290.png","powers":["information:info:listExamine"],"username":"liuyinpark"}
     * token : xEWhKo9GeWXU9yp4YPue/3IjbWToZf0Ejucnq/Xgmwxql40IPPORreI8fJ9+9er+Jugp8SMklLiJo0Nkl8k/v0sPM8PfeJ553kT/2ZEne/0=
     */

    private String msg;
    private String message;
    private String code;
    private String details;
    private UserBean user;
    private String token;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserBean {
        /**
         * accountNonExpired : true
         * accountNonLocked : true
         * authorities : [{"authority":"information:info:listExamine"}]
         * buttons : []
         * credentialsNonExpired : true
         * enabled : true
         * menus : [{"opSequence":0,"opSort":2,"opType":1,"operationId":15,"operationName":"首页","parentId":0,"url":"dashboard"},{"opSequence":1,"opSort":1,"opType":1,"operationId":1,"operationName":"资讯管理","parentId":0,"url":"information"},{"opSequence":2,"opSort":1,"opType":1,"operationId":3,"operationName":"企业管理","parentId":0,"url":"parkmanage"},{"opSequence":6,"opSort":1,"opType":1,"operationId":13,"operationName":"系统管理","parentId":0,"url":"msm-manage"},{"opSequence":7,"opSort":1,"opType":1,"operationId":42,"operationName":"资源分配","parentId":0,"url":"permission"},{"opSequence":1,"opSort":2,"opType":1,"operationId":2,"operationName":"资讯发布","parentId":1,"url":"information-manage"},{"opPower":"information:info:listExamine","opSequence":2,"opSort":2,"opType":1,"operationId":41,"operationName":"发布审核","parentId":1,"url":"information-exverify"},{"opSequence":1,"opSort":2,"opType":1,"operationId":4,"operationName":"认证审核","parentId":3,"url":"enterprise-cert"},{"opSequence":2,"opSort":2,"opType":1,"operationId":5,"operationName":"入驻管理","parentId":3,"url":"enterprise-settled"},{"opSequence":3,"opSort":2,"opType":1,"operationId":65,"operationName":"账户管理","parentId":3,"url":"enterprise-account"},{"opSequence":1,"opSort":2,"opType":1,"operationId":47,"operationName":"用户管理","parentId":13,"url":"user-manage"},{"opSequence":1,"opSort":2,"opType":1,"operationId":75,"operationName":"领域管理","parentId":13,"url":"question-type"},{"opSequence":2,"opSort":2,"opType":1,"operationId":48,"operationName":"部门管理","parentId":13,"url":"dept-manage"},{"opSequence":3,"opSort":2,"opType":1,"operationId":49,"operationName":"角色管理","parentId":13,"url":"role-managet"},{"opSequence":4,"opSort":2,"opType":1,"operationId":14,"operationName":"园区信息管理","parentId":13,"url":"park-information"}]
         * parkId : 1
         * peopleId : 12
         * peopleLoginname : liuyinpark
         * peopleName : 刘隐
         * peopleTag : 0
         * peopleType : 1
         * photoLogo : http://121.36.144.200/group1/M00/00/01/wKgAnF6c9rmAO5hTAAAgDOh_BRw290.png
         * powers : ["information:info:listExamine"]
         * username : liuyinpark
         */

        private boolean accountNonExpired;
        private boolean accountNonLocked;
        private boolean credentialsNonExpired;
        private boolean enabled;
        private int parkId;
        private int peopleId;
        private String peopleLoginname;
        private String peopleName;
        private String peopleTag;
        private String peopleType;
        private String photoLogo;
        private String username;
        private List<AuthoritiesBean> authorities;
        private List<?> buttons;
        private List<MenusBean> menus;
        private List<String> powers;

        public boolean isAccountNonExpired() {
            return accountNonExpired;
        }

        public void setAccountNonExpired(boolean accountNonExpired) {
            this.accountNonExpired = accountNonExpired;
        }

        public boolean isAccountNonLocked() {
            return accountNonLocked;
        }

        public void setAccountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
        }

        public boolean isCredentialsNonExpired() {
            return credentialsNonExpired;
        }

        public void setCredentialsNonExpired(boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getParkId() {
            return parkId;
        }

        public void setParkId(int parkId) {
            this.parkId = parkId;
        }

        public int getPeopleId() {
            return peopleId;
        }

        public void setPeopleId(int peopleId) {
            this.peopleId = peopleId;
        }

        public String getPeopleLoginname() {
            return peopleLoginname;
        }

        public void setPeopleLoginname(String peopleLoginname) {
            this.peopleLoginname = peopleLoginname;
        }

        public String getPeopleName() {
            return peopleName;
        }

        public void setPeopleName(String peopleName) {
            this.peopleName = peopleName;
        }

        public String getPeopleTag() {
            return peopleTag;
        }

        public void setPeopleTag(String peopleTag) {
            this.peopleTag = peopleTag;
        }

        public String getPeopleType() {
            return peopleType;
        }

        public void setPeopleType(String peopleType) {
            this.peopleType = peopleType;
        }

        public String getPhotoLogo() {
            return photoLogo;
        }

        public void setPhotoLogo(String photoLogo) {
            this.photoLogo = photoLogo;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<AuthoritiesBean> getAuthorities() {
            return authorities;
        }

        public void setAuthorities(List<AuthoritiesBean> authorities) {
            this.authorities = authorities;
        }

        public List<?> getButtons() {
            return buttons;
        }

        public void setButtons(List<?> buttons) {
            this.buttons = buttons;
        }

        public List<MenusBean> getMenus() {
            return menus;
        }

        public void setMenus(List<MenusBean> menus) {
            this.menus = menus;
        }

        public List<String> getPowers() {
            return powers;
        }

        public void setPowers(List<String> powers) {
            this.powers = powers;
        }

        public static class AuthoritiesBean {
            /**
             * authority : information:info:listExamine
             */

            private String authority;

            public String getAuthority() {
                return authority;
            }

            public void setAuthority(String authority) {
                this.authority = authority;
            }
        }

        public static class MenusBean {
            /**
             * opSequence : 0
             * opSort : 2
             * opType : 1
             * operationId : 15
             * operationName : 首页
             * parentId : 0
             * url : dashboard
             * opPower : information:info:listExamine
             */

            private int opSequence;
            private int opSort;
            private int opType;
            private int operationId;
            private String operationName;
            private int parentId;
            private String url;
            private String opPower;

            public int getOpSequence() {
                return opSequence;
            }

            public void setOpSequence(int opSequence) {
                this.opSequence = opSequence;
            }

            public int getOpSort() {
                return opSort;
            }

            public void setOpSort(int opSort) {
                this.opSort = opSort;
            }

            public int getOpType() {
                return opType;
            }

            public void setOpType(int opType) {
                this.opType = opType;
            }

            public int getOperationId() {
                return operationId;
            }

            public void setOperationId(int operationId) {
                this.operationId = operationId;
            }

            public String getOperationName() {
                return operationName;
            }

            public void setOperationName(String operationName) {
                this.operationName = operationName;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getOpPower() {
                return opPower;
            }

            public void setOpPower(String opPower) {
                this.opPower = opPower;
            }
        }
    }
}
