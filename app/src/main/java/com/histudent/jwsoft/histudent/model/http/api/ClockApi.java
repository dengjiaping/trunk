package com.histudent.jwsoft.histudent.model.http.api;


import com.histudent.jwsoft.histudent.body.homepage.bean.HomePageAllBean;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by huyg on 2017/5/21.
 * 卡号相关操作
 */


public interface ClockApi {

    @FormUrlEncoded
    @POST("readPunch/bookTeacher/createBookTask")
    Observable<BaseHttpResponse> createBookTask(@Field("classId") String classId,
                                                @Field("name") String name,
                                                @Field("memo") String memo,
                                                @Field("startTime") String startTime,
                                                @Field("endTime") String endTime,
                                                @Field("isEveryDay") boolean isEveryDay,
                                                @Field("standardDays") String standardDays);
    @FormUrlEncoded
    @POST("/readPunch/bookTeacher/editBookTask")
    Observable<BaseHttpResponse> editBookTask(@Field("bookTaskId") String bookTaskId,
                                              @Field("name") String name, @Field("memo") String memo,
                                              @Field("endTime") String endTime,
                                              @Field("isEveryDay") boolean isEveryDay,
                                              @Field("standardDays") String standardDays);

}
