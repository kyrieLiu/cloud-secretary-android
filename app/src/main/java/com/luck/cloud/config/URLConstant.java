package com.luck.cloud.config;

/**
 * Created by liuyin on 2019/2/28 21:05
 * Describe 接口地址常量
 */
public class URLConstant {

    public static final String BASE_URL = "http://47.114.80.143/api";//生产环境
    public static final String BASE_HTML_URL = "http://ic.jingcaiwang.cn:18080/assetH5/index.html#/";//生产环境 H5页面地址

    public static final boolean DEBUG = true;
    public static final String AS_ASSETSAPI = "/ac";

    //登录
    //public static final String LOGIN = BASE_URL + "/login/login";
    public static final String LOGIN = BASE_URL + "/into/listQuestion";
    //OA用户登录
    public static final String OA_LOGIN = BASE_URL + "/rbac/oaAppLogin";
    //资产承包台账房间树
    public static final String ASSETS_TREE = BASE_URL + AS_ASSETSAPI + "/api/assets/assetsTree";
    //设备台账房间树
    public static final String EQUIPMENT_TREE = BASE_URL + AS_ASSETSAPI + "/api/equipment/equipmentTree";
    //资产承包台账出租率和空置率
    public static final String ASSETS_RENT_RATE = BASE_URL + AS_ASSETSAPI + "/api/assets/assetsRentalRate";
    //设备设施信息详情
    public static final String EQUIPMENT_DETAIL = BASE_URL + AS_ASSETSAPI + "/api/equipment/equipmentDetail";
    //资产详情
    public static final String ASSETS_DETAIL = BASE_URL + AS_ASSETSAPI + "/api/assets/assetsDetail";
    //房屋出租合同列表
    public static final String ASSETS_CONTRACT_LIST = BASE_URL + AS_ASSETSAPI + "/api/assets/assetsContractList";
    //园区列表
    public static final String PARK_LIST = BASE_URL + AS_ASSETSAPI + "/api/parkinfo/listParkInfo";
    //物业服务标准园区选择
    public static final String PROPERTY_PARK_LIST = BASE_URL + AS_ASSETSAPI + "/api/parkinfo/listParkInfoByCompany";
    //首页待办事项列表
    public static final String WAIT_DONE_HOME = BASE_URL + AS_ASSETSAPI + "/api/task/appIndexList";
    //待办任务更多列表
    public static final String WAIT_DONE_MORE = BASE_URL + AS_ASSETSAPI + "/api/task/listByPage";
    //资产修缮列表
    public static final String ASSETS_REPAIR_LIST = BASE_URL + AS_ASSETSAPI + "/api/repairPlan/listRepairPlan";
    //修缮详情
    public static final String REPAIR_DETAIL = BASE_URL + AS_ASSETSAPI + "/api/repairPlan/detailRepairPlan";
    //修缮终审列表
    public static final String REPAIR_FINAL_LIST = BASE_URL + AS_ASSETSAPI + "/api/repairPlanFinal/listRepairPlanFinal";
    //更新工单为已读
    public static final String UPDATE_REPAIR_IS_READ = BASE_URL + AS_ASSETSAPI + "/api/repairPlan/updateIsRead";
    //终审详情审核
    public static final String REPAIR_PLAN_FINISH = BASE_URL + AS_ASSETSAPI + "/api/repairPlanFinal/saveRepairPlanFinish";
    //督查督办列表
    public static final String SUPERVISE_LIST = BASE_URL + AS_ASSETSAPI + "/api/supervisory/listByPageMul";
    //督查督办详情
    public static final String SUPERVISE_DETAIL = BASE_URL + AS_ASSETSAPI + "/api/supervisory/getDetail";
    //督查督办验收
    public static final String SUPERVISE_SAVE = BASE_URL + AS_ASSETSAPI + "/api/supervisory/save";
    //更新督查督办是否阅读
    public static final String UPDATE_SUPERVISE_IS_READ = BASE_URL + AS_ASSETSAPI + "/api/supervisory/updateIsRead";
    //物业服务标准列表
    public static final String GET_DOC_LIST = BASE_URL + "/cms/api/doc/getDocList";
    //供应商查看
    public static final String SUPPLIER_LIST = BASE_URL + AS_ASSETSAPI + "/api/supplier/listByPageSupplier";
    //总览
    public static final String OVERVIEW = BASE_URL + AS_ASSETSAPI + "/api/overview/overview";
    //文件共享列表
    public static final String SHARED_FILES_LIST = BASE_URL + AS_ASSETSAPI + "/api/fileshare/listByPage";
    //共享文件筛选项
    public static final String SHARED_FILES_FILTER_LIST = BASE_URL + AS_ASSETSAPI + "/api/fileshare/fileSharingTypes";
    //修改个人信息
    public static final String UPDATE_USER_INFO = BASE_URL + "/rbac/api/user/updateUserInfo";
    //上传文件
    public static final String UPLOAD_FILE = BASE_URL + "/uploadFiles";

    //储备中修缮计划列表
    public static final String REPAIR_APPROVAL_LIST = BASE_URL + AS_ASSETSAPI + "/api/repairPlan4Exam/listRepairPlan";
    //储备中修缮计划操作
    public static final String REPAIR_APPROVAL_SAVE = BASE_URL + AS_ASSETSAPI + "/api/repairPlan4Exam/saveRepairPlan";

    //物业服务标准详情接口
    public static final String GET_DOC_DETAIL = BASE_URL  + "/cms/api/doc/getDocDetail";


}
