package com.histudent.jwsoft.histudent.presenter.homework;

import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.presenter.homework.contract.WorkDetailStudentContract;

import javax.inject.Inject;

/**
 * Created by huyg on 2017/10/25.
 */

public class WorkDetailStudentPresenter extends RxPresenter<WorkDetailStudentContract.View> implements WorkDetailStudentContract.Presenter{

    private ApiFactory mApiFactory;

    @Inject
    public WorkDetailStudentPresenter(ApiFactory mApiFactory) {
        this.mApiFactory = mApiFactory;
    }
}
