package com.histudent.jwsoft.histudent.presenter.clock;

import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.presenter.clock.contract.AddClockContract;
import com.histudent.jwsoft.histudent.model.rx.RxException;
import com.histudent.jwsoft.histudent.model.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2017/8/18.
 */

public class AddClockPresenter extends RxPresenter<AddClockContract.View> implements AddClockContract.Presenter {


    private ApiFactory mApiFactory;

    @Inject
    public AddClockPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void createClockTask(String classId, String name, String memo, String startTime, String endTime, boolean isEveryDay, String standardDays) {
        Disposable disposable = mApiFactory.getClockApi().createBookTask(classId, name, memo, startTime, endTime, isEveryDay, standardDays)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse baseHttpResponse) throws Exception {
                        if (baseHttpResponse.isSuccess()) {
                            mView.createTaskSuccess();
                        } else {
                            mView.createTaskFailure();
                        }
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.createTaskFailure();
                }));
        addDispose(disposable);
    }

    @Override
    public void editClockTask(String bookTaskId, String name, String memo, String endTime, boolean isEveryDay, String standardDays) {
        Disposable disposable = mApiFactory.getClockApi().editBookTask(bookTaskId, name, memo, endTime, isEveryDay, standardDays)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse baseHttpResponse) throws Exception {
                        if (baseHttpResponse.isSuccess()) {
                            mView.editTaskSuccess();
                        } else {
                            mView.editTaskFailure();
                        }
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.editTaskFailure();
                }));
        addDispose(disposable);
    }
}
