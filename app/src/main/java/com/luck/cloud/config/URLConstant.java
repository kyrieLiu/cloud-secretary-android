package com.luck.cloud.config;

/**
 * Created by liuyin on 2019/2/28 21:05
 * Describe 接口地址常量
 */
public class URLConstant {

    //public static final String BASE_URL = "http://10.17.7.103:8066";//生产环境
    public static final String BASE_URL = "http://121.36.144.200:8066";//生产环境

    public static final boolean DEBUG = true;
    public static final String AS_ASSETSAPI = "/ac";

    public static final String LOGIN = BASE_URL + "/login/login";
    public static final String REGISTER = BASE_URL + "/sys/people/save";
    //字典数据
    public static final String DICT_DATA = BASE_URL + "/sys/dictionary/getDictValues";
    public static final String STUDY_SCIENCE_TAB = BASE_URL + "/sys/domain/list";
    //学习列表
    public static final String STUDY_LIST = BASE_URL + "/information/info/page";
    public static final String FOOT_LIST = BASE_URL + "/sys/track/page";
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
    //动态列表
    public static final String DYNAMIC_LIST = BASE_URL + "/information/dynamic/page";
    //删除动态
    public static final String DYNAMIC_REMOVE = BASE_URL + "/information/dynamic/remove";
    //关注
    public static final String ATTENTION = BASE_URL + "/sys/attention/save";
    //取消关注
    public static final String ATTENTION_CANCEL = BASE_URL + "/sys/attention/remove";
    //点赞
    public static final String LIKE = BASE_URL + "/information/like/save";
    //取消点赞
    public static final String LIKE_CANCEL = BASE_URL + "/information/like/remove";
    //收藏
    public static final String COLLECT = BASE_URL + "/information/collect/save";
    //取消收藏
    public static final String COLLECT_CANCEL = BASE_URL + "/information/collect/remove";
    //评论
    public static final String COMMENT = BASE_URL + "/information/message/save";
    //我的关注
    public static final String MY_ATTENTION = BASE_URL + "/sys/attention/pageAttention";
    //我的粉丝
    public static final String MY_FANS = BASE_URL + "/sys/attention/pageFans";
    //我的收藏
    public static final String MY_COLLECT = BASE_URL + "/information/dynamic/pageMyCollect";


    //园区列表
    public static final String PARK_LIST = BASE_URL + AS_ASSETSAPI + "/api/parkinfo/listParkInfo";
    public static final String GET_DOC_LIST = BASE_URL + "/cms/api/doc/getDocList";

    //上传文件
    public static final String UPLOAD_FILE = BASE_URL + "/uploadFiles";
    //文件列表
    public static final String FILE_LIST = BASE_URL + "/work/file/page";
    //打卡
    public static final String SAVE_CLOCK = BASE_URL + "/system/clock/save";
    //打卡记录
    public static final String CLOCK_RECORD = BASE_URL + "/system/clock/page";
    //保存日程
    public static final String SAVE_ARRANGE = BASE_URL + "/work/weekly/save";
    public static final String ARRANGE_LIST = BASE_URL + "/work/weekly/page";
    public static final String DELETE_ARRANGE = BASE_URL + "/work/weekly/remove";
    public static final String VILLAGE_INFO = BASE_URL + "/work/village/page";
    public static final String UPDATE_VILLAGE = BASE_URL + "/work/village/save";
    public static final String LOW_LIST = BASE_URL + "/work/low/page";
    public static final String LOW_SAVE = BASE_URL + "/work/low/save";
    public static final String LOW_DELETE = BASE_URL + "/work/low/remove";


}
