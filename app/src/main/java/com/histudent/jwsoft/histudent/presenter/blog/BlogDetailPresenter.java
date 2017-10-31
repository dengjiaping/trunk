package com.histudent.jwsoft.histudent.presenter.blog;

import com.histudent.jwsoft.histudent.base.RxPresenter;
import com.histudent.jwsoft.histudent.bean.BlogDetailBean;
import com.histudent.jwsoft.histudent.body.homepage.bean.HomePageAllBean;
import com.histudent.jwsoft.histudent.model.http.ApiFactory;
import com.histudent.jwsoft.histudent.presenter.blog.contract.BlogDetailContract;
import com.histudent.jwsoft.histudent.rx.RxException;
import com.histudent.jwsoft.histudent.rx.RxResult;
import com.histudent.jwsoft.histudent.rx.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by huyg on 2017/9/11.
 */

public class BlogDetailPresenter extends RxPresenter<BlogDetailContract.View> implements BlogDetailContract.Presenter {
    private ApiFactory mApiFactory;

    @Inject
    public BlogDetailPresenter(ApiFactory apiFactory) {
        this.mApiFactory = apiFactory;
    }

    @Override
    public void getBlogDetail(String actId) {
        Disposable disposable = mApiFactory.getBlogApi().getBlogDetail(actId)
                .compose(RxSchedulers.io_main())
                .compose(RxResult.handleResult())
                .subscribe(new Consumer<BlogDetailBean>() {
                    @Override
                    public void accept(BlogDetailBean blogDetailBean) throws Exception {
                        String content = blogDetailBean.getContent();
                        mView.showBlogDetail(content);
                    }
                }, new RxException<>(e -> e.printStackTrace()));
        addDispose(disposable);
    }
}
