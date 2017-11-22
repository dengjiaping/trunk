package com.histudent.jwsoft.histudent.model.bean.main;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by lichaojie on 2017/11/14.
 * desc:
 */

public class HomeEntity implements MultiItemEntity {

    //轮播图 type:0
    public List<HomePageEntity.BannerEntity> mBannerEntities;
    //班级 type:1
    public List<HomePageEntity.RecommendClassEntity.SubClassEntity> mSubClassEntities;
    //活跃用户 type:2
    public List<HomePageEntity.ActiveUserEntity.SubActiveUserEntity> mSubActiveUserEntities;
    //热门话题 type:3
    public List<HomePageEntity.HotTopicEntity.SubHotTopicEntity> mSubHotTopicEntities;
    //推荐动态-上传图片 type:4
    public HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity mUploadPhotoEntity;
    //推荐动态-随记 type:5
    public HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity mEssayEntity;
    //推荐动态-日志 type:6
    public HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity mActionLogEntity;

    private int mType;

    @Override
    public int getItemType() {
        return getType();
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public List<HomePageEntity.BannerEntity> getBannerEntities() {
        return mBannerEntities;
    }

    public void setBannerEntities(List<HomePageEntity.BannerEntity> bannerEntities) {
        mBannerEntities = bannerEntities;
    }

    public List<HomePageEntity.RecommendClassEntity.SubClassEntity> getSubClassEntities() {
        return mSubClassEntities;
    }

    public void setSubClassEntities(List<HomePageEntity.RecommendClassEntity.SubClassEntity> subClassEntities) {
        mSubClassEntities = subClassEntities;
    }

    public List<HomePageEntity.ActiveUserEntity.SubActiveUserEntity> getSubActiveUserEntities() {
        return mSubActiveUserEntities;
    }

    public void setSubActiveUserEntities(List<HomePageEntity.ActiveUserEntity.SubActiveUserEntity> subActiveUserEntities) {
        mSubActiveUserEntities = subActiveUserEntities;
    }

    public List<HomePageEntity.HotTopicEntity.SubHotTopicEntity> getSubHotTopicEntities() {
        return mSubHotTopicEntities;
    }

    public void setSubHotTopicEntities(List<HomePageEntity.HotTopicEntity.SubHotTopicEntity> subHotTopicEntities) {
        mSubHotTopicEntities = subHotTopicEntities;
    }

    public HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity getUploadPhotoEntity() {
        return mUploadPhotoEntity;
    }

    public void setUploadPhotoEntity(HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity uploadPhotoEntity) {
        mUploadPhotoEntity = uploadPhotoEntity;
    }

    public HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity getEssayEntity() {
        return mEssayEntity;
    }

    public void setEssayEntity(HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity essayEntity) {
        mEssayEntity = essayEntity;
    }

    public HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity getActionLogEntity() {
        return mActionLogEntity;
    }

    public void setActionLogEntity(HomePageEntity.RecommendDynamicEntity.SubRecommendDynamicEntity actionLogEntity) {
        mActionLogEntity = actionLogEntity;
    }
}
