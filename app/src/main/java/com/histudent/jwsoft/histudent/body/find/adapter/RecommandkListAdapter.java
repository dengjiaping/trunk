package com.histudent.jwsoft.histudent.body.find.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.HiBaRecommandListBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.manage.PhotoManager;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;

/**
 * 精选适配
 * Created by ZhangYT on 2016/8/10.
 */
public class RecommandkListAdapter extends BaseAdapter {

    private List<HiBaRecommandListBean> list;
    private Context context;

    public RecommandkListAdapter(List<HiBaRecommandListBean> list, Context context) {

        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.recommand_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }
        HiBaRecommandListBean baRecommandListBean = list.get(position);
        viewHolder.tv_title.setText(baRecommandListBean.getTitle());
        viewHolder.tv_body.setText(baRecommandListBean.getSummaryInfo().getTextInfo());


        if (!StringUtil.isEmpty(baRecommandListBean.getImgUrl())) {

            ViewGroup.LayoutParams params = viewHolder.iv.getLayoutParams();
            double scale = parent.getWidth() / getWidth(baRecommandListBean.getImgUrl());
            params.width = parent.getWidth();
            params.height = (int) scale * getHeight(baRecommandListBean.getImgUrl());
            viewHolder.iv.setLayoutParams(params);
            CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(context, baRecommandListBean.getImgUrl(),
                    viewHolder.iv, PhotoManager.getInstance().getDefaultPlaceholderResource());
        }

        return convertView;
    }


    class ViewHolder {
        private ImageView iv;
        private TextView tv_body, tv_title;

        public ViewHolder(View convertView) {

            iv = ((ImageView) convertView.findViewById(R.id.iv));
            tv_title = ((TextView) convertView.findViewById(R.id.tv_title));
            tv_body = ((TextView) convertView.findViewById(R.id.tv_body));
        }
    }

    private int getHeight(String url) {

        String str1 = url.substring(url.lastIndexOf("@") + 1, url.length());
        str1 = str1.substring(str1.lastIndexOf("w") + 2, str1.lastIndexOf("h"));

        Log.e("Hieght", str1);
        return Integer.parseInt(str1);
    }

    private int getWidth(String url) {

        String str1 = url.substring(url.lastIndexOf("@") + 1, url.length());
        str1 = str1.substring(0, str1.lastIndexOf("w"));
        Log.e("Width", str1);
        return Integer.parseInt(str1);
    }

}
