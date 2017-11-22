package com.histudent.jwsoft.histudent.model.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.histudent.jwsoft.histudent.R;
import com.histudent.jwsoft.histudent.view.activity.homework.WorkCreateDivideGroupActivity;
import com.histudent.jwsoft.histudent.view.activity.homework.WorkReceiverPersonDetailActivity;
import com.histudent.jwsoft.histudent.model.bean.homework.HomeworkSelectGroupL0Bean;
import com.histudent.jwsoft.histudent.model.bean.homework.HomeworkSelectGroupL1Bean;
import com.histudent.jwsoft.histudent.model.constant.TransferKeys;
import com.histudent.jwsoft.histudent.tool.CommonAdvanceUtils;

import java.util.List;

/**
 * Created by lichaojie on 2017/10/27.
 * desc:
 */

public class OnItemSelectReceiverPersonListener extends SimpleClickListener {

    private final Context CONTEXT;
    private final List<MultiItemEntity> LIST_DATA;
    private static final String TAG = OnItemSelectReceiverPersonListener.class.getName();

    private OnItemSelectReceiverPersonListener(Context context, List<MultiItemEntity> list) {
        this.CONTEXT = context;
        this.LIST_DATA = list;
    }

    public static final OnItemSelectReceiverPersonListener create(Context context, List<MultiItemEntity> list) {
        return new OnItemSelectReceiverPersonListener(context, list);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        final int id = view.getId();
        switch (id) {
            case R.id.ll_receive_select_class_check:
            case R.id.ll_receive_select_class_sub_group_check:
                solveCheckStatus(adapter, view, position);
                break;
            case R.id.ll_receive_class_look_group_detail:
                solveJumpGroupDetail(adapter, view, position);
                break;
            case R.id.ll_create_divide_group:
            case R.id.ll_head_create_divide_group:
                solveJumpCreateDivideGroup(adapter, view, position);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    public void updateListData(List<MultiItemEntity> listData) {
        LIST_DATA.clear();
        LIST_DATA.addAll(listData);
    }


    /**
     * 处理跳转创建分组页面
     *
     * @param adapter
     * @param view
     * @param position
     */
    private void solveJumpCreateDivideGroup(BaseQuickAdapter adapter, View view, int position) {
        final Intent intent = new Intent(CONTEXT, WorkCreateDivideGroupActivity.class);
        final MultiItemEntity multiItemEntity = LIST_DATA.get(position);
        if (multiItemEntity instanceof HomeworkSelectGroupL1Bean) {
            final HomeworkSelectGroupL1Bean homeworkSelectGroupL1Bean = (HomeworkSelectGroupL1Bean) multiItemEntity;
            intent.putExtra(TransferKeys.CLASS_ID, homeworkSelectGroupL1Bean.getClassId());
            intent.putExtra(TransferKeys.TYPE, 0);//0:代表添加分组类型
        }
        if (multiItemEntity instanceof HomeworkSelectGroupL0Bean) {
            final HomeworkSelectGroupL0Bean homeworkSelectGroupL0Bean = (HomeworkSelectGroupL0Bean) multiItemEntity;
            intent.putExtra(TransferKeys.CLASS_ID, homeworkSelectGroupL0Bean.getClassId());
            intent.putExtra(TransferKeys.TYPE, 0);//0:代表添加分组类型
        }
        CommonAdvanceUtils.startActivity(CONTEXT, intent);
    }

    /**
     * 处理跳转查看组成员页面
     *
     * @param adapter
     * @param view
     * @param position
     */
    private void solveJumpGroupDetail(BaseQuickAdapter adapter, View view, int position) {
        final Intent intent = new Intent(CONTEXT, WorkReceiverPersonDetailActivity.class);
        final MultiItemEntity multiItemEntity = LIST_DATA.get(position);
        if (multiItemEntity instanceof HomeworkSelectGroupL1Bean) {
            final HomeworkSelectGroupL1Bean homeworkSelectGroupL1Bean = (HomeworkSelectGroupL1Bean) multiItemEntity;
            intent.putExtra(TransferKeys.GROUP_ID, homeworkSelectGroupL1Bean.getGroupDivideId());
            intent.putExtra(TransferKeys.CLASS_ID, homeworkSelectGroupL1Bean.getClassId());
        }
        CommonAdvanceUtils.startActivity(CONTEXT, intent);
    }

    /**
     * 处理用户点击选择状态
     *
     * @param adapter
     * @param view
     * @param position
     */
    private void solveCheckStatus(BaseQuickAdapter adapter, View view, int position) {
        final MultiItemEntity multiItemEntity = LIST_DATA.get(position);
        if (multiItemEntity instanceof HomeworkSelectGroupL0Bean) {
            //一级标题
            final HomeworkSelectGroupL0Bean homeworkSelectGroupL0Bean = (HomeworkSelectGroupL0Bean) multiItemEntity;
            final boolean isCheck = homeworkSelectGroupL0Bean.isCheck();
            if (isCheck) {
                homeworkSelectGroupL0Bean.setCheck(false);
            } else {
                //如果用户点击学校班级 并且处理选择状态的话 下面所对应的分组列表内容选择状态均取消(需求定的)
                homeworkSelectGroupL0Bean.setCheck(true);
                final List<HomeworkSelectGroupL1Bean> subItems = homeworkSelectGroupL0Bean.getSubItems();
                if (subItems != null && subItems.size() > 0) {
                    for (HomeworkSelectGroupL1Bean subItem : subItems) {
                        subItem.setCheck(false);
                    }
                }

            }


        } else if (multiItemEntity instanceof HomeworkSelectGroupL1Bean) {
            //二级标题
            final HomeworkSelectGroupL1Bean homeworkSelectGroupL1Bean = (HomeworkSelectGroupL1Bean) multiItemEntity;
            final boolean isCheck = homeworkSelectGroupL1Bean.isCheck();
            if (isCheck) {
                homeworkSelectGroupL1Bean.setCheck(false);
            } else {
                homeworkSelectGroupL1Bean.setCheck(true);
            }
            //如果用户点击的是子条目(作业分组)任何一个并且选中的话 其父标题去掉打勾状态(需求定)
            final String classId = homeworkSelectGroupL1Bean.getClassId();
            for (MultiItemEntity itemEntity : LIST_DATA) {
                if (itemEntity instanceof HomeworkSelectGroupL0Bean) {
                    final HomeworkSelectGroupL0Bean entity = (HomeworkSelectGroupL0Bean) itemEntity;
                    if (classId.equals(entity.getClassId())) {
                        entity.setCheck(false);
                        break;
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
