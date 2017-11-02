package com.histudent.jwsoft.histudent.presenter.homework.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.bean.homework.CommonMemberBean;

import java.util.List;

/**
 * Created by lichaojie on 2017/10/31.
 * desc:
 */

public interface WorkAddMemberContract {

    interface View extends BaseView {
        void updateListData(List<CommonMemberBean> commonMemberBean);

        void controlDialogStatus(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void getGroupMemberList(String classId);
    }
}
