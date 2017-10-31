package com.histudent.jwsoft.histudent.adapter.homework;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.bean.homework.CommonMemberBean;
import com.histudent.jwsoft.histudent.bean.homework.CommonSubjectBean;
import com.histudent.jwsoft.histudent.commen.view.IconView;

import java.util.List;

/**
 * Created by lichaojie on 2017/10/25.
 * desc:
 * 科目管理
 */

public class HomeworkSubjectManageAdapter extends BaseQuickAdapter<CommonSubjectBean, BaseViewHolder> {

    private List<CommonSubjectBean> mList;


    public static final HomeworkSubjectManageAdapter create(@LayoutRes int layoutResId, @Nullable List<CommonSubjectBean> data) {
        return new HomeworkSubjectManageAdapter(layoutResId, data);
    }

    private HomeworkSubjectManageAdapter(@LayoutRes int layoutResId, @Nullable List<CommonSubjectBean> data) {
        super(layoutResId, data);
        this.mList = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonSubjectBean item) {
        helper.setText(R.id.tv_subject_manage_name, item.getSubjectName());
        helper.addOnClickListener(R.id.ll_subject_manage_delete)
                .addOnClickListener(R.id.ll_subject_manage_select);

        final IconView selectCheckBox = helper.getView(R.id.iv_subject_manage_select);
        if (item.isCheck()) {
            selectCheckBox.setText(R.string.icon_check);
            selectCheckBox.setTextColor(ContextCompat.getColor(mContext, R.color._28ca7e));
        } else {
            selectCheckBox.setText(R.string.icon_check2);
            selectCheckBox.setTextColor(ContextCompat.getColor(mContext, R.color._cccccc));
        }
        if (item.isShowDeleteIcon()) {
            helper.getView(R.id.ll_subject_manage_delete).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.ll_subject_manage_delete).setVisibility(View.GONE);
        }
        helper.addOnClickListener(R.id.ll_add_layout);
        if (helper.getAdapterPosition() == mList.size() - 1) {
            helper.getView(R.id.view_add_line).setVisibility(View.VISIBLE);
            helper.getView(R.id.ll_add_layout).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.view_add_line).setVisibility(View.GONE);
            helper.getView(R.id.ll_add_layout).setVisibility(View.GONE);
        }
    }
}
