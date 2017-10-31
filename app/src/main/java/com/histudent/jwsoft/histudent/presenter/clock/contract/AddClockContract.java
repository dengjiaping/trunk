package com.histudent.jwsoft.histudent.presenter.clock.contract;


import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;


/**
 * Created by huyg on 2017/8/18.
 */

public interface AddClockContract {


    interface View extends BaseView{
        void createTaskSuccess();
        void createTaskFailure();
        void editTaskSuccess();
        void editTaskFailure();
    }

    interface Presenter extends BasePresenter<View> {
        void createClockTask(String classId,String name,String memo,String startTime, String endTime, boolean isEveryDay,String standardDays);
        void editClockTask(String bookTaskId,String name,String memo, String endTime, boolean isEveryDay,String standardDays);
    }

}
