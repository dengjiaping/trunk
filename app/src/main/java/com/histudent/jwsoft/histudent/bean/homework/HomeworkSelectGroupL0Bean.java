package com.histudent.jwsoft.histudent.bean.homework;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.histudent.jwsoft.histudent.adapter.homework.HomeworkSelectPersonExpandableAdapter;

import java.io.Serializable;

/**
 * Created by lichaojie on 2017/10/26.
 * desc:
 */

public class HomeworkSelectGroupL0Bean extends AbstractExpandableItem<HomeworkSelectGroupL1Bean>
        implements MultiItemEntity, Serializable {


    private boolean isCheck;
    private String mClassThumbIcon;
    private String mClassName;
    private String mClassId;
    private boolean isExpandable;
    /**
     * 每个班级下对应一个teamId
     */
    private String mTeamId;

    public String getTeamId() {
        return mTeamId;
    }

    public HomeworkSelectGroupL0Bean setTeamId(String teamId) {
        mTeamId = teamId;
        return this;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public HomeworkSelectGroupL0Bean setExpandable(boolean expandable) {
        isExpandable = expandable;
        return this;
    }

    public String getClassId() {
        return mClassId;
    }

    public HomeworkSelectGroupL0Bean setClassId(String classId) {
        mClassId = classId;
        return this;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public HomeworkSelectGroupL0Bean setCheck(boolean check) {
        isCheck = check;
        return this;
    }

    public String getClassThumbIcon() {
        return mClassThumbIcon;
    }

    public HomeworkSelectGroupL0Bean setClassThumbIcon(String classThumbIcon) {
        mClassThumbIcon = classThumbIcon;
        return this;
    }

    public String getClassName() {
        return mClassName;
    }


    public HomeworkSelectGroupL0Bean setClassName(String className) {
        mClassName = className;
        return this;
    }

    @Override
    public int getItemType() {
        return HomeworkSelectPersonExpandableAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
