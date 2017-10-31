package com.histudent.jwsoft.histudent.commen.utils;
import com.histudent.jwsoft.histudent.comment2.bean.CommentBean;
import com.histudent.jwsoft.histudent.body.hiworld.adapter.CommentAdpater;

import java.util.List;

/**
 * 保存评论的内容
 * Created by ZhangYT on 2016/7/14.
 */
public class TmpData {
    private CommentAdpater adpater;
    private static TmpData tmpData;
    private List<CommentBean> list;

    private TmpData() {
    }


    //返回获取数据的对象
    public static TmpData getTmpDataInstance() {
        if (tmpData == null) {
            tmpData = new TmpData();
        }
        return tmpData;
    }

    //设置评论的内容的适配器
    public void setCommentAdapter(CommentAdpater adpater1) {
        adpater = adpater1;
    }



    //设置评论集合
    public void setCommentList(List<CommentBean>list){
        this.list=list;

    }



    //返回评论集合
    public List<CommentBean> getCommentList(){
        return this.list;
    }


    //返回评论适配器
    public CommentAdpater getCommentAapter() {
        return this.adpater;
    }
}
