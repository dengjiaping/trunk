package com.histudent.jwsoft.histudent.presenter.image;

import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.bean.AlbumBean;
import com.histudent.jwsoft.histudent.body.mine.model.AlbumInfoModel;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.histudent.jwsoft.histudent.presenter.image.contract.AlbumContract;
import com.histudent.jwsoft.histudent.rx.RxException;
import com.histudent.jwsoft.histudent.rx.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2017/9/28.
 */

public class AlbumPresenter extends RxPresenter<AlbumContract.View> implements AlbumContract.Presenter{

    private ApiFactory mApiFactory;

    @Inject
    public AlbumPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getAlbumList(String ownerId, int ownerType, String categoryId, int pageIndex, int pageSize) {
        Disposable disposable = mApiFactory.getAlbumApi().getAlbumList(ownerId,ownerType,categoryId,pageIndex,pageSize)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<HttpResponse<AlbumBean>>() {
                    @Override
                    public void accept(HttpResponse<AlbumBean> response) throws Exception {
                        if (response.isSuccess()) {
                            AlbumBean albumBean=response.getData();
                            if (albumBean!=null){
                                mView.showAlbumList(albumBean.getItems());
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
