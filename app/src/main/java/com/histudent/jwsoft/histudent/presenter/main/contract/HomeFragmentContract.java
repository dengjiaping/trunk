package com.histudent.jwsoft.histudent.presenter.main.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.comment2.bean.AddressInforBean;
import com.histudent.jwsoft.histudent.model.bean.main.HomeEntity;
import com.histudent.jwsoft.histudent.model.bean.main.HomePageEntity;

import java.util.List;

/**
 * Created by lichaojie on 2017/11/13.
 * desc:
 */

public interface HomeFragmentContract {

    interface View extends BaseView {
        void responseSuccess(List<HomeEntity> homePageEntities);
    }

    interface Presenter extends BasePresenter<View> {
        void requestHomeListData(AddressInforBean addressInformation);
    }
}
