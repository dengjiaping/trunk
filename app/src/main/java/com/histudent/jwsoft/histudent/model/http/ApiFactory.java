package com.histudent.jwsoft.histudent.model.http;


import com.histudent.jwsoft.histudent.model.http.api.AlbumApi;
import com.histudent.jwsoft.histudent.model.http.api.BlogApi;
import com.histudent.jwsoft.histudent.model.http.api.ClassApi;
import com.histudent.jwsoft.histudent.model.http.api.ClockApi;
import com.histudent.jwsoft.histudent.model.http.api.CommonApi;
import com.histudent.jwsoft.histudent.model.http.api.FindApi;
import com.histudent.jwsoft.histudent.model.http.api.HomeApi;
import com.histudent.jwsoft.histudent.model.http.api.MineAPi;
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
    private HomeApi mHomeAPi;
    private ClassApi mClassApi;
    private FindApi mFindApi;
    private MineAPi mMineAPi;


    public ClockApi getClockApi() {
        return mClockApi;
    }

    public BlogApi getBlogApi() {
        return mBlogApi;
    }

    public SnsApi getSnsApi() {
        return mSnsApi;
    }

    public PhotoApi getPhotoApi() {
        return mPhotoApi;
    }

    public AlbumApi getAlbumApi() {
        return mAlbumApi;
    }

    public WorkApi getWorkApi() {
        return mWorkApi;
    }

    public CommonApi getCommonApi() {
        return mCommonApi;
    }

    public HomeApi getHomeAPi() {
        return mHomeAPi;
    }

    public ClassApi getClassApi() {
        return mClassApi;
    }

    public FindApi getFindApi() {
        return mFindApi;
    }

    public MineAPi getMineAPi() {
        return mMineAPi;
    }


    public ApiFactory() {
        final RetrofitClient retrofitClient = RetrofitClient.getInstance();
        mClockApi = retrofitClient.create(ClockApi.class);
        mBlogApi = retrofitClient.create(BlogApi.class);
        mSnsApi = retrofitClient.create(SnsApi.class);
        mPhotoApi = retrofitClient.create(PhotoApi.class);
        mAlbumApi = retrofitClient.create(AlbumApi.class);
        mWorkApi = retrofitClient.create(WorkApi.class);
        mCommonApi = retrofitClient.create(CommonApi.class);
        mHomeAPi = retrofitClient.create(HomeApi.class);
        mClassApi = retrofitClient.create(ClassApi.class);
        mFindApi = retrofitClient.create(FindApi.class);
        mMineAPi = retrofitClient.create(MineAPi.class);
    }

}
