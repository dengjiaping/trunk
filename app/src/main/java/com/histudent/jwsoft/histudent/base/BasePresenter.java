package com.histudent.jwsoft.histudent.base;

/**
 * Created by huyg on 2017/1/3.
 * Presenter 基类
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();
}
