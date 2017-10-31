package com.histudent.jwsoft.histudent.model.http.api;

import com.histudent.jwsoft.histudent.bean.BlogDetailBean;
import com.histudent.jwsoft.histudent.bean.ImageInfoBean;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by huyg on 2017/9/27.
 */

public interface SnsApi {

    @FormUrlEncoded
    @POST("sns/action/praise")
    Observable<BaseHttpResponse> praise(@Field("doOrUndo") boolean doOrUndo,@Field("objectType") int objectType,@Field("objectId") String objectId);


    @FormUrlEncoded
    @POST("sns/action/objectPraiseAndComment")
    Observable<HttpResponse<ImageInfoBean>> objectPraiseAndComment(@Field("objectType") int objectType, @Field("objectId") String objectId);
}
