package com.histudent.jwsoft.histudent.presenter.homework;

import com.histudent.jwsoft.histudent.adapter.homework.convert.HomeworkAlreadyConvert;
import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.bean.homework.AlreadyCompleteHomeworkEntity;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkAlreadyBean;
import com.histudent.jwsoft.histudent.manage.ParamsManager;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkAlreadyCompleteContract;
import com.histudent.jwsoft.histudent.rx.RxException;
import com.histudent.jwsoft.histudent.rx.RxSchedulers;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by lichaojie on 2017/11/1.
 * desc:
 * 已完成作业列表
 */

public class WorkAlreadyCompletePresenter extends RxPresenter<WorkAlreadyCompleteContract.View>
        implements WorkAlreadyCompleteContract.Presenter {

    private ApiFactory APIFACTORY;

    @Inject
    public WorkAlreadyCompletePresenter(ApiFactory apiFactory) {
        this.APIFACTORY = apiFactory;
    }


    /**
     * 老师或者学生获取相应作业列表
     */
    @Override
    public void getAlreadyCompleteHomeworkList(Map<String, Object> paramsMap) {

        Disposable disposable = APIFACTORY.getWorkApi().getAlreadyCompleteHomeworkList(paramsMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<AlreadyCompleteHomeworkEntity>>() {
                    @Override
                    public void accept(HttpResponse<AlreadyCompleteHomeworkEntity> response) throws Exception {
                        if (response.isSuccess()) {
                            final AlreadyCompleteHomeworkEntity data = response.getData();
                            final ArrayList<HomeworkAlreadyBean> convertEntity = HomeworkAlreadyConvert.create(data).convertEntity();
                            mView.updateListData(convertEntity);
                        }
                        mView.showContent(response.getMsg());
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.showContent(e.getMessage());
                }));
        addDispose(disposable);
    }


    /**
     * 老师--获取所有作业列表
     */
    @Override
    public void getAlreadyCompleteAllHomeworkList(Map<String, Object> paramsMap) {

        Disposable disposable = APIFACTORY.getWorkApi().getAlreadyCompleteAllHomeworkList(paramsMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<AlreadyCompleteHomeworkEntity>>() {
                    @Override
                    public void accept(HttpResponse<AlreadyCompleteHomeworkEntity> response) throws Exception {
                        if (response.isSuccess()) {
                            final AlreadyCompleteHomeworkEntity data = response.getData();
                            final ArrayList<HomeworkAlreadyBean> convertEntity = HomeworkAlreadyConvert.create(data).convertEntity();
                            mView.updateListData(convertEntity);
                        }
                        mView.showContent(response.getMsg());
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.showContent(e.getMessage());
                }));
        addDispose(disposable);
    }

    @Override
    public void deleteCompleteSpecifiedHomework(String homeworkId) {

        final Map<String, Object> paramsMap = ParamsManager.getInstance()
                .setParams("hwId", homeworkId)
                .getParamsMap();
        Disposable disposable = APIFACTORY.getWorkApi().deleteCompleteSpecifiedHomework(paramsMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse baseHttpResponse) throws Exception {
                        if (baseHttpResponse.isSuccess()) {
                            mView.deleteHomeworkSuccess();
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
