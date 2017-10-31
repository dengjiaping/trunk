package com.histudent.jwsoft.histudent.presenter.image.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.body.mine.model.PhotosModel;

import java.util.List;

/**
 * Created by huyg on 2017/9/28.
 */

public interface UploadContract {

    interface View extends BaseView {
        void showRecentlyList(List<PhotosModel> photosModels);
        void requestError();
    }

    interface Presenter extends BasePresenter<View>{
        void getRecentlyList(String ownerId,int ownerType,int pageIndex,int pageSize);
    }
}
