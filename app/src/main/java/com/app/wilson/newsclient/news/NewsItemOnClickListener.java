package com.app.wilson.newsclient.news;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.app.wilson.newsclient.domain.NewsBean;

/**
 * Created by wilson on 16/5/12.
 */

    public class NewsItemOnClickListener implements View.OnClickListener{

    private Context context;
    private NewsBean news;

    public NewsItemOnClickListener(NewsBean news, Context context) {
        this.news = news;
        this.context = context;
    }

    @Override
        public void onClick(View view) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("docId",news.getDocid());
            context.startActivity(intent);
        }
    }
