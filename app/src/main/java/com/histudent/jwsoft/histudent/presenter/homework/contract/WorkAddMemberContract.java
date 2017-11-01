package com.histudent.jwsoft.histudent.presenter.homework.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.bean.homework.CommonMemberBean;

import java.util.List;

/**
 * Created by lichaojie on 2017/10/31.
 * desc:
 */

public class WorkAddMemberContract {

    public interface View extends BaseView {
        void updateListData(List<CommonMemberBean> commonMemberBean);

        void controlDialogStatus(String message);
    }

    public interface Presenter extends BasePresenter<View> {
        void getGroupMemberList(String classId);
    }
}
