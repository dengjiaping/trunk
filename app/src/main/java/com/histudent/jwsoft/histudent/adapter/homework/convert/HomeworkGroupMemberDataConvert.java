package com.histudent.jwsoft.histudent.adapter.homework.convert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkSelectGroupL0Bean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkSelectGroupL1Bean;
import com.histudent.jwsoft.histudent.bean.homework.SelectReceiverPersonEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichaojie on 2017/10/27.
 * desc:
 * 小组名称列表
 */

public class HomeworkGroupMemberDataConvert {

    private String RESPONSE;
    private SelectReceiverPersonEntity mReceiverPersonEntity;

    private HomeworkGroupMemberDataConvert(String response) {
        this.RESPONSE = response;
    }

    private HomeworkGroupMemberDataConvert(SelectReceiverPersonEntity entity) {
        this.mReceiverPersonEntity = entity;
    }

    public static final HomeworkGroupMemberDataConvert create(String response) {
        return new HomeworkGroupMemberDataConvert(response);
    }

    public static final HomeworkGroupMemberDataConvert create(SelectReceiverPersonEntity entity) {
        return new HomeworkGroupMemberDataConvert(entity);
    }

    public List<HomeworkSelectGroupL0Bean> convert() {
        final List<HomeworkSelectGroupL0Bean> dataList = new ArrayList<>();
        final JSONObject jsonObject = JSONObject.parseObject(RESPONSE);
        final JSONArray teamsJsonArray = jsonObject.getJSONArray("teams");
        final int size = teamsJsonArray.size();
        for (int i = 0; i < size; i++) {
            final HomeworkSelectGroupL0Bean groupL0Bean = new HomeworkSelectGroupL0Bean();
            final JSONObject itemJsonObject = teamsJsonArray.getJSONObject(i);
            final String classFullName = itemJsonObject.getString("classFullName");
            final String classId = itemJsonObject.getString("classId");
            final String classCoverImg = itemJsonObject.getString("classCoverImg");
            final String headTeamId = itemJsonObject.getString("teamId");
            groupL0Bean.setClassThumbIcon(classCoverImg)
                    .setClassId(classId)
                    .setExpandable(true)
                    .setTeamId(headTeamId)
                    .setClassName(classFullName);

            final JSONArray teamList = itemJsonObject.getJSONArray("teamList");
            final int subSize = teamList.size();
            for (int j = 0; j < subSize; j++) {
                final JSONObject subItemObject = teamList.getJSONObject(j);
                final HomeworkSelectGroupL1Bean groupL1Bean = new HomeworkSelectGroupL1Bean();
                final String groupName = subItemObject.getString("name");
                final int studentNum = subItemObject.getInteger("studentNum");
                final String teamId = subItemObject.getString("teamId");
                groupL1Bean.setClassId(classId)
                        .setClassName(classFullName)
                        .setGroupDivideName(groupName)
                        .setGroupMemberCount(studentNum)
                        .setGroupDivideId(teamId)
                        .setShowCreateGroup(false);
                if (j == subSize - 1)
                    groupL1Bean.setShowCreateGroup(true);
                groupL0Bean.addSubItem(groupL1Bean);
            }
            dataList.add(groupL0Bean);
        }
        return dataList;
    }


    public List<HomeworkSelectGroupL0Bean> convertEntity() {
        final List<HomeworkSelectGroupL0Bean> dataList = new ArrayList<>();
        final List<SelectReceiverPersonEntity.ReceiverClass> teams = mReceiverPersonEntity.getTeams();
        if (teams.size() > 0) {
            for (SelectReceiverPersonEntity.ReceiverClass team : teams) {
                final String classCoverImg = team.getClassCoverImg();
                final String classFullName = team.getClassFullName();
                final String classId = team.getClassId();
                final String teamId = team.getTeamId();
                final List<SelectReceiverPersonEntity.ReceiverClass.ReceiverClassGroupPerson> teamList = team.getTeamList();

                //transfer entity
                final HomeworkSelectGroupL0Bean groupL0Bean = new HomeworkSelectGroupL0Bean();
                groupL0Bean.setClassThumbIcon(classCoverImg)
                        .setClassId(classId)
                        .setExpandable(true)
                        .setTeamId(teamId)
                        .setClassName(classFullName);

                if (teamList.size() > 0) {
                    final int subSize = teamList.size();
                    for (int i = 0; i < teamList.size(); i++) {
                        final SelectReceiverPersonEntity.ReceiverClass.ReceiverClassGroupPerson subGroupPerson = teamList.get(i);
                        final String name = subGroupPerson.getName();
                        final int studentNum = subGroupPerson.getStudentNum();
                        final String subTeamId = subGroupPerson.getTeamId();
                        //transfer sub entity
                        final HomeworkSelectGroupL1Bean groupL1Bean = new HomeworkSelectGroupL1Bean();
                        groupL1Bean.setClassId(classId)
                                .setClassName(classFullName)
                                .setGroupDivideName(name)
                                .setGroupMemberCount(studentNum)
                                .setGroupDivideId(subTeamId)
                                .setShowCreateGroup(false);
                        if (i == subSize - 1)
                            groupL1Bean.setShowCreateGroup(true);
                        groupL0Bean.addSubItem(groupL1Bean);
                    }

                }
                dataList.add(groupL0Bean);
            }
        }

        return dataList;
    }

}
