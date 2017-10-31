package com.histudent.jwsoft.histudent.presenter.homework.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.bean.homework.WorkCompleteBean;

import java.util.List;

/**
 * Created by huyg on 2017/10/25.
 */

public interface WorkNoCompleteContract {

    interface View extends BaseView {
        void showCompleteList(List<WorkCompleteBean.ItemsBean> itemsBeen);
        void getCompleteListFail();
    }

    interface Presenter extends BasePresenter<View> {
        void getCompleteList(String homeworkId,boolean isComplete, int index, int size);
    }


}
