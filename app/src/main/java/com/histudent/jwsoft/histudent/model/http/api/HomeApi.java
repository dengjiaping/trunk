package com.histudent.jwsoft.histudent.model.http.api;

import com.histudent.jwsoft.histudent.model.bean.main.HomePageEntity;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by lichaojie on 2017/11/13.
 * desc:
 */

public interface HomeApi {


    @FormUrlEncoded
    @POST
    Observable<HttpResponse<HomePageEntity>> requestHomeListData(@Url String url, @FieldMap Map<String, Object> map);

}
