package com.histudent.jwsoft.histudent.model.http.api;

import com.histudent.jwsoft.histudent.bean.BlogDetailBean;
import com.histudent.jwsoft.histudent.body.homepage.bean.HomePageAllBean;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by huyg on 2017/9/11.
 */

public interface BlogApi {

    @FormUrlEncoded
    @POST("sharePages/timeline/activityInfo")
    Observable<HttpResponse<BlogDetailBean>> getBlogDetail(@Field("actId") String actId);
}
