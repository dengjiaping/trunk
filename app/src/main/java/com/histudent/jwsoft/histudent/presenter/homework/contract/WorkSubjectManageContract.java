package com.histudent.jwsoft.histudent.presenter.homework.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.model.bean.homework.CommonSubjectBean;

import java.util.List;

/**
 * Created by lichaojie on 2017/11/1.
 * desc:
 */

public interface WorkSubjectManageContract {

    interface View extends BaseView {
        void controlDialogStatus(String message);

        void updateListData(List<CommonSubjectBean> commonSubjectBean);

        void deleteSpecifiedSubjectSuccess();

        void addSpecifiedSubjectSuccess(String subjectId);
    }

    interface Presenter extends BasePresenter<View> {
        void getSubjectList();

        void deleteSpecifiedSubject(String subjectId);

        void addSpecifiedSubject(String subjectName);
    }
}
