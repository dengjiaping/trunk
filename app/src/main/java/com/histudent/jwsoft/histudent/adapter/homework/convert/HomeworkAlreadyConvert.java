package com.histudent.jwsoft.histudent.adapter.homework.convert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadyBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadySubBean;

import java.util.ArrayList;

/**
 * Created by lichaojie on 2017/10/26.
 * desc:
 * 老师或者学生的作业数据转换
 */

public class HomeworkAlreadyConvert {

    private final String RESPONSE;

    public static final HomeworkAlreadyConvert create(String response) {
        return new HomeworkAlreadyConvert(response);
    }

    private HomeworkAlreadyConvert(String response) {
        this.RESPONSE = response;
    }

    public ArrayList<HomeworkAlreadyBean> convert() {
        final ArrayList<HomeworkAlreadyBean> list = new ArrayList<>();
        final JSONObject jsonObject = JSONObject.parseObject(RESPONSE);
        final JSONArray itemsJsonArray = jsonObject.getJSONArray("items");
        final int size = itemsJsonArray.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                final JSONObject subJsonObject = itemsJsonArray.getJSONObject(i);
                final String timeStr = subJsonObject.getString("timeStr");
                final String weekStr = subJsonObject.getString("weekStr");
                final HomeworkAlreadyBean homeworkAlreadyBean = new HomeworkAlreadyBean(true, timeStr + " " + weekStr);
                list.add(homeworkAlreadyBean);
                //获取该时间下的所有作业信息
                final JSONArray workList = subJsonObject.getJSONArray("workList");
                final int subSize = workList.size();
                if (subSize > 0) {
                    for (int j = 0; j < subSize; j++) {
                        final HomeworkAlreadySubBean homeworkAlreadySubBean = new HomeworkAlreadySubBean();
                        final JSONObject itemBean = workList.getJSONObject(j);
                        final String subjectName = itemBean.getString("subjectName");
                        final String groupName = itemBean.getString("groupName");
                        final String contents = itemBean.getString("contents");
                        final String ownerName = itemBean.getString("ownerName");
                        final String ownerId = itemBean.getString("ownerId");
                        final String logo = itemBean.getString("logo");
                        final String id = itemBean.getString("id");
                        homeworkAlreadySubBean.setHomeWorkName(subjectName)
                                .setHomeworkContent(contents)
                                .setGroupName(groupName)
                                .setPublishOwner(ownerName)
                                .setThumb(logo)
                                .setOwnerId(ownerId)
                                .setId(id);
                        final HomeworkAlreadyBean subHomeAlreadyBean = new HomeworkAlreadyBean(homeworkAlreadySubBean);
                        list.add(subHomeAlreadyBean);
                    }
                }
            }
        }
        return list;
    }
}
