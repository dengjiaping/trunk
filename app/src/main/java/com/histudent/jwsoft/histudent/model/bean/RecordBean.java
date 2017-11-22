package com.histudent.jwsoft.histudent.model.bean;

/**
 * Created by huyg on 2017/10/25.
 *
 */
//        id (string, optional): 标识 ,
//        homeworkId (string, optional): 作业Id ,
//        userId (string, optional): 学生Id ,
//        userName (string, optional): 学生姓名（冗余） ,
//        userAvatar (string, optional): 学生头像（冗余） ,
//        completeId (string, optional): 完成Id ,
//        completeTime (string, optional): 完成时间 ,
//        isComment (boolean, optional): 是否评论 ,
//        commentTime (string, optional): 评论时间

public class RecordBean {
    private String id;
    private String homeworkId;
    private String userName;
    private String userAvatar;
    private String completeId;
    private String completeTime;
    private boolean isComment;
    private String commentTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getCompleteId() {
        return completeId;
    }

    public void setCompleteId(String completeId) {
        this.completeId = completeId;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public boolean isComment() {
        return isComment;
    }

    public void setComment(boolean comment) {
        isComment = comment;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}
