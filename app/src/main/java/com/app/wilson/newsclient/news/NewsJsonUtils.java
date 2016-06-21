package com.app.wilson.newsclient.news;

import com.app.wilson.newsclient.domain.NewsBean;
import com.app.wilson.newsclient.domain.NewsDetailBean;
import com.app.wilson.newsclient.domain.TopicBean;
import com.app.wilson.newsclient.utils.JsonUtils;
import com.app.wilson.newsclient.utils.LogUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;


public class NewsJsonUtils {

    private final static String TAG = "NewsJsonUtils";

    /**
     * 将获取到的json转换为新闻列表对象
     * @param res
     * @param value
     * @return
     */
    public static List<NewsBean> readJsonNewsBeans(String res, String value) {
        List<NewsBean> beans = new ArrayList<NewsBean>();
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(value);
            if(jsonElement == null) {
                return null;
            }
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 1; i < jsonArray.size(); i++) {
                JsonObject jo = jsonArray.get(i).getAsJsonObject();

                    NewsBean news = JsonUtils.deserialize(jo, NewsBean.class);
                    beans.add(news);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "readJsonNewsBeans error" , e);
        }
        return beans;
    }

    public static List<TopicBean> readJsonTopicBeans(String res, String value) {
        List<TopicBean> beans = new ArrayList<TopicBean>();
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(value);
            if(jsonElement == null) {
                return null;
            }
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 1; i < jsonArray.size(); i++) {
                JsonObject jo = jsonArray.get(i).getAsJsonObject();

                TopicBean topic = JsonUtils.deserialize(jo, TopicBean.class);
                    beans.add(topic);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "readJsonTopicBeans error" , e);
        }
        return beans;
    }

    public static NewsDetailBean readJsonNewsDetailBeans(String res, String docId) {
        NewsDetailBean newsDetailBean = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(docId);
            if(jsonElement == null) {
                return null;
            }
            newsDetailBean = JsonUtils.deserialize(jsonElement.getAsJsonObject(), NewsDetailBean.class);
        } catch (Exception e) {
            LogUtils.e(TAG, "readJsonNewsBeans error" , e);
        }
      return newsDetailBean;
    }

}
