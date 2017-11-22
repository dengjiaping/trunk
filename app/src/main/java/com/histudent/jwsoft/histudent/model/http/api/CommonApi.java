package com.histudent.jwsoft.histudent.model.http.api;

import com.histudent.jwsoft.histudent.model.bean.UploadAuthBean;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by huyg on 2017/10/26.
 */

public interface CommonApi {
    @FormUrlEncoded
    @POST("user/cloudAcl/getVodUploadAuth")
    Observable<HttpResponse<UploadAuthBean>> getVodUploadAuth(@Field("fileSize") long fileSize);

    @FormUrlEncoded
    @POST("file/downloadfile")
    Observable<ResponseBody> downloadFile(@Field("fileId") String fileId);
}
