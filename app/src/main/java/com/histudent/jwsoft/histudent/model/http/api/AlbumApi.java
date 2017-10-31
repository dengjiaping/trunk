package com.histudent.jwsoft.histudent.model.http.api;

import com.histudent.jwsoft.histudent.bean.AlbumBean;
import com.histudent.jwsoft.histudent.body.mine.model.AlbumInfoModel;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by huyg on 2017/9/28.
 */

public interface AlbumApi {


    @FormUrlEncoded
    @POST("album/list")
    Observable<HttpResponse<AlbumBean>> getAlbumList(@Field("ownerId") String ownerId,
                                                     @Field("ownerType") int ownerType,
                                                     @Field("categoryid") String categoryId,
                                                     @Field("pageIndex") int pageIndex,
                                                     @Field("pageSize") int pageSize);
}
