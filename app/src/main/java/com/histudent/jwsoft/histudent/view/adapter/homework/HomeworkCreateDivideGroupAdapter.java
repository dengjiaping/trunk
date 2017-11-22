package com.histudent.jwsoft.histudent.view.adapter.homework;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.view.activity.homework.WorkCreateDivideGroupActivity;
import com.histudent.jwsoft.histudent.view.activity.homework.WorkReceiverPersonDetailActivity;
import com.histudent.jwsoft.histudent.model.bean.homework.CommonMemberBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;

import java.util.List;

/**
 * Created by lichaojie on 2017/10/24.
 * desc:
 */

public class HomeworkCreateDivideGroupAdapter extends BaseQuickAdapter<CommonMemberBean, BaseViewHolder> {

    /**
     * 0:仅仅是查看分组详情页面 无删除icon
     * 1:创建分组页面 含删除icon
     */
    private int mType = -1;

    public static final HomeworkCreateDivideGroupAdapter create(@LayoutRes int layoutResId, @Nullable List<CommonMemberBean> data) {
        return new HomeworkCreateDivideGroupAdapter(layoutResId, data);
    }

    private HomeworkCreateDivideGroupAdapter(@LayoutRes int layoutResId, @Nullable List<CommonMemberBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, CommonMemberBean item) {
        final View deleteIcon = helper.getView(R.id.ll_delete_add_group_member);
        if (mType == 0) {
            deleteIcon.setVisibility(View.GONE);
        } else if (mType == 1) {
            deleteIcon.setVisibility(View.VISIBLE);
        }
        helper.addOnClickListener(R.id.ll_delete_add_group_member);
        helper.addOnClickListener(R.id.ll_add_layout);
        helper.setText(R.id.tv_add, "添加成员");
        helper.setText(R.id.tv_create_homework_divider_group_name, item.getName());
        if (mContext instanceof WorkCreateDivideGroupActivity) {
            final List<CommonMemberBean> listData = ((WorkCreateDivideGroupActivity) mContext).getListData();
            if (listData.size() > 0) {
                if (helper.getAdapterPosition() == listData.size() - 1) {
                    helper.getView(R.id.ll_add_layout).setVisibility(View.VISIBLE);
                    helper.getView(R.id.view_line).setVisibility(View.VISIBLE);
                } else {
                    helper.getView(R.id.ll_add_layout).setVisibility(View.GONE);
                    helper.getView(R.id.view_line).setVisibility(View.GONE);
                }
            } else {
                helper.getView(R.id.ll_add_layout).setVisibility(View.GONE);
                helper.getView(R.id.view_line).setVisibility(View.VISIBLE);
            }
        } else if (mContext instanceof WorkReceiverPersonDetailActivity) {
            helper.getView(R.id.ll_add_layout).setVisibility(View.GONE);
            helper.getView(R.id.view_line).setVisibility(View.GONE);
        }

        final ImageView imageHeadView = helper.getView(R.id.iv_create_homework_divider_group_icon);
        CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext,
                item.getHeadIconUrl(),
                imageHeadView,
                ContextCompat.getDrawable(mContext, R.drawable.avatar_def));
    }

    public void setType(int type) {
        this.mType = type;
    }
}
