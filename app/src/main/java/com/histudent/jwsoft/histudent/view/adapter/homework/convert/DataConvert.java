package com.histudent.jwsoft.histudent.view.adapter.homework.convert;

import android.text.TextUtils;

import com.histudent.jwsoft.histudent.model.bean.homework.CommonMemberBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichaojie on 2017/10/25.
 * desc:
 */

public abstract class DataConvert {


    protected List<CommonMemberBean> ENTITYS = new ArrayList<>();
    protected String mJsonData = null;

    public abstract List<CommonMemberBean> convert();

    public DataConvert setJsonData(String jsonData) {
        this.mJsonData = jsonData;
        return this;
    }

    protected String getJson() {
        if (TextUtils.isEmpty(mJsonData))
            throw new NullPointerException("DATA IS NULL");
        return mJsonData;
    }
}
