package com.histudent.jwsoft.histudent.presenter.main;

import com.google.gson.Gson;
import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.commen.cache.HiCache;
import com.histudent.jwsoft.histudent.commen.url.HistudentUrl;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.model.bean.main.HomeEntity;
import com.histudent.jwsoft.histudent.model.bean.main.HomePageEntity;
import com.histudent.jwsoft.histudent.model.constant.NetUrl;
import com.histudent.jwsoft.histudent.model.convert.main.HomeFragmentDataConvert;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.model.manage.ParamsManager;
import com.histudent.jwsoft.histudent.model.rx.RxException;
import com.histudent.jwsoft.histudent.model.rx.RxSchedulers;
import com.histudent.jwsoft.histudent.presenter.main.contract.HomeFragmentContract;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by lichaojie on 2017/11/13.
 * desc:
 */

public class HomeFragmentPresenter extends RxPresenter<HomeFragmentContract.View> implements HomeFragmentContract.Presenter {

    private ApiFactory mApiFactory;


    @Inject
    public HomeFragmentPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void requestHomeListData(AddressInforBean addressInformation) {
        final Map<String, Object> paramsMap = ParamsManager.getInstance()
                .setParams("city", addressInformation.getCity())
                .setParams("quName", addressInformation.getAreaStr())
                .setParams("slideShowCategory", 4)
                .setParams("rcPageSize", 10)
                .setParams("ruPageSize", 15)
                .setParams("rtPageSize", 20)
                .setParams("raPageSize", 15)
                .getParamsMap();

        final Disposable disposable = mApiFactory.getHomeAPi().requestHomeListData(NetUrl.Api.HOME_LIST, paramsMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<HomePageEntity>>() {
                    @Override
                    public void accept(HttpResponse<HomePageEntity> response) throws Exception {
                        if (response.isSuccess()) {
                            final HomePageEntity homePageEntity = response.getData();
                            final List<HomeEntity> homeEntities = HomeFragmentDataConvert.create(homePageEntity).convert();
                            if (homeEntities != null) {
                                HiCache.getInstance().saveHttpDataIntoDB(HistudentUrl.homePageInfo_url, "", new Gson().toJson(homeEntities));
                            }
                            mView.responseSuccess(homeEntities);
                        }
                        mView.showContent(response.getMsg());
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.showContent(e.getMessage());
                }));
        addDispose(disposable);
    }
}
