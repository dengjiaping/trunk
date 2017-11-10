package com.histudent.jwsoft.histudent.presenter.homework.contract;

import android.content.Context;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadyBean;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by lichaojie on 2017/11/1.
 * desc:
 */

public interface WorkAlreadyCompleteContract {

    interface View extends BaseView {

        void controlDialogStatus(String message);

        void updateListData(ArrayList<HomeworkAlreadyBean> convertEntity);

        void deleteHomeworkSuccess();
    }

    interface Presenter extends BasePresenter<View> {

        void getAlreadyCompleteHomeworkList(Context context, Map<String, Object> map);

        void getAlreadyCompleteAllHomeworkList(Context context, Map<String, Object> map);

        void deleteCompleteSpecifiedHomework(String homeworkId);
    }
}
