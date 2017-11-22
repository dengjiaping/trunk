package com.histudent.jwsoft.histudent.model.constant;

import okhttp3.MediaType;

/**
 * Created by huyg on 2017/6/30.
 */

public class Const {
    public static final String CLASS_NOTICE_ADD = "AddClassNotice";
    public static final String HOMEWORK_ASSIGN = "AssignHomework";
    public static final String IM_BUILD = "BuildIm";
    public static final String CLASS_ACTIVITY_RECOMMEND = "ClassActivityRecommend";
    public static final String CLASS_MEMBER_ADD = "ClassMemberAdd";
    public static final String CLASS_MEMBER_INVITE = "ClassMemberInvite";
    public static final String CLASS_HUODONG_CREATE = "CreateClassHuodong";
    public static final String CLASS_HUODONG_TRACK_CREATE = "CreateClassHuodongTrack";
    public static final String CLASS_DAY_ACTIVITY_SYNCHRONOUS = "DayActivitySynchronousClass";


    public static final String CLASS_NOTICE_ADD_FIRST = "FirstAddClassNotice";
    public static final String HOMEWORK_ASSIGN_FRIST = "FirstAssignHomework";
    public static final String CLASS_BLOG_SYNCHRONOUS_FIRST = "FirstBlogSynchronousClass";
    public static final String IM_BUILD_FIRST = "FirstBuildIm";
    public static final String CLASS_ACTIVITY_RECOMMEND_FIRST = "FirstClassActivityRecommend";
    public static final String CLASS_ACTIVITY_SHARE_FRIST = "FirstClassActivityShare";
    public static final String CLASS_ACTIVITY_SHAIELD_FIRST = "FirstClassActivityShield";
    public static final String CLASS_ALBUM_CREATE_FIST = "FirstCreateClassAlbum";
    public static final String CLASS_HUODONG_CREATE_FIRST = "FirstCreateClassHuodong";
    public static final String CLASS_MICROBLOG_SYNCHRONOUS_FRIST = "FirstMicroBlogSynchronousClass";
    public static final String CLASS_PHOTO_SYNCHRONOUS_FIRST = "FirstPhotoSynchronousClass";
    public static final String CLASS_HUODONG_SHARE_FIRST = "FirstShareClassHuodong";
    public static final String CLASS_ACTIVITIES_VIEW = "ViewClassActivities";


    //通知类型
    public static final int NOTICE_TYPE_MIME = 6;
    public static final int NOTICE_TYPE_COMMENT = 5;
    public static final int NOTICE_TYPE_PRAISE = 7;
    public static final int NOTICE_TYPE_SYSTEM = 8;


    //图片裁剪相关
    public static final String AUTHORITIES_SIT = "com.histudent.jwsoft.histudent.fileprovider.sit";
    public static final String AUTHORITIES_UAT = "com.histudent.jwsoft.histudent.fileprovider.uat";



    //发布作业类型，文字，视频，语音，照片
    public static final int WORK_TEXT = 0;
    public static final int WORK_VEDIO = 1;
    public static final int WORK_VOICE = 2;
    public static final int WORK_PHOTO = 3;

    //录音状态
    public static final int SUCCESS = 0;
    public static final int FAIL = -1;
    public static final int READY = 1;
    public static final int START = 2;
    public static final int CANCEL = 3;
    public static final int MAX = 4;

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

}
