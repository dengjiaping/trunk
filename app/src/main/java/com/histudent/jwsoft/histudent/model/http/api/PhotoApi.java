package com.histudent.jwsoft.histudent.model.http.api;

import com.histudent.jwsoft.histudent.model.bean.UploadImgBean;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by huyg on 2017/9/28.
 */

public interface PhotoApi {

    @FormUrlEncoded
    @POST("photo/recentlyList")
    Observable<HttpResponse<UploadImgBean>> recentlyList(@Field("ownerId") String ownerId,
                                                        @Field("ownerType") int ownerType,
                                                        @Field("pageIndex") int pageIndex,
                                                        @Field("pageSize") int pageSize);

}
