package com.histudent.jwsoft.histudent.adapter.homework.convert;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by lichaojie on 2017/10/26.
 * desc:
 */

public abstract class HomeworkDataConvert<T> {

    public final ArrayList<T> ENTITIES = new ArrayList<>();
    protected String mJsonData = null;

    protected HomeworkDataConvert setJsonData(String jsonData) {
        this.mJsonData = jsonData;
        return this;
    }

    public abstract void convert();

    protected String getJson() {
        if (TextUtils.isEmpty(mJsonData))
            throw new NullPointerException("DATA IS NULL");
        return mJsonData;
    }
}
