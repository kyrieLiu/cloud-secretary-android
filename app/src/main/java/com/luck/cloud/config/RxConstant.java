package com.luck.cloud.config;

/**
 * Created by liuyin on 2019/4/29 18:45
 * Description: RxJava 观察者与被观察者之间传递消息的标识常量
 */
public class RxConstant {

    public final static String RX_REPAIR_ORDER_UPDATE = "RX_REPAIR_ORDER_UPDATE";//修缮工单状态发生变化
    public final static String RX_SUPERVISE_HANDLE_UPDATE = "RX_SUPERVISE_HANDLE_UPDATE";//督查督办验收是否已读
    public final static String RX_PERSON_DATA_UPDATE = "RX_PERSON_DATA_UPDATE";//个人资料信息发生变化
    public final static String RX_SUPERVISE_ACCEPT = "RX_SUPERVISE_ACCEPT";//督查督办验收
    public final static String RX_REPAIR_CHECK = "RX_REPAIR_CHECK";//修缮审核

}
