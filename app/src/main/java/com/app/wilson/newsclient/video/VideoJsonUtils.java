package com.app.wilson.newsclient.video;

import com.app.wilson.newsclient.domain.NewsBean;
import com.app.wilson.newsclient.domain.NewsDetailBean;
import com.app.wilson.newsclient.domain.TopicBean;
import com.app.wilson.newsclient.domain.VideoBean;
import com.app.wilson.newsclient.utils.JsonUtils;
import com.app.wilson.newsclient.utils.LogUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;


public class VideoJsonUtils {

    private final static String TAG = "VideoJsonUtils";

    /**
     * 将获取到的json转换为视频表对象
     * @param res
     * @param value
     * @return
     */
    public static List<VideoBean> readJsonVideoBeans(String res, String value) {
        List<VideoBean> beans = new ArrayList<VideoBean>();
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

                    VideoBean videoBean = JsonUtils.deserialize(jo, VideoBean.class);
                    beans.add(videoBean);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "VideoJsonUtils error" , e);
        }
        return beans;
    }


}
