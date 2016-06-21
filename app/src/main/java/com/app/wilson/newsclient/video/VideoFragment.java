package com.app.wilson.newsclient.video;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.app.wilson.newsclient.R;
import com.app.wilson.newsclient.commons.Urls;
import com.app.wilson.newsclient.domain.NewsBean;
import com.app.wilson.newsclient.domain.TopicBean;
import com.app.wilson.newsclient.domain.VideoBean;
import com.app.wilson.newsclient.news.NewsItemAdapter;
import com.app.wilson.newsclient.news.NewsJsonUtils;
import com.app.wilson.newsclient.utils.LogUtils;
import com.app.wilson.newsclient.utils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilson on 16/5/10.
 */
public class VideoFragment extends Fragment {

    private static final String TAG = "VideoFragment";


    private int pageSize = 10;

    private int page = 1;

    private RecyclerView videoListView;

    private List<VideoBean> videoBeanList;


    private VideoItemAdapter videoItemAdapter;




    static final class MgsWhat{
        static final int REFRESH_DATA_SUCCESS= 1;
        static final int REFRESH_DATA_FAILURE= 2;
        static final int REFRESH_DATA_SUCCESS_NO_DATA= 3;
    }

     //Handler 处理消息
    private Handler handler =new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MgsWhat.REFRESH_DATA_SUCCESS:
                    videoItemAdapter.notifyDataSetChanged();
                    break;
                case MgsWhat.REFRESH_DATA_FAILURE:
                    Toast.makeText(getActivity(),"数据加载失败，请稍后再试", Toast.LENGTH_SHORT).show();
                    break;
                case MgsWhat.REFRESH_DATA_SUCCESS_NO_DATA:
                    Toast.makeText(getActivity(),"亲，没有更多数据啦", Toast.LENGTH_SHORT).show();
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
        View view  = inflater.inflate(R.layout.video_list,container,false);

        videoListView =(RecyclerView)view.findViewById(R.id.video_list);

        videoItemAdapter=new VideoItemAdapter(getActivity());
        videoBeanList= new ArrayList<VideoBean>();

        videoItemAdapter.setDatas(videoBeanList);
        videoListView.setAdapter(videoItemAdapter);
        loadVideoData();
        Log.i(getTag(),"onCreateView");
        return view;
    }


    private void loadVideoData() {
        String url = Urls.VIDEO_LIST_URL
                .replace("{beginNum}", String.valueOf((page - 1) * pageSize))
                .replace("{pageSize}", String.valueOf(pageSize));

        OkHttpUtils.get(url, new OkHttpUtils.ResultCallback<String>() {

            @Override
            public void onSuccess(String response) {
                LogUtils.i("videoBeanList:",response);

                List<VideoBean> list = VideoJsonUtils.readJsonVideoBeans(response,"V9LG4B3A0" );
                if (null != list && list.size() > 0) {

                    videoBeanList.addAll(list);
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









}
