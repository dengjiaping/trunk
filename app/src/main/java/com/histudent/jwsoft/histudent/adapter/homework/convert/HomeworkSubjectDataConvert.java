package com.histudent.jwsoft.histudent.adapter.homework.convert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.bean.homework.CommonSubjectBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadyBean;
import com.histudent.jwsoft.histudent.bean.homework.SubjectEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichaojie on 2017/10/30.
 * desc:
 */

public class HomeworkSubjectDataConvert {

    private String mResponse;
    private final List<SubjectEntity> LIST = new ArrayList<>();

    private HomeworkSubjectDataConvert(String response) {
        this.mResponse = response;
    }

    private HomeworkSubjectDataConvert(List<SubjectEntity> list) {
        LIST.clear();
        this.LIST.addAll(list);
    }

    public static final HomeworkSubjectDataConvert create(String response) {
        return new HomeworkSubjectDataConvert(response);
    }

    public static final HomeworkSubjectDataConvert create(List<SubjectEntity> list) {
        return new HomeworkSubjectDataConvert(list);
    }

    public List<CommonSubjectBean> convert() {
        final JSONArray jsonArray = JSONObject.parseArray(mResponse);
        final int size = jsonArray.size();
        final List<CommonSubjectBean> listData = new ArrayList<>();
        if (size > 0) {
            CommonSubjectBean commonSubjectBean;
            for (int i = 0; i < size; i++) {
                commonSubjectBean = new CommonSubjectBean();
                final JSONObject jsonObject = jsonArray.getJSONObject(i);
                final String subjectId = jsonObject.getString("subjectId");
                final String name = jsonObject.getString("name");
                commonSubjectBean.setSubjectName(name)
                        .setShowDeleteIcon(true)
                        .setSubjectId(subjectId);
                if (name.equals("语文")
                        || name.equals("英语")
                        || name.equals("外语")
                        || name.equals("科学")
                        || name.equals("数学")) {
                    commonSubjectBean.setShowDeleteIcon(false);
                }
                listData.add(commonSubjectBean);
            }
        }
        return listData;
    }

    public List<CommonSubjectBean> convertEntity() {
        final List<CommonSubjectBean> list = new ArrayList<>();
        CommonSubjectBean commonSubjectBean;
        if (LIST.size() > 0) {
            for (SubjectEntity bean : LIST) {
                commonSubjectBean = new CommonSubjectBean();
                final String subjectId = bean.getSubjectId();
                final String subjectName = bean.getName();

                commonSubjectBean.setSubjectName(subjectName)
                        .setShowDeleteIcon(true)
                        .setSubjectId(subjectId);
                if (subjectName.equals("语文")
                        || subjectName.equals("英语")
                        || subjectName.equals("外语")
                        || subjectName.equals("科学")
                        || subjectName.equals("数学")) {
                    commonSubjectBean.setShowDeleteIcon(false);
                }
                list.add(commonSubjectBean);
            }
        }
        return list;
    }
}
