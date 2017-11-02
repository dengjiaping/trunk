package com.histudent.jwsoft.histudent.model.http.api;

import android.icu.text.AlphabeticIndex;

import com.histudent.jwsoft.histudent.bean.RecordBean;
import com.histudent.jwsoft.histudent.bean.homework.AddHomeworkSubjectEntity;
import com.histudent.jwsoft.histudent.bean.homework.AlreadyCompleteHomeworkEntity;
import com.histudent.jwsoft.histudent.bean.homework.ClassMemberEntity;
import com.histudent.jwsoft.histudent.bean.homework.CommentBean;
import com.histudent.jwsoft.histudent.bean.homework.CompleteDetailBean;
import com.histudent.jwsoft.histudent.bean.homework.GroupMemberDetailEntity;
import com.histudent.jwsoft.histudent.bean.homework.SelectReceiverPersonEntity;
import com.histudent.jwsoft.histudent.bean.homework.SubjectEntity;
import com.histudent.jwsoft.histudent.bean.homework.HomeworkDetailBean;
import com.histudent.jwsoft.histudent.bean.homework.WorkCompleteBean;
import com.histudent.jwsoft.histudent.model.http.BaseHttpResponse;
import com.histudent.jwsoft.histudent.model.http.HttpResponse;
import com.netease.nimlib.sdk.Observer;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

/**
 * Created by huyg on 2017/10/25.
 * 班级作业
 */

public interface WorkApi {

    /**
     * 创建作业
     *
     * @return
     */
    @Multipart
    @POST("homework/homeWorkAddBatch")
    Observable<BaseHttpResponse> createHomeWork(@Part List<MultipartBody.Part> parts);


    @FormUrlEncoded
    @POST("homework/homeWorkDetailed")
    Observable<HttpResponse<HomeworkDetailBean>> getHomeworkDetail(@Field("hwId") String homework);

    /**
     * 作业完成详细列表
     *
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
     *
     * @param completeId
     * @return
     */
    @FormUrlEncoded
    @POST("homework/teacher/completeDetailed")
    Observable<BaseHttpResponse> getCompleteDetail(@Field("completeId") String completeId);


    @FormUrlEncoded
    @POST("homework/singleNotice")
    Observable<BaseHttpResponse> singleNotice(@Field("hwId") String homeworkId,@Field("userId") String userId,@Field("noticeType") int type);

    /**
     * 评价完成的作业
     *
     * @param completeId     作业id
     * @param commentContent 评论内容
     * @param haveImg        是否有图片
     * @param haveVoice      是否有语音
     * @param voiceId        语音Id
     * @param voiceLength    语音长度
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

    @FormUrlEncoded
    @POST("homework/teacher/commentProposalList")
    Observable<HttpResponse<List<CommentBean>>> commentProposalList(@Field("completeId") String completeId);

    /**
     * 完成作业/修改完成作业(需要在线提交作业)
     *
     * @return
     */
    @Multipart
    @POST("homework/student/complete")
    Observable<BaseHttpResponse> completeHomework(@Part List<MultipartBody.Part> parts);

    /**
     * 完成作业接口(不需要在线提交作业)
     *
     * @param homeworkId 作业id
     * @return
     */
    @FormUrlEncoded
    @POST("homework/student/finishHomeWork")
    Observable<BaseHttpResponse> finishHomework(@Field("homeworkId") String homeworkId);

    /**
     * 作业完成详情
     *
     * @param homeworkId 作业id
     * @return
     */
    @FormUrlEncoded
    @POST("homework/completeDetailed")
    Observable<HttpResponse<CompleteDetailBean>> completeDetailed(@Field("homeworkId") String homeworkId, @Field("studentId") String studentId);



    /**
     * 获取小组成员详细列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/homework/teamMember/list")
    Observable<HttpResponse<GroupMemberDetailEntity>> getMemberDetailList(@FieldMap Map<String, Object> map);

    /**
     * 删除小组成员详细列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/homework/team/del")
    Observable<HttpResponse<String>> delMemberDetailList(@FieldMap Map<String, Object> map);


    /**
     * 获取该班级下所有成员列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/class/classMembers")
    Observable<HttpResponse<ClassMemberEntity>> getClassMembersList(@FieldMap Map<String, Object> map);

    /**
     * 获取所有科目列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/homework/subject/list")
    Observable<HttpResponse<List<SubjectEntity>>> getAllSubjectsList(@FieldMap Map<String, Object> map);

    /**
     * 添加指定下的科目
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/homework/subject/add")
    Observable<HttpResponse<AddHomeworkSubjectEntity>> addSpecifiedSubject(@FieldMap Map<String, Object> map);

    /**
     * 删除指定下的科目
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/homework/subject/del")
    Observable<BaseHttpResponse> deleteSpecifiedSubject(@FieldMap Map<String, Object> map);

    /**
     * 获取班级下所有分组成员列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/homework/team/list")
    Observable<HttpResponse<SelectReceiverPersonEntity>> getSelectReceiverList(@FieldMap Map<String, Object> map);


    /**
     * 修改小组
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/homework/team/edit")
    Observable<BaseHttpResponse> modifyGroupInformation(@FieldMap Map<String, Object> map);

    /**
     * 添加小组
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/homework/team/add")
    Observable<BaseHttpResponse> addGroupInformation(@FieldMap Map<String, Object> map);


    /**
     * 获取作业列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/homework/homeWorkListNew")
    Observable<HttpResponse<AlreadyCompleteHomeworkEntity>> getAlreadyCompleteHomeworkList(@FieldMap Map<String, Object> map);

    /**
     * 获取全部作业列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/homework/homeWorkAllListNew")
    Observable<HttpResponse<AlreadyCompleteHomeworkEntity>> getAlreadyCompleteAllHomeworkList(@FieldMap Map<String, Object> map);

    /**
     * 删除作业
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/homework/homeWorkDelete")
    Observable<BaseHttpResponse> deleteCompleteSpecifiedHomework(@FieldMap Map<String, Object> map);

}
