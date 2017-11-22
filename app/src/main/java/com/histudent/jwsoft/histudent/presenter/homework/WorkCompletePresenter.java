package com.histudent.jwsoft.histudent.presenter.homework;

import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.model.bean.homework.WorkCompleteBean;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkCompleteContract;
import com.histudent.jwsoft.histudent.model.rx.RxException;
import com.histudent.jwsoft.histudent.model.rx.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2017/10/25.
 */

public class WorkCompletePresenter extends RxPresenter<WorkCompleteContract.View> implements WorkCompleteContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public WorkCompletePresenter(ApiFactory mApiFactory) {
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
                                List<WorkCompleteBean.ItemsBean> itemsBeens = workComplete.getItems();

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
