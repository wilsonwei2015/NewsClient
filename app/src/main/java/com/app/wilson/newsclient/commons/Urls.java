package com.app.wilson.newsclient.commons;

/**
 * Created by wilson on 16/5/10.
 */
public class Urls {

    public static final String WY_HOST = "http://c.m.163.com/";



    //新闻头条
    public static final String HEAD_LINE_URL= WY_HOST +"nc/ad/headline/{beginNum}-{pageSize}.html";
    //新闻分类
    public static final String NEWS_TOPIC_URL =WY_HOST +"nc/topicset/android/subscribe/manage/listspecial.html";
    //新闻列表
    public static final String NEWS_LIST_URL = WY_HOST + "nc/article/list/{tid}/{beginNum}-{pageSize}.html";
    //新闻详情
    public static final String NEWS_DETAIL_URL = WY_HOST + "nc/article/{docid}/full.html";
    //视频列表
    public static final String VIDEO_LIST_URL =WY_HOST+"nc/video/list/V9LG4B3A0/y/{beginNum}-{pageSize}.html";
    //百度定位
    public static final String LOCATION_URL = "http://api.map.baidu.com/geocoder";
    // 天气预报url
    public static final String WEATHER_URL = "http://wthrcdn.etouch.cn/weather_mini?city=";










}
