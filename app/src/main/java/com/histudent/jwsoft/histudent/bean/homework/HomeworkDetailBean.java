package com.histudent.jwsoft.histudent.bean.homework;

import java.util.List;

/**
 * Created by huyg on 2017/10/27.
 */

public class HomeworkDetailBean {
    /**
     * id : 22708729-d271-403d-8b9f-f1c626ddecd0
     * contents : 录音文字图片
     * hasVoice : true
     * hasImage : false
     * hasVideo : false
     * videoId :
     * videoCover :
     * onlyOnline : true
     * subjectId : 3cb1aaf8-c9e4-4ad7-b07a-ad4d3c7732de
     * subjectName : 化学
     * ownerId : 0b41d615-d0ee-44a5-ab3b-46f90ed69c28
     * ownerName : 张悦
     * ownerAvatar : http://img.hitongx.com/UploadFiles/UploadAvatar/20170921/656650e3-fbd7-44b6-bddc-614ff556ad79.jpg@100w_100h_1e_1c_2o_50-1ci.jpg
     * classId : 49a81041-c7f2-470b-92ba-169297073599
     * className : 2017级1班
     * gradeName : 2017级
     * createTime : 2017-10-18 14:41:10
     * timeShow : 2017-10-18
     * imgList : []
     * voiceId : 00000000-0000-0000-0000-000000000000
     * voiceLength : 0
     * isComplete : false
     */

    private String id;
    private String contents;
    private boolean hasVoice;
    private boolean hasImage;
    private boolean hasVideo;
    private String videoId;
    private String videoCover;
    private boolean onlyOnline;
    private String subjectId;
    private String subjectName;
    private String ownerId;
    private String ownerName;
    private String ownerAvatar;
    private String classId;
    private String className;
    private String gradeName;
    private String createTime;
    private String timeShow;
    private String voiceId;
    private String voiceLength;
    private boolean isComplete;
    private List<WorkImgDetailBean> imgList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean isHasVoice() {
        return hasVoice;
    }

    public void setHasVoice(boolean hasVoice) {
        this.hasVoice = hasVoice;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoCover() {
        return videoCover;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
    }

    public boolean isOnlyOnline() {
        return onlyOnline;
    }

    public void setOnlyOnline(boolean onlyOnline) {
        this.onlyOnline = onlyOnline;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAvatar() {
        return ownerAvatar;
    }

    public void setOwnerAvatar(String ownerAvatar) {
        this.ownerAvatar = ownerAvatar;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTimeShow() {
        return timeShow;
    }

    public void setTimeShow(String timeShow) {
        this.timeShow = timeShow;
    }

    public String getVoiceId() {
        return voiceId;
    }

    public void setVoiceId(String voiceId) {
        this.voiceId = voiceId;
    }

    public String getVoiceLength() {
        return voiceLength;
    }

    public void setVoiceLength(String voiceLength) {
        this.voiceLength = voiceLength;
    }

    public boolean isIsComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public List<WorkImgDetailBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<WorkImgDetailBean> imgList) {
        this.imgList = imgList;
    }
}
