package com.histudent.jwsoft.histudent.adapter.homework.convert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.histudent.jwsoft.histudent.bean.homework.CommonSubjectBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichaojie on 2017/10/30.
 * desc:
 */

public class HomeworkSubjectDataConvert {

    private final String mResponse;

    private HomeworkSubjectDataConvert(String response) {
        this.mResponse = response;
    }

    public static final HomeworkSubjectDataConvert create(String response) {
        return new HomeworkSubjectDataConvert(response);
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
}
