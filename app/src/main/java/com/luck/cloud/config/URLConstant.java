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
    public static final String REGISTER = BASE_URL + "/sys/people/save";
    //字典数据
    public static final String DICT_DATA = BASE_URL + "/sys/dictionary/getDictValues";
    public static final String STUDY_SCIENCE_TAB = BASE_URL + "/sys/domain/list";
    //学习列表
    public static final String STUDY_LIST = BASE_URL + "/information/info/page";
    //学习详情
    public static final String STUDY_DETAIL = BASE_URL + "/information/info/door/getDetail";
    //活动列表
    public static final String ACTIVE_LIST = BASE_URL + "/activity/info/page";
    //我报名的活动列表
    public static final String ACTIVE_LIST_MY = BASE_URL + "/activity/info/pageMy";
    //发起活动
    public static final String ACTIVE_CREATE = BASE_URL + "/activity/info/save";
    //活动详情
    public static final String ACTIVE_DETAIL = BASE_URL + "/activity/info/get";
    //活动报名
    public static final String ACTIVE_APPLY = BASE_URL + "/activity/user/save";
    //留言
    public static final String ACTIVE_MESSAGE_SAVE = BASE_URL + "/activity/message/save";
    //个人信息
    public static final String USER_INFO = BASE_URL + "/sys/people/get";

    //修改个人信息
    public static final String UPDATE_USER_INFO = BASE_URL + "/sys/people/save";
    //首页通知公告
    public static final String HOME_NOTICE = BASE_URL + "/information/notice/getHome";
    //通知公告列表
    public static final String NOTICE_LIST = BASE_URL + "/information/notice/page";
    //通知公告详情
    public static final String NOTICE_DETAIL = BASE_URL + "/information/notice/get";
    //保存动态
    public static final String DYNAMIC_SAVE = BASE_URL + "/information/dynamic/save";


    //园区列表
    public static final String PARK_LIST = BASE_URL + AS_ASSETSAPI + "/api/parkinfo/listParkInfo";
    public static final String GET_DOC_LIST = BASE_URL + "/cms/api/doc/getDocList";

    //上传文件
    public static final String UPLOAD_FILE = BASE_URL + "/uploadFiles";


}
