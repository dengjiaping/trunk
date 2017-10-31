package com.histudent.jwsoft.histudent.presenter.homework;

import com.google.common.annotations.GwtIncompatible;
import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.bean.homework.WorkCompleteBean;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkNoCompleteContract;
import com.histudent.jwsoft.histudent.rx.RxException;
import com.histudent.jwsoft.histudent.rx.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2017/10/25.
 */

public class WorkNoCompletePresenter extends RxPresenter<WorkNoCompleteContract.View> implements WorkNoCompleteContract.Presenter{

    private ApiFactory mApiFactory;

    @Inject
    public WorkNoCompletePresenter(ApiFactory mApiFactory) {
        this.mApiFactory = mApiFactory;
    }



    @Override
    public void getCompleteList(String homeworkId, boolean isComplete, int index, int size) {
        Disposable disposable = mApiFactory.getWorkApi().getCompleteList(homeworkId, isComplete, index, size)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<WorkCompleteBean>>() {
                    @Override
                    public void accept(HttpResponse<WorkCompleteBean> response) throws Exception {
                        if (response.isSuccess()) {
                            WorkCompleteBean workComplete = response.getData();
                            if (workComplete != null) {
                                List<WorkCompleteBean.ItemsBean> itemsBeens= workComplete.getItems();
                                mView.showCompleteList(itemsBeens);
                            }
                        } else {
                            mView.getCompleteListFail();
                        }

                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.getCompleteListFail();
                }));
        addDispose(disposable);
    }

}