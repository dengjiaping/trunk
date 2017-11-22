package com.histudent.jwsoft.histudent.view.adapter.homework.convert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.model.bean.homework.CommonMemberBean;
import com.histudent.jwsoft.histudent.model.bean.homework.GroupMemberDetailEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichaojie on 2017/10/27.
 * desc:
 */

public class HomeworkGroupDetailDataConvert {

    private String mResponse;
    private GroupMemberDetailEntity mGroupMemberDetailEntity;

    private HomeworkGroupDetailDataConvert(String response) {
        this.mResponse = response;
    }

    private HomeworkGroupDetailDataConvert(GroupMemberDetailEntity groupMemberDetailEntity) {
        this.mGroupMemberDetailEntity = groupMemberDetailEntity;
    }

    public static final HomeworkGroupDetailDataConvert create(String response) {
        return new HomeworkGroupDetailDataConvert(response);
    }

    public static final HomeworkGroupDetailDataConvert create(GroupMemberDetailEntity groupMemberDetailEntity) {
        return new HomeworkGroupDetailDataConvert(groupMemberDetailEntity);
    }

    public List<CommonMemberBean> convert() {
        final JSONObject jsonObject = JSONObject.parseObject(mResponse);
        final List<CommonMemberBean> listData = new ArrayList<>();
        final String teamName = jsonObject.getString("teamName");
        final JSONArray memberList = jsonObject.getJSONArray("memberList");
        final int size = memberList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                final CommonMemberBean commonMemberBean = new CommonMemberBean();
                final JSONObject itemJsonObject = memberList.getJSONObject(i);
                final String avatar = itemJsonObject.getString("avatar");
                final String realName = itemJsonObject.getString("realName");
                final String teamMemberId = itemJsonObject.getString("teamMemberId");
                final String userId = itemJsonObject.getString("userId");
                listData.add(commonMemberBean
                        .setTeamName(teamName)
                        .setHeadIconUrl(avatar)
                        .setName(realName)
                        .setTeamMemberId(teamMemberId)
                        .setUserId(userId));
            }
        }
        return listData;
    }


    public List<CommonMemberBean> convertEntity() {

        final List<CommonMemberBean> listData = new ArrayList<>();
        CommonMemberBean commonMemberBean;
        final List<GroupMemberDetailEntity.SubMemberEntity> memberList = mGroupMemberDetailEntity.getMemberList();
        final String teamName = mGroupMemberDetailEntity.getTeamName();
        for (GroupMemberDetailEntity.SubMemberEntity subMemberEntity : memberList) {
            commonMemberBean = new CommonMemberBean();
            final String avatar = subMemberEntity.getAvatar();
            final String realName = subMemberEntity.getRealName();
            final String teamMemberId = subMemberEntity.getTeamMemberId();
            final String userId = subMemberEntity.getUserId();
            commonMemberBean
                    .setTeamName(teamName)
                    .setHeadIconUrl(avatar)
                    .setName(realName)
                    .setTeamMemberId(teamMemberId)
                    .setUserId(userId);
            listData.add(commonMemberBean);
        }
        return listData;
    }

}
