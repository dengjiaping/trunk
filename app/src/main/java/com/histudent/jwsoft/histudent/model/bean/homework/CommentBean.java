package com.histudent.jwsoft.histudent.model.bean.homework;

/**
 * Created by huyg on 2017/11/1.
 */

public class CommentBean {
    private String commentId;
    private String commentContent;
    private long sort;
    private boolean isSign;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public long getSort() {
        return sort;
    }

    public void setSort(long sort) {
        this.sort = sort;
    }

    public boolean isSign() {
        return isSign;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }



}
