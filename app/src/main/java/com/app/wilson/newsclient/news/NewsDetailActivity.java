package com.app.wilson.newsclient.news;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.app.wilson.newsclient.R;
import com.app.wilson.newsclient.commons.Urls;
import com.app.wilson.newsclient.domain.NewsDetailBean;
import com.app.wilson.newsclient.utils.OkHttpUtils;

public class NewsDetailActivity extends AppCompatActivity {



    private TextView news_item_detail;

    private NewsDetailBean data;

    private String docId;

     final class MgsWhat{
        static final int REFRESH_DATA_SUCCESS= 1;
        static final int REFRESH_DATA_FAILURE= 2;
    }




    //Handler 处理消息
    private Handler handler =new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MgsWhat.REFRESH_DATA_SUCCESS:
                    freshUI();
                    break;
                case MgsWhat.REFRESH_DATA_FAILURE:
                    Toast.makeText(NewsDetailActivity.this,"数据加载失败，请稍后再试", Toast.LENGTH_SHORT).show();
                    break;
                default:break;
            }
        }
    };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        news_item_detail= (TextView)findViewById(R.id.news_item_details);
        Intent intent=getIntent();
        docId= intent.getStringExtra("docId");
        loadNewsData(docId);
    }


    private void loadNewsData(final String docid){
        String url =Urls.NEWS_DETAIL_URL.replace("{docid}",docid);

        OkHttpUtils.get(url, new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                data=NewsJsonUtils.readJsonNewsDetailBeans(response,docId);
                Message msg =new Message();
                msg.what=MgsWhat.REFRESH_DATA_SUCCESS;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Exception e) {
                Message msg =new Message();
                msg.what=MgsWhat.REFRESH_DATA_FAILURE;
                handler.sendMessage(msg);
            }
        });


    }

    private void freshUI(){

        news_item_detail.setText(Html.fromHtml(data.getBody()));

    }


}
