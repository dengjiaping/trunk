package com.histudent.jwsoft.histudent.adapter.homework;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadyBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadySubBean;
import com.histudent.jwsoft.histudent.commen.utils.SystemUtil;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;

import java.util.List;

/**
 * Created by lichaojie on 2017/10/24.
 * desc:
 * 作业列表adapter
 */

public class HomeworkAlreadyAdapter extends BaseSectionQuickAdapter<HomeworkAlreadyBean, BaseViewHolder> {

    private int mType = -1;

    public static final HomeworkAlreadyAdapter create(int layoutResId, int sectionHeadResId, List<HomeworkAlreadyBean> data) {
        return new HomeworkAlreadyAdapter(layoutResId, sectionHeadResId, data);
    }

    private HomeworkAlreadyAdapter(int layoutResId, int sectionHeadResId, List<HomeworkAlreadyBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, HomeworkAlreadyBean item) {
        helper.setText(R.id.tv_homework_title_head_date, item.getTitleHeadContent());
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeworkAlreadyBean item) {

        int maxLength = 0;
        final HomeworkAlreadySubBean homeworkAlreadySubBean = item.t;
        helper.setText(R.id.tv_homework_name, homeworkAlreadySubBean.getHomeWorkName())
                .setText(R.id.tv_homework_content_detail_desc, homeworkAlreadySubBean.getHomeworkContent())
                .setText(R.id.tv_publish_owner, "发布人: " + homeworkAlreadySubBean.getPublishOwner());
        //针对分组单独进行设置
        final LinearLayout groupLayout = helper.getView(R.id.ll_homework_type_group);
        final String groupName = homeworkAlreadySubBean.getGroupName();
        groupLayout.removeAllViews();
        if (groupName.length() > 0) {
            final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(SystemUtil.dp2px(12), 0, 0, 0);
            if (groupName.contains(",")) {
                //有多个组
                maxLength = homeworkAlreadySubBean.getHomeWorkName().length();
                final String[] split = groupName.split(",");
                final int length = split.length;
                if (length > 0) {
                    for (int i = 0; i < length; i++) {
                        final TextView subGroupView = (TextView) LayoutInflater
                                .from(mContext)
                                .inflate(R.layout.item_homework_list_sub_content_group_name_view, null);
                        final String subContent = split[i];
                        subGroupView.setText(subContent);
                        subGroupView.setLayoutParams(params);
                        groupLayout.addView(subGroupView);

                        maxLength += subContent.length();
                        //根据字符总数 动态显示分组状态
                        if (maxLength > 18) {
                            //分组的名称 已超过限定的长度  就不再给Layout继续添加子view 移除最后一个view 并添加一个带省略号的TextView
                            final int childCount = groupLayout.getChildCount();
                            groupLayout.removeViewAt(childCount - 1);
                            final TextView textView = (TextView) LayoutInflater
                                    .from(mContext)
                                    .inflate(R.layout.item_homework_list_sub_content_group_name_view, null);
                            textView.setBackground(null);
                            textView.setText("...");
                            params.setMargins(SystemUtil.dp2px(5), 0, 0, 0);
                            textView.setLayoutParams(params);
                            groupLayout.addView(textView);
                            break;
                        }
                    }
                }


            } else {
                //仅有一组数据
                final TextView textView = (TextView) LayoutInflater
                        .from(mContext)
                        .inflate(R.layout.item_homework_list_sub_content_group_name_view, null);
                textView.setText(groupName);
                textView.setLayoutParams(params);
                groupLayout.addView(textView);
                maxLength = homeworkAlreadySubBean.getHomeWorkName().length() + groupName.length();
                if (maxLength > 12) {
                    textView.setText("...");
                    textView.setBackground(null);
                }
            }
        }


        final View publishName = helper.getView(R.id.ll_homework_publish_name);
        final TextView complete = helper.getView(R.id.tv_complete);
        if (mType == 3) {
            //老师账号 我的相关数据
            publishName.setVisibility(View.GONE);
            complete.setVisibility(View.GONE);
        } else if (mType == 1) {
            publishName.setVisibility(View.VISIBLE);
            complete.setVisibility(View.VISIBLE);
            if (homeworkAlreadySubBean.isComplete()) {
                complete.setText("已完成");
                complete.setTextColor(ContextCompat.getColor(mContext, R.color._28ca7e));
                complete.setBackground(ContextCompat.getDrawable(mContext, R.drawable.homework_group_shape_green));
            } else {
                complete.setText("未完成");
                complete.setTextColor(ContextCompat.getColor(mContext, R.color._FF5858));
                complete.setBackground(ContextCompat.getDrawable(mContext, R.drawable.homework_group_shape_red));
            }
        } else {
            //老师账号 全部状态
            publishName.setVisibility(View.VISIBLE);
            complete.setVisibility(View.GONE);
        }

        final ImageView logo = helper.getView(R.id.iv_homeWork_type_icon);
        CommonGlideImageLoader.getInstance().displayNetImageWithAnimation(mContext, homeworkAlreadySubBean.getThumb(), logo);
    }


    public HomeworkAlreadyAdapter setType(int type) {
        this.mType = type;
        return this;
    }
}
