package com.histudent.jwsoft.histudent.presenter.homework;

import com.histudent.jwsoft.histudent.adapter.homework.convert.HomeworkSubjectDataConvert;
import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.bean.homework.AddHomeworkSubjectEntity;
import com.histudent.jwsoft.histudent.bean.homework.CommonSubjectBean;
import com.histudent.jwsoft.histudent.bean.homework.SubjectEntity;
import com.histudent.jwsoft.histudent.body.message.uikit.infra.Params;
import com.histudent.jwsoft.histudent.constant.ParamKeys;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.manage.UserManager;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkSubjectManageContract;
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

public class WorkSubjectManagePresenter extends RxPresenter<WorkSubjectManageContract.View>
        implements WorkSubjectManageContract.Presenter {

    private final ApiFactory APIFACTORY;

    @Inject
    public WorkSubjectManagePresenter(ApiFactory apiFactory) {
        this.APIFACTORY = apiFactory;
    }


    @Override
    public void getSubjectList() {
        final Map<String, Object> paramsMap = ParamsManager.getInstance()
                .setParams(ParamKeys.CLASS_ID, UserManager.getInstance().getCurrentClassId())
                .getParamsMap();
        Disposable disposable = APIFACTORY.getWorkApi().getAllSubjectsList(paramsMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<List<SubjectEntity>>>() {
                    @Override
                    public void accept(HttpResponse<List<SubjectEntity>> listHttpResponse) throws Exception {
                        if (listHttpResponse.isSuccess()) {
                            final List<SubjectEntity> data = listHttpResponse.getData();
                            final List<CommonSubjectBean> commonSubjectBean = HomeworkSubjectDataConvert.create(data).convertEntity();
                            mView.updateListData(commonSubjectBean);
                        }
                        mView.controlDialogStatus(listHttpResponse.getMsg());
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.controlDialogStatus(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void deleteSpecifiedSubject(String subjectId) {
        final Map<String, Object> paramsMap = ParamsManager.getInstance().setParams(ParamKeys.SUBJECT_ID, subjectId).getParamsMap();
        Disposable disposable = APIFACTORY.getWorkApi().deleteSpecifiedSubject(paramsMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse baseHttpResponse) throws Exception {
                        if (baseHttpResponse.isSuccess()) {
                            mView.deleteSpecifiedSubjectSuccess();
                        }
                        mView.controlDialogStatus(baseHttpResponse.getMsg());
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.controlDialogStatus(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void addSpecifiedSubject(String subjectName) {
        final Map<String, Object> paramsMap = ParamsManager.getInstance()
                .setParams(ParamKeys.SUBJECT_NAME, subjectName)
                .setParams(ParamKeys.CLASS_ID, UserManager.getInstance().getCurrentClassId())
                .getParamsMap();
        Disposable disposable = APIFACTORY.getWorkApi().addSpecifiedSubject(paramsMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<AddHomeworkSubjectEntity>>() {
                    @Override
                    public void accept(HttpResponse<AddHomeworkSubjectEntity> response) throws Exception {
                        if (response.isSuccess()) {
                            mView.addSpecifiedSubjectSuccess(response.getData().getId());
                        }
                        mView.controlDialogStatus(response.getMsg());
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.controlDialogStatus(e.getMessage());
                }));
        addDispose(disposable);
    }
}
