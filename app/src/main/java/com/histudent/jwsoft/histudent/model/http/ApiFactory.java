package com.histudent.jwsoft.histudent.model.http;


import com.histudent.jwsoft.histudent.model.http.api.AlbumApi;
import com.histudent.jwsoft.histudent.model.http.api.BlogApi;
import com.histudent.jwsoft.histudent.model.http.api.ClockApi;
import com.histudent.jwsoft.histudent.model.http.api.CommonApi;
import com.histudent.jwsoft.histudent.model.http.api.PhotoApi;
import com.histudent.jwsoft.histudent.model.http.api.SnsApi;
import com.histudent.jwsoft.histudent.model.http.api.WorkApi;

/**
 * Created by huyg on 2017/1/3.
 */

public class ApiFactory {
    private ClockApi mClockApi;
    private BlogApi mBlogApi;
    private SnsApi mSnsApi;
    private PhotoApi mPhotoApi;
    private AlbumApi mAlbumApi;
    private WorkApi mWorkApi;
    private CommonApi mCommonApi;


    public ClockApi getClockApi() {
        return mClockApi;
    }

    public BlogApi getBlogApi(){
        return mBlogApi;
    }

    public SnsApi getSnsApi(){
        return mSnsApi;
    }

    public PhotoApi getPhotoApi() {
        return mPhotoApi;
    }

    public AlbumApi getAlbumApi() {
        return mAlbumApi;
    }

    public WorkApi  getWorkApi(){
        return mWorkApi;
    }

    public CommonApi getCommonApi(){
        return mCommonApi;
    }


    public ApiFactory() {
        mClockApi = RetrofitClient.getInstance().create(ClockApi.class);
        mBlogApi = RetrofitClient.getInstance().create(BlogApi.class);
        mSnsApi = RetrofitClient.getInstance().create(SnsApi.class);
        mPhotoApi = RetrofitClient.getInstance().create(PhotoApi.class);
        mAlbumApi = RetrofitClient.getInstance().create(AlbumApi.class);
        mWorkApi = RetrofitClient.getInstance().create(WorkApi.class);
        mCommonApi = RetrofitClient.getInstance().create(CommonApi.class);
    }

}
