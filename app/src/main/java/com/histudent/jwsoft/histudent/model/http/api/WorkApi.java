package com.histudent.jwsoft.histudent.model.http.api;

import android.icu.text.AlphabeticIndex;

import com.histudent.jwsoft.histudent.bean.RecordBean;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkDetailBean;
import com.histudent.jwsoft.histudent.bean.homework.WorkCompleteBean;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by huyg on 2017/10/25.
 * 班级作业
 */

public interface WorkApi {

    /**
     * 创建作业
     * @return
     */
    @Multipart
    @POST("homework/homeWorkAddBatch")
    Observable<BaseHttpResponse> createHomeWork(@Part List<MultipartBody.Part> parts);

    /**
     * 作业完成详细列表
     * @param homeworkId 作业id
     * @param isComplete 是否完成
     * @param index
     * @param size
     * @return
     */
    @FormUrlEncoded
    @POST("homework/teacher/completeList")
    Observable<HttpResponse<WorkCompleteBean>> getCompleteList(@Field("homeworkId") String homeworkId,
                                                               @Field("isCompleted") boolean isComplete,
                                                               @Field("pageIndex") int index,
                                                               @Field("pageSize") int size);

    /**
     * 作业完成详细情况
     * @param completeId
     * @return
     */
    @FormUrlEncoded
    @POST("homework/teacher/completeDetailed")
    Observable<BaseHttpResponse> getCompleteDetail(@Field("completeId") String completeId);

    /**
     * 评价完成的作业
     * @param completeId 作业id
     * @param commentContent 评论内容
     * @param haveImg  是否有图片
     * @param haveVoice 是否有语音
     * @param voiceId 语音Id
     * @param voiceLength 语音长度
     * @return
     */
    @FormUrlEncoded
    @POST("homework/teacher/comment")
    Observable<BaseHttpResponse> commentHomework(@Field("completeId") String completeId,
                                                 @Field("commentContent") String commentContent,
                                                 @Field("haveImg") boolean haveImg,
                                                 @Field("haveVoice") boolean haveVoice,
                                                 @Field("voiceId") String voiceId,
                                                 @Field("voiceLength") String voiceLength);

    /**
     * 完成作业/修改完成作业(需要在线提交作业)
     * @param homeworkId 作业id
     * @param content 内容
     * @param haveImg 是否有图片
     * @param haveVoice 是否有语音
     * @param voiceId 语音Id
     * @param voiceLength 语音长度
     * @param videoIds 视频Id
     * @param uploadImgs 图片中已经上传的id集合
     * @return
     */
    @FormUrlEncoded
    @POST("homework/student/complete")
    Observable<BaseHttpResponse> completeHomework(@Field("homeworkId") String homeworkId,
                                                  @Field("content") String content,
                                                  @Field("haveImg") boolean haveImg,
                                                  @Field("haveVoice") boolean haveVoice,
                                                  @Field("voiceId") String voiceId,
                                                  @Field("voiceLength") String voiceLength,
                                                  @Field("videoIds") String videoIds,
                                                  @Field("uploadImgs") String uploadImgs);

    /**
     * 完成作业接口(不需要在线提交作业)
     * @param homeworkId 作业id
     * @return
     */
    @FormUrlEncoded
    @POST("homework/student/finishHomeWork")
    Observable<BaseHttpResponse> finishHomework(@Field("homeworkId") String homeworkId);


    @FormUrlEncoded
    @POST("homework/homeWorkDetailed")
    Observable<HttpResponse<HomeworkDetailBean>> getHomeworkDetail(@Field("hwId") String homeworkId);


}
