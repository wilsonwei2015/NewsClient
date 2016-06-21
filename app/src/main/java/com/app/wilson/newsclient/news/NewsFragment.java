package com.app.wilson.newsclient.news;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.wilson.newsclient.R;
import com.app.wilson.newsclient.commons.Urls;
import com.app.wilson.newsclient.domain.NewsBean;
import com.app.wilson.newsclient.domain.TopicBean;
import com.app.wilson.newsclient.main.MainActivity;
import com.app.wilson.newsclient.utils.JsonUtils;
import com.app.wilson.newsclient.utils.LogUtils;
import com.app.wilson.newsclient.utils.OkHttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilson on 16/5/10.
 */
public class NewsFragment extends Fragment {

    private static final String TAG = "NewsFragment";


    private int pageSize = 10;

    private int page = 1;

    private String currentTopicId;

    private TabLayout topicTabLayout;

    private ListView newsListView;

    private List<NewsBean> newsBeanList;

    private List<TopicBean> topicBeanList;



    private NewsItemAdapter newsItemAdapter;




    static final class MgsWhat{
        static final int REFRESH_DATA_SUCCESS= 1;
        static final int REFRESH_DATA_FAILURE= 2;
        static final int REFRESH_DATA_SUCCESS_NO_DATA= 3;
        static final int REFRESH_TOPIC_SUCCESS= 4;
    }

     //Handler 处理消息
    private Handler handler =new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MgsWhat.REFRESH_DATA_SUCCESS:
                    newsItemAdapter.notifyDataSetChanged();
                    break;
                case MgsWhat.REFRESH_DATA_FAILURE:
                    Toast.makeText(getActivity(),"数据加载失败，请稍后再试", Toast.LENGTH_SHORT).show();
                    break;
                case MgsWhat.REFRESH_DATA_SUCCESS_NO_DATA:
                    Toast.makeText(getActivity(),"亲，没有更多数据啦", Toast.LENGTH_SHORT).show();
                    break;
                case MgsWhat.REFRESH_TOPIC_SUCCESS:
                    freshTabs();
                    TopicBean topic1 =topicBeanList.get(0);
                    loadNewsData(topic1.getTid());
                    break;
                default:break;
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(getTag(),"onCreate");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.news_list,container,false);

        newsListView =(ListView)view.findViewById(R.id.news_list_view);
        topicTabLayout =(TabLayout)view.findViewById(R.id.tabs);

        newsItemAdapter=new NewsItemAdapter(getActivity());
        newsBeanList= new ArrayList<NewsBean>();
        topicBeanList = new ArrayList<TopicBean>();

        newsItemAdapter.setDatas(newsBeanList);

        newsListView.setAdapter(newsItemAdapter);

        topicTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        topicTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        topicTabLayout.setOnTabSelectedListener(new OnNewsTabSelectedListener());
        loadTopicsData();
        Log.i(getTag(),"onCreateView");
        return view;
    }

    class OnNewsTabSelectedListener implements TabLayout.OnTabSelectedListener{

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            loadNewsData(topicBeanList.get(tab.getPosition()).getTid());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            page=1;
            loadNewsData(topicBeanList.get(tab.getPosition()).getTid());

        }
    }



    private void loadNewsData(final String  tid) {
        String url = Urls.NEWS_LIST_URL.replace("{tid}", tid)
                .replace("{beginNum}", String.valueOf((page - 1) * pageSize))
                .replace("{pageSize}", String.valueOf(pageSize));

        OkHttpUtils.get(url, new OkHttpUtils.ResultCallback<String>() {

            @Override
            public void onSuccess(String response) {
                LogUtils.i("newsListData:",response);

                List<NewsBean> list = NewsJsonUtils.readJsonNewsBeans(response,tid );
                if (null != list && list.size() > 0) {

                    if(!tid.equals(currentTopicId)){
                        newsBeanList.clear();
                        currentTopicId=tid;

                    }

                    newsBeanList.addAll(list);
                    page++;
                    Message msg = new Message();
                    msg.what = MgsWhat.REFRESH_DATA_SUCCESS;
                    handler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    msg.what = MgsWhat.REFRESH_DATA_SUCCESS_NO_DATA;
                    handler.sendMessage(msg);
                }
            }


            @Override
            public void onFailure(Exception e) {
                Message msg = new Message();
                msg.what = MgsWhat.REFRESH_DATA_FAILURE;
                handler.sendMessage(msg);
            }
        });
    }

    private void loadTopicsData(){
        OkHttpUtils.get(Urls.NEWS_TOPIC_URL, new OkHttpUtils.ResultCallback<String>() {

            @Override
            public void onSuccess(String response) {
                LogUtils.i("topicListData:",response);
                List<TopicBean> list = NewsJsonUtils.readJsonTopicBeans(response, "tList");
                if (null != list && list.size() > 0) {
                    topicBeanList.addAll(list);
                    Message msg = new Message();
                    msg.what = MgsWhat.REFRESH_TOPIC_SUCCESS;
                    handler.sendMessage(msg);
                }

            }
            @Override
            public void onFailure(Exception e) {
                Message msg =new Message();
                msg.what=MgsWhat.REFRESH_DATA_FAILURE;
                handler.sendMessage(msg);
            }
        });

    }


    private void freshTabs(){

        for(TopicBean tbBean:topicBeanList){
            TabLayout.Tab tb =topicTabLayout.newTab();
            tb.setText(tbBean.getTname());
            topicTabLayout.addTab(tb);
        }
    }


    public class NewsItemOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {



        }
    }






















}
