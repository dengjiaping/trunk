package com.histudent.jwsoft.histudent.body.myclass.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.histudent.jwsoft.histudent.HiStudentApplication;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.body.message.model.ClassModel;
import com.histudent.jwsoft.histudent.body.myclass.fragment.ClassFragment;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.comment2.utils.ClassAppKey;
import com.histudent.jwsoft.histudent.entity.ReadClockMessageEvent;
import com.netease.nim.uikit.common.util.string.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangYT on 2017/4/24.
 */

public class ClassAppAdapter extends BaseAdapter {

    private Context context;
    private List<Object> list;
    private boolean isShow;
    private ClassModel model;

    public ClassAppAdapter(Activity context, List<Object> list, ClassModel model) {
        this.context = context;
        this.list = list;
        this.model = model;
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
        String name = "", logo = "", title = "";
        int imageId = 0;
        ClassModel.ClassAppsBean bean = null;

        Object o = list.get(position);

        //根据标记判读是传入的集合是listView的还是gridView的

        if (convertView == null) {
            if (o instanceof ClassModel.ClassAppsBean) {
                convertView = View.inflate(context, R.layout.app_item, null);
            }

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = ((ViewHolder) convertView.getTag());
        }

        if (o instanceof ClassModel.ClassAppsBean) {
            bean = ((ClassModel.ClassAppsBean) o);
            name = bean.getAppName();
            logo = bean.getLogo();
            title = bean.getAppName();
            viewHolder.app_name.setText(StringUtil.isEmpty(name) ? title : name);
            if (!StringUtil.isEmpty(logo)) {
                CommonGlideImageLoader.getInstance().displayNetImage(context, logo,
                        viewHolder.app_log);
            }

            final String appKey = bean.getAppKey();
            if (ClassAppKey.HOME_SCHOOL_COMMICATION.equals(appKey)) {
                if (model.getImNum() > 0) {
                    viewHolder.tv_notice_num.setVisibility(View.VISIBLE);
                    viewHolder.tv_notice.setVisibility(View.GONE);
                    viewHolder.tv_notice_num.setText(model.getImNum() > 99 ? "99+" : model.getImNum() + "");
                } else {
                    viewHolder.tv_notice_num.setVisibility(View.GONE);
                    viewHolder.tv_notice.setVisibility(View.GONE);
                }
            } else if (ClassAppKey.NOTICE.equals(appKey)) {
                if (model.getNoticeNum() > 0) {
                    viewHolder.tv_notice_num.setVisibility(View.GONE);
                    viewHolder.tv_notice.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tv_notice_num.setVisibility(View.GONE);
                    viewHolder.tv_notice.setVisibility(View.GONE);
                }
            } else if (ClassAppKey.HOMEWORK.equals(appKey)) {
                viewHolder.tv_notice_num.setVisibility(View.GONE);
                viewHolder.tv_notice.setVisibility(View.GONE);
            } else if (ClassAppKey.READ_PUNCH_THE_CLOCK.equals(appKey)
                    || ClassAppKey.READ_PUNCH_THE_CLOCK_TEST.equals(appKey)) {
                //根据是否是新的模块  添加新图标样式
                final List<ClassModel.ClassAppsBean> classApps = model.getClassApps();
                final ClassModel.ClassAppsBean classAppsBean = classApps.get(position);
                final boolean isNewFunction = classAppsBean.isIsNew();
                final String alertTypePriority = classAppsBean.getAlertTypePriority();
                final int msgNum = classAppsBean.getMsgNum();
                //存放优先级信息 1:新   2:红点  3:数字
                final List<String> listPriority = new ArrayList<>();
                if (alertTypePriority != null) {
                    if (alertTypePriority.contains(",")) {
                        final String[] split = alertTypePriority.split(",");
                        for (int i = 0; i < split.length; i++) {
                            listPriority.add(split[i]);
                        }
                    }
                }

                //显示新类型
                if (listPriority.contains(String.valueOf(1))) {
                    final SharedPreferences sharedPreferences = HiStudentApplication
                            .getInstance()
                            .getSharedPreferences(ClassFragment.READ_CLOCK_NEW_SIGN, Context.MODE_PRIVATE);
                    final boolean isShow = sharedPreferences.getBoolean(ClassFragment.READ_CLOCK_NEW_SIGN, true);
                    if (isNewFunction && isShow) {
                        viewHolder.mIvFunctionNewIcon.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.mIvFunctionNewIcon.setVisibility(View.GONE);
                    }
                }

                if (viewHolder.mIvFunctionNewIcon.getVisibility() == View.VISIBLE){
                    //如果"新"图标显示 则不显示消息数量 直接结束
                    return convertView;
                }

                viewHolder.mIvFunctionNewIcon.setVisibility(View.GONE);
                //显示红点
                if (listPriority.contains(String.valueOf(2))) {
                    viewHolder.mDotCircle.setVisibility(View.VISIBLE);
                    return convertView;
                } else {
                    viewHolder.mDotCircle.setVisibility(View.GONE);
                }

                //显示数据数量
                if (listPriority.contains(String.valueOf(3))) {
                    viewHolder.mDotCircle.setVisibility(View.GONE);
                    if (msgNum == 0) {
                        viewHolder.mDotNumber.setVisibility(View.GONE);
                    } else {
                        viewHolder.mDotNumber.setVisibility(View.VISIBLE);
                        final String showNumber = msgNum > 99 ? "99+" : String.valueOf(msgNum);
                        viewHolder.mDotNumber.setText(showNumber);
                    }
                    EventBus.getDefault().postSticky(new ReadClockMessageEvent(msgNum));
                }

            }
        }

        return convertView;
    }

    class ViewHolder {
        private ImageView app_log;
        private TextView app_name;
        private TextView tv_notice, tv_notice_num;
        private ImageView mIvFunctionNewIcon;
        private TextView mDotCircle;
        private TextView mDotNumber;

        public ViewHolder(View convertView) {
            app_log = convertView.findViewById(R.id.app_iv);
            app_name = convertView.findViewById(R.id.app_name);
            tv_notice = convertView.findViewById(R.id.tv_notice);
            tv_notice_num = convertView.findViewById(R.id.tv_notice_num);
            mIvFunctionNewIcon = convertView.findViewById(R.id.iv_new_function);
            mDotCircle = convertView.findViewById(R.id.tv_common_dot_function);
            mDotNumber = convertView.findViewById(R.id.tv_common_num);
        }

    }

}
