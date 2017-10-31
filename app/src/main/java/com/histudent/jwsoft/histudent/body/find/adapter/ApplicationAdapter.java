package com.histudent.jwsoft.histudent.body.find.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.find.bean.FindHomeModel;
import com.histudent.jwsoft.histudent.body.find.bean.HuodongBean;
import com.histudent.jwsoft.histudent.body.hiworld.bean.SimpleUserModel;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.HiStudentHeadImageView;
import com.histudent.jwsoft.histudent.commen.view.MyGridView;
import com.histudent.jwsoft.histudent.manage.PhotoManager;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.List;

/**
 * Created by ZhangYT on 2016/9/19.
 */
public class ApplicationAdapter extends BaseAdapter {


    private Context context;
    private List<Object> list;
    private boolean isShow;
    private MyGridView gridView;
    private int type;

    public ApplicationAdapter(Context context, List<Object> list, MyGridView gridView, int type) {

        this.context = context;
        this.list = list;
        this.gridView = gridView;
        this.type = type;
    }


    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
        notifyDataSetChanged();
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
        String name = "", logo = "";
        FindHomeModel.MyAppListBean bean = null;

        Object o = list.get(position);

        if (convertView == null) {
            if (!(o instanceof SimpleUserModel)) {
                convertView = View.inflate(context, R.layout.find_gridview_item1, null);
            } else {
                convertView = View.inflate(context, R.layout.selete_sudent_item, null);
            }

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }

        if (o instanceof SimpleUserModel) {

            viewHolder.iv_action.setImageResource(R.mipmap.delete_red);
            logo = ((SimpleUserModel) o).getAvatar();
            if (!StringUtil.isEmpty(logo)) {
//                MyImageLoader.getIntent().displayNetImageWithAnimation(context, logo,
//                        viewHolder.iv_person, R.mipmap.default_image);
                CommonGlideImageLoader.getInstance().displayNetImage(context, logo,
                        viewHolder.iv_person, PhotoManager.getInstance().getDefaultPlaceholderResource());
            }
        } else if (o instanceof HuodongBean) {

            HuodongBean huodong = ((HuodongBean) o);
            name = huodong.getAppName();
            logo = huodong.getLogo();

            if (!isShow()) {
                viewHolder.iv_action.setVisibility(View.GONE);
            } else {
                viewHolder.iv_action.setVisibility(View.VISIBLE);
                if (huodong.isAdd()) {
                    viewHolder.iv_action.setImageResource(R.mipmap.added);
                } else {
                    viewHolder.iv_action.setImageResource(R.mipmap.add_green);
                }
            }

            viewHolder.tv_title.setText(name);
            if (!StringUtil.isEmpty(logo)) {
                CommonGlideImageLoader.getInstance().displayNetImage(context, logo,
                        viewHolder.iv, PhotoManager.getInstance().getDefaultPlaceholderResource());
            } else {
                viewHolder.iv.setImageResource(R.mipmap.more_application);
            }


            if (type == 1) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(SystemUtil.dp2px(40), SystemUtil.dp2px(40));
                params.setMargins(SystemUtil.dp2px(10), 0, SystemUtil.dp2px(10), 0);
                viewHolder.iv.setLayoutParams(params);
            }
        }


        return convertView;
    }


    class ViewHolder {
        private ImageView iv;
        private TextView tv_title;
        private ImageView iv_action;
        private HiStudentHeadImageView iv_person;

        public ViewHolder(View convertView) {

            iv = convertView.findViewById(R.id.find_grid_view_iv);
            tv_title = convertView.findViewById(R.id.find_gird_view_tv);
            iv_action = convertView.findViewById(R.id.iv_action);
            iv_person = convertView.findViewById(R.id.iv_person);
        }
    }
}
