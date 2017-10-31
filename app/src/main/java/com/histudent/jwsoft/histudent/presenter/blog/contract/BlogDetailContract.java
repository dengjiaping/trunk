package com.histudent.jwsoft.histudent.presenter.blog.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;

/**
 * Created by huyg on 2017/9/11.
 */

public interface BlogDetailContract {


    interface Presenter extends BasePresenter<View>{
        void getBlogDetail(String actId);
    }

    interface View extends BaseView{
        void showBlogDetail(String content);
    }


}
