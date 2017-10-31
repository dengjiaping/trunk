package com.histudent.jwsoft.histudent.presenter.image.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.bean.ImageInfoBean;

/**
 * Created by huyg on 2017/9/12.
 */

public interface ShowImageContract {


    interface View extends BaseView {
        void getPhotoInfo();
        void showPraiseInfo(ImageInfoBean imageInfoBean);
    }


    interface Presenter extends BasePresenter<View>{
        void praiseImg(boolean doOrUndo,int objectType,String objectId);
        void getImgPraiseInfo(int objectType,String objectId);
    }


}
