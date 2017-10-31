package com.histudent.jwsoft.histudent.body.mine.model;

/**
 * Created by liuguiyu-pc on 2016/11/29.
 */

public class RankListModel {


    /**
     * rank : 1
     * rankImg : http://hitongxue.oss-cn-hangzhou.aliyuncs.com/UserLevel/Lv.1.png
     * pointLower : 0
     */

    private int rank;
    private String rankImg;
    private int pointLower;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getRankImg() {
        return rankImg;
    }

    public void setRankImg(String rankImg) {
        this.rankImg = rankImg;
    }

    public int getPointLower() {
        return pointLower;
    }

    public void setPointLower(int pointLower) {
        this.pointLower = pointLower;
    }
}
