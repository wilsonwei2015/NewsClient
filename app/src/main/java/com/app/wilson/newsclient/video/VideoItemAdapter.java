package com.app.wilson.newsclient.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.wilson.newsclient.R;
import com.app.wilson.newsclient.domain.VideoBean;
import com.app.wilson.newsclient.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Created by wilson on 16/5/12.
 */
    public class VideoItemAdapter extends RecyclerView.Adapter<VideoItemAdapter.VideoItemViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<VideoBean> datas;



    public void setDatas(List<VideoBean> datas) {
        this.datas = datas;
    }

    public  class VideoItemViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView topicDesc;
        private ImageView topicImg;
        private ImageView cover;

        public VideoItemViewHolder(View view){
            super(view);
            topicDesc=(TextView) view.findViewById(R.id.video_item_topicDesc_tv);
            title=(TextView)view.findViewById(R.id.video_item_title_tv);
            topicImg = (ImageView)view.findViewById(R.id.video_item_topicImg_tv);
            //cover = (ImageView)view.findViewById(R.id.news_item_img_iv);
        };
    }



    public VideoItemAdapter(Context c) {
        this.context=c;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public VideoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        VideoItemViewHolder holder = new VideoItemViewHolder(LayoutInflater.from(
                context).inflate(R.layout.video_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoItemViewHolder holder, int position) {
        VideoBean video = datas.get(position);
        holder.title.setText(video.getTitle());
        holder.topicDesc.setText(video.getTopicDesc());
        ImageLoaderUtils.display(context, holder.topicImg,video.getCover());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    }
