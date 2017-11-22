package com.histudent.jwsoft.histudent.presenter.homework.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.model.bean.homework.CommonMemberBean;

import java.util.List;

/**
 * Created by lichaojie on 2017/11/1.
 * desc:
 */

public interface WorkCreateDivideGroupContract {


    interface View extends BaseView {

        void controlDialogStatus(String message);

        void updateListData(List<CommonMemberBean> list);

        void addGroupInformationSuccess(String teamId);

        void modifyGroupInformationSuccess();
    }


    interface Presenter extends BasePresenter<View> {

        void getGroupMemberList(String teamId);

        void modifyGroupInformation(String groupName, String teamId, String studentId);

        void addGroupInformation(String groupName, String classId, String studentId);
    }
}
