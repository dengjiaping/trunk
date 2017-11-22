package com.histudent.jwsoft.histudent.view.adapter.homework;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.model.bean.homework.HomeworkSelectGroupL0Bean;
import com.histudent.jwsoft.histudent.model.bean.homework.HomeworkSelectGroupL1Bean;
import com.histudent.jwsoft.histudent.commen.utils.imageloader.CommonGlideImageLoader;
import com.histudent.jwsoft.histudent.commen.view.IconView;

import java.util.List;

/**
 * Created by lichaojie on 2017/10/26.
 * desc:
 */

public class HomeworkSelectPersonExpandableAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private final List<MultiItemEntity> LISTDATA;

    public static final HomeworkSelectPersonExpandableAdapter create(List<MultiItemEntity> data) {
        return new HomeworkSelectPersonExpandableAdapter(data);
    }

    private HomeworkSelectPersonExpandableAdapter(List<MultiItemEntity> data) {
        super(data);
        this.LISTDATA = data;
        addItemType(TYPE_LEVEL_0, R.layout.item_homework_select_receiver);
        addItemType(TYPE_LEVEL_1, R.layout.item_homework_select_receiver_group_sub);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        final int itemViewType = helper.getItemViewType();
        switch (itemViewType) {
            case TYPE_LEVEL_0:
                final HomeworkSelectGroupL0Bean entityL0 = (HomeworkSelectGroupL0Bean) item;
                final ImageView classIcon = helper.getView(R.id.iv_receive_select_class_icon);
                CommonGlideImageLoader.getInstance()
                        .displayNetImageWithCircle(mContext, entityL0.getClassThumbIcon(),
                                classIcon, ContextCompat.getDrawable(mContext, R.mipmap.default_class_style));
                helper.setText(R.id.tv_receive_class_name, entityL0.getClassName());
                helper.addOnClickListener(R.id.ll_receive_select_class_check);
                helper.addOnClickListener(R.id.ll_head_create_divide_group);
                final List<HomeworkSelectGroupL1Bean> subItems = entityL0.getSubItems();
                final IconView selectClassCheckBox = helper.getView(R.id.iv_receive_select_class_check);
                //标题0 班级名称 选择状态
                if (entityL0.isCheck()) {
                    selectClassCheckBox.setText(R.string.icon_check);
                    selectClassCheckBox.setTextColor(ContextCompat.getColor(mContext, R.color._28ca7e));
                } else {
                    selectClassCheckBox.setText(R.string.icon_check2);
                    selectClassCheckBox.setTextColor(ContextCompat.getColor(mContext, R.color._cccccc));
                }
                if (subItems == null || subItems.size() == 0) {
                    //如果没有子列表
                    helper.getView(R.id.ll_head_create_divide_group).setVisibility(View.VISIBLE);
                    helper.getView(R.id.view_head_group_line).setVisibility(View.VISIBLE);
                    return;

                } else {
                    helper.getView(R.id.ll_head_create_divide_group).setVisibility(View.GONE);
                    helper.getView(R.id.view_head_group_line).setVisibility(View.GONE);
                }

                final IconView iconView = helper.getView(R.id.iv_receive_class_show);
                helper.getView(R.id.ll_receive_class_expand).setOnClickListener((View view) -> {
                    if (entityL0.isExpandable()) {
                        entityL0.setExpandable(false);
                        collapse(helper.getAdapterPosition());
                        iconView.setText(R.string.icon_down);
                    } else {
                        entityL0.setExpandable(true);
                        expand(helper.getAdapterPosition());
                        iconView.setText(R.string.icon_up);
                    }
                    notifyDataSetChanged();
                });
                break;
            case TYPE_LEVEL_1:
                final HomeworkSelectGroupL1Bean entityL1 = (HomeworkSelectGroupL1Bean) item;
                helper.setText(R.id.tv_receive_class_sub_group_name, entityL1.getGroupDivideName())
                        .setText(R.id.tv_receive_class_sub_group_member_count, entityL1.getGroupMemberCount() + "人");
                final View createGroupLayout = helper.getView(R.id.ll_create_divide_group);
                final View creteGroupLine = helper.getView(R.id.view_create_group_line);
                helper.addOnClickListener(R.id.ll_create_divide_group);
                helper.addOnClickListener(R.id.ll_receive_class_look_group_detail);
                helper.addOnClickListener(R.id.ll_receive_select_class_sub_group_check);
                final IconView selectCheckBox = helper.getView(R.id.iv_receive_select_class_sub_group_check);
                //分组名称选择状态
                if (entityL1.isCheck()) {
                    selectCheckBox.setText(R.string.icon_check);
                    selectCheckBox.setTextColor(ContextCompat.getColor(mContext, R.color._28ca7e));
                } else {
                    selectCheckBox.setText(R.string.icon_check2);
                    selectCheckBox.setTextColor(ContextCompat.getColor(mContext, R.color._cccccc));
                }
                //是否需要点击选择分组
                if (entityL1.isShowCreateGroup()) {
                    creteGroupLine.setVisibility(View.VISIBLE);
                    createGroupLayout.setVisibility(View.VISIBLE);
                } else {
                    creteGroupLine.setVisibility(View.GONE);
                    createGroupLayout.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }
}
