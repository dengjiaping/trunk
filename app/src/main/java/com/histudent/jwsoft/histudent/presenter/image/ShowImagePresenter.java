package com.histudent.jwsoft.histudent.presenter.image;

import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.bean.ImageInfoBean;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.presenter.image.contract.ShowImageContract;
import com.histudent.jwsoft.histudent.rx.RxException;
import com.histudent.jwsoft.histudent.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2017/9/12.
 */

public class ShowImagePresenter extends RxPresenter<ShowImageContract.View> implements ShowImageContract.Presenter{

    private ApiFactory mApiFactory;

    @Inject
    public ShowImagePresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }


    @Override
    public void praiseImg(boolean doOrUndo, int objectType, String objectId) {
        Disposable disposable = mApiFactory.getSnsApi().praise(doOrUndo,objectType,objectId)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<BaseHttpResponse>() {
                    @Override
                    public void accept(BaseHttpResponse baseHttpResponse) throws Exception {
                        if (baseHttpResponse.isSuccess()) {
                            mView.getPhotoInfo();
                        } else {

                        }
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                }));
        addDispose(disposable);
    }

    @Override
    public void getImgPraiseInfo(int objectType, String objectId) {
        Disposable disposable = mApiFactory.getSnsApi().objectPraiseAndComment(objectType,objectId)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<ImageInfoBean>>() {
                    @Override
                    public void accept(HttpResponse<ImageInfoBean> response) throws Exception {
                        if (response.isSuccess()) {
                            ImageInfoBean imageInfoBean=response.getData();
                            if (imageInfoBean!=null){
                                mView.showPraiseInfo(imageInfoBean);
                            }


                        } else {
                        }
                    }
                }, new RxException<>(e -> {
                    e.printStackTrace();
                }));
        addDispose(disposable);
    }
}
