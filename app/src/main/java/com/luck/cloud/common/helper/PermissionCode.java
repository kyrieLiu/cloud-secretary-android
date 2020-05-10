package com.luck.cloud.common.helper;

/**
 * Created by liuyin on 2019/6/20 19:17
 * Describe: 存放所有权限的code值
 */
public enum PermissionCode {

    //首页banner
    HOME_BANNER(".118.1", true),
    //首页资产管理
    HOME_ASSET_MANAGE(".118.2", true),
    //首页统计分析
    HOME_STATIC(".118.3", true),
    //首页资产运营
    HOME_ASSET_OPERATION(".118.4", true),
    //首页待办事项
    HOME_WAIT_DONE(".118.5", false),
    //房产台账
    MANAGE_HOUSE_STANDING_BOOK(".119.1", false),
    //设备台账
    MANAGE_HOUSE_EQUIPMENT_BOOK(".119.2", false),
    //房产出租率统计
    STATIC_HOUSE_OCCUPANCY_RATE(".120.1", false),
    //资产收支统计
    STATIC_INCOME_EXPENSES(".120.5", false),
    //客户行业分析统计
    STATIC_INDUSTRY_ANALYSIS(".120.2", false),
    //资产修缮统计 
    STATIC_ASSETS_REPAIR(".120.3", false),
    //督查督办统计
    STATIC_SUPERVISE_EXAMINE(".120.4", false),
    //修缮计划单
    REPAIR_PLAN(".121.1", false),
    //修缮终审
    REPAIR_FINAL_JUDGMENT(".121.2", false),
    //督查督办单
    SUPERVISE_HANDLE(".121.3", false),
    //督察督办验收
    SUPERVISE_CHECK_ACCEPT(".121.4", false),
    //物业服务标准
    PROPERTY_SERVISE_STANDARDS(".121.5", false),
    //查看供应商
    SEE_SUPPLIER(".121.6", false),
    //修缮审批 zy
    REPAIR_Approval(".121.7", false),
    //资产总览
    ASSET_PANDECT(".124", false),
    //资产分布数据图
    ASSET_DISTRIBUTED_DATA(".124.1", false),
    //我的待办事项
    MINE_WAIT_DONE(".122.1", false),
    //我的文件共享
    MINE_FILE_SHARE(".122.2", false),
    //设置个人资料
    MINE_SETTING_DATA(".122.3", true),
    //当前版本
    MINE_CURRENT_VERSION(".122.4", true),
    //退出登录
    MINE_LOGOUT(".122.5", true);

    /**
     * 权限Code
     */
    private String code;
    /**
     * 是否所有角色都拥有这个权限
     */
    private boolean isAllRolePermission;

    PermissionCode(String code, boolean isAllRolePermission) {
        this.code = code;
        this.isAllRolePermission = isAllRolePermission;
    }

    public String getCode() {
        return code;
    }

    public boolean isAllRolePermission() {
        return isAllRolePermission;
    }


    /**
     * 根据code获取对应的枚举
     *
     * @param code
     * @return
     */
    public static PermissionCode getById(String code) {
        for (PermissionCode transactType : values()) {
            if (transactType.getCode().equals(code)) {
                //获取指定的枚举
                return transactType;
            }
        }
        return null;
    }
}
