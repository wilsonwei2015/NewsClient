package com.app.wilson.newsclient.news;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.wilson.newsclient.R;
import com.app.wilson.newsclient.domain.NewsBean;
import com.app.wilson.newsclient.domain.TopicBean;
import com.app.wilson.newsclient.utils.ImageLoaderUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by wilson on 16/5/12.
 */
    public class NewTopicAdapter extends PagerAdapter {

    private static final String TAG="NewTopicAdapter";

    private Context context;
    private List<TopicBean> datas;



    public void setDatas(List<TopicBean> datas) {
        this.datas = datas;
    }



    public final class ViewHolder{
        private TextView title;

    }

    public NewTopicAdapter(Context c) {
        this.context=c;
    }


    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return datas.get(position).getTname();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
