package com.histudent.jwsoft.histudent.presenter.homework.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkSelectGroupL0Bean;

import java.util.List;

/**
 * Created by lichaojie on 2017/11/1.
 * desc:
 */

public class WorkSelectReceiverPersonContract {

    public interface View extends BaseView {
        void updateListData(List<HomeworkSelectGroupL0Bean> list);

        void controlDialogStatus(String message);
    }

    public interface Presenter extends BasePresenter<View> {

        void getSelectReceiverPersonList(String classId);
    }
}
