package com.app.wilson.newsclient.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.wilson.newsclient.R;
import com.app.wilson.newsclient.domain.NewsBean;
import com.app.wilson.newsclient.utils.ImageLoaderUtils;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by wilson on 16/5/12.
 */
    public class NewsItemAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<NewsBean> datas;



    public void setDatas(List<NewsBean> datas) {
        this.datas = datas;
    }

    public final class NewsItemViewHolder{
        private TextView title;
        private TextView source;
        private TextView ptime;
        private ImageView image;
        private TextView replycont;
        private TextView cont;


    }



    public NewsItemAdapter(Context c) {
        this.context=c;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int i) {
            return datas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            NewsItemViewHolder newsItemViewHolder = null;

            final NewsBean news = datas.get(i);

            if(null==view){

                view=inflater.inflate(R.layout.news_item,null);
                newsItemViewHolder = new NewsItemViewHolder();
                newsItemViewHolder.source=(TextView) view.findViewById(R.id.news_item_source_tv);
                newsItemViewHolder.title=(TextView)view.findViewById(R.id.news_item_tile_tv);
                newsItemViewHolder.ptime=(TextView)view.findViewById(R.id.news_item_date_tv);
                newsItemViewHolder.replycont=(TextView)view.findViewById(R.id.news_item_relpycont_tv);
                newsItemViewHolder.cont=(TextView)view.findViewById(R.id.news_item_cont_tv);


                newsItemViewHolder.image = (ImageView)view.findViewById(R.id.news_item_img_iv);
                view.setTag(newsItemViewHolder);

            }else{
                newsItemViewHolder =(NewsItemViewHolder)view.getTag();
            }

            newsItemViewHolder.ptime.setText(news.getPtime());
            newsItemViewHolder.title.setText(news.getTitle());
            newsItemViewHolder.source.setText(news.getSource());
            newsItemViewHolder.replycont.setText(news.getReplyCount());
            newsItemViewHolder.cont.setText(news.getDigest());

            ImageLoaderUtils.display(context,newsItemViewHolder.image,news.getImgsrc());

            view.setOnClickListener(new NewsItemOnClickListener(news,this.context));

            return view;
        }
    }
