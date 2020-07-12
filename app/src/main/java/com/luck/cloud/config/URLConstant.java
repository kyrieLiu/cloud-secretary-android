package com.luck.cloud.config;

/**
 * Created by liuyin on 2019/2/28 21:05
 * Describe 接口地址常量
 */
public class URLConstant {

    public static final String BASE_URL = "http://192.168.0.113:8066";//生产环境
    public static final String BASE_HTML_URL = "http://ic.jingcaiwang.cn:18080/assetH5/index.html#/";//生产环境 H5页面地址

    public static final boolean DEBUG = true;
    public static final String AS_ASSETSAPI = "/ac";

    public static final String LOGIN = BASE_URL + "/login/login";
    //字典数据
    public static final String DICT_DATA = BASE_URL + "/sys/dictionary/getDictValues";
    public static final String STUDY_SCIENCE_TAB = BASE_URL + "/sys/domain/list";
    //学习列表
    public static final String STUDY_LIST = BASE_URL + "/information/info/page";
    //学习详情
    public static final String STUDY_DETAIL = BASE_URL + "/information/info/door/getDetail";


    //OA用户登录
    public static final String OA_LOGIN = BASE_URL + "/rbac/oaAppLogin";
    //园区列表
    public static final String PARK_LIST = BASE_URL + AS_ASSETSAPI + "/api/parkinfo/listParkInfo";
    //待办任务更多列表
    public static final String WAIT_DONE_MORE = BASE_URL + "/station/info/page/1/10";//物业服务标准列表
    public static final String GET_DOC_LIST = BASE_URL + "/cms/api/doc/getDocList";
    //修改个人信息
    public static final String UPDATE_USER_INFO = BASE_URL + "/rbac/api/user/updateUserInfo";
    //上传文件
    public static final String UPLOAD_FILE = BASE_URL + "/uploadFiles";


}
