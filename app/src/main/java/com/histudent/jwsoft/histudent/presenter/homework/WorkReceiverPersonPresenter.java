package com.histudent.jwsoft.histudent.presenter.homework;

import com.histudent.jwsoft.histudent.HiStudentLog;
import com.histudent.jwsoft.histudent.adapter.homework.convert.HomeworkGroupDetailDataConvert;
import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.bean.homework.CommonMemberBean;
import com.histudent.jwsoft.histudent.bean.homework.GroupMemberDetailEntity;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkReceiverPersonContract;
import com.histudent.jwsoft.histudent.rx.RxException;
import com.histudent.jwsoft.histudent.rx.RxSchedulers;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by lichaojie on 2017/10/31.
 * desc:
 * 显示分组成员
 */

public class WorkReceiverPersonPresenter extends RxPresenter<WorkReceiverPersonContract.View>
        implements WorkReceiverPersonContract.Presenter {

    private static final String TAG = WorkReceiverPersonPresenter.class.getName();
    private final ApiFactory API_FACTORY;

    @Inject
    public WorkReceiverPersonPresenter(ApiFactory apiFactory) {
        this.API_FACTORY = apiFactory;
    }

    @Override
    public void getGroupMemberList(String teamId) {
        final Map<String, Object> paramsMap = ParamsManager.getInstance().setParams(ParamKeys.TEAM_ID, teamId).getParamsMap();
        Disposable disposable = API_FACTORY.getWorkApi().getMemberDetailList(paramsMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<GroupMemberDetailEntity>>() {
                    @Override
                    public void accept(HttpResponse<GroupMemberDetailEntity> groupMemberDetailEntityHttpResponse) throws Exception {
                        final GroupMemberDetailEntity data = groupMemberDetailEntityHttpResponse.getData();
                        if (data != null) {
                            final List<CommonMemberBean> convert = HomeworkGroupDetailDataConvert
                                    .create(data)
                                    .convertEntity();
                            mView.updateListData(convert);
                        }
                        mView.controlDialogStatus(null);
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    final String message = e.getMessage();
                    mView.controlDialogStatus(message);
                }));
        addDispose(disposable);
    }

    @Override
    public void deleteGroupName(String teamId) {
        final Map<String, Object> paramsMap = ParamsManager.getInstance().setParams(ParamKeys.TEAM_ID, teamId).getParamsMap();
        Disposable disposable = API_FACTORY.getWorkApi().delMemberDetailList(paramsMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse baseHttpResponse) throws Exception {
                        if (baseHttpResponse.isSuccess()) {
                            final String result = baseHttpResponse.getMsg();
                            HiStudentLog.i(TAG, "onSuccess: result-->" + result);
                            mView.deleteGroupListComplete();
                        } else {
                            HiStudentLog.i(TAG, "failure");
                        }
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                }));
        addDispose(disposable);
    }
}
