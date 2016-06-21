package com.app.wilson.newsclient.video;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.app.wilson.newsclient.domain.NewsBean;
import com.app.wilson.newsclient.domain.VideoBean;
import com.app.wilson.newsclient.news.NewsDetailActivity;

/**
 * Created by wilson on 16/5/12.
 */

    public class VideoItemOnClickListener implements View.OnClickListener{

    private Context context;
    private VideoBean video;

    public VideoItemOnClickListener(VideoBean video, Context context) {
        this.video = video;
        this.context = context;
    }

    @Override
        public void onClick(View view) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("m3u8_url",video.getM3u8_url());
        intent.putExtra("mp4_url",video.getMp4_url());
        intent.putExtra("topicDesc",video.getTopicDesc());
        intent.putExtra("cover",video.getCover());
            context.startActivity(intent);
        }
    }
