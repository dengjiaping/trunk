package com.histudent.jwsoft.histudent.presenter.homework;

import com.histudent.jwsoft.histudent.adapter.homework.convert.HomeworkGroupMemberDataConvert;
import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkSelectGroupL0Bean;
import com.histudent.jwsoft.histudent.bean.homework.SelectReceiverPersonEntity;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkSelectReceiverPersonContract;
import com.histudent.jwsoft.histudent.rx.RxException;
import com.histudent.jwsoft.histudent.rx.RxSchedulers;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by lichaojie on 2017/11/1.
 * desc:
 */

public class WorkSelectReceiverPersonPresenter extends RxPresenter<WorkSelectReceiverPersonContract.View>
        implements WorkSelectReceiverPersonContract.Presenter {

    private final ApiFactory APIFACTORY;

    @Inject
    public WorkSelectReceiverPersonPresenter(ApiFactory apiFactory) {
        this.APIFACTORY = apiFactory;
    }

    @Override
    public void getSelectReceiverPersonList(String classId) {
        final Map<String, Object> paramsMap = ParamsManager.getInstance()
                .setParams(ParamKeys.CLASS_ID, "8d3c244c-065a-4bfa-b67e-82949c11760d")
//                .setParams(ParamKeys.CLASS_ID, classId)
                .getParamsMap();
        Disposable disposable = APIFACTORY.getWorkApi().getSelectReceiverList(paramsMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<SelectReceiverPersonEntity>>() {
                    @Override
                    public void accept(HttpResponse<SelectReceiverPersonEntity> response) throws Exception {
                        if(response.isSuccess()){
                            final SelectReceiverPersonEntity data = response.getData();
                            final List<HomeworkSelectGroupL0Bean> homeworkSelectGroupL0Been =
                                    HomeworkGroupMemberDataConvert.create(data).convertEntity();
                            mView.updateListData(homeworkSelectGroupL0Been);
                        }
                        mView.controlDialogStatus(null);
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.controlDialogStatus(e.getMessage());
                }));
        addDispose(disposable);
    }
}
