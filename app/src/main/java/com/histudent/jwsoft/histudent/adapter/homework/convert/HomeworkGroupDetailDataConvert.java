package com.histudent.jwsoft.histudent.adapter.homework.convert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.bean.homework.CommonMemberBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichaojie on 2017/10/27.
 * desc:
 */

public class HomeworkGroupDetailDataConvert {

    private final String RESPONSE;

    private HomeworkGroupDetailDataConvert(String response) {
        this.RESPONSE = response;
    }

    public static final HomeworkGroupDetailDataConvert create(String response) {
        return new HomeworkGroupDetailDataConvert(response);
    }

    public List<CommonMemberBean> convert() {
        final JSONObject jsonObject = JSONObject.parseObject(RESPONSE);
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

}
