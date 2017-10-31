package com.histudent.jwsoft.histudent.adapter.homework;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadyBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadySubBean;
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
        final HomeworkAlreadySubBean homeworkAlreadySubBean = item.t;
        helper.setText(R.id.tv_homework_name, homeworkAlreadySubBean.getHomeWorkName())
                .setText(R.id.tv_homework_content_detail_desc, homeworkAlreadySubBean.getHomeworkContent())
                .setText(R.id.tv_homework_group_name, homeworkAlreadySubBean.getGroupName())
                .setText(R.id.tv_publish_owner, "发布人: " + homeworkAlreadySubBean.getPublishOwner());
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
