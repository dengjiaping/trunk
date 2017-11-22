package com.histudent.jwsoft.histudent.view.adapter.homework;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.model.bean.homework.CommonMemberBean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;

import java.util.List;

/**
 * Created by lichaojie on 2017/10/25.
 * desc:
 * 添加成员列表
 */

public class HomeworkAddMemberAdapter extends BaseQuickAdapter<CommonMemberBean, BaseViewHolder> {


    public static final HomeworkAddMemberAdapter create(@LayoutRes int layoutResId, @Nullable List<CommonMemberBean> data) {
        return new HomeworkAddMemberAdapter(layoutResId, data);
    }

    private HomeworkAddMemberAdapter(@LayoutRes int layoutResId, @Nullable List<CommonMemberBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonMemberBean item) {
        final IconView selectCheckBox = helper.getView(R.id.iv_select);
        if (item.isCheck()) {
            selectCheckBox.setText(R.string.icon_check);
            selectCheckBox.setTextColor(ContextCompat.getColor(mContext, R.color._28ca7e));
        } else {
            selectCheckBox.setText(R.string.icon_check2);
            selectCheckBox.setTextColor(ContextCompat.getColor(mContext, R.color._cccccc));
        }
        helper.setText(R.id.tv_select_name, item.getName());
        helper.addOnClickListener(R.id.ll_add_member_search_select);
        final ImageView iconHead = helper.getView(R.id.iv_select_head_icon);
        CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext, item.getHeadIconUrl(), iconHead);
    }
}
