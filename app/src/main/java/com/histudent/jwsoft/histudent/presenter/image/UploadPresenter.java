package com.histudent.jwsoft.histudent.presenter.image;

import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.model.bean.UploadImgBean;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.presenter.image.contract.UploadContract;
import com.histudent.jwsoft.histudent.model.rx.RxException;
import com.histudent.jwsoft.histudent.model.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2017/9/28.
 */

public class UploadPresenter extends RxPresenter<UploadContract.View> implements UploadContract.Presenter {

    private ApiFactory mApiFactory;

    @Inject
    public UploadPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void getRecentlyList(String ownerId, int ownerType, int pageIndex, int pageSize) {
        Disposable disposable = mApiFactory.getPhotoApi().recentlyList(ownerId, ownerType, pageIndex, pageSize)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<UploadImgBean>>() {
                    @Override
                    public void accept(HttpResponse<UploadImgBean> response) throws Exception {
                        if (response.isSuccess()) {
                            UploadImgBean uploadImgBean = response.getData();
                            if (uploadImgBean != null) {
                                mView.showRecentlyList(uploadImgBean.getItems());
                            }

                        } else {
                            mView.requestError();
                        }
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                    mView.requestError();
                }));
        addDispose(disposable);
    }
}
