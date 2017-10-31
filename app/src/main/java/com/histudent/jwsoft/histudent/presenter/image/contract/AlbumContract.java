package com.histudent.jwsoft.histudent.presenter.image.contract;

import com.histudent.jwsoft.histudent.base.BasePresenter;
import com.histudent.jwsoft.histudent.base.BaseView;
import com.histudent.jwsoft.histudent.body.mine.model.AlbumInfoModel;

import java.util.List;

import retrofit2.http.Field;

/**
 * Created by huyg on 2017/9/28.
 */

public interface AlbumContract {


    interface View extends BaseView{
        void showAlbumList(List<AlbumInfoModel> albumInfoModels);
        void requestError();
    }

    interface Presenter extends BasePresenter<View>{
        void getAlbumList(String ownerId,int ownerType,String categoryId,int pageIndex,int pageSize);
    }
}
