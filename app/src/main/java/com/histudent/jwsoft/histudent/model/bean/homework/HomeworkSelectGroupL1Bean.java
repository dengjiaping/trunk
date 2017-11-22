package com.histudent.jwsoft.histudent.model.bean.homework;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.histudent.jwsoft.histudent.view.adapter.homework.HomeworkSelectPersonExpandableAdapter;

import java.io.Serializable;

/**
 * Created by lichaojie on 2017/10/26.
 * desc:
 * 选择分组下的每一个条目(包含分组名称及人数)
 */

public class HomeworkSelectGroupL1Bean implements MultiItemEntity, Serializable {
    private boolean isCheck;
    private String mGroupDivideName;
    private int mGroupMemberCount;
    //根据此标记去判断是否显示子标题中的创建分组
    private boolean isShowCreateGroup;
    private String mMemberThumbIcon;

    private String mClassId;
    private String mClassName;
    private String mGroupDivideId;

    private String mGradeName;

    public String getGradeName() {
        return mGradeName;
    }

    public HomeworkSelectGroupL1Bean setGradeName(String gradeName) {
        mGradeName = gradeName;
        return this;
    }

    public String getMemberThumbIcon() {
        return mMemberThumbIcon;
    }

    public HomeworkSelectGroupL1Bean setMemberThumbIcon(String memberThumbIcon) {
        mMemberThumbIcon = memberThumbIcon;
        return this;
    }

    public String getClassId() {
        return mClassId;
    }

    public HomeworkSelectGroupL1Bean setClassId(String classId) {
        mClassId = classId;
        return this;
    }

    public String getClassName() {
        return mClassName;
    }

    public HomeworkSelectGroupL1Bean setClassName(String className) {
        mClassName = className;
        return this;
    }

    public String getGroupDivideId() {
        return mGroupDivideId;
    }

    public HomeworkSelectGroupL1Bean setGroupDivideId(String groupDivideId) {
        mGroupDivideId = groupDivideId;
        return this;
    }

    public boolean isShowCreateGroup() {
        return isShowCreateGroup;
    }

    public HomeworkSelectGroupL1Bean setShowCreateGroup(boolean showCreateGroup) {
        isShowCreateGroup = showCreateGroup;
        return this;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public HomeworkSelectGroupL1Bean setCheck(boolean check) {
        isCheck = check;
        return this;
    }

    public String getGroupDivideName() {
        return mGroupDivideName;
    }

    public HomeworkSelectGroupL1Bean setGroupDivideName(String groupDivideName) {
        mGroupDivideName = groupDivideName;
        return this;
    }

    public int getGroupMemberCount() {
        return mGroupMemberCount;
    }

    public HomeworkSelectGroupL1Bean setGroupMemberCount(int groupMemberCount) {
        mGroupMemberCount = groupMemberCount;
        return this;
    }

    @Override
    public int getItemType() {
        return HomeworkSelectPersonExpandableAdapter.TYPE_LEVEL_1;
    }
}
